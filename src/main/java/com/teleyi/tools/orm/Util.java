package com.teleyi.tools.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static String snakeToCamel(String snake) {
        return snakeToCamel(snake, false);
    }

    public static String snakeToCamel(String snake, boolean capital) {
        if (null == snake) {
            throw new IllegalArgumentException();
        }
        String[] split = snake.split("_");
        String camel = capital ? firstToUppercase(split[0]) : split[0];
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                camel += firstToUppercase(split[i]);
            }
        }
        return camel;
    }

    public static String firstToUppercase(String string) {
        char[] cs = string.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static List<Element> filterElements(List<Element> elements, String... blacklist) {
        List<Element> whiteElements = new ArrayList<Element>();
        List<String> lBlacklist = Arrays.asList(blacklist);
        for (Element eLement : elements) {
            if (!lBlacklist.contains(eLement.getColumnName())) {
                whiteElements.add(eLement);
            }
        }
        return whiteElements;
    }

    public static List<Element> fetchElements(String tableName) throws Exception {
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        // String DB_URL = "jdbc:mysql://127.0.0.1:3306/db_aegypius?useUnicode=true&characterEncoding=UTF-8";
        String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + Setting.DATABASE + "?useUnicode=true&characterEncoding=UTF-8";
        String DB_USER = "root";
        String DB_PASSWORD = "root";
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            statement.execute("use information_schema");
            String sql = "select * from columns where table_name='" + tableName + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Element> elements = new ArrayList<Element>();
            while (resultSet.next()) {
                Element element = new Element(resultSet.getString("column_name"), resultSet.getString("data_type"), resultSet.getString("column_comment"));
                elements.add(element);
            }
            return elements;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
                if (null != statement) {
                    statement.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static void batchGenerate(Generator... generators) {
        for (Generator generator : generators) {
            System.out.println("\n\n\n\n");
            generator.generate();
        }
    }
}