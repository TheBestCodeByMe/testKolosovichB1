package task1;

import task1.constants.Constants;
import task1.database.ConnectionDatabase;
import task1.service.FileGeneration;
import task1.service.WorkWithFiles;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException, SQLException {
        FileGeneration fileGeneration = new FileGeneration();
        fileGeneration.generateFiles();
        WorkWithFiles workWithFiles = new WorkWithFiles();
        //workWithFiles.mergeDelFiles();
/*
        File folder = new File(Constants.FILE_PATH);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                ConnectionDatabase.getProgress();
                workWithFiles.insertFromFile(file);
            }
        }*/

        workWithFiles.sumAndMedian();
    }
}
