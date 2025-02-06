package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.CardsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.InventoryEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class InventoryEntryPostgres implements InventoryEntry {
    private final Integer id;
    private final Integer user;
    private final Integer card;
    private Integer amount;

    private final Logger logger = Logger.getLogger(InventoryEntryPostgres.class.getName());

    public InventoryEntryPostgres(Integer id, Integer user, Integer card, Integer amount) {
        this.id = id;
        this.user = user;
        this.card = card;
        this.amount = amount;
    }

    public Integer id() {
        return id;
    }

    public Integer user() {
        return user;
    }

    public Integer card() {
        return card;
    }

    public Integer amount() {
        return amount;
    }

    @Override
    public CardEntry getCard() {
        return CardsTablePostgres.getInstance().getCardWithId(card);
    }

    @Override
    public UserEntry getUser() {
        return UsersTablePostgres.getInstance().getUserWithId(user);
    }

    @Override
    public boolean setAmount(Integer amount) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE inventories SET amount = ? WHERE id = ?;");
                ps.setInt(1, amount);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.amount = amount;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean incrementAmount(Integer amount) {
        return setAmount(this.amount + amount);
    }

    @Override
    public boolean decrementAmount(Integer amount) {
        return setAmount(this.amount - amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InventoryEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.card, that.card) &&
                Objects.equals(this.amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, card, amount);
    }

    @Override
    public String toString() {
        return "InventoryEntryPostgres[" +
                "id=" + id + ", " +
                "user=" + user + ", " +
                "card=" + card + ", " +
                "amount=" + amount + ']';
    }

}
