package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoSql implements ProductDao {
    private static ProductDaoSql instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoSql() {
    }

    public static ProductDaoSql getInstance() {
        if (instance == null) {
            instance = new ProductDaoSql();
        }
        return instance;
    }

    @Override
    public void add(Product product) throws DaoException {
        String query = "INSERT INTO product(category_id, supplier_id, title, price, description) VALUES (?, ?, ?, ?, ?);";
        executeQuery(query, product);
    }

    @Override
    public Product find(int id) throws DaoException {

        String query = "SELECT product.id AS productId, product.title, product.price, product.description AS pdesc,category.id AS categoryId, category.name, department, category.description AS cdesc,supplier.id AS supplierId, supplier.name AS sname, supplier.description AS sdesc FROM product " +
                "INNER JOIN supplier ON (supplier.id = product.supplier_id) " +
                "INNER JOIN category ON (category.id = product.category_id) " +
                "WHERE product.id = ?;";
        return getProduct(query, id);
    }

    @Override
    public void remove(int id) throws DaoException {
        String query = "DELETE FROM product WHERE id = ?;";
        executeQuery(query, id);
    }

    @Override
    public List<Product> getAll() throws DaoException {
        String query = "SELECT product.id AS productId ,product.title, product.price, product.description AS pdesc,category.id AS categoryId, category.name, department, category.description AS cdesc,supplier.id AS supplierId, supplier.name AS sname, supplier.description AS sdesc FROM product " +
                "INNER JOIN supplier ON (supplier.id = product.supplier_id) " +
                "INNER JOIN category ON (category.id = product.category_id);";
        return createProductList(query);
    }

    @Override
    public List<Product> getBy(Supplier supplier) throws DaoException {
        String supplierName = supplier.getName();
        String query = "SELECT product.id AS productId, product.title, product.price, product.description AS pdesc,category.id AS categoryId, category.name, department, category.description AS cdesc,supplier.id AS supplierId, supplier.name AS sname, supplier.description AS sdesc FROM product " +
                "INNER JOIN supplier ON (supplier.id = product.supplier_id) " +
                "INNER JOIN category ON (category.id = product.category_id) " +
                "WHERE supplier.name = ?;";
        return createProductList(query, supplierName);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) throws DaoException {
        String productCategoryName = productCategory.getName();
        String query = "SELECT product.id AS productId , product.title, product.price, product.description AS pdesc,category.id AS categoryId, category.name, department, category.description AS cdesc,supplier.id AS supplierId, supplier.name AS sname, supplier.description AS sdesc FROM product " +
                "INNER JOIN supplier ON (supplier.id = product.supplier_id) " +
                "INNER JOIN category ON (category.id = product.category_id) " +
                "WHERE category.name = ?;";
        return createProductList(query, productCategoryName);
    }

    private void executeQuery(String query, int id) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            statement.execute();
            if (statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find product with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, Product product) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, product.getProductCategory().getId());
            statement.setInt(2, product.getSupplier().getId());
            statement.setString(3, product.getName());
            statement.setFloat(4, product.getDefaultPrice());
            statement.setString(5, product.getDescription());

            statement.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<Product> createProductList(String query) throws DaoException {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);

        ) {
            while (resultSet.next()) {
                Product result = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("title"),
                        resultSet.getFloat("price"), "USD",
                        resultSet.getString("pdesc"),
                        (new ProductCategory(
                                resultSet.getInt("categoryId"),
                                resultSet.getString("name"),
                                resultSet.getString("department"),
                                resultSet.getString("cdesc"))),
                        new Supplier(
                                resultSet.getInt("supplierId"),
                                resultSet.getString("sname"),
                                resultSet.getString("sdesc")));
                productList.add(result);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return productList;
    }

    private List<Product> createProductList(String query, String supplierOrProductCategoryName) throws DaoException {
        List<Product> productList = new ArrayList<>();
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplierOrProductCategoryName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product result = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("title"),
                        resultSet.getFloat("price"), "USD",
                        resultSet.getString("pdesc"),
                        (new ProductCategory(
                                resultSet.getInt("categoryId"),
                                resultSet.getString("name"),
                                resultSet.getString("department"),
                                resultSet.getString("cdesc"))),
                        new Supplier(
                                resultSet.getInt("supplierId"),
                                resultSet.getString("sname"),
                                resultSet.getString("sdesc")));
                productList.add(result);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return productList;
    }

    private Product getProduct(String query, int id) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product result = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("title"),
                        resultSet.getFloat("price"), "USD",
                        resultSet.getString("pdesc"),
                        (new ProductCategory(
                                resultSet.getInt("categoryId"),
                                resultSet.getString("name"),
                                resultSet.getString("department"),
                                resultSet.getString("cdesc"))),
                        new Supplier(
                                resultSet.getInt("supplierId"),
                                resultSet.getString("sname"),
                                resultSet.getString("sdesc")));
                return result;
            } else {
                throw new DaoRecordNotFoundException("couldn't find product with id: " + id);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());

        }
    }
}
