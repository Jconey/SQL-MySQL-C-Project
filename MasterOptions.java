import java.sql.*;
import java.util.Scanner;

public class MasterOptions {
    private static int selectedMasterID = -1;
    private static String selectedMasterName = "";
    private static int selectedMasterMonsterID = -1;
    public static void printStartOptions() throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Are you a new master or a returning master?");
        System.out.println("\"returning\" or \"new\"");
        boolean repeat = false;
        Connection conn = null;
        while(!repeat) {
            String input = in.nextLine();
            if (input.equals("new")) {
                try {
                    repeat = true;
                    System.out.println("What is your name master?");
                    String name = in.nextLine();
                    int idOfMonster = startNewMasterSelection();
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
                    Statement insertStatement = conn.createStatement();
                    String insertQuery = "INSERT INTO masters (name ,monsterID) VALUES('" + name + "', '" + idOfMonster + "')";
                    insertStatement.executeUpdate(insertQuery);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (input.equals("returning")) {
                repeat = true;
                System.out.println("What is your name returning master?");
                selectedMasterName = in.nextLine();
                getMasterByName();
                System.out.println("Welcome back " + selectedMasterName);
                printOptions();
            } else {
                System.out.println("\"returning\" or \"new\"");
            }
        }
    }
    private static void printOptions() throws SQLException {
        Scanner in = new Scanner(System.in);
        int input = 0;
        while(true) {
        System.out.println("1 to print your monster's stats");
        System.out.println("2 to train your monster");
        System.out.println("3 to put your monster in the ring");
        System.out.println("4 to quit");
        input = in.nextInt();
        MonsterOptions monsterOptions = new MonsterOptions(selectedMasterMonsterID);
            switch (input) {
                case 1: {
                    monsterOptions.printStats();
                    break;
                }
                case 2: {
                    monsterOptions.train();
                    break;
                }
                case 3: {
                    System.out.println("NOT SETUP YET");
                }
                case 4: {
                    System.out.println("GoodBye!");
                    System.exit(0);
                }
            }
        }
    }
    private static int startNewMasterSelection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement insertStatement = conn.createStatement();
        String insertQuery = "SELECT * FROM monsters";
        ResultSet results = insertStatement.executeQuery(insertQuery);
        int m = 0;
        while(results.next()) {
            System.out.println(m + " Health: " + results.getInt("health") + " Damage: " + results.getInt("damage") + " Name: " + results.getString("name"));
            m++;
        }
        System.out.println("Which monster would you like?");
        Scanner in = new Scanner(System.in);
        int ret = in.nextInt();
        return ret;
    }
    private static void getMasterByName() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement insertStatement = conn.createStatement();
        String insertQuery = "SELECT * FROM masters WHERE name='" + selectedMasterName + "'";
        ResultSet results = insertStatement.executeQuery(insertQuery);
        if(results.next()) {
            selectedMasterMonsterID = results.getInt("monsterID");
            selectedMasterID = results.getInt("id");
        }
    }
}
