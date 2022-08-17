package task1.service;

import task1.constants.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FileGeneration {
    /* 1.	Сгенерировать 100 текстовых файлов со следующей структурой,
    каждый из которых содержит 100 000 строк случайная дата за последние 5 лет
    || случайный набор 10 латинских символов || случайный набор 10 русских символов
    || случайное положительное четное целочисленное число в диапазоне от 1 до 100 000 000
    || случайное положительное число с 8 знаками после запятой в диапазоне от 1 до 20
    */

    // метод генерации файлов
    public void generateFiles() throws IOException {
        File f = new File(Constants.FILE_PATH);

        for (int i = 1; i <= Constants.NUMBER_OF_FILES; i++) {
            File f2 = new File(f, "file" + i + ".txt");
            try (FileWriter fw = new FileWriter(f2);
                 BufferedWriter writer = new BufferedWriter(fw)) {
                for (int j = 0; j < Constants.NUMBER_OF_RECORD; j++) {
                    writer.write(genTextForFiles());
                    if (j!=Constants.NUMBER_OF_RECORD-1) {
                        writer.newLine();
                    }
                }
            }
        }
    }

    // генерация даты
    private String genDate() {
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(Constants.START_DATE, Constants.NOW); // для большей пропускной способности в многопоточных средах использую ThreadLocalRandom

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(new Date(randomMillisSinceEpoch));
    }

    // генерация символов (латинских/русских)
    private String genSymbol(int leftLimit, int rightLimit, int leftException, int rightException) {
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i < leftException || i > rightException))
                .limit(Constants.TARGET_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // генерация положительного целого числа
    private int genPositiveEvenInt() {
        int random = ThreadLocalRandom.current().nextInt(Constants.LEFT_BORDER_FOR_INT, Constants.RIGHT_BORDER_FOR_INT + 1);
        if (random % 2 != 0) {
            random++;
        }
        return random;
    }

    // генерация дробного числа с 8 знаками после запятой
    private String genPositiveDouble() {
        return (String.format("%." + Constants.NUMBER_FOR_DECIMAL_PLACES + "f", (Math.random() * (Constants.RIGHT_BORDER_FOR_DOUBLE - Constants.LEFT_BORDER_FOR_DOUBLE)) + Constants.LEFT_BORDER_FOR_DOUBLE));
    }

    // генерация текста для файлов в необходимом порядке и виде
    public String genTextForFiles() {
        return genDate() + "||" + genSymbol(65, 122, 91, 96) +
                "||" + genSymbol(1040, 1103, 0, 0) + "||"
                + genPositiveEvenInt() + "||" + genPositiveDouble() + "||";
    }
}
