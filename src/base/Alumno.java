
package base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Alumno{
	private IntegerProperty codigoAlumno;
	private StringProperty nombreAlumno;
	private StringProperty apellidoAlumno;
	private IntegerProperty edadAlumno;
	private StringProperty generoAlumno;
        private CentroEstudios centroEstudio;
	private Carrera carrera;
	private Date fechaIngreso;

	public Alumno(Integer codigoAlumno, String nombreAlumno, String apellidoAlumno, 
Integer edadAlumno, String generoAlumno,Date fechaIngreso,CentroEstudios centroEstudio, Carrera carrera 
) { 
		this.codigoAlumno = new SimpleIntegerProperty(codigoAlumno);
		this.nombreAlumno = new SimpleStringProperty(nombreAlumno);
		this.apellidoAlumno = new SimpleStringProperty(apellidoAlumno);
		this.edadAlumno = new SimpleIntegerProperty(edadAlumno);
		this.generoAlumno = new SimpleStringProperty(generoAlumno);
                this.fechaIngreso = fechaIngreso;
                this.centroEstudio = centroEstudio;
		this.carrera = carrera;
		
	}

	//Metodos atributo: codigoAlumno
	public int getCodigoAlumno() {
		return codigoAlumno.get();
	}
	public void setCodigoAlumno(int codigoAlumno) {
		this.codigoAlumno = new SimpleIntegerProperty(codigoAlumno);
	}
	public IntegerProperty CodigoAlumnoProperty() {
		return codigoAlumno;
	}
	//Metodos atributo: nombreAlumno
	public String getNombreAlumno() {
		return nombreAlumno.get();
	}
	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = new SimpleStringProperty(nombreAlumno);
	}
	public StringProperty NombreAlumnoProperty() {
		return nombreAlumno;
	}
	//Metodos atributo: apellidoAlumno
	public String getApellidoAlumno() {
		return apellidoAlumno.get();
	}
	public void setApellidoAlumno(String apellidoAlumno) {
		this.apellidoAlumno = new SimpleStringProperty(apellidoAlumno);
	}
	public StringProperty ApellidoAlumnoProperty() {
		return apellidoAlumno;
	}
	//Metodos atributo: edadAlumno
	public int getEdadAlumno() {
		return edadAlumno.get();
	}
	public void setEdadAlumno(int edadAlumno) {
		this.edadAlumno = new SimpleIntegerProperty(edadAlumno);
	}
	public IntegerProperty EdadAlumnoProperty() {
		return edadAlumno;
	}
	//Metodos atributo: generoAlumno
	public String getGeneroAlumno() {
		return generoAlumno.get();
	}
	public void setGeneroAlumno(String generoAlumno) {
		this.generoAlumno = new SimpleStringProperty(generoAlumno);
	}
	public StringProperty GeneroAlumnoProperty() {
		return generoAlumno;
	}
	//Metodos atributo: carrera
	public Carrera getCarrera() {
		return carrera;
	}
	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	//Metodos atributo: fechaIngreso
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
       public CentroEstudios getCentroEstudio() {
		return centroEstudio;
	}
	public void setCentroEstudio(CentroEstudios centroEstudio) {
		this.centroEstudio = centroEstudio;
	}
        /* METODOS PARA GUARDAR -- ACTUALIZAR -- BORRAR*/
        public int guardarRegistro(Connection connection){
            try {
                PreparedStatement instruccion = connection.prepareStatement(
                        "INSERT INTO tbl_alumnos (nombre_alumno, apellido_alumno, edad, genero, fecha_ingreso, "
                        + "codigo_carrera, codigo_centro_estudio) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)"      
                ); // evitar inyeccion de sql
                // a travez de fuciones reemplazaremos el VALUE (sus signos de interrogacion)con el parametro 
                // de la clase de objeto ALUMNO
                instruccion.setString(1, nombreAlumno.get());
                instruccion.setString(2, apellidoAlumno.get());
                instruccion.setInt(3, edadAlumno.get());
                instruccion.setString(4, generoAlumno.get());
                instruccion.setDate(5, fechaIngreso);
                instruccion.setInt(6, carrera.getCodigoCarrera());
                instruccion.setInt(7, centroEstudio.getCodigoCentroEstudio());
               // debemos ejecutar tanto a la hora de guardarRegistros o actualizarRegistros
                //usando executeUpdate
            return instruccion.executeUpdate(); // ya que esta instruccion devuelve 0 o 1 
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 0;
            }
            
        }
        public int actualizarRegistro(Connection connection){
            try {
                PreparedStatement instruccion = connection.prepareStatement(
                        "UPDATE tbl_alumnos " +
                                " SET nombre_alumno= ?, " +
                                " apellido_alumno = ?, " +
                                " edad = ?, " +
                                " genero = ?, " +
                                " fecha_ingreso = ?, " +
                                " codigo_carrera = ?, " +
                                " codigo_centro_estudio = ?" +
                                " WHERE codigo_alumno = ?"
                );
                // vamos a parametrizar los valores de los ? , para ingresar datos
                instruccion.setString(1, nombreAlumno.get());
                instruccion.setString(2, apellidoAlumno.get());
                instruccion.setInt(3, edadAlumno.get());
                instruccion.setString(4, generoAlumno.get());
                instruccion.setDate(5, fechaIngreso);
                instruccion.setInt(6, carrera.getCodigoCarrera());
                instruccion.setInt(7, centroEstudio.getCodigoCentroEstudio());
                instruccion.setInt(8, codigoAlumno.get());
                return instruccion.executeUpdate();
               
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
        public int borrarRegistro(Connection connection){
            try {
                PreparedStatement instruccion = connection.prepareStatement(
                        "DELETE FROM tbl_alumnos " +
                        " WHERE codigo_alumno = ? "
                 );
                instruccion.setInt(1, codigoAlumno.get());
                return instruccion.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
        
        public static void llenarInformacionAlumnos(Connection connection,ObservableList <Alumno> lista){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultado = statement.executeQuery(
                        "SELECT A.codigo_alumno, " +
                        "A.nombre_alumno, " +
                        "A.apellido_alumno, " + 
                        "A.edad, " +
                        "A.genero, " +
                        "A.fecha_ingreso, " +
                        "A.codigo_carrera, " +
                        "A.codigo_centro_estudio, " +
                        "B.nombre_carrera, " +
                        "B.cantidad_asignaturas, " +        
                        "C.nombre_centro_estudio " +
                        "FROM tbl_alumnos A " +
                        "INNER JOIN tbl_carreras B " +
                        "ON(A.codigo_carrera = B.codigo_carrera) " +
                        "INNER JOIN tbl_centro_estudios C "+
                        "ON(A.codigo_centro_estudio = C.codigo_centro_estudio)"
                );
                // cuando llamo resultado.getXXX el parametro es el script de la base de datos y no de los parametros del objeto ALUMNO
                while(resultado.next()){
                    lista.add(
                               new Alumno(
                                         resultado.getInt("codigo_alumno"),
                                         resultado.getString("nombre_alumno"),
                                         resultado.getString("apellido_alumno"),
                                         resultado.getInt("edad"),
                                         resultado.getString("genero"),
                                         resultado.getDate("fecha_ingreso"),
                                         new CentroEstudios(resultado.getInt("codigo_centro_estudio"),
                                                 resultado.getString("nombre_centro_estudio")
                                         ),
                                         new Carrera(resultado.getInt("codigo_carrera"),
                                         resultado.getString("nombre_carrera"),
                                         resultado.getInt("cantidad_asignaturas"))
                                         )
                               );
                              
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}
