package task1.database;

import task1.constants.Constants;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionDatabase implements Runnable {
    private static ConnectionDatabase instance;
    private static final String url = "jdbc:mysql://localhost:3306/testB1Task1";
    private static final String user = "root";
    private static final String password = "root";
    private final Statement statement;
    private final Connection connect;
    private ResultSet resultSet;

    public ConnectionDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);
            statement = connect.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("can't provide connection", e);
        }
    }

    public void execute(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getStringResult(String str, String search) {
        String result = "";
        try {
            resultSet = statement.executeQuery(str);
            if (resultSet.next()) {
                result = resultSet.getString(search);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static synchronized ConnectionDatabase getInstance() {
        if (instance == null) {
            instance = new ConnectionDatabase();
        }
        return instance;
    }

    public void close() throws SQLException {
        connect.close();
    }

    public static void getProgress() {
        new Thread(new ConnectionDatabase()).start();
    }

    @Override
    public void run() {
        for (int k = 0; k < Constants.NUMBER_OF_RECORD; k++) {
            long currentProgress = Constants.NUMBER_OF_RECORD - Constants.CURRENT_ID;
            if (currentProgress == 1) {
                System.out.println("Импортировано " + Constants.FILE_ID + " файлов" + "!");
                Constants.FILE_ID++;
                return;
            }
            System.out.println("Импортировано в файл:" + Constants.CURRENT_ID + "; осталось " + currentProgress + " из " + Constants.NUMBER_OF_RECORD);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
