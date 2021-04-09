package com.ocado.basket;

import java.util.ArrayList;
import java.util.List;

class Bot {
    private AuditLog log;
    private int counter = 0;
    private int x, y;

    public Bot(boolean shouldLog, Database database) {
        this.log = shouldLog ? new AuditLog(database) : null;
    }

    private boolean isLoggingEnabled(){
        return log != null;
    }
    public void setLocation(int x, int y) throws Exception {
        if (isValidLocation(x, y)) {
            throw new Exception("Invalid location");
        }
        this.x = x;
        this.y = y;

        updateAuditLog();
    }

    private boolean isValidLocation(int x, int y) {
        return x < 0 || y < 0;
    }

    private void updateAuditLog() {
        if (isLoggingEnabled()) {
            addCurrentPositionToLog();
            incrementCounter();
        }
    }

    private void incrementCounter() {
        counter++;
    }

    private void addCurrentPositionToLog() {
        if (counter % 2 == 0)
            log.recordEvent(String.format("%s: (%d, %d)", "Bot position", x, y));
    }

    public List<String> getMessages() {
        return isLoggingEnabled() ? log.getMessages() : List.of("Logging disabled");
    }
}

class AuditLog {
    private static final List<String> pendingMessages = new ArrayList<>();
    private final Database db;// = new OracleJdbcDriver("admin", "123password");

    AuditLog(Database database) {
        this.db = database;
    }

    public List<String> getMessages() {
        return List.copyOf(pendingMessages);
    }

    public void recordEvent(String message) {
        synchronized (pendingMessages) {
            pendingMessages.add(message);
        }
    }

    public void flush() {
        synchronized (pendingMessages) {
            pendingMessages.forEach(m -> db.execute("insert into messages(message) values(" + m + ")"));
            pendingMessages.clear();
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Database db = new Database.OracleJdbcDriver("admin", "123password");
        Bot bot = new Bot(true, db);

        bot.setLocation(1, 2);
        bot.setLocation(3, 4);
        System.out.println(bot.getMessages());

        bot.setLocation(5, 6);
        System.out.println(bot.getMessages());
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
