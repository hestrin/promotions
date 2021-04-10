package com.ocado.basket.bot;

import java.util.*;

class Position {
    private final int x;
    private final int y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(int x, int y) throws Exception {
        if (x < 0 || y < 0)
            throw new Exception("Invalid location");

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

class Bot {
    private final String name;
    private final AuditLog log;
    private int counter = 0;
    private Position position;

    public Bot(String name, AuditLog log, Position startingPosition) {
        this.name = name;
        this.log = log;
        this.position = startingPosition;
        if (isLoggingEnabled())
            recordPosition();
    }

    public static BotBuilder builder() {
        return new BotBuilder();
    }

    public List<String> getMessages() {
        return isLoggingEnabled() ? log.getMessages(name) : List.of(String.format("Logging is disabled for bot %s", name));
    }

    public void moveTo(Position position) {
        this.position = position;
        if (isLoggingEnabled())
            recordPosition();
    }

    private boolean isLoggingEnabled() {
        return log != null;
    }

    private void recordPosition() {
        addCurrentPositionToLog();
        incrementCounter();
    }

    private void addCurrentPositionToLog() {
        if (counter % 2 == 0)
            log.recordEvent(name, String.format("Bot %s is located at: %s", name, position.toString()));
    }

    private void incrementCounter() {
        counter++;
    }

    static class BotBuilder {
        private String name = UUID.randomUUID().toString().toUpperCase().substring(0, 5);
        private boolean logPosition = true;
        private Position startingPosition = new Position();

        public BotBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BotBuilder logPosition(boolean logPosition) {
            this.logPosition = logPosition;
            return this;
        }

        public BotBuilder startingPosition(Position startingPosition) {
            this.startingPosition = startingPosition;
            return this;
        }


        public Bot build() {
            AuditLog log = logPosition ? AuditLog.getInstance() : null;
            Bot bot = new Bot(this.name, log, this.startingPosition);
            return bot;
        }
    }
}

class AuditLog {
    private static final AuditLog instance = new AuditLog();
    private static final Map<String, List<String>> pendingMessages = new HashMap<>();

    private AuditLog() {}

    public static AuditLog getInstance() { return instance; }

    public List<String> getMessages(String producerName) {
        return List.copyOf(pendingMessages.getOrDefault(producerName, Collections.emptyList()));
    }

    public void recordEvent(String producer, String message) {
        synchronized (pendingMessages) {
            if (!pendingMessages.containsKey(producer))
                pendingMessages.put(producer, new LinkedList<>());

            pendingMessages.get(producer).add(message);
        }
    }

    public void flush(Database db) {
        synchronized (pendingMessages) {
            pendingMessages.values().forEach(m -> db.execute("insert into messages(message) values(" + m + ")"));
            pendingMessages.clear();
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
//        Database db = new Database.OracleJdbcDriver("admin", "123password");

        Bot silentBot = Bot.builder().logPosition(false).build();
        silentBot.moveTo(new Position());
        silentBot.moveTo(new Position());
        System.out.println(silentBot.getMessages());

        Bot missingOne = Bot.builder().name("Missing one").logPosition(true).startingPosition(new Position(1, 1)).build();
        missingOne.moveTo(new Position());
        missingOne.moveTo(new Position());
        missingOne.moveTo(new Position(5, 6));
        missingOne.moveTo(new Position(4, 3));
        missingOne.moveTo(new Position(2, 1));
        System.out.println(missingOne.getMessages());

        Bot secondOne = Bot.builder().name("Second one").logPosition(true).startingPosition(new Position(1, 1)).build();
        secondOne.moveTo(new Position());
        System.out.println(secondOne.getMessages());
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
