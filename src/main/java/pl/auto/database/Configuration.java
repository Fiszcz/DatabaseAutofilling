package pl.auto.database;

public class Configuration {

    private String database;

    private String username;

    private String password;

    private char separator;

    private Integer frequencyOfRefresh;

    public Configuration() {
        this.database = "database";
        this.username = "root";
        this.password = "root";
        this.separator = ';';
        this.frequencyOfRefresh = 2500;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public char getSeparator() {
        return separator;
    }

    public Integer getFrequencyOfRefresh() {
        return frequencyOfRefresh;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setFrequencyOfRefresh(Integer frequencyOfRefresh) {
        this.frequencyOfRefresh = frequencyOfRefresh;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", separator='" + separator + '\'' +
                ", frequencyOfRefresh=" + frequencyOfRefresh +
                '}';
    }
}
