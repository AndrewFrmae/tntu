/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author andrew
 */
public class DataBase {

    private Connection connection;
    
    public DataBase(String username, String password, String name, String port) {
        try {
            // спробувати завантажити драйвер
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // у випадку невдачі, друкуємо інформацію про виключну ситуацію
            ex.printStackTrace();
        }
        // формуємо адресу БД
        String url = "jdbc:mysql://localhost:" + 
                port + "/" + name;
        String login = username;
        String passwd = password;
        try {
            // з’єднуємося із сервером БД
            connection = DriverManager.getConnection(url, login, passwd);
            System.out.println("Connection established.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException ex) {
        }
    }
}
