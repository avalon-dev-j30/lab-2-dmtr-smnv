package ru.avalon.java.j30.labs;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Лабораторная работа №3
 * <p>
 * Курс: "DEV-OCPJP. Подготовка к сдаче сертификационных экзаменов серии Oracle Certified Professional Java Programmer"
 * <p>
 * Тема: "JDBC - Java Database Connectivity" 
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Main {

    /**
     * Точка входа в приложение
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        try (Connection connection = getConnection()) {
            ProductCode code = new ProductCode("MO", 'N', "Movies");
            code.save(connection);
            printAllCodes(connection);
            System.out.println("-------");
            code.setCode("MV");
            code.save(connection);
            printAllCodes(connection);
        }
        
        /*
         * TODO #14 Средствами отладчика проверьте корректность работы программы
         */
    }
    
    /**
     * Выводит в кодсоль все коды товаров
     * 
     * @param connection действительное соединение с базой данных
     * @throws SQLException 
     */    
    private static void printAllCodes(Connection connection) throws SQLException {
        Collection<ProductCode> codes = ProductCode.all(connection);
        for (ProductCode code : codes) {
            System.out.println(code);
        }
    }
    
    /**
     * Возвращает URL, описывающий месторасположение базы данных
     * 
     * @return URL в виде объекта класса {@link String}
     */
    private static String getUrl() throws IOException {
        return getProperties().getProperty("url");
    }
    
    /**
     * Возвращает параметры соединения
     * 
     * @return Объект класса {@link Properties}, содержащий параметры user и 
     * password
     */
    private static Properties getProperties() throws IOException {
        Properties config = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("resources/db.properties")) {
            config.load(stream);
        }
        return config;
    }
    /**
     * Возвращает соединение с базой данных Sample
     * 
     * @return объект типа {@link Connection}
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException, IOException {
        return DriverManager.getConnection(getUrl(), getProperties());
    }
    
}
