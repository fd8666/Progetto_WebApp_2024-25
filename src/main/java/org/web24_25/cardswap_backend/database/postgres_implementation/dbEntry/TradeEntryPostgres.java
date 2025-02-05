package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.OffersTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.RequestsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.OfferEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.RequestEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TradeEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class TradeEntryPostgres implements TradeEntry {
    private final Integer id;
    private final Integer from;
    private final Integer to;
    private final String status;
    private String message;

    private final Logger logger = Logger.getLogger(TradeEntryPostgres.class.getName());

    public TradeEntryPostgres(Integer id, Integer from, Integer to, String status, String message) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.status = status;
        this.message = message;
    }

    public Integer id() {
        return id;
    }

    public Integer from() {
        return from;
    }

    public Integer to() {
        return to;
    }

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean accept() {
        if (status.equals("accepted") || status.equals("rejected")) return false;
        return setStatus("accepted");
    }

    @Override
    public boolean reject() {
        if (status.equals("accepted") || status.equals("rejected")) return false;
        return setStatus("refused");
    }

    @Override
    public boolean setStatus(String status) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE trades SET status = ? WHERE id = ?;");
                ps.setString(1, status);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                return res != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean setMessage(String message) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE trades SET message = ? WHERE id = ?;");
                ps.setString(1, message);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.message = message;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public UserEntry getFrom() {
        return UsersTablePostgres.getInstance().getUserWithId(from);
    }

    @Override
    public UserEntry getTo() {
        return UsersTablePostgres.getInstance().getUserWithId(from);
    }

    @Override
    public List<OfferEntry> getOffers() {
        return OffersTablePostgres.getInstance().getOffersWithTrade(id);
    }

    @Override
    public List<RequestEntry> getRequests() {
        return RequestsTablePostgres.getInstance().getRequestsWithTrade(id);
    }

    @Override
    public String toJson() {
        return "{\"id\":" + id + ",\"from\":" + from + ",\"to\":" + to + ",\"status\":\"" + status + "\",\"message\":\"" + message + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TradeEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to) &&
                Objects.equals(this.status, that.status) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, status, message);
    }

    @Override
    public String toString() {
        return "TradeEntryPostgres[" +
                "id=" + id + ", " +
                "from=" + from + ", " +
                "to=" + to + ", " +
                "status=" + status + ", " +
                "message=" + message + ']';
    }

}
