package org.example.miniproject2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.miniproject2.Database;
import java.sql.*;

public class HelloController {

    @FXML
    private TextField queryInput; // FXML injected TextField for query input

    @FXML
    private TextArea resultArea; // FXML injected TextArea for displaying results

    @FXML
    private Button executeButton; // FXML injected Button to execute the query

    @FXML
    private void handleExecuteButtonAction() {
        String query = queryInput.getText().trim(); //

        if (query.isEmpty()) {
            resultArea.setText("Please enter an SQL query.");
            return;
        }

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getConnection();
            statement = connection.createStatement();
            int affectedRows = 0;

            // Check the type of query
            if (query.toLowerCase().startsWith("select")) {
                resultSet = statement.executeQuery(query); // Execute SELECT query
                StringBuilder results = new StringBuilder();

                while (resultSet.next()) {
                    results.append(resultSet.getString(1)).append("\n"); // Assuming the first column is what you want to display
                }

                resultArea.setText(results.toString());
            } else if (query.toLowerCase().startsWith("update") || query.toLowerCase().startsWith("delete")) {
                affectedRows = statement.executeUpdate(query); // Execute UPDATE or DELETE query
                resultArea.setText("Rows affected: " + affectedRows);
            } else {
                resultArea.setText("Invalid query. Only SELECT, UPDATE, or DELETE queries are allowed.");
            }

        } catch (SQLException e) {
            resultArea.setText("Error executing query: " + e.getMessage());
        } finally {
            // Close ResultSet
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            // Close Statement
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error closing Statement: " + e.getMessage());
                }
            }
            // Close Connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }
}
