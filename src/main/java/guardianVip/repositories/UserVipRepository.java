package guardianVip.repositories;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import guardianVip.sql.interfaces.SQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserVipRepository {

    private Map<String, UserVip> userVips = new HashMap<>();
    private GuardianVips plugin;
    private Gson gson = new Gson();

    public UserVipRepository(GuardianVips plugin) {
        this.plugin = plugin;
        initTables();
    }

    public UserVip selectById(String uuid) {
        PreparedStatement ps;
        SQL sql = plugin.getDatabaseManager().getSql();
        try {
            ps = sql.prepareStatement("select name, vipActivated " +
                    "from guardianvips " +
                    "where uuid = '"+uuid+"';");

            ResultSet result = ps.executeQuery();
            UserVip userVip = new UserVip();

            while (result.next()) {
                String name = result.getString("name");
                String vipActivatedString = result.getString("vipActivated");
                VipActive[] vipActives = gson.fromJson(vipActivatedString, VipActive[].class);
                List<VipActive> vipActivated = Arrays.stream(vipActives).collect(Collectors.toList());
                userVip.setName(name);
                userVip.setUuid(UUID.fromString(uuid));
                userVip.setVipsActivated(vipActivated);
            }
            if (userVip.getName() == null || userVip.getName().equals("")) {
                return null;
            }
            userVips.put(userVip.getName(), userVip);
            return userVip;

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error on save UserVip, contact the developer");
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, UserVip> getAll() {
        return userVips;
    }

    public void loadAll() {

        PreparedStatement ps;
        SQL sql = plugin.getDatabaseManager().getSql();
        try {
            ps = sql.prepareStatement("select name, uuid, vipActivated " +
                    "from guardianvips;");

            ResultSet result = ps.executeQuery();

            while (result.next()) {
            UserVip userVip = new UserVip();
                String name = result.getString("name");
                String vipActivatedString = result.getString("vipActivated");
                String uuid = result.getString("uuid");
                VipActive[] vipActives = gson.fromJson(vipActivatedString, VipActive[].class);
                List<VipActive> vipActivated = Arrays.stream(vipActives).collect(Collectors.toList());
                userVip.setName(name);
                userVip.setUuid(UUID.fromString(uuid));
                userVip.setVipsActivated(vipActivated);
                if (!(userVip.getName() == null)|| !userVip.getName().equals("")) {
                    userVips.put(userVip.getName(), userVip);
                }
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error on save UserVip, contact the developer");
            e.printStackTrace();
        }
    }

    public void unloadUserVip(String user) {
        userVips.remove(user);
    }

    public UserVip saveUserVip(UserVip userVip) {
        JsonElement vipsActivated = gson.toJsonTree(userVip.getVipsActivated());
        SQL sql = plugin.getDatabaseManager().getSql();
        try {
            PreparedStatement ps;
            ps = sql.prepareStatement("update guardianvips " +
                    "set vipActivated = '"+ vipsActivated + "' " +
                    "where uuid = '" + userVip.getUuid() + "';");

            ps.execute();
            ps.close();
            return userVip;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error on save UserVip, contact the developer");
            e.printStackTrace();
            return null;
        }
    }

    public UserVip create(UserVip userVip) {
        JsonElement vipsActivated = gson.toJsonTree(userVip.getVipsActivated());
        try {
            SQL sql = plugin.getDatabaseManager().getSql();
            PreparedStatement ps;
            ps = sql.prepareStatement("insert into guardianvips (name, uuid, vipActivated) " +
                    "values('" +userVip.getName() + "','" + userVip.getUuid() + "','" + vipsActivated + "')");

            ps.execute();
            ps.close();
        return userVip;
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error on save UserVip, contact the developer");
            e.printStackTrace();
            return null;
        }

    }

    public void saveUserVip(String user) {
        UserVip userVip = userVips.get(user);
        if (userVip != null) {
            saveUserVip(userVip);
        }
    }
    public UserVip seletcByName(Player player) {
        if (userVips.containsKey(player.getName())) {
            return userVips.get(player.getName());
        } else {
            return selectById(String.valueOf(player.getUniqueId()));
        }
    }

    private void initTables() {
        SQL sql = plugin.getDatabaseManager().getSql();
        try {
            PreparedStatement preparedStatement;
            if (plugin.getYamlConfig().getConfigFile().getString("storage.type").equalsIgnoreCase("MYSQL")) {
            preparedStatement = sql.prepareStatement("create table if not exists "+plugin.getYamlConfig().getConfigFile().getString("storage.db")+".guardianvips (name varchar(255) not null, uuid varchar(255) not null, vipActivated longtext)");
            } else {
                preparedStatement = sql.prepareStatement("create table if not exists guardianvips (name varchar(255) not null, uuid varchar(255) not null, vipActivated longtext)");
            }
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("Error on save UserVip, contact the developer");
            e.printStackTrace();
        }

    }

    public void removeUserVipOnMap(String key) {
        userVips.remove(key);
    }

}
