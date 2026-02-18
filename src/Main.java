import system.*;
import system.base.*;
import system.data.PassingTrain;
import system.data.ScheduleEntry;
import system.exceptions.*;
import dataStructures.*;

import java.io.*;
import java.util.Scanner;

/**
 * Main class of the project.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        RailWayNetwork sys = load();
        executeCommand(in, sys);
        save(sys);
    }

    /**
     * Executes the command given by the user.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void executeCommand(Scanner in, RailWayNetwork sys) {
        boolean end = false;
        do {
            String cmd = in.next().toUpperCase();
            try {
                Command command = Command.fromString(cmd);
                switch (command) {
                    case IL -> insertRail(in, sys);
                    case RL -> removeRail(in, sys);
                    case CL -> consultRailStations(in, sys);
                    case CE -> consultStationRails(in, sys);
                    case IH -> insertSchedule(in, sys);
                    case RH -> removeSchedule(in, sys);
                    case CH -> consultRailSchedulesDepartingAt(in, sys);
                    case LC -> listStationTrains(in, sys);
                    case MH -> bestSchedule(in, sys);
                    case TA -> end = exit(in);
                }
            } catch (NoCommandException ignored)  {}
        } while (!end);
    }

    /**
     * Executes the IL command: Inserts a rail in the system.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void insertRail(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        List<String> stations = new DoubleList<>();
        fillStationsNamesList(in, stations);
        try {
            sys.insertRail(rail,stations);
            System.out.print(Output.RAIL_INSERTION_SUCCESS.printOutput());
        }
        catch(ExistentRailException e) {
            System.out.print(Output.EXISTENT_RAIL.printOutput());
        }
    }

    /**
     * Executes the RL command: Removes a rail from the system.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void removeRail(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        try {
            sys.removeRail(rail);
            System.out.print(Output.RAIL_REMOVAL_SUCCESS.printOutput());
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
    }

    /**
     * Executes the CL command: Lists the stations of a given rail.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void consultRailStations(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        try {
            Iterator<StationGet> it = sys.listRailStations(rail);
            while(it.hasNext()) {
                StationGet s = it.next();
                System.out.println(s.getName());
            }
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
    }

    /**
     ******* SECOND PHASE OF THE PROJECT *********
     * Executes the CE command: Lists the rails that a station belongs to.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void consultStationRails(Scanner in, RailWayNetwork sys) {
        String station = in.nextLine().trim();
        try {
            Iterator<RailGet> it = sys.listStationRails(station);
            while(it.hasNext()) {
                RailGet r = it.next();
                System.out.println(r.getName());
            }
        }catch(NonExistentStationException e) {
            System.out.print(Output.NONEXISTENT_STATION.printOutput());
        }
    }

    /**
     * Executes the IH command: Inserts a schedule to a given rail.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void insertSchedule(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        int train = in.nextInt(); in.nextLine();
        List<ScheduleEntry> entries = new DoubleList<>();
        fillTimetableEntries(in,entries,sys);
        try {
            sys.insertSchedule(rail,entries,train);
            System.out.print(Output.SCHEDULE_CREATION_SUCCESS.printOutput());
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
        catch(InvalidScheduleException e){
            System.out.print(Output.INVALID_SCHEDULE.printOutput());
        }
    }

    /**
     * Executes the RH command: Removes a schedule from a given rail.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void removeSchedule(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        String input = in.nextLine().trim();
        int i = input.lastIndexOf(" ");
        String station = input.substring(0,i);
        String[] time = input.substring(i+1).split(":");
        String hour = time[0];
        String min = time[1];
        try {
            sys.removeSchedule(rail,station,hour, min);
            System.out.print(Output.SCHEDULE_REMOVAL_SUCCESS.printOutput());
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
        catch(NonExistentScheduleException e) {
            System.out.print(Output.NONEXISTENT_SCHEDULE.printOutput());
        }
    }

    /**
     * Executes the CH command: Lists all the schedules of a rail that depart from a given station.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void consultRailSchedulesDepartingAt(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        String station = in.nextLine().trim();
        try {
            Iterator<TrainGet> schIt = sys.consultRailSchedule(rail,station);
            while(schIt.hasNext()) {
                TrainGet t = schIt.next();
                Iterator<ScheduleEntry> schEntriesIt = t.listScheduleEntries();
                System.out.println(t.getNumber());
                while(schEntriesIt.hasNext()) {
                    ScheduleEntry e = schEntriesIt.next();
                    System.out.printf(Output.INFO.printOutput(), e.station().getName(),
                            e.time().getHour(), e.time().getMin());
                }
            }
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
        catch(NonExistentDepartureStationException e){
            System.out.print(Output.NONEXISTENT_DEPARTURE.printOutput());
        }
    }

    /**
     ******** SECOND PHASE OF THE PROJECT **********
     * Executes the LC command: Lists all trains that go by a given station.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void listStationTrains(Scanner in, RailWayNetwork sys) {
        String station = in.nextLine().trim();
        try {
            Iterator<PassingTrain> it = sys.listTrains(station);
            while(it.hasNext()) {
                PassingTrain tr = it.next();
                System.out.printf(Output.TRAIN.printOutput(), tr.train().getNumber(),
                                tr.time().getHour(), tr.time().getMin());
            }
        } catch(NonExistentStationException e){
            System.out.print(Output.NONEXISTENT_STATION.printOutput());
        }
    }

    /**
     * Executes the MH command: Lists the best schedule that includes the best route
     * between two given stations until a given expected time.
     * @param in Input scanner.
     * @param sys System interface.
     * @pre in != null && sys != null
     */
    private static void bestSchedule(Scanner in, RailWayNetwork sys) {
        String rail = in.nextLine().trim();
        String dep = in.nextLine().trim();
        String arrv = in.nextLine().trim();
        String[] expect = in.nextLine().split(":");
        String hour = expect[0];
        String min = expect[1];
        try {
            TrainGet best = sys.getBestTrain(rail,dep,arrv,hour,min);
            Iterator<ScheduleEntry> schEntriesIt = best.listScheduleEntries();
            System.out.println(best.getNumber());
            while(schEntriesIt.hasNext()) {
                ScheduleEntry e = schEntriesIt.next();
                System.out.printf(Output.INFO.printOutput(), e.station().getName(),
                        e.time().getHour(), e.time().getMin());
            }
        }
        catch(NonExistentRailException e) {
            System.out.print(Output.NONEXISTENT_RAIL.printOutput());
        }
        catch(NonExistentDepartureStationException e) {
            System.out.print(Output.NONEXISTENT_DEPARTURE.printOutput());
        }
        catch(ImpossibleRouteException e) {
            System.out.print(Output.IMPOSSIBLE_ROUTE.printOutput());
        }
    }

    /**
     * Executes the TA command: Terminates de application.
     * @param in Input scanner.
     * @pre in != null
     */
    private static boolean exit(Scanner in) {
        System.out.print(Output.APP_TERMINATED.printOutput());
        in.close();
        return true;
    }

    /**
     * Auxiliary method to fill the list with the names of the stations being inserted in a rail.
     * @param in Input Scanner.
     * @param stations List of the names of the stations.
     * @pre stations.size() > 1 && in != null
     */
    private static void fillStationsNamesList(Scanner in, List<String> stations) {
        boolean end = false;
        while(!end) {
            String station = in.nextLine().trim();
            if(station.isEmpty())
                end = true;
            else
                stations.addLast(station);
        }
    }

    /**
     * Auxiliary method to fill the list the entries of the schedule being inserted in a given rail.
     * @param in Input Scanner.
     * @param entries List of entries of the schedule.
     * @param sys System interface.
     * @pre entries.size() > 1 && in != null && sys != null
     */
    private static void fillTimetableEntries(Scanner in, List<ScheduleEntry> entries, RailWayNetwork sys) {
        boolean end = false;
        while(!end) {
            String entry = in.nextLine().trim();
            if(entry.isEmpty())
                end = true;
            else {
                int i = entry.lastIndexOf(" ");

                String station = entry.substring(0, i);
                String[] time = entry.substring(i + 1).split(":");
                String hour = time[0];
                String min = time[1];

                sys.saveSchEntry(station, hour, min, entries);
            }
        }
    }

    /**
     * Loads the most recent use of the program.
     * If it was erased, or it is the first time running the program, it creates a new system.
     * @return System interface.
     */
    private static RailWayNetwork load() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("out.txt"));
            RailWayNetwork system = (RailWayNetwork) inStream.readObject();
            inStream.close();
            return system;
        }
        catch (IOException | ClassNotFoundException e)  {
            return new RailWayNetworkClass();
        }
    }

    /**
     * Saves the running progress of the program.
     * @param sys System interface.
     * @pre sys != null
     */
    private static void save(RailWayNetwork sys) {
        try {
            ObjectOutputStream file = new ObjectOutputStream((new FileOutputStream("out.txt")));
            file.writeObject(sys);
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println();
        }
    }
}