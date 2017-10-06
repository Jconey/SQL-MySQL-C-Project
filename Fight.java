import java.sql.*;
import java.util.Random;

public class Fight {
    private static int firstHealth, firstDamage, firstID, secondHealth, secondDamage, secondID;
    private static String firstName, secondName;

    public static String startFight() throws SQLException {
        String winner = "";
        getContestants();
        winner = simulateFight();
        return winner;
    }
    private static String simulateFight() throws SQLException {
        String winner = "";
        while(firstHealth > 0 && secondHealth > 0) {
            System.out.println( secondName + " dealt " + secondDamage + " to " + firstName);
            firstHealth = firstHealth - secondDamage;
            if(firstHealth < 1) {
                removeMonsterByID(firstID);
                return secondName;
            }
            System.out.println(firstName + " dealt " + firstDamage + " to " + secondName);
            secondHealth = secondHealth - firstDamage;
            if(secondHealth < 1) {
                removeMonsterByID(secondID);
                return firstName;
            }
        }
        return winner;
    }
    private static void getContestants() throws SQLException {
        Random ran = new Random();
         firstID = ran.nextInt(30) + 1;
         secondID = ran .nextInt(30) + 1;
        while(firstID == secondID) {
            firstID = ran.nextInt(30) + 1;
            secondID = ran.nextInt(30) + 1;
        }
        ResultSet firstRes = getMonsterByID(firstID);
        ResultSet secondRes = getMonsterByID(secondID);
        if(firstRes.next() && secondRes.next()) {
            firstHealth = firstRes.getInt("health");
            firstDamage = firstRes.getInt("damage");
            firstName = firstRes.getString("name");
            firstID = firstRes.getInt("id");

            secondHealth = secondRes.getInt("health");
            secondDamage = secondRes.getInt("damage");
            secondName = secondRes.getString("name");
            secondID = firstRes.getInt("id");

        } else {
            System.out.println("BAD ID WAS GIVEN");
        }
        System.out.println(firstName + " .VS. " + secondName);
    }
    private static ResultSet getMonsterByID(int id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement startStatement = conn.createStatement();
        String getFirst = "SELECT * FROM monsters WHERE id=" + "'" + id + "'";
        ResultSet result = startStatement.executeQuery(getFirst);
        return result;
    }
    private static void removeMonsterByID(int id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata", "root", "JCbnb34r");
        Statement removeStatement = conn.createStatement();
        String remove = "DELETE FROM monsters WHERE id = " + "'" + id + "'";
        removeStatement.executeUpdate(remove);
    }
}
