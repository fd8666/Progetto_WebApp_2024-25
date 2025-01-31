package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.RequestEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.RequestEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.RequestsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class RequestsTablePostgres implements RequestsTable {
    private static final HashMap<Integer, RequestEntry> requests = new HashMap<>();
    private static RequestsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(RequestsTablePostgres.class.getName());

    public static RequestsTablePostgres getInstance() {
        if (instance == null) {
            instance = new RequestsTablePostgres();
        }
        return instance;
    }

    private RequestsTablePostgres() {}

    @Override
    public boolean addRequest(Integer tradeId, Integer cardId) {
        return false;
    }

    @Override
    public List<RequestEntry> getRequestsWithTrade(Integer tradeId) {
        ArrayList<RequestEntry> requests_list = new ArrayList<>();
        for (RequestEntry re : requests.values()) {
            if (re.trade().equals(tradeId)) {
                requests_list.add(re);
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM requests WHERE trade = ?;");
                ps.setInt(1, tradeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (requests_list.contains(requests.get(rs.getInt("id")))) {
                        requests_list.add(requests.get(rs.getInt("id")));
                    } else {
                        RequestEntry rep = new RequestEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("trade"),
                            rs.getInt("card")
                        );
                        requests.put(rep.id(), rep);
                        requests_list.add(rep);
                    }
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return requests_list;
    }

    @Override
    public RequestEntry getRequestWithId(Integer RequestId) {
        if (requests.containsKey(RequestId)) {
            return requests.get(RequestId);
        } else {
            if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
                try {
                    PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM requests WHERE id = ?;");
                    ps.setInt(1, RequestId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        RequestEntry rep = new RequestEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("trade"),
                            rs.getInt("card")
                        );
                        requests.put(rep.id(), rep);
                        rs.close();
                        return rep;
                    }
                } catch (SQLException e) {
                    logger.severe(e.getMessage());
                }
            }
        }
        return null;
    }
}
