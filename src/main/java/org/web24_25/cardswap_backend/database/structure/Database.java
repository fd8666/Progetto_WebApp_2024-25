package org.web24_25.cardswap_backend.database.structure;

public interface Database {
    static Database getInstance() {
        return null;
    }
    boolean verifyConnectionAndReconnect();
    boolean isConnected();
    boolean reconnect();
    boolean closeConnection();
}
