import java.sql.*;
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
                Connection conn = null;
                this.health = ++this.health;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
                Statement updateStatement = conn.createStatement();
                String updateQuery = "UPDATE monsters set health = '" + this.health + "'" + "WHERE id='" + this.id + "'";
                updateStatement.executeUpdate(updateQuery);
            } else if (input == 2) {
                goodInput = true;
                Connection conn = null;
                this.damage = ++this.damage;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
                Statement updateStatement = conn.createStatement();
                String updateQuery = "UPDATE monsters SET damage = '" + this.damage + "'" + "WHERE id='" + this.id + "'";
                updateStatement.executeUpdate(updateQuery);
            } else {
                System.out.println("What would you like to train?");
                System.out.println("1 - Health");
                System.out.println("2 - Damage");
            }
        }
    }
}
