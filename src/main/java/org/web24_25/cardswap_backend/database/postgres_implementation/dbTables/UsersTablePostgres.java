package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.UserEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.UsersTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersTablePostgres implements UsersTable {
    private static final HashMap<Integer, UserEntry> users = new HashMap<>();
    private static UsersTablePostgres instance;

    public static UsersTablePostgres getInstance() {
        if (instance == null) {
            instance = new UsersTablePostgres();
        }
        return instance;
    }

    private UsersTablePostgres() {}

    @Override
    public boolean createUserWithPassword( String username, String email, String password_hash ) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?);");
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password_hash);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                //TODO: log error
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean createUserWithGoogle(String username, String email, String google_id) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("INSERT INTO users (username, email, google_id) VALUES (?, ?, ?);");
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, google_id);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                //TODO: log error
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("DELETE FROM users WHERE id = ?;");
                ps.setInt(1, id);
                ps.executeUpdate();
                users.remove(id);
                return true;
            } catch (SQLException e) {
                //TODO: log error
            }
        }
        return false;
    }

    @Override
    public UserEntry getUserFromId(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
                try {
                    PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM users WHERE id = ?;");
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        UserEntryPostgres uep = new UserEntryPostgres(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("password_hash"),
                                rs.getString("google_id"),
                                rs.getDate("creation_date")
                        );
                        users.put(uep.id(), uep);
                        rs.close();
                        return uep;
                    }
                } catch (SQLException e) {
                    DatabasePostgres.logger.severe(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public UserEntry getUserFromUsername(String username) {
        for (UserEntry ue : users.values()) {
            if (ue.username().equals(username)) {
                return ue;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM users WHERE username = ?;");
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    UserEntryPostgres uep = new UserEntryPostgres(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("google_id"),
                            rs.getDate("creation_date")
                    );
                    users.put(uep.id(), uep);
                    rs.close();
                    return uep;
                }
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return null;
    }

    public List<UserEntry> getUsersRange(int start_id, int limit) {
        List<UserEntry> user_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM users WHERE id >= ? LIMIT ?;");
                ps.setInt(1, start_id);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (users.containsKey(rs.getInt("id"))) {
                        user_list.add(users.get(rs.getInt("id")));
                        continue;
                    }
                    UserEntryPostgres uep = new UserEntryPostgres(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("google_id"),
                            rs.getDate("creation_date")
                    );
                    users.put(uep.id(), uep);
                    user_list.add(uep);
                }
                rs.close();
                return user_list;
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return user_list;
    }

    public int getUserCount() {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT COUNT(*) FROM users;");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return 0;
    }
}
