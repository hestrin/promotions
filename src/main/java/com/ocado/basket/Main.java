package com.ocado.basket;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Bot {
    private Optional<AuditLog> l = Optional.empty();
    String message;
    public int throttleCounter;
    private int x, y;

    public Bot(boolean shouldLog, Database database) {
        if (shouldLog) {
            l = Optional.of(new AuditLog(database));
        }
    }

    public void setLocation(int x, int y) throws Exception {
        if (x < 0 || y < 0) {
            throw new Exception("Invalid location");
        }
        this.x = x;
        this.y = y;
        reportStatus();
    }

    private void reportStatus() {
        if (throttleCounter % 2 == 0) {
            message = String.format("%s: (%d, %d)", "Bot position", x, y);
            l.ifPresent(l -> l.recordEvent(message));
        }
        throttleCounter++;
    }
}

class AuditLog {
    private static final List<String> pendingMessages = new ArrayList<>();
    private final Database db;// = new OracleJdbcDriver("admin", "123password");

    AuditLog(Database database) {
        this.db = database;
    }

    public void recordEvent(String message) {
        synchronized (pendingMessages) {
            pendingMessages.add(message);
        }
    }

    public void flush() {
        synchronized (db) {
            pendingMessages.forEach(
                    m -> db.execute("insert into messages(message) values(" + m + ")")
            );
        }
        pendingMessages.clear();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        Database db = new Database.OracleJdbcDriver("admin", "123password");
        Bot bot = new Bot(false, db);


        bot.throttleCounter = 2;
        bot.setLocation(1, 2);
        bot.setLocation(3, 4);
        System.out.println(bot.message);
//        Assert.assertEquals("Bot position" + ": (1, 2)", bot.message);

        bot.throttleCounter = 2;
        bot.setLocation(5, 6);
        System.out.println(bot.message);
//        Assert.assertEquals("Bot position" + ": (5, 6)", bot.message);
    }
}

// Infrastucture code - only exists for compilation purposes
interface Database {
    default void execute(String sql) {
        /* ... */
    }

    static class OracleJdbcDriver implements Database {
        public OracleJdbcDriver(String user, String password) {
            /* ... */
        }
    }
}
