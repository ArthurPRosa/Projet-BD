package demo;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.completion.Completion;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.Connection;
import org.aesh.terminal.tty.Signal;
import org.aesh.terminal.utils.Config;

/**
 * Handling the I/O of the console
 */
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

    /**
     * Initialize the console
     */
    public static void init() {
        try {
            tConnection = new TerminalConnection(new Console());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Prompt for the user to type a command
     */
    public static void prompt() {
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
                }, getCompletions());
                while (!readlineAvailable.get() || inParsing.get())
                    Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Prompt the user to enter some data
     * 
     * @return data from the user
     */
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

    /**
     * Writes a message on the terminal and lets the user enter some data
     * 
     * @param prompt message to display
     * @return data from the user
     */
    public static String read(String prompt) {
        if (prompt != null)
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
        // let open the connection to the terminal using this thread
        connection.openNonBlocking();
    }

    /**
     * @param prompt message to display
     * @param parser how to parse the data from the user
     * @param <T>    data type
     * @return the data from the user, with type T
     */
    public static <T> T readWithParse(String prompt, Parser<T> parser) {
        try {
            return parser.parse(read(prompt));
        } catch (ParseException | NumberFormatException e) {
            System.out.println("Vous n'avez pas donné le bon type !");
            return readWithParse(prompt, parser);
        }
    }

    private static List<Completion> getCompletions() {
        List<Completion> completions = new LinkedList<>();
        completions.add(completeOperation -> {
            for (String cmd : Command.commands) {
                if (cmd.startsWith(completeOperation.getBuffer()))
                    completeOperation.addCompletionCandidate(cmd);
            }

        });
        return completions;
    }

    public interface Parser<T> {
        T parse(String toParse) throws ParseException;
    }

}
