package me.dalynkaa.spbedwars.utils.database;

import me.dalynkaa.spbedwars.SPBedWars;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
    SPBedWars main;
    private final String ip;
    private final Integer port;
    private final String user;
    private final String password;
    private final String database_name;
    private static Connection connection;

    private UserDatabase userDatabase;
    public DatabaseController(@NotNull SPBedWars main) throws SQLException, ClassNotFoundException {
        this.main = main;
        this.ip = main.getConfig().getString("mariadb.ip", "127.0.0.1");
        this.port = main.getConfig().getInt("mariadb.port", 3306);
        this.user = main.getConfig().getString("mariadb.user", "bedwars");
        this.password = main.getConfig().getString("mariadb.password", "RGEdYHlS8Wf@1rOU");
        this.database_name = main.getConfig().getString("mariadb.database", "bedwars");

        Connection connection = ConntectToDb();
        Statement s = connection.createStatement();

        s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS `BUsers` (
                 `id` int(11) NOT NULL AUTO_INCREMENT,
                 `uuid` varchar(40) NOT NULL,
                 `previusGameId` varchar(40) DEFAULT NULL,
                 `GameId` varchar(40) DEFAULT NULL,
                 PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci"""); // create user table
        connection.commit();
        this.userDatabase = new UserDatabase(main, this, "BUsers");
    }

    public Connection ConntectToDb() throws SQLException, ClassNotFoundException {
        String jdbcDriver = "org.mariadb.jdbc.Driver";
        Class.forName(jdbcDriver);
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mariadb://" + this.ip + ":" + this.port + "/" + this.database_name + "?user=" + this.user + "&password=" + this.password);
        }
        return connection;
    }

    public UserDatabase getUserDatabase(){
        return this.userDatabase;
    }
}
