package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoSql implements SupplierDao {
    private static SupplierDaoSql instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoSql() {
    }

    public static SupplierDaoSql getInstance() {
        if (instance == null) {
            instance = new SupplierDaoSql();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) throws DaoException {
        String query = "INSERT INTO supplier(name, description) VALUES (?, ?);";
        executeQuery(query, supplier);
    }

    @Override
    public Supplier find(int id) throws DaoException {
        String query = "SELECT * FROM supplier WHERE id = ?;";
        return findSupplier(query, id);
    }

    @Override
    public void remove(int id) throws DaoException {
        String query = "DELETE FROM supplier WHERE id = ?;";
        executeQuery(query, id);
    }

    @Override
    public List<Supplier> getAll() throws DaoException {
        String query = "SELECT * FROM supplier;";
        return getAllSupplier(query);
    }

    private void executeQuery(String query, int id) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            statement.execute();
            if (statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find supplier with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, Supplier supplier) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());

            statement.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<Supplier> getAllSupplier(String query) throws DaoException {
        List<Supplier> supplierList = new ArrayList<>();
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);

        ) {
            while (resultSet.next()) {
                Supplier result = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));

                supplierList.add(result);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return supplierList;
    }

    private Supplier findSupplier(String query, int id) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Supplier result = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));

                return result;
            } else {
                throw new DaoRecordNotFoundException("couldn't find supplier with id: " + id);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
