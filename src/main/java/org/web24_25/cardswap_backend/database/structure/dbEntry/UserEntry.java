package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.sql.Date;

public interface UserEntry {
    Integer id();
    String username();
    String email();
    String password_hash();
    String google_id();
    Date creation_date();
    boolean setUsername(String username);
    boolean setPasswordHash(String password_hash);
}
