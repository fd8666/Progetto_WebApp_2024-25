package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.TradeEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.TradesTable;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class TradesTablePostgres implements TradesTable {
    private static final HashMap<Integer, TradeEntry> trades = new HashMap<>();
    private static TradesTablePostgres instance;
    public static final Logger logger = Logger.getLogger(TradesTablePostgres.class.getName());

    public static TradesTablePostgres getInstance() {
        if (instance == null) {
            instance = new TradesTablePostgres();
        }
        return instance;
    }

    private TradesTablePostgres() {}

    @Override
    public boolean addTrade(Integer from, Integer to, String status, String message) {
        return false;
    }

    @Override
    public TradeEntry getTradeWithId(Integer tradeId) {
        return null;
    }

    @Override
    public List<TradeEntry> getTradesFromUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<TradeEntry> getTradesToUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<TradeEntry> getTradesWithStatus(String status) {
        return List.of();
    }

    @Override
    public List<TradeEntry> getTradesFromUserWithStatus(Integer userId, String status) {
        return List.of();
    }

    @Override
    public List<TradeEntry> getTradesToUserWithStatus(Integer userId, String status) {
        return List.of();
    }
}
