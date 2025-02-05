package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.TradeEntry;

import java.util.List;

public interface TradesTable {
    boolean addTrade(Integer from, Integer to, String message);
    TradeEntry getTradeWithId(Integer tradeId);
    List<TradeEntry> getTradesFromUser(Integer userId);
    List<TradeEntry> getTradesToUser(Integer userId);
    List<TradeEntry> getTradesWithStatus(String status);
    List<TradeEntry> getTradesFromUserWithStatus(Integer userId, String status);
    List<TradeEntry> getTradesToUserWithStatus(Integer userId, String status);
    List<TradeEntry> getTradesToUserWithRange(Integer userId, Integer start, Integer limit);
    List<TradeEntry> getTradesFromUserWithRange(Integer userId, Integer start, Integer limit);
    TradeEntry getLatestTradeFromUser(Integer userId);
}
