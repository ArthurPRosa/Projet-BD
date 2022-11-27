package demo;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.Connection;
import org.aesh.terminal.tty.Signal;
import org.aesh.terminal.utils.Config;

public class Console implements Consumer<org.aesh.terminal.Connection> {
    private static Readline commandReadline;
    private static Readline inputReadline;

    private static TerminalConnection tConnection;

    static private AtomicBoolean readlineAvailable = new AtomicBoolean(true);
    static private AtomicBoolean inParsing = new AtomicBoolean(false);
    static private String lastInput;

    public static void close() {
        tConnection.close();
    }

    public static void init() {
        try {
            tConnection = new TerminalConnection(new Console());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void listenCommands() {
        try {
            while (true) {
                while (!readlineAvailable.compareAndSet(true, false))
                    ;
                commandReadline.readline(tConnection, ">", (s) -> {
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            inParsing.set(true);
                            Command.parseCommand(s);
                            inParsing.set(false);
                        }
                    });
                    th.start();
                    readlineAvailable.set(true);
                });
                while (!readlineAvailable.get() || inParsing.get())
                    Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String read() {
        if (!readlineAvailable.compareAndSet(true, false))
            throw new IllegalStateException();
        inputReadline.readline(tConnection, "<", (s) -> {
            lastInput = s;
            readlineAvailable.set(true);
        });
        try {
            while (!readlineAvailable.get())
                Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastInput;
    }

    public static String read(String prompt) {
        System.out.println(prompt);
        return read();
    }

    // Available on AEsh github repo
    @Override
    public void accept(Connection connection) {
        commandReadline = ReadlineBuilder.builder().enableHistory(true).build();
        inputReadline = ReadlineBuilder.builder().enableHistory(false).build();
        // setting our own signal handler for ctrl-c signals, lets close if we get any
        connection.setSignalHandler(signal -> {
            if (signal == Signal.INT)
                connection.write(Config.getLineSeparator());
            connection.close();
            System.exit(0);
        });
        // lets open the connection to the terminal using this thread
        connection.openNonBlocking();
    }

    public static <T> T readWithParse(String prompt, Parser<T> parser) {
        try {
            return parser.parse(read(prompt));
        } catch (ParseException | NumberFormatException e) {
            System.out.println("Vous n'avez pas donné le bon type !" );
            return readWithParse(prompt, parser);
        }
    }

    public interface Parser<T> {
        T parse(String toParse) throws ParseException;
    }

}