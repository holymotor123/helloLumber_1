package at.ac.tuwien.sepm.assignment.group02.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by e0701149 on 20.11.17.
 */
public class DBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Connection con = null;

    public static Connection getConnection(){
        LOG.debug("called getConnection");

        if (con == null) {
            con = openConnection();
        }
        return con;
    }

    private static Connection openConnection() {
        LOG.debug("called openConnection");

        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("ERROR: failed to load H2 JDBC driver.");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:h2:file:~/restaurantManagementDB;INIT=runscript from 'classpath:create.sql'",
                    "sa", "");
        } catch (SQLException e) {
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(){
        LOG.debug("called closeConnection");
        try{
            if(con!=null) {
                con.close();
                con = null;
            }
        } catch (SQLException e){
            LOG.error("ERROR: SQLException{}",e);
            e.printStackTrace();
        }
    }

    /* just for testing
    public static void dropTable(){
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "DROP TABLE is_part_of;\n" +
                            "DROP TABLE RECEIPT;\n" +
                            "DROP TABLE PRODUCT;"
            );
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            LOG.error("SQLException on createProduct");
        }
    }
    */

}
