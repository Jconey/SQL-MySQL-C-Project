import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class StartUp {
    public static void main(String[] args) {
        try{
            Scanner in = new Scanner(System.in);
            MasterOptions master;
            Fight f;
            System.out.println("Are you a rising master or here to watch a good fight? - Type \"master\" or \"fight\"");
            boolean inputGood = false;
            while(!inputGood) {
                String input = in.nextLine();
                if (input.equals("master")) {
                    master = new MasterOptions();
                    master.printStartOptions();
                    inputGood = true;
                } else if (input.equals("fight")) {
                    f = new Fight();
                    String winner = f.startFight();
                    System.out.println(winner + " WINS!\n");
                    inputGood = true;
                } else {
                    System.out.println("Please choose  \"master\" or \"fight\"");
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void generateMonsters(int num, String letter) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Random ran = new Random(10);
        String crtRandom;
        Statement crtStatement = conn.createStatement();
        String crtStatementString;
        int healthInsert = 0;
        int damageInsert = 0;
        try {
            for (int i = num - 10; i < num; i++) {
                crtRandom = letter + ran.nextInt(10) + ran.nextInt(10);
                healthInsert = ran.nextInt(10) + 1;
                damageInsert = ran.nextInt(4) + 1;
                crtStatementString = "INSERT INTO monsters (id, health, damage ,name) VALUES ('" + i + "'," + "'" + healthInsert + "', '" + damageInsert + "', '"+ crtRandom + "' )";
                crtStatement.executeUpdate(crtStatementString);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("ITS A CRAAAP");
        }
    }
    private static void resetAllTables() throws SQLException {
        dropMasterTable();
        dropMonsterTable();
        createMasterTable();
        createMonsterTable();
    }
    private static void dropMonsterTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String dropAll = "DROP TABLE monsters";
        startStatement.executeUpdate(dropAll);
        return;
    }
    private static void dropMasterTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String dropAll = "DROP TABLE masters";
        startStatement.executeUpdate(dropAll);
        return;
    }
    private static void createMonsterTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String strSelect = "CREATE TABLE monsters(id int, name VARCHAR(255), health int, damage int, PRIMARY KEY (id))";
        startStatement.executeUpdate(strSelect);
        generateMonsters(11, "A");
        generateMonsters(21, "B");
        generateMonsters(31, "C");
        return;
    }
    private static void createMasterTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String strSelect = "CREATE TABLE masters(id int NOT NULL AUTO_INCREMENT, name VARCHAR(255), monsterID int, PRIMARY KEY(id))";
        startStatement.executeUpdate(strSelect);
        return;
    }
}
