package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.OfferEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.OfferEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.OffersTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class OffersTablePostgres implements OffersTable {
    private static final HashMap<Integer, OfferEntry> offers = new HashMap<>();
    private static OffersTablePostgres instance;
    public static final Logger logger = Logger.getLogger(OffersTablePostgres.class.getName());

    private OffersTablePostgres() {}

    public static OffersTablePostgres getInstance() {
        if (instance == null) {
            instance = new OffersTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addOffer(Integer tradeId, Integer cardId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO offers (trade, card) VALUES (?, ?);");
                ps.setInt(1, tradeId);
                ps.setInt(2, cardId);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<OfferEntry> getOffersWithTrade(Integer tradeId) {
        ArrayList<OfferEntry> offers_list = new ArrayList<>();
        for (OfferEntry oe : offers.values()) {
            if (oe.trade().equals(tradeId)) {
                offers_list.add(oe);
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM offers WHERE trade = ?;");
                ps.setInt(1, tradeId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (offers_list.contains(offers.get(rs.getInt("id")))) {
                        offers_list.add(offers.get(rs.getInt("id")));
                    } else {
                        OfferEntry tep = new OfferEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("trade"),
                            rs.getInt("card")
                        );
                        offers.put(tep.id(), tep);
                        offers_list.add(tep);
                    }
                }
                rs.close();
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return offers_list;
    }

    @Override
    public OfferEntry getOfferWithId(Integer offerId) {
        if (offers.containsKey(offerId)) {
            return offers.get(offerId);
        } else {
            if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
                try {
                    PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM offers WHERE id = ?;");
                    ps.setInt(1, offerId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        OfferEntry tep = new OfferEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("trade"),
                            rs.getInt("card")
                        );
                        offers.put(tep.id(), tep);
                        rs.close();
                        return tep;
                    }
                } catch (SQLException e) {
                    logger.severe(e.getMessage());
                }
            }
        }
        return null;
    }
}
