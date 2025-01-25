package org.web24_25.cardswap_backend.database.postgress_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.util.Objects;

public class UserEntryPostgress implements UserEntry {
    private final int id;
    private final String name;

    public UserEntryPostgress(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserEntryPostgress) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Test[" +
                "id=" + id + ", " +
                "name=" + name + ']';
    }
}
