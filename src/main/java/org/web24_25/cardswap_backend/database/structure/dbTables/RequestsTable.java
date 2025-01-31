package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.RequestEntry;

import java.util.List;

public interface RequestsTable {
    boolean addRequest(Integer tradeId, Integer cardId);
    List<RequestEntry> getRequestsWithTrade(Integer tradeId);
    RequestEntry getRequestWithId(Integer offerId);
}
