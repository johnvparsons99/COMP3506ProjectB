import java.util.*;
import java.util.stream.Collectors;

public class Airport extends AirportBase {

    /**
     * Creates a new AirportBase instance with the given capacity.
     *
     * @param capacity capacity of the airport shuttles
     *                 (same for all shuttles)
     */
    public Airport(int capacity) {
        super(capacity);
    }

    /* Implement all the necessary methods of the Airport here */
    /**
     * Given a terminal and a shuttle, returns the other terminal that the
     * shuttle travels between.
     *
     * @param shuttle  shuttle to look for opposite terminal on
     * @param terminal terminal to find opposite of
     * @return opposite terminal or null if the shuttle is not incident to
     * the given terminal
     */
    @Override
    public TerminalBase opposite(ShuttleBase shuttle, TerminalBase terminal) {
        return null;
    }

    /**
     * Adds the given terminal to the airport, and returns the added terminal.
     *
     * @param terminal terminal to add
     * @return terminal that was added
     */
    @Override
    public TerminalBase insertTerminal(TerminalBase terminal) {
        return null;
    }

    /**
     * Creates and returns a new shuttle connecting origin to destination.
     * All shuttles are bidirectional.
     *
     * @param origin      origin terminal of shuttle
     * @param destination destination terminal of shuttle
     * @param time        time it takes to go from origin to destination, in minutes
     * @return newly created shuttle
     */
    @Override
    public ShuttleBase insertShuttle(TerminalBase origin, TerminalBase destination, int time) {
        return null;
    }

    /**
     * Removes the given terminal and all of its incident shuttles from
     * the airport. All shuttles going to/from the given terminal should
     * be removed.
     *
     * @param terminal terminal to remove
     * @return true if removed successfully, false otherwise (if the terminal
     * was not in the airport)
     */
    @Override
    public boolean removeTerminal(TerminalBase terminal) {
        return false;
    }

    /**
     * Removes the given shuttle from the airport.
     *
     * @param shuttle shuttle to remove
     * @return true if removed successfully, false otherwise (if the shuttle
     * was not in the airport)
     */
    @Override
    public boolean removeShuttle(ShuttleBase shuttle) {
        return false;
    }

    /**
     * Returns a list of all shuttles incident to the given terminal.
     *
     * @param terminal terminal to find incident shuttles of
     * @return list of incident shuttles
     */
    @Override
    public List<ShuttleBase> outgoingShuttles(TerminalBase terminal) {
        return null;
    }

    /**
     * Returns the shortest path between the given origin and destination
     * terminals. The shortest path is the path that requires the least number
     * of shuttles.
     * <p>
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin      the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    @Override
    public Path findShortestPath(TerminalBase origin, TerminalBase destination) {
        return null;
    }

    /**
     * Returns the fastest path between the given origin and destination
     * terminals. The fastest path has the lowest total time spent travelling
     * and waiting.
     * <p>
     * The returned Path consists of a list of terminals in the path, and the
     * total time spent travelling along the path. The first element of the
     * Path's terminal list should be the given origin terminal, and the last
     * element should be the given destination terminal. Any intermediate
     * terminals in the path should appear in the list in the order travelled.
     *
     * @param origin      the starting terminal
     * @param destination the destination terminal
     * @return Path instance containing the list of terminals and the total
     * time taken in the path, or null if destination is not reachable from
     * origin
     */
    @Override
    public Path findFastestPath(TerminalBase origin, TerminalBase destination) {
        return null;
    }

    /* End of My Functions */

    static class Terminal extends TerminalBase {
        /**
         * Creates a new TerminalBase instance with the given terminal ID
         * and waiting time.
         *
         * @param id          terminal ID
         * @param waitingTime waiting time for the terminal, in minutes
         */
        public Terminal(String id, int waitingTime) {
            super(id, waitingTime);
        }

        /* Implement all the necessary methods of the Terminal here */
    }

    static class Shuttle extends ShuttleBase {
        /**
         * Creates a new ShuttleBase instance, travelling from origin to
         * destination and requiring 'time' minutes to travel.
         *
         * @param origin      origin terminal
         * @param destination destination terminal
         * @param time        time required to travel, in minutes
         */
        public Shuttle(TerminalBase origin, TerminalBase destination, int time) {
            super(origin, destination, time);
        }

        /* Implement all the necessary methods of the Shuttle here */
    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of Airport's run configuration
     */
    public static void main(String[] args) {
        Airport a = new Airport(3);
        Terminal terminalA = (Terminal) a.insertTerminal(new Terminal("A", 1));
        Terminal terminalB = (Terminal) a.insertTerminal(new Terminal("B", 3));
        Terminal terminalC = (Terminal) a.insertTerminal(new Terminal("C", 4));
        Terminal terminalD = (Terminal) a.insertTerminal(new Terminal("D", 2));

        Shuttle shuttle1 = (Shuttle) a.insertShuttle(terminalA, terminalB, 2);
        Shuttle shuttle2 = (Shuttle) a.insertShuttle(terminalA, terminalC, 5);
        Shuttle shuttle3 = (Shuttle) a.insertShuttle(terminalA, terminalD, 18);
        Shuttle shuttle4 = (Shuttle) a.insertShuttle(terminalB, terminalD, 8);
        Shuttle shuttle5 = (Shuttle) a.insertShuttle(terminalC, terminalD, 15);

        // Opposite
        assert a.opposite(shuttle1, terminalA).getId().equals("B");

        // Outgoing Shuttles
        assert a.outgoingShuttles(terminalA).stream()
                .map(ShuttleBase::getTime)
                .collect(Collectors.toList()).containsAll(List.of(2, 5, 18));

        // Remove Terminal
        a.removeTerminal(terminalC);
        assert a.outgoingShuttles(terminalA).stream()
                .map(ShuttleBase::getTime)
                .collect(Collectors.toList()).containsAll(List.of(2, 18));

        // Shortest path
        Path shortestPath = a.findShortestPath(terminalA, terminalD);
        assert shortestPath.terminals.stream()
                .map(TerminalBase::getId)
                .collect(Collectors.toList()).equals(List.of("A", "D"));
        assert shortestPath.time == 19;

        // Fastest path
        Path fastestPath = a.findFastestPath(terminalA, terminalD);
        assert fastestPath.terminals.stream()
                .map(TerminalBase::getId)
                .collect(Collectors.toList()).equals(List.of("A", "B", "D"));
        assert fastestPath.time == 14;
    }
}
