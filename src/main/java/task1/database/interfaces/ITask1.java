package task1.database.interfaces;

import task1.entity.Task1;

import java.sql.SQLException;

public interface ITask1 {
    void insert(Task1 object) throws SQLException;
    String sum() throws SQLException;
    String median() throws SQLException;
}
