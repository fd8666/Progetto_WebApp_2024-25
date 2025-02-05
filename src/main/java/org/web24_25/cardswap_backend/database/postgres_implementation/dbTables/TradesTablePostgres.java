package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.TradeEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TradeEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.TradesTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class TradesTablePostgres implements TradesTable {
    private static final HashMap<Integer, TradeEntry> trades = new HashMap<>();
    private static TradesTablePostgres instance;
    public static final Logger logger = Logger.getLogger(TradesTablePostgres.class.getName());

    public static TradesTablePostgres getInstance() {
        if (instance == null) {
            instance = new TradesTablePostgres();
        }
        return instance;
    }

    private TradesTablePostgres() {}

    @Override
    public boolean addTrade(Integer from, Integer to, String message) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("INSERT INTO trades (\"from\", \"to\", message) VALUES (?, ?, ?);");
                ps.setInt(1, from);
                ps.setInt(2, to);
                ps.setString(3, message);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public TradeEntry getTradeWithId(Integer tradeId) {
        if (trades.containsKey(tradeId)) {
            return trades.get(tradeId);
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE id = ?;");
                ps.setInt(1, tradeId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades.put(tep.id(), tep);
                    rs.close();
                    return tep;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    private TradeEntry makeTradeEntryFromResultSet(ResultSet rs) throws SQLException {
        if (trades.containsKey(rs.getInt("id"))) {
            return trades.get(rs.getInt("id"));
        }
        TradeEntryPostgres tep = new TradeEntryPostgres(
            rs.getInt("id"),
            rs.getInt("from"),
            rs.getInt("to"),
            rs.getString("status"),
            rs.getString("message")
        );
        trades.put(tep.id(), tep);
        return tep;
    }

    @Override
    public List<TradeEntry> getTradesFromUser(Integer userId) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"from\" = ?;");
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades_list.add(tep);
                    trades.put(tep.id(), tep);
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesToUser(Integer userId) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"to\" = ?;");
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades_list.add(tep);
                    trades.put(tep.id(), tep);
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesWithStatus(String status) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE status = ?;");
                ps.setString(1, status);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades_list.add(tep);
                    trades.put(tep.id(), tep);
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesFromUserWithStatus(Integer userId, String status) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"from\" = ? AND status = ?;");
                ps.setInt(1, userId);
                ps.setString(2, status);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades_list.add(tep);
                    trades.put(tep.id(), tep);
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesToUserWithStatus(Integer userId, String status) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"to\" = ? AND status = ?;");
                ps.setInt(1, userId);
                ps.setString(2, status);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    TradeEntryPostgres tep = new TradeEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("from"),
                        rs.getInt("to"),
                        rs.getString("status"),
                        rs.getString("message")
                    );
                    trades_list.add(tep);
                    trades.put(tep.id(), tep);
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesToUserWithRange(Integer userId, Integer start, Integer limit) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"to\" = ? AND id > ? LIMIT ?;");
                ps.setInt(1, userId);
                ps.setInt(2, start);
                ps.setInt(3, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    trades_list.add(makeTradeEntryFromResultSet(rs));
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public List<TradeEntry> getTradesFromUserWithRange(Integer userId, Integer start, Integer limit) {
        ArrayList<TradeEntry> trades_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"from\" = ? AND id > ? LIMIT ?;");
                ps.setInt(1, userId);
                ps.setInt(2, start);
                ps.setInt(3, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (trades.containsKey(rs.getInt("id"))) {
                        trades_list.add(trades.get(rs.getInt("id")));
                        continue;
                    }
                    trades_list.add(makeTradeEntryFromResultSet(rs));
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return trades_list;
    }

    @Override
    public TradeEntry getLatestTradeFromUser(Integer userId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM trades WHERE \"from\" = ? ORDER BY id DESC LIMIT 1;");
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return makeTradeEntryFromResultSet(rs);
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }
}
