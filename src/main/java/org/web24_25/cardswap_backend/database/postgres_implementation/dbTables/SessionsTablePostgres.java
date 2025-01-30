package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.SessionEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.SessionsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class SessionsTablePostgres implements SessionsTable {
    private static final HashMap<Integer, SessionEntry> sessions = new HashMap<>();
    private static SessionsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(SessionsTablePostgres.class.getName());

    public static SessionsTablePostgres getInstance() {
        if (instance == null) {
            instance = new SessionsTablePostgres();
        }
        return instance;
    }

    private SessionsTablePostgres() {}

    @Override
    public boolean createSession(int user_id, String cookie) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("INSERT INTO sessions (user_id, cookie) VALUES (?, ?);");
                ps.setInt(1, user_id);
                ps.setString(2, cookie);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public SessionEntry getSessionById(int id) {
        if (sessions.containsKey(id)) {
            return sessions.get(id);
        } else {
            if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
                try {
                    PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM sessions WHERE id = ?;");
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        SessionEntryPostgres sep = new SessionEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("ipv4"),
                            rs.getString("cookie"),
                            rs.getString("user_agent"),
                            rs.getDate("creation_date"),
                            rs.getInt("time_to_live"),
                            rs.getBoolean("valid")
                        );
                        sessions.put(sep.id(), sep);
                        rs.close();
                        return sep;
                    }
                } catch (SQLException e) {
                    DatabasePostgres.logger.severe(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public SessionEntry getSessionByCookie(String cookie) {
        for (SessionEntry se : sessions.values()) {
            if (se.cookie().equals(cookie)) {
                return se;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM users WHERE username = ?;");
                ps.setString(1, cookie);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    SessionEntryPostgres uep = new SessionEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("ipv4"),
                        rs.getString("cookie"),
                        rs.getString("user_agent"),
                        rs.getDate("creation_date"),
                        rs.getInt("time_to_live"),
                        rs.getBoolean("valid")
                    );
                    sessions.put(uep.id(), uep);
                    rs.close();
                    return uep;
                }
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<SessionEntry> getValidUsersSessions(int start_id, int limit) {
        List<SessionEntry> sessions_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM valid_sessions WHERE id >= ? LIMIT ?;");
                ps.setInt(1, start_id);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (sessions.containsKey(rs.getInt("id"))) {
                        sessions_list.add(sessions.get(rs.getInt("id")));
                        continue;
                    }
                    SessionEntryPostgres sep = new SessionEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("ipv4"),
                        rs.getString("cookie"),
                        rs.getString("user_agent"),
                        rs.getDate("creation_date"),
                        rs.getInt("time_to_live"),
                        rs.getBoolean("valid")
                    );
                    sessions.put(sep.id(), sep);
                    sessions_list.add(sep);
                }
                rs.close();
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return sessions_list;
    }

    @Override
    public List<SessionEntry> getValidUserSessions(int user_id) {
        List<SessionEntry> sessions_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM valid_sessions WHERE id = ?;");
                ps.setInt(1, user_id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (sessions.containsKey(rs.getInt("id"))) {
                        sessions_list.add(sessions.get(rs.getInt("id")));
                        continue;
                    }
                    SessionEntryPostgres sep = new SessionEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("ipv4"),
                            rs.getString("cookie"),
                            rs.getString("user_agent"),
                            rs.getDate("creation_date"),
                            rs.getInt("time_to_live"),
                            rs.getBoolean("valid")
                    );
                    sessions.put(sep.id(), sep);
                    sessions_list.add(sep);
                }
                rs.close();
            } catch (SQLException e) {
                DatabasePostgres.logger.severe(e.getMessage());
            }
        }
        return sessions_list;
    }
}
