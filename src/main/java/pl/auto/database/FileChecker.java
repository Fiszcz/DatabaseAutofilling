package pl.auto.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileChecker {

    private Integer frequencyOfRefresh;
    private char separator;

    public FileChecker(Integer frequencyOfRefresh, char separator) {
        this.frequencyOfRefresh = frequencyOfRefresh;
        this.separator = separator;
    }

    public void save(String tekst) throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("plik.txt");
        zapis.print(tekst);
        zapis.close();

    }
    public void run() {

        File folder = new File("/home/scresh/IdeaProjects/DatabaseAutofilling/src/main/resources/toDo");
        File[] listOfFiles = folder.listFiles();


        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().endsWith("INPROGRESS")) {
                DatabaseAutofilling.logSystem.logNewFile(listOfFiles[i].getName());
                Runnable r = new OperationThread(listOfFiles[i], separator);

                new Thread(r).start();
            }
        }

        try {
            Thread.sleep(this.frequencyOfRefresh);
            run();
        } catch (InterruptedException e) {
            DatabaseAutofilling.logSystem.logError("Interrupted", e);
            run();
        }
    }
}
