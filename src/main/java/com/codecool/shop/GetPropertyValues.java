package com.codecool.shop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class GetPropertyValues {
    private String result = "";
    private InputStream inputStream;

    public String getPropValues(String wishedInfo) throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            String url = prop.getProperty("url");
            String database = prop.getProperty("database");
            String databaseTest = prop.getProperty("databasetest");

            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            switch (wishedInfo) {
                case "url":

                    result = url;
                    break;
                case "database":
                    result = database;
                    break;
                case "databasetest":
                    result = databaseTest;
                    break;
                case "user":
                    result = user;
                    break;
                case "password":
                    result = password;
                    break;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            Objects.requireNonNull(inputStream).close();
        }
        return result;
    }
}
