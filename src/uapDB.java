import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Imrul
 */
public class uapDB 
{
    Connection conn = null;
    //****************************Database Connection ****************************
    public static Connection connectDB()
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/uap_db","root","");
    //        JOptionPane.showMessageDialog(null, "Connect");
            System.out.println("Connected");

            return conn;
        }
        catch(SQLException e)
        {
        ///JOptionPane.showMessageDialog(null, e);
        System.out.println("Not Connected");
        
        return null;
        
        }
    }     
}
