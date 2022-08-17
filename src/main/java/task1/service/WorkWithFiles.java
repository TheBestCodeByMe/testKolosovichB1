package task1.service;

import task1.constants.Constants;
import task1.database.ConnectionDatabase;
import task1.database.SQLFactory;
import task1.entity.Task1;

import java.io.*;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class WorkWithFiles {
    /*
     * 3.	Создать процедуру импорта файлов с таким набором полей в таблицу в
     * СУБД. При импорте должен выводится ход процесса (сколько
     * строк импортировано, сколько осталось)
     * */

    private final SQLFactory sqlFactory = new SQLFactory();

    // метод для вызова расчёта суммы и медианы
    public void sumAndMedian() throws SQLException {
        System.out.println("Сумма целых чисел равна: " + sqlFactory.getTask1().sum() +
                ", медиана дробных чисел равна: " + sqlFactory.getTask1().median());
    }

    // добавление в БД из файла
    public void insertFromFile(File file) {
        Task1 task1 = new Task1();

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            String line;
            String[] chunks;
            int id = 0, intNum;
            String randomDate;
            String latinString, rusString;
            double floatNum;

            for (int k = 0; k < Constants.NUMBER_OF_RECORD; k++) {
                line = lines.get(k);
                chunks = line.split(Pattern.quote("||"));

                Constants.CURRENT_ID = id++;
                randomDate = chunks[0];
                latinString = chunks[1];
                rusString = chunks[2];
                intNum = Integer.parseInt(chunks[3]);
                floatNum = Double.parseDouble(chunks[4].replaceAll(",", "."));

                task1.setDate(randomDate);
                task1.setLatinChars(latinString);
                task1.setRussianChars(rusString);
                task1.setIntNumber(intNum);
                task1.setFloatNumber(floatNum);

                sqlFactory.getTask1().insert(task1);
            }

        } catch (IOException | SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    /*
     * 2.	Реализовать объединение файлов в один. При объединении должна быть
     * возможность удалить из всех файлов строки с заданным сочетанием символов,
     * например, «abc» с выводом информации о количестве удаленных строк
     * */

    // метод для объединения файлов и запроса на удаление определённых данных
    public void mergeDelFiles() throws IOException {
        System.out.println("Вы хотите сначала удалить определённые данные из файла? (Введите 'yes', если хотите, иначе нажмите любую клавишу)");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Введите текст для удаления: ");
            String delStr = scanner.next();
            System.out.println("Количество удалённых строк = " + delStrings(delStr));
        }
        mergingFiles();
    }

    // объединение файлов
    private void mergingFiles() throws IOException {
        ArrayList<String> list = new ArrayList<String>();

        //Reading data files
        try {
            File folder = new File(Constants.FILE_PATH);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (BufferedReader t = new BufferedReader(new FileReader(file));) {
                        String s = null;
                        while ((s = t.readLine()) != null) {
                            list.add(s);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Writing merged data file
        BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.FILE_PATH + "mergingFile/merged-output.txt"));
        for (String s : list) {
            writer.write(s);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    // удаление строки в файлах
    private int delStrings(String txtForReplace) {
        ArrayList<String> list = new ArrayList<String>();
        int count = 0;

        try {
            File folder = new File(Constants.FILE_PATH);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (BufferedReader t = new BufferedReader(new FileReader(file));
                    ) {
                        String newStr = null;
                        String s;
                        while ((newStr = t.readLine()) != null) {
                            s = newStr;
                            newStr = newStr.replaceAll(txtForReplace, "");
                            list.add(newStr + "\n");

                            if (!s.equals(newStr)) {
                                count++;
                            }
                        }

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            for (String str : list) {
                                writer.write(str);
                                writer.flush();
                            }
                        }
                        list.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
