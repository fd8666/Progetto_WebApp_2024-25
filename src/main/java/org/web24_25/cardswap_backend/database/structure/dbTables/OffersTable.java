package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.OfferEntry;

import java.util.List;

public interface OffersTable {
    boolean addOffer(Integer tradeId, Integer cardId);
    List<OfferEntry> getOffersWithTrade(Integer tradeId);
    OfferEntry getOfferWithId(Integer offerId);
}
