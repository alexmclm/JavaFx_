
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class admiConexion {
    private Connection connection;
    private String url = "jdbc:mysql://localhost/db_alumnos";
    private String usuario = "root";
    private String password="";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establecerConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,usuario, password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void cerrarConexion(){
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
