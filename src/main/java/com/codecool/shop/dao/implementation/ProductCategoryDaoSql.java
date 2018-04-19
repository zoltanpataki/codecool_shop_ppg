package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoSql implements ProductCategoryDao {
    private static ProductCategoryDaoSql instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoSql() {
    }

    public static ProductCategoryDaoSql getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoSql();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) throws DaoException {
        String query = "INSERT INTO category(name, description, department) VALUES (?, ?, ?);";
        executeQuery(query, category);
    }

    @Override
    public ProductCategory find(int id) throws DaoException {
        String query = "SELECT * FROM category WHERE id = ?;";
        return findProductCategory(query, id);
    }

    @Override
    public void remove(int id) throws DaoException {
        String query = "DELETE FROM category WHERE id = ?;";
        executeQuery(query, id);
    }

    @Override
    public List<ProductCategory> getAll() throws DaoException {
        String query = "SELECT * FROM category;";
        return getAllProductCategory(query);
    }

    private void executeQuery(String query, int id) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            statement.execute();
            if (statement.execute()){
                throw new DaoRecordNotFoundException("couldn't find product category with id: " + id);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void executeQuery(String query, ProductCategory category) throws DaoException {
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setString(3, category.getDepartment());

            statement.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private ProductCategory findProductCategory(String query, int id) throws DaoException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ProductCategory result = new ProductCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));

                return result;
            } else {
                throw new DaoRecordNotFoundException("couldn't find product category with id: " + id);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<ProductCategory> getAllProductCategory(String query) throws DaoException {
        List<ProductCategory> categoryList = new ArrayList<>();
        try {
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ProductCategory result = new ProductCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));

                categoryList.add(result);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return categoryList;
    }
}
