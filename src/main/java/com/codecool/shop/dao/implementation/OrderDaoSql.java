package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoSql implements OrderDao {
    private static OrderDaoSql instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoSql() {
    }

    public static OrderDaoSql getInstance() {
        if (instance == null) {
            instance = new OrderDaoSql();
        }
        return instance;
    }

    @Override
    public void add(Order order) throws DaoException {
        String query = "INSERT INTO orders (user_id, status, order_date) VALUES (?, ?, ?);";
        executeQuery(query, order);
    }

    public int generateNewOrderId() throws DaoException {
        String query = "SELECT id FROM orders ORDER BY id DESC LIMIT 1;";
        return getOrderId(query);
    }

    public void updateStatus(int orderId, String status) throws DaoException {
        String query = "UPDATE orders SET status =  ?  WHERE id = ? ;";
        executeQuery(query, orderId, status);
    }

    @Override
    public Order find(int id) throws DaoException {
        String query = "SELECT * FROM orders WHERE id = ?;";
        return getOrder(query, id);
    }

    @Override
    public void remove(int id) throws DaoException {
        String query = "DELETE FROM orders WHERE id = ?;";
        executeQuery(query, id);
    }

    @Override
    public List<Order> getAll() throws DaoException {
        String query = "SELECT * FROM orders;";
        return createOrderList(query);
    }

    private void executeQuery(String query, int id) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            statement.execute();
            if (!statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find order with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, Order order) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, order.getUser_id());
            statement.setString(2, order.getStatus());
            statement.setTimestamp(3, order.getDate());

            statement.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, int orderId, String status) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.execute();
            if (statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find order with id: " + orderId);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private int getOrderId(String query) throws DaoException {
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);

        ) {
            if (resultSet.next()) {
                int result = resultSet.getInt("id");
                return result;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Order getOrder(String query, int id) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order result = new Order(
                        resultSet.getInt("user_id"),
                        resultSet.getString("status"));

                return result;
            } else {
                throw new DaoRecordNotFoundException("couldn't find order with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<Order> createOrderList(String query) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);

        ) {
            while (resultSet.next()) {
                Order result = new Order(
                        resultSet.getInt("user_id"),
                        resultSet.getString("status"));

                orderList.add(result);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return orderList;
    }
}
