package task1.database;

import task1.database.interfaces.ITask1;
import task1.entity.Task1;

import java.sql.SQLException;

public class SQLTask1 implements ITask1 {
    private static SQLTask1 instance;
    private final ConnectionDatabase dbConnectionDatabase;

    private SQLTask1() {
        dbConnectionDatabase = ConnectionDatabase.getInstance();
    }

    public static synchronized SQLTask1 getInstance() {
        if (instance == null) {
            instance = new SQLTask1();
        }
        return instance;
    }

    // добавление в БД
    @Override
    public void insert(Task1 object) throws SQLException {
        String query = "INSERT INTO task1(date, latinChars, russianChars, intNumber, floatNumber) VALUES('" +
                object.getDate() + "', '" +
                object.getLatinChars() + "', '" +
                object.getRussianChars() + "', '" +
                object.getIntNumber() + "', '" +
                object.getFloatNumber() + "')";
        dbConnectionDatabase.execute(query);
    }

    // расчёт суммы
    @Override
    public String sum() throws SQLException {
        String query = "SELECT sum(intNumber) as sumNumbers from task1";
        return dbConnectionDatabase.getStringResult(query, "sumNumbers");
    }

    // расчёт медианы
    @Override
    public String median() throws SQLException {
        String query = "SELECT AVG(t2.floatNumber/2) as median" +
                "    FROM (" +
                "            SELECT t1.floatNumber, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum" +
                "            FROM task1 t1, (SELECT @rownum:=0) r" +
                "    WHERE t1.floatNumber is NOT NULL" +
                "    ORDER BY t1.floatNumber" +
                "     ) as t2" +
                "    WHERE t2.row_number IN ( FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2) )";
        return dbConnectionDatabase.getStringResult(query, "median");

    }
}
