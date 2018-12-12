package pl.auto.database;

import java.io.File;

public class FileChecker {

    private Integer frequencyOfRefresh;

    public FileChecker(Integer frequencyOfRefresh) {
        this.frequencyOfRefresh = frequencyOfRefresh;
    }

    public void run() {

        File folder = new File("C:\\Users\\Fiszcz\\Downloads\\Oswietlenie_filu\\pl.auto.database\\src\\main\\resources\\toDo");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().endsWith("INPROGRESS")) {
                DatabaseAutofilling.logSystem.logNewFile(listOfFiles[i].getName());
                Runnable r = new OperationThread(listOfFiles[i]);
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
