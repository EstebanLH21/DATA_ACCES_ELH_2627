package com.dataaccess.finalactivity.ELH2627;

import java.sql.Connection;

public class FlightsApp {
    public static void main(String[] args) {

        try (Connection conn = DatabaseManager.getConnection()) {

        } catch (Exception e) {
            System.err.println("ERROR: The database service is not available.");
            System.err.println("EXACT REASON: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // 2. If no arguments, show help
        if (args.length == 0) {
            printHelp();
            return;
        }


        String command = args[0];


        switch (command) {
            case "-h":
            case "--help":
                printHelp();
                break;

            case "-a":
            case "--add":
                if (args.length == 2) {

                    XMLImporter.importPassengers(args[1]);
                } else {
                    printHelp();
                }
                break;

            case "-f":
            case "--flights":
                if (args.length == 4) {

                    FlightsManager.showFlights(args[1], args[2], args[3]);
                } else {
                    printHelp();
                }
                break;

            case "-o":
            case "--origins":

                FlightsManager.showOrigins();
                break;

            case "-d":
            case "--destinations":
                if (args.length == 2) {

                    FlightsManager.showDestinations(args[1]);
                } else {
                    printHelp();
                }
                break;

            case "-b":
            case "--buy":
                if (args.length == 4) {
                    TicketsManager.buyTicket(args[1], args[2], args[3], false);
                } else if (args.length == 5) {
                    TicketsManager.buyTicket(args[1], args[2], args[3], true);
                } else {
                    printHelp();
                }
                break;
            case "-c":
            case "--cancel":
                if (args.length == 2) {

                    TicketsManager.cancelTicket(args[1], args[2]);
                } else {
                    printHelp();
                }
                break;

            default:

                printHelp();
        }
    }


    private static void printHelp() {
        System.out.println("Usage: flights [options]");
        System.out.println("Options:");
        System.out.println("  -h, --help: show this help");
        System.out.println("  -a, --add <filename.xml>: add the passengers in the XML file to the database.");
        System.out.println("  -f, --flights <origin> <destination> <date>: show the flights available from origin to destination");
        System.out.println("  -b, --buy <customerId> <flightId> <date> [--print]: buy a ticket for a flight in a specific date (--print option is opcional)");
        System.out.println("  -o, --origins: show the origins of the flights");
        System.out.println("  -d, --destinations <origin>: show the destinations of the flights from origin");
        System.out.println("  -c, --cancel <idPassenger> <date>");
    }
}