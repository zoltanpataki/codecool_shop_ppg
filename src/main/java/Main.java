import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {
    private static String categoryId;
    private static String supplierId;

    public static void main(String[] args) {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);
        populateData();

        get("/", (Request req, Response res) -> {
            String id = "1";
            String type = "category";
            CartDao cartDataStore = CartDaoMem.getInstance();
            return new ThymeleafTemplateEngine().render(ProductController.renderProductsPerCategory(req, res, id, type, cartDataStore.getPrice(), cartDataStore.getAmount()));
        });

        get("/category", (Request req, Response res) -> {
            try {
                categoryId = req.queryParams("category");
                supplierId = null;
                String type = "category";
                CartDao cartDataStore = CartDaoMem.getInstance();
                return new ThymeleafTemplateEngine().render(ProductController.renderProductsPerCategory(req, res, categoryId, type, cartDataStore.getPrice(), cartDataStore.getAmount()));
            } catch (DaoRecordNotFoundException e) {
                res.status(404);
                return new ThymeleafTemplateEngine().render(ProductController.renderErrorPage(req, res));
            }
        });

        get("/supplier", (Request req, Response res) -> {
            try {
                supplierId = req.queryParams("supplier");
                categoryId = null;
                String type = "supplier";
                CartDao cartDataStore = CartDaoMem.getInstance();
                return new ThymeleafTemplateEngine().render(ProductController.renderProductsPerSupplier(req, res, supplierId, type, cartDataStore.getPrice(), cartDataStore.getAmount()));
            } catch (DaoRecordNotFoundException e) {
                res.status(404);
                return new ThymeleafTemplateEngine().render(ProductController.renderErrorPage(req, res));
            }
        });

        post("/addToCart", (Request req, Response res) -> {
            String quantity = req.queryParams("quantity");
            String Id = req.queryParams("productId");
            CartDao cartDataStore = CartDaoMem.getInstance();
            ProductDao productDataStore = ProductDaoSql.getInstance();
            Product product = productDataStore.find(parseInt(Id));
            LineItem lineItem = new LineItem(Integer.parseInt(Id), product.getName(), parseInt(quantity), product.getDefaultPrice());
            cartDataStore.add(lineItem);
            ProductController.setPriceAndAmount(cartDataStore);
            if (categoryId == null && supplierId != null) {
                res.redirect("/supplier?supplier=" + supplierId);
            } else if (supplierId == null && categoryId != null) {
                res.redirect("/category?category=" + categoryId);
            } else {
                res.redirect("/");
            }
            return null;
        });

        get("/cart", (Request req, Response res) -> {
            CartDao cartDataStore = CartDaoMem.getInstance();
            return new ThymeleafTemplateEngine().render(ProductController.renderCartForm(req, res, cartDataStore.getPrice(), cartDataStore.getAmount()));
        });

        get("/registration_page", (Request req, Response res) -> new ThymeleafTemplateEngine().render(ProductController.renderRegisterPage(req, res)));

        get("/login_page", (Request req, Response res) -> new ThymeleafTemplateEngine().render(ProductController.renderLoginPage(req, res)));

        get("/itemDecrease/:lineItemId", (Request req, Response res) -> {
            String lineItemId = req.params(":lineItemId");
            CartDao cartDataStore = CartDaoMem.getInstance();
            LineItem lineItem;
            for (int i = 0; i < cartDataStore.getAll().size(); i++) {
                if (cartDataStore.getAll().get(i).getLineItemId() == Integer.parseInt(lineItemId)) {
                    lineItem = cartDataStore.getAll().get(i);
                    lineItem.setQuantity(lineItem.getQuantity() - 1);
                    ProductController.setPriceAndAmount(cartDataStore);
                    if (lineItem.getQuantity() == 0) {
                        cartDataStore.removeLineItem(lineItem);
                    }
                    break;
                }

            }
            res.redirect("/cart");
            return null;
        });

        get("/itemIncrease/:lineItemId", (Request req, Response res) -> {
            String lineItemId = req.params(":lineItemId");
            CartDao cartDataStore = CartDaoMem.getInstance();
            LineItem lineItem;
            for (int i = 0; i < cartDataStore.getAll().size(); i++) {
                if (cartDataStore.getAll().get(i).getLineItemId() == Integer.parseInt(lineItemId)) {
                    lineItem = cartDataStore.getAll().get(i);
                    lineItem.setQuantity(lineItem.getQuantity() + 1);
                    ProductController.setPriceAndAmount(cartDataStore);
                    break;
                }

            }
            res.redirect("/cart");
            return null;
        });

        get("/delete/:lineItemId", (Request req, Response res) -> {
            String lineItemId = req.params(":lineItemId");
            CartDao cartDataStore = CartDaoMem.getInstance();
            LineItem lineItem;
            for (int i = 0; i < cartDataStore.getAll().size(); i++) {
                if (cartDataStore.getAll().get(i).getLineItemId() == Integer.parseInt(lineItemId)) {
                    lineItem = cartDataStore.getAll().get(i);
                    cartDataStore.removeLineItem(lineItem);
                    ProductController.setPriceAndAmount(cartDataStore);
                    break;
                }
            }
            res.redirect("/cart");
            return null;
        });

        get("/checkout", (Request req, Response res) -> {
            CartDao cartDataStore = CartDaoMem.getInstance();
            OrderDao orderDataStore = OrderDaoSql.getInstance();
            OrderDataDao orderedItems = OrderDataDaoSql.getInstance();
            Order order = new Order(req.session().attribute("userId"), "checked");
            order.setLineItems(cartDataStore.getAll());
            orderDataStore.add(order);
            int orderId = orderDataStore.generateNewOrderId();
            System.out.println("oid: " + orderId);
            for (int i = 0; i < order.getLineItems().size(); i++) {
                orderedItems.add(order.getLineItems().get(i), orderId);
            }
            return new ThymeleafTemplateEngine().render(ProductController.renderCheckoutForm(req, res, orderId, cartDataStore.getPrice(), cartDataStore.getAmount()));
        });

        post("/payment", (Request req, Response res) -> {
            int orderId = Integer.parseInt(req.queryParams("orderId"));
            OrderDao orderDataStore = OrderDaoSql.getInstance();
            Order order = orderDataStore.find(orderId);
            orderDataStore.updateStatus(orderId, "confirmed");
            OrderDataDao orderedItems = OrderDataDaoSql.getInstance();
            order.getUserData().setUserName(req.queryParams("name"));
            order.getUserData().seteMail(req.queryParams("email_address"));
            order.getUserData().setPhoneNumber(req.queryParams("phone_number"));
            order.getUserData().setBillingCountry(req.queryParams("billCountry"));
            order.getUserData().setBillingCity(req.queryParams("billCity"));
            order.getUserData().setBillingAddress(req.queryParams("billAddress"));
            order.getUserData().setBillingZIPCode(req.queryParams("billZip_code"));
            order.getUserData().setShippingCountry(req.queryParams("shipCountry"));
            order.getUserData().setShippingCity(req.queryParams("shipCity"));
            order.getUserData().setShippingAddress(req.queryParams("shipAddress"));
            order.getUserData().setShippingZIPCode(req.queryParams("shipZip_code"));
            return new ThymeleafTemplateEngine().render(ProductController.renderPaymentForm(orderId, req, res, orderedItems.getPrice(orderId), order.getAmount()));
        });

        post("/success", (Request req, Response res) -> {
            int orderId = Integer.parseInt(req.queryParams("orderId"));
            OrderDao orderDataStore = OrderDaoSql.getInstance();
            orderDataStore.updateStatus(orderId, "paid");
            return new ThymeleafTemplateEngine().render(ProductController.renderSuccessPage(req, res));
        });

        post("/register_user", (Request req, Response res) -> {
            User user = new User();
            user.setUserName(req.queryParams("username"));
            user.setEmail(req.queryParams("email"));
            user.setPassword(req.queryParams("password"));
            UserDao userData = UserDaoSQL.getInstance();
            userData.addUser(user);
            EmailSender.sender(req.queryParams("email"), req.queryParams("username"));
            res.redirect("/");
            return "";
        });

        get("/username", "application/json", (Request req, Response res) -> {
            UserDao userDataAccess = UserDaoSQL.getInstance();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username_exist", userDataAccess.checkUserExists(req.queryParams("username")));
            return jsonObject;
        });

        get("/email", "application/json", (Request req, Response res) -> {
            UserDao userDataAccess = UserDaoSQL.getInstance();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email_exist", userDataAccess.checkEmailExists((req.queryParams("email"))));
            return jsonObject;
        });

        post("/login", (Request req, Response res) -> {
            User user = new User();
            user.setUserName(req.queryParams("username"));
            user.setPassword(req.queryParams("password"));
            System.out.println("pw " + req.queryParams("password"));
            UserDao userData = UserDaoSQL.getInstance();
            if (userData.isCredentialsValid(user)) {
                req.session(true);// create and return session
                req.session().attribute("username", req.queryParams("username"));
                req.session().attribute("userId", userData.getId(req.session().attribute("username")));
                req.session(ProductController.isLoggedIn = true);
                ProductController.username = req.session().attribute("username");
                res.redirect("/");
                return "";
            } else {
                res.redirect("/login_page");
                return "";
            }
        });

        get("/logout", (Request req, Response res) -> {
            req.session().removeAttribute("username");
            req.session().removeAttribute("userId");
            ProductController.isLoggedIn = false;
            ProductController.username = "";
            res.redirect("/");
            return "";
        });

        exception(DaoRecordNotFoundException.class, (Exception exception, Request req, Response res) -> {
            res.status(404);
            res.body(new ThymeleafTemplateEngine().render(ProductController.renderErrorPage(req, res)));
        });

        exception(DaoException.class, (Exception exception, Request req, Response res) -> {
            res.status(500);
            res.body("<html><body><center></br><h1>500 Internal Server Error :(</h1></br><h2>The Powerpuff Girls are working on it!<h2></center></body></html>");
        });

        enableDebugScreen();

    }

    private static void populateData() {
        ProductDaoSql.getInstance();
        ProductCategoryDaoSql.getInstance();
    }
}



