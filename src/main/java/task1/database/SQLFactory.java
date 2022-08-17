package task1.database;

public class SQLFactory extends AbstractFactory {
    @Override
    public SQLTask1 getTask1() {
        return SQLTask1.getInstance();
    }
}
