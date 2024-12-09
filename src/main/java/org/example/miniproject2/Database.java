package org.example.miniproject2;

import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://gliderserver.mysql.database.azure.com:3306/gliderdatabase?useSSL=true", "glider", "Gpassword123");
    }

    public static void main(String[] args){
        Connection connection = null;
        try{
            connection = getConnection();
            if(connection != null){
                System.out.println("Connection was successful");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        }
}
