package pl.auto.database;

import java.sql.SQLException;

public class Log {

    public void logStartApp() {
        System.out.println("DatabaseAutofilling System został uruchomiony");
    }

    public void logNotFoundConfiguration() {
        System.out.println("BŁĄD - Nie można znaleźć pliku z konfiguracją!");
    }

    public void logNotReadConfiguration() {
        System.out.println("BŁĄD - Nie można odczytać prawidłowo pliku z konfiguracją");
    }

    public void logConfiguration(String configuration) {
        System.out.println("Wczytano konfigurację: " + configuration);
    }

    public void logConnectionProblem(SQLException e) {
        System.out.println("Problem z połączeniem z bazą danych\n: " + e.getMessage() + "\nKod błedu: " + e.getErrorCode());
    }

    public void logConnection() {
        System.out.println("Połączono z bazą danych");
    }

    public void logError(String error, InterruptedException e) {
        System.out.println(error + "Error" + '\n' + e.getMessage());
    }

    public void logNewFile(String name) {
        System.out.println("New file: " + name);
    }

    public void logStartWorkAtFile(String name) {
        System.out.println("Zaczeliśmy pracę nad: " + name);
    }

    public void logNotFoundFile(String name) {
        System.out.println("Plik nad którym zaczeliśmy pracować nagle przestał istnieć " + name);
    }

    public void logNotReadFile(String name) {
        System.out.println("Plik nad którym zaczeliśmy pracować jest nie do odczytania " + name);
    }

    public void logSQLException(String whichOperation, SQLException e) {
        System.out.println("Wystąpił problem podczas wywoływania zapytania, typu " + whichOperation + " na bazie danych\n"
                + e.getErrorCode() + "\n" + e.getMessage() + '\n' + e.getSQLState());
    }

    public void logEndWorkAtFile(String name) {
        System.out.println("Skończyliśmy pracę nad: " + name);
    }

    public void logHowManyUpdated(Integer howMany) {
        System.out.println("Zaktualizowano w bazie danych " + howMany + " rekordów");
    }
}
