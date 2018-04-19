package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.OrderDataDao;
import com.codecool.shop.model.LineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDataDaoSql implements OrderDataDao {

    private static OrderDataDaoSql instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDataDaoSql() {
    }

    public static OrderDataDaoSql getInstance() {
        if (instance == null) {
            instance = new OrderDataDaoSql();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem, int orderId) throws DaoException {
        String query = "INSERT INTO order_data (order_id, product_id, name, quantity, unit_price) VALUES (?, ?, ?, ?, ?);";
        executeQuery(query, lineItem, orderId);
    }

    public int getPrice(int orderId) throws DaoException {
        String query = "SELECT SUM(quantity * unit_price) FROM order_data WHERE order_id = ?;";
        return findOrderPrice(query, orderId);
    }

    @Override
    public LineItem find(int productId) throws DaoException {
        String query = "SELECT * FROM order_data WHERE product_id = ?;";
        return getLineItem(query, productId);
    }

    @Override
    public void remove(int id) throws DaoException {
        String query = "DELETE FROM order_data WHERE id = ?;";
        executeQuery(query, id);
    }

    @Override
    public List<LineItem> getAll() throws DaoException {
        String query = "SELECT * FROM order_data;";
        return getLineItems(query);
    }

    private void executeQuery(String query, int id) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            statement.execute(query);
            if (!statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find lineitem with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private int findOrderPrice(String query, int orderId) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("sum");
            } else {
                throw new DaoRecordNotFoundException("couldn't find order with id: " + orderId);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private LineItem getLineItem(String query, int productId) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LineItem result = new LineItem(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("unit_price"));

                return result;
            } else {
                throw new DaoRecordNotFoundException("couldn't find product with id: " + productId);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, LineItem lineItem, int orderId) throws DaoException{
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, orderId);
            statement.setInt(2, lineItem.getLineItemId());
            statement.setString(3, lineItem.getProductName());
            statement.setInt(4, lineItem.getQuantity());
            statement.setFloat(5, lineItem.getDefaultPrice());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<LineItem> getLineItems(String query) throws DaoException {
        List<LineItem> lineItemList = new ArrayList<>();

        try {
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                LineItem result = new LineItem(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("unit_price"));
                lineItemList.add(result);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return lineItemList;
    }
}
