
package base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class CentroEstudios{
	private IntegerProperty codigoCentroEstudio;
	private StringProperty nombreCentroEstudio;

	public CentroEstudios(int codigoCentroEstudio, String nombreCentroEstudio) { 
		this.codigoCentroEstudio = new SimpleIntegerProperty(codigoCentroEstudio);
		this.nombreCentroEstudio = new SimpleStringProperty(nombreCentroEstudio);
	}

	//Metodos atributo: codigoCentroEstudio
	public int getCodigoCentroEstudio() {
		return codigoCentroEstudio.get();
	}
	public void setCodigoCentroEstudio(int codigoCentroEstudio) {
		this.codigoCentroEstudio = new SimpleIntegerProperty(codigoCentroEstudio);
	}
	public IntegerProperty CodigoCentroEstudioProperty() {
		return codigoCentroEstudio;
	}
	//Metodos atributo: nombreCentroEstudio
	public String getNombreCentroEstudio() {
		return nombreCentroEstudio.get();
	}
	public void setNombreCentroEstudio(String nombreCentroEstudio) {
		this.nombreCentroEstudio = new SimpleStringProperty(nombreCentroEstudio);
	}
	public StringProperty NombreCentroEstudioProperty() {
		return nombreCentroEstudio;
	}
        
        /*   METODOS PARA LLENAR INFORMACION DEL SQL*/
        public static void llenarInformacion(Connection connection, ObservableList<CentroEstudios> lista){
                    try {
                Statement statement = connection.createStatement();
                ResultSet resultado = statement.executeQuery("SELECT codigo_centro_estudio,"
                        + "nombre_centro_estudio "
                        + "FROM tbl_centro_estudios"
                );
                
                while(resultado.next()){
                    lista.add(
                            new CentroEstudios(
                                    resultado.getInt("codigo_centro_estudio"),
                                    resultado.getString("nombre_centro_estudio") 
                                    )
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }
        public String toString(){
        return codigoCentroEstudio.get()+ " NombreCentro : " + nombreCentroEstudio.get();
        }
}