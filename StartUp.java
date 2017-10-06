import java.sql.*;
import java.util.Random;

public class StartUp {
    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
            Statement startStatement = conn.createStatement();
            String dropAll = "DROP TABLE monsters";
            startStatement.executeUpdate(dropAll);
            String strSelect = "CREATE TABLE monsters(id int, name VARCHAR(255), health int, damage int, PRIMARY KEY (id))";
            startStatement.executeUpdate(strSelect);
            generateMonsters(11, "A");
            generateMonsters(21, "B");
            generateMonsters(31, "C");
            Fight f = new Fight();
            String winner = f.startFight();
            System.out.println(winner + " WINS!\n");
        } catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("ITS A CRAAAP");
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
                //System.out.println(crtStatementString);
                crtStatement.executeUpdate(crtStatementString);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("ITS A CRAAAP");
        }
    }
}
