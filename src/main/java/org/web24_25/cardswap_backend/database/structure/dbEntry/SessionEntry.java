package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.sql.Date;

public interface SessionEntry {
    Integer id();
    Integer user_id();
    String ipv4();
    String cookie();
    String user_agent();
    Date creation_date();
    Integer time_to_live();
    Boolean valid();
}
