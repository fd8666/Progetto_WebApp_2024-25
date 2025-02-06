package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.*;
import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.requests.CreateTrade;

import java.util.Objects;

@Service
public class TradeService {
    public TradeService() {}

    public ResponseEntity<String> createTrade(CreateTrade trade, HttpSession session) {
        if (trade.to() == null || trade.offer() == null || trade.request() == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Missing fields\"}");
        }
        if (trade.offer().isEmpty() || trade.request().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"result\":\"Empty offer or request\"}");
        }

        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            TradesTablePostgres.getInstance().addTrade(userEntry.id(), trade.to(), trade.message());
            var t = TradesTablePostgres.getInstance().getLatestTradeFromUser(userEntry.id());
            for (Integer cardId : trade.offer()) {
                OffersTablePostgres.getInstance().addOffer(t.id(), cardId);
            }
            for (Integer cardId : trade.request()) {
                RequestsTablePostgres.getInstance().addRequest(t.id(), cardId);
            }
            return ResponseEntity.ok(t.id().toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error creating trade\"}");
        }
    }

    public ResponseEntity<String> getTrade(int id, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            var trade = TradesTablePostgres.getInstance().getTradeWithId(id);
            if (trade == null) {
                return ResponseEntity.badRequest().body("{\"result\":\"Trade not found\"}");
            }
            if (!Objects.equals(userEntry.id(), trade.from()) && !Objects.equals(userEntry.id(), trade.to())) {
                return ResponseEntity.badRequest().body("{\"result\":\"Unauthorized\"}");
            }
            return ResponseEntity.ok(trade.toJson());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trade\"}");
        }
    }

    public ResponseEntity<String> getOffers(int id, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            var trade = TradesTablePostgres.getInstance().getTradeWithId(id);
            if (trade == null) {
                return ResponseEntity.badRequest().body("{\"result\":\"Trade not found\"}");
            }
            if (!Objects.equals(userEntry.id(), trade.from()) && !Objects.equals(userEntry.id(), trade.to())) {
                return ResponseEntity.badRequest().body("{\"result\":\"Unauthorized\"}");
            }
            StringBuilder offers = new StringBuilder("{ \"offers\": [");
            for (var offer : OffersTablePostgres.getInstance().getOffersWithTrade(trade.id())) {
                offers.append(offer.id()).append(",");
            }
            if (offers.toString().endsWith(",")) {
                offers = new StringBuilder(offers.substring(0, offers.length() - 1));
            }
            offers.append("]}");
            return ResponseEntity.ok(offers.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trade\"}");
        }
    }

    public ResponseEntity<String> getRequests(int id, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            var trade = TradesTablePostgres.getInstance().getTradeWithId(id);
            if (trade == null) {
                return ResponseEntity.badRequest().body("{\"result\":\"Trade not found\"}");
            }
            if (!Objects.equals(userEntry.id(), trade.from()) && !Objects.equals(userEntry.id(), trade.to())) {
                return ResponseEntity.badRequest().body("{\"result\":\"Unauthorized\"}");
            }
            StringBuilder requests = new StringBuilder("{ \"requests\": [");
            for (var offer : RequestsTablePostgres.getInstance().getRequestsWithTrade(trade.id())) {
                requests.append(offer.id()).append(",");
            }
            if (requests.toString().endsWith(",")) {
                requests = new StringBuilder(requests.substring(0, requests.length() - 1));
            }
            requests.append("]}");
            return ResponseEntity.ok(requests.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trade\"}");
        }
    }

    public ResponseEntity<String> getTradesToUser(HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        try {
            StringBuilder trades = new StringBuilder("{ \"trades\": [");
            for (var trade : TradesTablePostgres.getInstance().getTradesToUser(userEntry.id())) {
                trades.append(trade.toJson()).append(",");
            }
            if (trades.toString().endsWith(",")) {
                trades = new StringBuilder(trades.substring(0, trades.length() - 1));
            }
            trades.append("]}");
            return ResponseEntity.ok(trades.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trades\"}");
        }
    }

    public ResponseEntity<String> getTradesFromUser(HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        try {
            StringBuilder trades = new StringBuilder("{ \"trades\": [");
            for (var trade : TradesTablePostgres.getInstance().getTradesFromUser(userEntry.id())) {
                trades.append(trade.toJson()).append(",");
            }
            if (trades.toString().endsWith(",")) {
                trades = new StringBuilder(trades.substring(0, trades.length() - 1));
            }
            trades.append("]}");
            return ResponseEntity.ok(trades.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trades\"}");
        }
    }

    public ResponseEntity<String> acceptTrade(int id, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        try {
            var trade = TradesTablePostgres.getInstance().getTradeWithId(id);
            if (trade == null) {
                return ResponseEntity.badRequest().body("{\"result\":\"Trade not found\"}");
            }
            if (!Objects.equals(userEntry.id(), trade.to())) {
                return ResponseEntity.badRequest().body("{\"result\":\"Unauthorized\"}");
            }
            if (trade.accept())
                return ResponseEntity.ok("{\"result\":\"Trade accepted\"}");
            else
                return ResponseEntity.badRequest().body("{\"result\":\"Error accepting trade\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error accepting trade\"}");
        }
    }

    public ResponseEntity<String> rejectTrade(int id, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }

        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }
        try {
            var trade = TradesTablePostgres.getInstance().getTradeWithId(id);
            if (trade == null) {
                return ResponseEntity.badRequest().body("{\"result\":\"Trade not found\"}");
            }
            if (!Objects.equals(userEntry.id(), trade.to())) {
                return ResponseEntity.badRequest().body("{\"result\":\"Unauthorized\"}");
            }
            if (trade.reject())
                return ResponseEntity.ok("{\"result\":\"Trade rejected\"}");
            else
                return ResponseEntity.badRequest().body("{\"result\":\"Error rejecting trade\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error rejecting trade\"}");
        }
    }

    public ResponseEntity<String> getTradesToUserRange(int start, int limit, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            StringBuilder trades = new StringBuilder("{ \"trades\": [");
            for (var trade : TradesTablePostgres.getInstance().getTradesToUserWithRange(userEntry.id(), start, limit)) {
                trades.append(trade.toJson()).append(",");
            }
            if (trades.toString().endsWith(",")) {
                trades = new StringBuilder(trades.substring(0, trades.length() - 1));
            }
            trades.append("]}");
            return ResponseEntity.ok(trades.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trades\"}");
        }
    }

    public ResponseEntity<String> getTradesFromUserRange(int start, int limit, HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        SessionEntry sessionEntry = SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
        if (sessionEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
        }
        UserEntry userEntry = UsersTablePostgres.getInstance().getUserWithId(sessionEntry.user_id());
        if (userEntry == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
        }

        try {
            StringBuilder trades = new StringBuilder("{ \"trades\": [");
            for (var trade : TradesTablePostgres.getInstance().getTradesFromUserWithRange(userEntry.id(), start, limit)) {
                trades.append(trade.toJson()).append(",");
            }
            if (trades.toString().endsWith(",")) {
                trades = new StringBuilder(trades.substring(0, trades.length() - 1));
            }
            trades.append("]}");
            return ResponseEntity.ok(trades.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting trades\"}");
        }
    }
}
