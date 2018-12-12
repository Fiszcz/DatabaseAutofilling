package pl.auto.database;

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

import static java.nio.file.Files.move;
import static pl.auto.database.DatabaseAutofilling.logSystem;

public class OperationThread implements Runnable {

    private File file;

    public OperationThread(File file) {
        this.file = file;
    }

    public void run() {
        renameFile();

        BufferedReader br;
        String whichOperation = "";
        try {
            br = new BufferedReader(new FileReader(file));
            String query;

            whichOperation = br.readLine().trim().toUpperCase();
            switch (whichOperation){
                case "INSERT":
                    query = createInsertQuery(br);
                    executeQuery(query);
                    break;
                case "DELETE":
                    query = createDeleteQuery(br);
                    executeQuery(query);
                    break;
            }

            br.close();

            moveFileToDone();

        } catch (FileNotFoundException e) {
            logSystem.logNotFoundFile(file.getName());
            Runtime.getRuntime().exit(4);
        } catch (IOException e) {
            logSystem.logNotReadFile(file.getName());
            Runtime.getRuntime().exit(2);
        } catch (SQLException e) {
            logSystem.logSQLException(whichOperation, e);
            Runtime.getRuntime().exit(5);
        }

    }

    private void moveFileToDone() throws IOException {
        String movedFileAdress = file.getAbsolutePath().replace("toDo", "done").replace("INPROGRESS", "");

        move(file.toPath(), Paths.get(movedFileAdress));

        logSystem.logEndWorkAtFile(file.getName().replace("INPROGRESS", ""));
    }

    private void executeQuery(String query) throws SQLException {
        Statement stmt = DatabaseAutofilling.conn.createStatement();
        Integer howMany = stmt.executeUpdate(query);
        logSystem.logHowManyUpdated(howMany);
    }

    private String createInsertQuery(BufferedReader br) throws IOException {
        String table = br.readLine().trim();
        String fields = br.readLine().trim();

        String query = "INSERT INTO " + table + " (" + join(fields.split(";"), ",") + ") VALUES ";
        while(true){
            query += " (" + join(br.readLine().trim().split(";"), ",") + ")";
            if(br.ready())
                query += ",";
            else{
                query += ";";
                break;
            }
        }

        return query;
    }

    private String createDeleteQuery(BufferedReader br) throws IOException {
        String table = br.readLine().trim();
        String id = br.readLine().trim();

        String query = "DELETE FROM " + table + " WHERE " +  id + " IN ( ";

        while(true){
            query += br.readLine().trim();
            if(br.ready())
                query += ",";
            else{
                query += ");";
                break;
            }
        }

        return query;
    }

    private static String join(String[] parts, String delim) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            result.append(part);
            if (delim != null && i < parts.length-1) {
                result.append(delim);
            }
        }
        return result.toString();
    }

    private void renameFile() {
        File renamedFile = new File(file.getAbsolutePath() + "INPROGRESS");

        logSystem.logStartWorkAtFile(file.getName());

        file.renameTo(renamedFile);
        file = renamedFile;
    }

}
