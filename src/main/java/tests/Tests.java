package tests;

import org.junit.Test;
import pl.auto.database.Configuration;
import pl.auto.database.DatabaseAutofilling;
import pl.auto.database.Log;
import pl.auto.database.OperationThread;

import java.io.*;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class Tests {
    private File file;
    private String folderPath = "C:\\Projects\\DatabaseAutofilling\\src\\main\\resources";


    @Test
    public void testCreateInsertQuery() throws IOException {
        String fileContent = "programmingLanguages\n"
                + "language;rank;change;share;trend\n"
                + "Python;1;+1;25.36%;+5.2%\n"
                + "Java;2;-1;21.56%;-1.1%\n"
                + "Javascript;3;+1;8.4%;+0.0%\n"
                + "C#;4;+1;7.63%;-0.4%\n"
                + "PHP;5;-2;7.31%;-1.3%";

        Reader inputString = new StringReader(fileContent);
        BufferedReader reader = new BufferedReader(inputString);
        
        OperationThread operationThread = new OperationThread(new File("fdsdf"), ';');

        String query_received = OperationThread.createInsertQuery(reader);
        String query_expected = "INSERT INTO programmingLanguages (language,rank,change,share,trend) VALUES "
            +"(Python,1,+1,25.36%,+5.2%), "
            +"(Java,2,-1,21.56%,-1.1%), "
            +"(Javascript,3,+1,8.4%,+0.0%), "
            +"(C#,4,+1,7.63%,-0.4%), "
            +"(PHP,5,-2,7.31%,-1.3%);";

        Assert.assertEquals(query_received, query_expected);


    }

//    @Test
//    public void testcreateInsertQuery(){
//        BufferedReader br;
//        try {
//            br = new BufferedReader(new FileReader(file));
//            String tableName = "test";
//            String insert = "insert into " + tableName + " (id, name, surname) values (null, 'Jacek', 'Klimek')";
//            String create;
//            create = OperationThread.createInsertQuery(br);
//            Assert.assertEquals(insert,create);
//        }  catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Test
    public void testrenameFile(){
        file = new File("testDelete.txt");
        OperationThread ot = new OperationThread(file, ';');
        ot.renameFile();
        String path="testDelete.txtINPROGRESS";
        Assert.assertEquals(path, ot.file.getName());

    }

    @Test
    public void testJoin() {
        String[] parts = new String[]{"id", "name", "surname"};
        String delim = ",";
        String expected = "id,name,surname";
        String joined = OperationThread.join(parts, delim);

        Assert.assertEquals(expected, joined);
    }

    @Test
    public void testJoinNullDelim() {
        String[] parts = new String[]{"id", "name", "surname"};
        String delim = null;
        String expected = "idnamesurname";
        String joined = OperationThread.join(parts, delim);

        Assert.assertEquals(expected, joined);
    }

    @Test
    public void testDelete() {

        DatabaseAutofilling.logSystem = new Log();
        DatabaseAutofilling.logSystem.logStartApp();
        Statement stmt = null;
        String tableName = "test";

        try {
            /* sql commands */
            String insert = "insert into " + tableName + " (id, name, surname) values (null, 'Jacek', 'Klimek')";
            String[] returnId = {"BATCHID"};
            Integer insertedId = 0;
            String count = "select COUNT(1) as CNT from " + tableName;

            /* set conf */
            Configuration config = DatabaseAutofilling.getConfiguration();
            String connectionAdress = DatabaseAutofilling.getConnectionAdress(config);
            DatabaseAutofilling.conn = DriverManager.getConnection(connectionAdress);
            stmt = DatabaseAutofilling.conn.createStatement();

            /* count before insert */
            ResultSet rs = stmt.executeQuery(count);
            rs.next();
            Integer cntStart = rs.getInt("CNT");

            /* insert and get ID */
            PreparedStatement statement = DatabaseAutofilling.conn.prepareStatement(insert, returnId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet rsKey = statement.getGeneratedKeys()) {
                if (rsKey.next()) {
                    insertedId = rsKey.getInt(1);
                }
                rsKey.close();
            }

            /* count after insert */
            rs = stmt.executeQuery(count);
            rs.next();
            Integer cntProg = rs.getInt("CNT");

            if (cntStart + 1 == cntProg) {
                File file = new File("testDelete.txt");
                try {
                    BufferedWriter output = new BufferedWriter(new FileWriter(file));
                    output.write("DELETE\n");
                    output.write(tableName + "\n");
                    output.write("id\n");
                    output.write(insertedId.toString());
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /* read file and delete */
                BufferedReader br;
                String whichOperation = "";
                OperationThread operationThread = new OperationThread(file, ';');
                try {
                    br = new BufferedReader(new FileReader(file));
                    String query;
                    whichOperation = br.readLine().trim().toUpperCase();
                    switch (whichOperation) {
                        case "DELETE":
                            query = operationThread.createDeleteQuery(br);
                            operationThread.executeQuery(query);
                            break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                /* count after delete */
                rs = stmt.executeQuery(count);
                rs.next();
                Integer cntEnd = rs.getInt("CNT");
                assertEquals(cntStart, cntEnd);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSeparators()
    {
        DatabaseAutofilling.logSystem = new Log();
        DatabaseAutofilling.logSystem.logStartApp();

        File folder = new File(folderPath + "\\toDo");
        String path = folder + "\\sepTest1.txt";

        try{
            Configuration config = DatabaseAutofilling.getConfiguration();
            String connectionAdress = DatabaseAutofilling.getConnectionAdress(config);
            DatabaseAutofilling.conn = DriverManager.getConnection(connectionAdress);


            file = new File(path);

            Writer writer = new BufferedWriter( new FileWriter(file));
            writer.write("INSERT\n" +
                    "test\n" +
                    "id;name;surname\n" +
                    "1000;'aaa';'bbb'\n" +
                    "1001;'cccc';'ddd'\n" +
                    "1002;'eeee';'fff'");
            writer.flush();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        char separator = ';';
        OperationThread ot = new OperationThread(file, separator);
        Thread t =  new Thread(ot);
        t.start();

        try
        {
            t.join();

            PreparedStatement statement = DatabaseAutofilling.conn.prepareStatement("DELETE FROM test WHERE ID = 1000 OR ID = 1001 OR ID = 1002",Statement.NO_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        File fileInProgress = new File(path + "INPROGRESS");
        File fileDone = new File(folderPath + "\\done\\sepTest1.txt");

        Assert.assertTrue(!file.exists());
        Assert.assertTrue(!fileInProgress.exists());
        Assert.assertTrue(fileDone.exists());
        fileDone.delete();

    }
}