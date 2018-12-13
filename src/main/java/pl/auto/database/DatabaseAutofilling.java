package pl.auto.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAutofilling {

    public static Log logSystem;
    public static Connection conn = null;

    public static void main(String[] args) {
        logSystem = new Log();
        logSystem.logStartApp();

        Configuration configuration = getConfiguration();
        String connectionAdress = getConnectionAdress(configuration);

        try {
            conn = DriverManager.getConnection(connectionAdress);
            logSystem.logConnection();
        } catch (SQLException e) {
            logSystem.logConnectionProblem(e);
            Runtime.getRuntime().exit(3);
        }

        new FileChecker(configuration.getFrequencyOfRefresh(), configuration.getSeparator()).run();

    }

    public static String getConnectionAdress(Configuration configuration) {
        return "jdbc:mysql://localhost:3306/" + configuration.getDatabase()
                + "?user=" + configuration.getUsername()
                + "&password=" + configuration.getPassword()
                + "&useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC";
    }

    public static Configuration getConfiguration() {
        File file = new File("/home/scresh/IdeaProjects/DatabaseAutofilling/src/main/resources/configurationDA.txt");
        Configuration configuration = new Configuration();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null){
                String[] parts = st.split("=");
                switch (parts[0]) {
                    case "database":
                        configuration.setDatabase(parts[1]);
                        break;
                    case "username":
                        configuration.setUsername(parts[1]);
                        break;
                    case "password":
                        configuration.setPassword(parts[1]);
                        break;
                    case "frequencyOfRefresh":
                        configuration.setFrequencyOfRefresh(Integer.valueOf(parts[1]));
                        break;
                    case "separator":
                        configuration.setSeparator(parts[1].charAt(0));
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            logSystem.logNotFoundConfiguration();
            Runtime.getRuntime().exit(1);
        } catch (IOException e) {
            logSystem.logNotReadConfiguration();
            Runtime.getRuntime().exit(2);
        }

        logSystem.logConfiguration(configuration.toString());
        return configuration;
    }
}
