import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class MonsterOptions {
    static int id, damage, health;
    static String name;
    MonsterOptions(int id) throws SQLException {
        this.id = id;
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement insertStatement = conn.createStatement();
        String insertQuery = "SELECT * FROM monsters WHERE id='" + id + "'";
        ResultSet results = insertStatement.executeQuery(insertQuery);
        if(results.next()) {
            this.name = results.getString("name");
            this.damage = results.getInt("damage");
            this.health = results.getInt("health");
        }
    }

    public void printStats() {
        System.out.println("Health = " + this.health + " Damage = " + this.damage + " Name = " + name + " ");
    }
    public void train() throws SQLException {
        System.out.println("What would you like to train?");
        System.out.println("1 - Health");
        System.out.println("2 - Damage");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        boolean goodInput = false;
        while(!goodInput) {
            if (input == 1) {
                goodInput = true;
                updateHealth(1);

            } else if (input == 2) {
                goodInput = true;
                updateDamage(1);
            } else {
                System.out.println("What would you like to train?");
                System.out.println("1 - Health");
                System.out.println("2 - Damage");
            }
        }
    }
    public void fightMasterVsMonster() throws SQLException {
        Random ran = new Random();
        int firstID = -1;
        int firstHealth = -1;
        int firstDamage = -1;
        String firstName = "";
        firstID = ran.nextInt(30) + 1;
        while(firstID == this.id) {
            firstID = ran.nextInt(30) + 1;
        }
        ResultSet firstRes = getMonsterByID(firstID);
        if(firstRes.next()) {
            firstHealth = firstRes.getInt("health");
            firstDamage = firstRes.getInt("damage");
            firstName = firstRes.getString("name");
            firstID = firstRes.getInt("id");
        } else {
            System.out.println("BAD ID WAS GIVEN");
        }
        System.out.println(firstName + " .VS. " + this.name);
        String winner = simulateFight(firstHealth,firstDamage,firstName, firstID);
        System.out.println( winner + " WINS!");
        if(winner.equals(this.name)) {
            System.out.println("Your monster has won!");
            System.out.println("Your monster has earned enough experience to increase health or damage by 2 or 1 each");
            System.out.println("1 - Increase health by 2");
            System.out.println("2 - Increase damage by 2");
            System.out.println("3 - Increase health by 1 and damage by 1");
            boolean inputGood = false;
            Scanner in = new Scanner(System.in);
            while(!inputGood) {
                int input = in.nextInt();
                if (input == 1) {
                    updateHealth(2);
                    inputGood = true;
                } else if (input == 2) {
                    updateDamage(2);
                    inputGood = true;
                } else if (input == 3) {
                    updateHealth(1);
                    updateDamage(1);
                    inputGood = true;
                } else {
                    System.out.println("Please input 1, 2, or 3.");
                }
            }
        }
    }
    private static ResultSet getMonsterByID(int id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String getFirst = "SELECT * FROM monsters WHERE id=" + "'" + id + "'";
        ResultSet result = startStatement.executeQuery(getFirst);
        return result;
    }
    private String simulateFight(int firstHealth, int firstDamage, String firstName, int firstID) throws SQLException {
        String winner = "";
        int secondHealth = this.health;
        String secondName = this.name;
        int secondDamage = this.damage;

        while(firstHealth > 0 && secondHealth > 0) {
            System.out.println( secondName + " dealt " + secondDamage + " to " + firstName);
            firstHealth = firstHealth - secondDamage;
            if(firstHealth < 1) {
                return secondName;
            }
            System.out.println(firstName + " dealt " + firstDamage + " to " + secondName);
            secondHealth = secondHealth - firstDamage;
            if(secondHealth < 1) {
                return firstName;
            }
        }
        return winner;
    }
    private void updateHealth(int update) throws SQLException {
        Connection conn = null;
        this.health = update + this.health;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement updateStatement = conn.createStatement();
        String updateQuery = "UPDATE monsters set health = '" + this.health + "'" + "WHERE id='" + this.id + "'";
        updateStatement.executeUpdate(updateQuery);
        System.out.println("Increased monster's health to: " + this.health);
    }
    private void updateDamage(int update) throws SQLException {
        Connection conn = null;
        this.damage = update + this.damage;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement updateStatement = conn.createStatement();
        String updateQuery = "UPDATE monsters SET damage = '" + this.damage + "'" + "WHERE id='" + this.id + "'";
        updateStatement.executeUpdate(updateQuery);
        System.out.println("Increased monster's damage to: " + this.damage);
    }
    private void updateName(String name) throws SQLException {
        Connection conn = null;
        this.name = name;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement updateStatement = conn.createStatement();
        String updateQuery = "UPDATE monsters set name = '" + this.name + "'" + "WHERE id='" + this.id + "'";
        updateStatement.executeUpdate(updateQuery);
        System.out.println("Changed monster's name to: " + this.name);
    }
}
