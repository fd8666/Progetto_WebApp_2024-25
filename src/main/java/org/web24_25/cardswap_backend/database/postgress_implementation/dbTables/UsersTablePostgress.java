package org.web24_25.cardswap_backend.database.postgress_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgress_implementation.DatabasePostgress;
import org.web24_25.cardswap_backend.database.postgress_implementation.dbEntry.UserEntryPostgress;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.UsersTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UsersTablePostgress implements UsersTable {
    private static Map<Integer, UserEntry> users;
    private static UsersTablePostgress instance;

    public static UsersTablePostgress getInstance() {
        if (instance == null) {
            instance = new UsersTablePostgress();
        }
        return instance;
    }

    private UsersTablePostgress() {}

    @Override
    public UserEntry createUser(String username, String password) {
        ResultSet rs = DatabasePostgress.getInstance().executeQuery("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "');");
        try {
            UserEntryPostgress uep = new UserEntryPostgress(rs.getInt("id"), rs.getString("username"));
            users.put(uep.id(), uep);
            rs.close();
            return uep;
        } catch (SQLException e) {
            //TODO: log error
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUser(int id) {
        if (users.containsKey(id)) {
            users.remove(id);
            DatabasePostgress.getInstance().executeUpdate("DELETE FROM users WHERE id = " + id + ";");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserEntry getUser(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            ResultSet rs = DatabasePostgress.getInstance().executeQuery("SELECT * FROM users WHERE id = " + id + ";");
            try {
                UserEntryPostgress uep = new UserEntryPostgress(rs.getInt("id"), rs.getString("username"));
                users.put(uep.id(), uep);
                rs.close();
                return uep;
            } catch (SQLException e) {

            }
        }
        return null;
    }

    @Override
    public List<UserEntry> getAllUsers() {
        return List.of();
    }
}
