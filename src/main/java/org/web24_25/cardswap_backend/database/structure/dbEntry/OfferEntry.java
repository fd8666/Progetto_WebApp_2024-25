package org.web24_25.cardswap_backend.database.structure.dbEntry;

public interface OfferEntry {
    Integer id();
    Integer trade();
    Integer card();
    TradeEntry getTrade();
    CardEntry getCard();
}
