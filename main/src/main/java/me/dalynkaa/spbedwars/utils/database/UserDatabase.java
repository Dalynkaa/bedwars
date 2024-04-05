package me.dalynkaa.spbedwars.utils.database;


import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.UUIDUtils;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDatabase {
    private DatabaseController databaseController;
    private SPBedWars bedWars;
    private String tableName;

    public UserDatabase(SPBedWars main, DatabaseController database, String tableName){
        this.bedWars = main;
        this.databaseController = database;
        this.tableName = tableName;
    }

    private Connection getConnection() {
        try {
            return databaseController.ConntectToDb();
        }catch (SQLException | ClassNotFoundException exception){
            exception.printStackTrace();
            return null;
        }
    }
    public void insertUser(UUID uuid) {
        try {
            Connection c = getConnection();
            PreparedStatement s = c.prepareStatement("INSERT INTO `BUsers` (`uuid`) VALUES (?)");
            s.setString(1, uuid.toString());
            s.executeUpdate();
            c.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public boolean hasUserInTable(UUID uuid) {
        try {
            Connection c = getConnection();
            assert c != null;
            PreparedStatement s = c.prepareStatement("SELECT count(*) FROM `BUsers` WHERE `uuid` = ?");
            s.setString(1, uuid.toString());
            ResultSet result = s.executeQuery();
            result.next();
            int count = result.getInt(1);
            c.commit();
            return (count > 0);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    public void updateUser(BPlayer player) {
        try {
            Connection c = getConnection();
            assert c != null;
            PreparedStatement s = c.prepareStatement("UPDATE `BUsers` SET `previusGameId`=?,`GameId`=?,`editArena`=? WHERE `uuid` = ?");
            s.setString(1, UUIDUtils.UUIDtoString(player.getPreviusGame(), null));
            s.setString(2, UUIDUtils.UUIDtoString(player.getCurrentGame(), null));
            s.setString(3,UUIDUtils.UUIDtoString(player.getEditArena(), null));
            s.setString(4, player.getUuid().toString());
            s.executeUpdate();
            c.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public ResultSet getUserByUUID(UUID uuid) {
        try {
            Connection c = getConnection();
            PreparedStatement s = c.prepareStatement("SELECT * FROM `BUsers` WHERE `uuid` = ?");
            s.setString(1, uuid.toString());
            ResultSet result = s.executeQuery();
            result.next();
            return result;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }


}
