package com.dataaccess.finalactivity.ELH2627;
import java.sql.Connection;

public class FlightsApp {
    public static void main(String[] args) {
        try(Connection conn = DatabaseManager.getConnection()){
            if(args.length == 0){
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
                        System.out.println("Comando --add detectado con archivo: " + args[1]);
                    } else {
                        printHelp();
                    }
                    break;

                case "-f":
                case "--flights":
                    if (args.length == 4) {
                        System.out.println("Comando --flights detectado");
                    } else {
                        printHelp();
                    }
                    break;

                case "-o":
                case "--origins":
                    // Este comando no necesita argumentos extra
                    System.out.println("Comando --origins detectado");
                    break;

                case "-d":
                case "--destinations":
                    if (args.length == 2) {
                        System.out.println("Comando --destinations detectado desde: " + args[1]);
                    } else {
                        printHelp();
                    }
                    break;

                case "-b":
                case "--buy":
                    if (args.length == 4) {
                        System.out.println("Comando --buy detectado");
                    } else {
                        printHelp();
                    }
                    break;

                default:
                    // Si el comando no se reconoce, mostramos la ayuda
                    printHelp();
            }
        }catch(Exception e){
            System.err.println(e);
            System.exit(1);
        }

    }
    private static void printHelp() {
        System.out.println("Usage: flights [options]");
        System.out.println("Options:");
        System.out.println("  -h, --help: show this help");
        System.out.println("  -a, --add <filename.xml>: add the passengers in the XML file to the database.");
        System.out.println("  -f, --flights <origin> <destination> <date>: show the flights available from origin to destination");
        System.out.println("  -b, --buy <customerId> <flightId> <date>: buy a ticket for a flight in a specific date");
        System.out.println("  -o, --origins: show the origins of the flights");
        System.out.println("  -d, --destinations <origin>: show the destinations of the flights from origin");
    }
}
