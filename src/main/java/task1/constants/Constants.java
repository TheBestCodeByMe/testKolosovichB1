package task1.constants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Constants {
    // date
    public static final long NOW = new Date().getTime(); //количество миллисекунд на сегодняшний день
    public static final long START_DATE = new Date(NOW- TimeUnit.DAYS.toMillis(1)*365*5).getTime(); // считаем дату 5 лет назад

    // length
    public static final int TARGET_STRING_LENGTH = 10;

    // borders for numbers
    public static final double LEFT_BORDER_FOR_DOUBLE = 1.0;
    public static final double RIGHT_BORDER_FOR_DOUBLE = 20.0;
    public static final int LEFT_BORDER_FOR_INT = 1;
    public static final int RIGHT_BORDER_FOR_INT = 100000000;

    // quantity
    public static final int NUMBER_FOR_DECIMAL_PLACES = 8;
    public static final int NUMBER_OF_FILES = 100;
    public static final int NUMBER_OF_RECORD = 100;
    public static int CURRENT_ID = 1;
    public static int FILE_ID;

    // path
    public static final String FILE_PATH = "D:\\Wooooorks\\testKolosovichB1\\src\\main\\resources\\files\\";
}
