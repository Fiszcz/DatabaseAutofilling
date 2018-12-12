package pl.auto.database;

public class Configuration {

    private String database;

    private String username;

    private String password;

    private Integer frequencyOfRefresh;

    public Configuration() {
        this.database = "database";
        this.username = "root";
        this.password = "root";
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

    public void setFrequencyOfRefresh(Integer frequencyOfRefresh) {
        this.frequencyOfRefresh = frequencyOfRefresh;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "database='" + database + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", frequencyOfRefresh=" + frequencyOfRefresh +
                '}';
    }
}
