
package formulario;

import base.Alumno;
import conexion.admiConexion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import base.Carrera;
import base.CentroEstudios;
import java.sql.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



public class PanelFXController implements Initializable {
    @FXML
    private RadioButton btonMasculono;
    @FXML
    private RadioButton btonFemenino;
    @FXML
    private DatePicker fecha;    
    @FXML
    private TextField txtcodigo;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextField txtapellido;
    @FXML
    private TextField txtedad;
    
    @FXML
    private Button guardar;
    @FXML
    private Button actualizar;
    @FXML
    private Button nuevo;
    @FXML
    private Button eliminar;    
    
    
    
    //componentes GUI
    @FXML
    private ComboBox<Carrera> cmbxCarrera;
    @FXML
    private ComboBox<CentroEstudios> cmbxCentroEstudio;
    @FXML
    private TableView<Alumno> tableAlumno; 
    
    // colecciones
    private ObservableList<Carrera> listaCarreras;
    private ObservableList<CentroEstudios> listaCentroEstudios;
    private ObservableList<Alumno> listaAlumnos;
     
    private admiConexion conexion;
   
    // columnas del tableView
    @FXML
    private TableColumn<Alumno, Integer> columnCodigo;
    @FXML
    private TableColumn<Alumno, String> columnNombre;
    @FXML
    private TableColumn<Alumno, String> columnApellido;  
    @FXML
    private TableColumn<Alumno,Integer > columnEdad;
    @FXML
    private TableColumn<Alumno, String> columnGenero;    
    @FXML
    private TableColumn<Alumno, Date> columnIngreso;
    @FXML
    private TableColumn<Alumno, Carrera> columnCarrera;
    @FXML
    private TableColumn<Alumno, CentroEstudios> columnCentroEstudio;
    

    @Override
    public void initialize(URL location , ResourceBundle resourcess) {
        conexion = new admiConexion();
        conexion.establecerConexion();
        //inicializo
        listaCarreras = FXCollections.observableArrayList();
        listaCentroEstudios = FXCollections.observableArrayList();
        listaAlumnos = FXCollections.observableArrayList();
        //lleno listas
        Carrera.llenarInformacion(conexion.getConnection(), listaCarreras);
        CentroEstudios.llenarInformacion(conexion.getConnection(), listaCentroEstudios);
        Alumno.llenarInformacionAlumnos(conexion.getConnection(), listaAlumnos);
        //enlazamos los comboBox y tableView consl os atributos
        cmbxCarrera.setItems(listaCarreras);
        cmbxCentroEstudio.setItems(listaCentroEstudios);
        tableAlumno.setItems(listaAlumnos);
        
        //enlazar columnas del TABLE VIEW con los atributos - los muestra!-
        columnNombre.setCellValueFactory(new PropertyValueFactory<Alumno,String>("nombreAlumno"));
        columnApellido.setCellValueFactory(new PropertyValueFactory<Alumno,String>("apellidoAlumno"));
        columnEdad.setCellValueFactory(new PropertyValueFactory<Alumno,Integer>("edadAlumno"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<Alumno,String>("generoAlumno"));
        columnIngreso.setCellValueFactory(new PropertyValueFactory<Alumno,Date>("fechaIngreso"));
        columnCodigo.setCellValueFactory(new PropertyValueFactory<Alumno,Integer>("codigoAlumno"));
        columnCarrera.setCellValueFactory(new PropertyValueFactory<Alumno,Carrera>("carrera"));
        columnCentroEstudio.setCellValueFactory(new PropertyValueFactory<Alumno,CentroEstudios>("centroEstudio"));
        // listaCarreras.add(new Carrera (1 ," sedf" ,34));
        gestionarLosEventos();
        conexion.cerrarConexion();
        
    }   
    // lo que haremos aca es gestionar de la base de datos plasmado en el tableView , los datos, a la hora de mostrar en los 
    // textfield y de ahi querer modificarlo o borrarlo, etc
    public void gestionarLosEventos(){
       tableAlumno.getSelectionModel().selectedItemProperty().addListener(
               // instancia anonima: instancia sin nombre
               new ChangeListener<Alumno>(){
                                  
  
           @Override
           //lo que hace aca es traer los datos de la base de datos a las casillas del texField completando estas casillas
           public void changed(ObservableValue<? extends Alumno> observable, Alumno oldValue, Alumno newValue) {
               if(newValue!= null ){
               txtcodigo.setText(String.valueOf(newValue.getCodigoAlumno()));
               txtnombre.setText(newValue.getNombreAlumno());
               txtapellido.setText(newValue.getApellidoAlumno());
               txtedad.setText(String.valueOf(newValue.getEdadAlumno()));
               if(newValue.getGeneroAlumno().equals("F")){
                  btonFemenino.setSelected(true);
                  btonMasculono.setSelected(false);
               }else{
                  btonFemenino.setSelected(false);
                  btonMasculono.setSelected(true);
               }  
               fecha.setValue(newValue.getFechaIngreso().toLocalDate());
               cmbxCarrera.setValue(newValue.getCarrera());
               cmbxCentroEstudio.setValue(newValue.getCentroEstudio());
               guardar.setDisable(true);
               eliminar.setDisable(false);
               actualizar.setDisable(false);
               }
           }
        }
       );
    }
    public void guardarRegistro(){
        
                                 // 0 xq es autoincremental , osea...despues lo cambiamos
        Alumno alumno = new Alumno(0,
                txtnombre.getText(),
                txtapellido.getText(),
                Integer.valueOf(txtedad.getText()), // como edad es integer. lo debo convertirlo con integer.valueOF
                btonMasculono.isSelected()?"M" :"F" ,
                Date.valueOf(fecha.getValue()), // para la fecha
                cmbxCentroEstudio.getSelectionModel().getSelectedItem(), // para acceder el modelo de datos de sql // para seleccionar un dato 
                cmbxCarrera.getSelectionModel().getSelectedItem() // idem para este
                // asi a ambos por que los 2 son objetos
                 ); 
      conexion.establecerConexion();
       
       int resultado = alumno.guardarRegistro(conexion.getConnection()); // ya que alumno.guardarRegistro..etc.. da un INT
       
       conexion.cerrarConexion();
       if(resultado ==1){
           listaAlumnos.add(alumno);
       Alert message = new Alert(AlertType.INFORMATION);
       message.setTitle("Registro guardado");
       message.setContentText("el registro ha sido guardado");
       message.setHeaderText("resulñtado");
       message.show();
       }
    }
    
    public void actualizar(){
        Alumno alumno = new Alumno(
                Integer.valueOf(txtcodigo.getText()), // en este caso al actualizaz registro, si necesito el codigoAlumno, por que ya existe codigoAlumno
                txtnombre.getText(),
                txtapellido.getText(),
                Integer.valueOf(txtedad.getText()), // como edad es integer. lo debo convertirlo con integer.valueOF
                btonMasculono.isSelected()?"M" :"F" ,
                Date.valueOf(fecha.getValue()), // para la fecha
                cmbxCentroEstudio.getSelectionModel().getSelectedItem(), // para acceder el modelo de datos de sql // para seleccionar un dato 
                cmbxCarrera.getSelectionModel().getSelectedItem() // idem para este
                // asi a ambos por que los 2 son objetos
                 ); 
        conexion.establecerConexion();
        int resultado = alumno.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        
               if(resultado ==1){
                   // cuando actualizdo, necesito a la lista de alumnos pasarle los datos que muestra en la tablaView
                   // de esa tabla lo hago con getselectionModel pero de ahi necesito su indice para acceder
                   // luego el otro atributo es el alumno objeto
           listaAlumnos.set(tableAlumno.getSelectionModel().getSelectedIndex()  ,alumno);
            Alert message = new Alert(AlertType.INFORMATION);
            message.setTitle("registro actualizado");
            message.setContentText("el registro se ha actualizado ");
            message.setHeaderText("resulñtado");
            message.show();
            }
    }
    @FXML
    public void limpiarComponente(){
    txtnombre.setText(null);
    txtapellido.setText(null);
    txtcodigo.setText(null);
    txtedad.setText(null);
    btonMasculono.setSelected(false);
    btonFemenino.setSelected(false);
    fecha.setValue(null);
    cmbxCarrera.setValue(null);
    cmbxCentroEstudio.setValue(null);
    
    guardar.setDisable(false);
    eliminar.setDisable(true);
    actualizar.setDisable(true);   
    }
    @FXML
    public void borrarRegistros(){
        conexion.establecerConexion();
        int resultado = tableAlumno.getSelectionModel().getSelectedItem().borrarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        if(resultado ==1 ){
        listaAlumnos.remove(tableAlumno.getSelectionModel().getSelectedIndex());    
        Alert message = new Alert(AlertType.INFORMATION);
        message.setTitle("registro eliminadio");
        message.setContentText("el registro se ha eliminado ");
        message.setHeaderText("resulñtado");
        message.show();
        }
    }


    
}
