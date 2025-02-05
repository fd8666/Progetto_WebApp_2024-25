package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.util.List;

public interface TradeEntry {
    Integer id();
    Integer from();
    Integer to();
    String status();
    String message();
    boolean accept();
    boolean reject();
    boolean setStatus(String status);
    boolean setMessage(String message);
    UserEntry getFrom();
    UserEntry getTo();
    List<OfferEntry> getOffers();
    List<RequestEntry> getRequests();
    String toJson();
}
