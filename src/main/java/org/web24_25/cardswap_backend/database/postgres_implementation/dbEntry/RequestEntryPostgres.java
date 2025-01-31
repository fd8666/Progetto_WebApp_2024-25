package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.RequestEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TradeEntry;

import java.util.Objects;
import java.util.logging.Logger;

public final class RequestEntryPostgres implements RequestEntry {
    private final Integer id;
    private final Integer trade;
    private final Integer card;

    private final Logger logger = Logger.getLogger(RequestEntryPostgres.class.getName());

    public RequestEntryPostgres(Integer id, Integer trade, Integer card) {
        this.id = id;
        this.trade = trade;
        this.card = card;
    }

    public Integer id() {
        return id;
    }

    public Integer trade() {
        return trade;
    }

    public Integer card() {
        return card;
    }

    @Override
    public TradeEntry getTrade() {
        return null;
    }

    @Override
    public CardEntry getCard() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RequestEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.trade, that.trade) &&
                Objects.equals(this.card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trade, card);
    }

    @Override
    public String toString() {
        return "OfferEntryPostgres[" +
                "id=" + id + ", " +
                "trade=" + trade + ", " +
                "card=" + card + ']';
    }

}
