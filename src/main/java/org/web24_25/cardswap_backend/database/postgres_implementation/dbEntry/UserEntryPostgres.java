package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class UserEntryPostgres implements UserEntry {
    private final Integer id;
    private String username;
    private final String email;
    private String password_hash;
    private final String google_id;
    private final Date creation_date;

    public static final Logger logger = Logger.getLogger(UserEntryPostgres.class.getName());

    public UserEntryPostgres(Integer id, String username, String email, String password_hash, String google_id, Date creation_date) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(username);
        Objects.requireNonNull(email);
        Objects.requireNonNull(creation_date);
        if (password_hash == null && google_id == null) {
            throw new IllegalArgumentException("Either password_hash or google_id must be non-null");
        }
        this.id = id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.google_id = google_id;
        this.creation_date = creation_date;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public boolean setUsername(String username) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE users SET username = ? WHERE id = ?;");
                ps.setString(1, username);
                ps.setInt(2, this.id);
                int result = ps.executeUpdate();
                if (result != 0) {
                    this.username = username;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean setPasswordHash(String password_hash) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE users SET password_hash = ? WHERE id = ?;");
                ps.setString(1, password_hash);
                ps.setInt(2, this.id);
                int result = ps.executeUpdate();
                if (result != 0) {
                    this.password_hash = password_hash;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password_hash() {
        return password_hash;
    }

    @Override
    public String google_id() {
        return google_id;
    }

    @Override
    public Date creation_date() {
        return creation_date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.password_hash, that.password_hash) &&
                Objects.equals(this.google_id, that.google_id) &&
                Objects.equals(this.creation_date, that.creation_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password_hash, google_id, creation_date);
    }

    @Override
    public String toString() {
        return "UserEntryPostgress[" +
                "id=" + id + ", " +
                "username=" + username + ", " +
                "email=" + email + ", " +
                "password_hash=" + password_hash + ", " +
                "google_id=" + google_id + ", " +
                "creation_date=" + creation_date + ']';
    }
}