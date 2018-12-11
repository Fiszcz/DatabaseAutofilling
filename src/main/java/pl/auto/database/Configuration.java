package pl.auto.database;

public class Configuration {

    private String database;

    private String username;

    private String password;

    private Integer frequencyOfRefresh;

    public Configuration(String database, String username, String password, Integer frequencyOfRefresh) {
        this.database = database;
        this.username = username;
        this.password = password;
        this.frequencyOfRefresh = frequencyOfRefresh;
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

    public Integer getFrequencyOfRefresh() {
        return frequencyOfRefresh;
    }
}
