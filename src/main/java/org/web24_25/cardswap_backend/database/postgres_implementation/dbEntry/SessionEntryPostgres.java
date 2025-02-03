package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;

public final class SessionEntryPostgres implements SessionEntry {
    private final Integer id;
    private final Integer user_id;
    private final String ipv4;
    private final String cookie;
    private final String user_agent;
    private final Date creation_date;
    private final Integer time_to_live;
    private Boolean valid;
    private final String private_key;

    private static final Logger logger = Logger.getLogger(SessionEntryPostgres.class.getName());

    public SessionEntryPostgres(Integer id, Integer user_id, String ipv4, String cookie, String user_agent, Date creation_date, Integer time_to_live, Boolean valid, String private_key) {
        this.id = id;
        this.user_id = user_id;
        this.ipv4 = ipv4;
        this.cookie = cookie;
        this.user_agent = user_agent;
        this.creation_date = creation_date;
        this.time_to_live = time_to_live;
        this.valid = valid;
        this.private_key = private_key;
    }

    public Integer id() {
        return id;
    }

    public Integer user_id() {
        return user_id;
    }

    public String ipv4() {
        return ipv4;
    }

    public String cookie() {
        return cookie;
    }

    public String user_agent() {
        return user_agent;
    }

    public Date creation_date() {
        return creation_date;
    }

    public Integer time_to_live() {
        return time_to_live;
    }

    public Boolean valid() {
        if (valid && creation_date.toLocalDate().plusDays(time_to_live / 3600).isAfter(LocalDate.now())) {
            valid = false;
        }
        return valid;
    }

    @Override
    public String private_key() {
        return private_key;
    }

    @Override
    public boolean invalidate() {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE sessions SET valid = ? WHERE id = ?;");
                ps.setBoolean(1, false);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.valid = false;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SessionEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.user_id, that.user_id) &&
                Objects.equals(this.ipv4, that.ipv4) &&
                Objects.equals(this.cookie, that.cookie) &&
                Objects.equals(this.user_agent, that.user_agent) &&
                Objects.equals(this.creation_date, that.creation_date) &&
                Objects.equals(this.time_to_live, that.time_to_live) &&
                Objects.equals(this.valid, that.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, ipv4, cookie, user_agent, creation_date, time_to_live, valid);
    }

    @Override
    public String toString() {
        return "SessionEntryPostgres[" +
                "id=" + id + ", " +
                "user_id=" + user_id + ", " +
                "ipv4=" + ipv4 + ", " +
                "cookie=" + cookie + ", " +
                "user_agent=" + user_agent + ", " +
                "creation_date=" + creation_date + ", " +
                "time_to_live=" + time_to_live + ", " +
                "valid=" + valid + ']';
    }
}
