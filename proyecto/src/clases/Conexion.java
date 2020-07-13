package clases;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Excepciones.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Roberto
 */
public class Conexion {
    private static Connection conexion;//variable que servira para la conexión a la base de datos
    private static final String driver="com.mysql.jdbc.Driver", url="jdbc:mysql://"; //variables que serivran en la conexion, estas nunca deben ser modificados0
    private static String user="root", ip="localhost", pass="", nombreBD="sce"; //Variables que pueden ser modificadas y por defecto son las que se muestran
    public final static String claveCifradoBase="SCEUser Cifrado AES";
    private Usuario usuario;
    /**
     * Crea un objeto conexión con datos predeterminados
     */
    public Conexion (){}
    /**
     * Construye un objeto conexión con datos especificos del servidor con el SGBD
     * @param user usuario de acceso
     * @param ip ip del servidor
     * @param pass contraseña del usuario
     * @param db base de datos a conectarse
     */
    public Conexion(String user, String ip, String pass, String db)
    {
        this.user=user;
        this.ip=ip;
        this.pass=pass;
        this.nombreBD=db;
    }
    public Conexion(String user, String pass, String db)
    {
        this.user=user;
        this.pass=pass;
        this.nombreBD=db;
    }

    public  Connection getConexion() throws NoSePuedeConectar {
        conectar();
        return conexion;
    }
    public void desconectar() throws SQLException{
        conexion.close();
    }
    /**
     * Metodo que genera la conexion utilizando los atributos de esta clase
     * @param nombreBD el nombre de la base de datos a conectarse
     */
    private void conectar() throws NoSePuedeConectar
    {
       conexion=null;
        try{
            Class.forName(driver);//Se utiliza el driver de conexion
            conexion=DriverManager.getConnection(url+ip+"/"+nombreBD,user,pass);//Se conecta con la base de datos enviando
            //los parametros url, user, pass,
        }catch (SQLException|NullPointerException ex) {
            throw new NoSePuedeConectar("No puede conectarse a la BD, error:\n"+ex.toString());
        } catch (ClassNotFoundException ex) {
            throw new NoSePuedeConectar("Error al registrar el driver de MySQL, error:\n" + ex.toString());
        }
    }
    /**
     * Función para crear un nuevo proveedor
     * @param Nombre
     * @param Nit
     * @param Credito
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public void crearProveedor(String Nombre, String Nit, boolean Credito) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        int credito = 0;
        String NitReal = "No Aplica";
        if (Credito) {
            credito = 1;
        }
        if (!Nit.equals("") && !Nit.equals("INGRESE EL NIT DEL PROVEEDOR (SI APLICA)")) {
            NitReal = Nit;
        }
        if (!existeProveedor(Nombre, Nit)) {
            instruccion.executeUpdate("INSERT INTO proveedor (Nombre, Nit, Credito) VALUES ('"+ Nombre +"', '"+ NitReal +"', "+ credito +");");
        }else{
//           Dialogo.setContenido("INFORMACION", "EL PROVEEDOR YA EXISTE", credito);
//           Dialogo.setVisible(true);
        }
        conexion.close();
    }
    public void crearPedido(String Factura, float Total, boolean credito) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("INSERT INTO compra (Factura, Total) VALUES ('" + Factura + "', " + Total + ");");
              
        conexion.close();
    }
    private boolean existeProveedor(String Nombre, String Nit) throws SQLException, NoSePuedeConectar{
        int cantNombre, cantNit;
        cantNit = cantNombre = 0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT COUNT(*) Cantidad FROM proveedor WHERE Nombre = '" + Nombre+"';");
        while (resultado.next()) {            
            cantNombre = resultado.getInt("Cantidad");
        }
        if (cantNombre != 0) {
            conexion.close();
            return true;
        }
        if (!Nit.equals("No Aplica")) {
            resultado = instruccion.executeQuery("SELECT COUNT(*) Cantidad FROM proveedor WHERE Nombre = '" + Nit+"';");
            while (resultado.next()) {            
                cantNit = resultado.getInt("Cantidad");
            }
            if (cantNit != 0) {
                conexion.close();
                return true;
            }
        }
        conexion.close();
        return false;
    }
    private void iniciarTablaProveedores() {
//        
        Proveedores = new DefaultTableModel(null, new String[]{"Nombre", "Nit", "Credito","Saldo"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
     private void iniciarTablaProductos() {
//        
        Productos = new DefaultTableModel(null, new String[]{"Codigo", "Barras", "Descripccion", "Existencias"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
     
    private void iniciarTablaPedidos() {
//        
        Productos = new DefaultTableModel(null, new String[]{"Numero", "Proveedor", "Fecha", "Total", "Saldo"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    private void iniciarTablaClientes() {
//        
        Clientes = new DefaultTableModel(null, new String[]{"Codigo", "Nit",  "Nombre",  "Descuento", "Credito", "Saldo"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    private void iniciarTablaExistencias() {
//        
        Existencias = new DefaultTableModel(null, new String[]{"Sucursal", "Cantidad"}){
            boolean[] canEdit = new boolean [] {
        false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    private void iniciarTablaTrabajadadores() {
//        
        trabajadores = new DefaultTableModel(null, new String[]{"id", "Nombre"}){
            boolean[] canEdit = new boolean [] {
        false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    private void iniciarTablaTelefonos() {
//        
        telefonos = new DefaultTableModel(null, new String[]{"Nombre", "Tipo", "Numero"}){
            boolean[] canEdit = new boolean [] {
        false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }

 private void iniciarTablaVehiculos() {
//        
        vehiculos = new DefaultTableModel(null, new String[]{"Identificador", "Placa", "Tipo", "Marca", "Linea", "Modelo", "VIN", "Descripccion"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    
    private void iniciarTablaMaquinaria() {
//        
        maquinaria = new DefaultTableModel(null, new String[]{"Identificador", "Tipo", "Marca", "Linea", "Modelo", "VIN", "Descripccion"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
    }
    private void iniciarTablaComputo() {
//        
    computo = new DefaultTableModel(null, new String[]{"Identificador", "Tipo", "Marca", "Modelo", "SO", "NS", "Descripcion"}){
        boolean[] canEdit = new boolean [] {
    false, false, false, false, false, false, false, false
        };
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
    return canEdit [columnIndex];
        }
    };
    }
    public void desHabilitarProvedor(String Nombre) throws SQLException, NoSePuedeConectar{
        conectar();
        
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE proveedor SET Habilitado = 0 WHERE Nombre = '" + Nombre + "';");
        conexion.close();
    }
    private DefaultTableModel Proveedores, Productos, Pedidos, Clientes, Existencias, trabajadores, telefonos, 
            vehiculos, maquinaria,computo ;
    /**
     * Metodo que regresa la lista de proveedores como un arreglo
     * @return
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public DefaultTableModel obtenerVehiculos() throws SQLException, NoSePuedeConectar{
        vehiculos = null;
        iniciarTablaVehiculos();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Identificador, Placa, Tipo, Marca, Linea, Modelo, VIN, Descripcion, Habilitado FROM vehiculo;");
        while(resultado.next()){
            boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            if(habilitado) vehiculos.addRow(new String[] {resultado.getString("Identificador"), resultado.getString("Placa"), resultado.getString("Tipo"), resultado.getString("Marca"), resultado.getString("Linea"), resultado.getString("Modelo"), resultado.getString("VIN"), resultado.getString("Descripcion")});
        }
        conexion.close();
        return vehiculos;
    }
    public DefaultTableModel obtenerMaquinaria() throws SQLException, NoSePuedeConectar{
        maquinaria = null;
        iniciarTablaMaquinaria();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Tipo, Marca, Linea, Modelo, VIN, Descripcion, Habilitado FROM maquinaria;");
        while(resultado.next()){
            boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            if(habilitado) maquinaria.addRow(new String[] {"MRM" + resultado.getString("id"), resultado.getString("Tipo"), resultado.getString("Marca"), (resultado.getString("Linea")== null) ? "":resultado.getString("Linea"), resultado.getString("Modelo"), resultado.getString("VIN"), resultado.getString("Descripcion")});
        }
        conexion.close();
        return maquinaria;
    }
    public DefaultTableModel obtenerComputo() throws SQLException, NoSePuedeConectar{
        computo = null;
        iniciarTablaComputo();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Identificador, Tipo, Marca, Modelo, SistemaOperativo, SN, Descripcion FROM equipodecomputo;");
        while(resultado.next()){
            computo.addRow(new String[] {resultado.getString("Identificador"), resultado.getString("Tipo"), resultado.getString("Marca"), resultado.getString("Modelo"), (resultado.getString("SistemaOperativo") == null ? "": resultado.getString("SistemaOperativo")), resultado.getString("SN") , resultado.getString("Descripcion")});
        }
        conexion.close();
        return computo;
    }
    /**
     * Metodo que regresa la lista de proveedores como un arreglo
     * @return
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public DefaultTableModel obtenerProceedores() throws SQLException, NoSePuedeConectar{
         Proveedores = null;
        iniciarTablaProveedores();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Nombre, Nit, Credito, Saldo, Habilitado FROM proveedor;");
        while(resultado.next()){
            boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            if(habilitado) Proveedores.addRow(new String[] {resultado.getString("Nombre"), resultado.getString("Nit"), (resultado.getString("Credito").equals("1"))? "SI": "NO", resultado.getString("Saldo")});
        }
        conexion.close();
        return Proveedores;
    }
    public DefaultTableModel obtenerTrabajadores() throws SQLException, NoSePuedeConectar{
        trabajadores = null;
        iniciarTablaTrabajadadores();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre, Apellido, Habilitado FROM Trabajador;");
        while(resultado.next()){
            boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            if(habilitado) trabajadores.addRow(new String[] {resultado.getInt("id") + "", resultado.getString("Nombre") + " " + resultado.getString("Apellido")});
        }
        conexion.close();
        return trabajadores;
    }
    public DefaultTableModel obtenerTelefonoCliente(int Cliente_id) throws SQLException, NoSePuedeConectar{
        telefonos = null;
        iniciarTablaTelefonos();
        if (Cliente_id != 0){
            conectar();
            Statement instruccion = conexion.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT t.Numero Numero, t.Extension Extencion, t.Nombre, ca.Codigo Codigo, tip.Tipo Tipo FROM telefono t INNER JOIN codigoarea ca ON t.CodigoArea_id = ca.id INNER JOIN tipotelefono tip ON t.Tipo_id = tip.id WHERE t.Cliente_id = " + Cliente_id + ";");
            while(resultado.next()){
                telefonos.addRow(new String[] {resultado.getString("Nombre") , resultado.getString("Tipo") , resultado.getString("Codigo") + " " + resultado.getString("Numero") + ((resultado.getString("Extencion") == null)? "": " Ext: " + resultado.getString("Extencion"))});
            }
            conexion.close();
        }
        return telefonos;
    }
    public DefaultTableModel obtenerProductos() throws SQLException, NoSePuedeConectar{
         Productos = null;
         iniciarTablaProductos();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Codigo, Codigo_Barras, Descripcion FROM producto where habilitado=1;");
        while(resultado.next()){
            Productos.addRow(new String[] {resultado.getString("Codigo"), resultado.getString("Codigo_Barras"), resultado.getString("Descripcion"), existencias(resultado.getInt("id"))+""});
        }
        conexion.close();
        return Productos;
    }
    public DefaultTableModel obtenerProductosfac(String Sucursal) throws SQLException, NoSePuedeConectar{
         Productos = null;
         iniciarTablaProductos();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Codigo, Codigo_Barras, Descripcion FROM producto where habilitado=1;");
        while(resultado.next()){
            Productos.addRow(new String[] {resultado.getString("Codigo"), resultado.getString("Codigo_Barras"), resultado.getString("Descripcion"), existencia(sucursalId(Sucursal),resultado.getInt("id"))+""});
        }
        conexion.close();
        return Productos;
    }
     public DefaultTableModel obtenerExistencias(String Codigo) throws SQLException, NoSePuedeConectar{
         Existencias = null;
         iniciarTablaExistencias();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT s.Nombre , e.Existencia from existencia e INNER JOIN producto p ON e.Producto_id = p.id INNER JOIN sucursales s on e.Sucursales_id = s.id WHERE p.Codigo = '"+ Codigo +"';");
        while(resultado.next()){
            Existencias.addRow(new String[] {resultado.getString("Nombre"), resultado.getString("Existencia")});
        }
        conexion.close();
        return Existencias;
    }
    public int sucursalId(String sucursal) throws SQLException, NoSePuedeConectar{
        int id= 0;
        conectar();
        
        Statement instrucion = conexion.createStatement();
        ResultSet resultado = instrucion.executeQuery("SELECT id FROM sucursales WHERE Nombre = '" + sucursal + "' OR Empresa = '" + sucursal + "';");
        while(resultado.next()){
            id = resultado.getInt("id");
        }
        
        conexion.close();
        return id;
    }
    private float existencia(int Sucursal ,int Producto) throws SQLException, NoSePuedeConectar{
        float exi= 0;
        conectar();
        
        Statement instrucion = conexion.createStatement();
        ResultSet resultado = instrucion.executeQuery("SELECT Existencia from existencia WHERE Producto_id = " + Producto + " AND Sucursales_id = " + Sucursal + ";");
        while(resultado.next()){
            exi = resultado.getFloat("Existencia");
        }
        
        conexion.close();
        return exi;
    }
    public DefaultTableModel obtenerPedidos() throws SQLException, NoSePuedeConectar{
        Pedidos = null;
        iniciarTablaProveedores();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Nombre, Nit, Credito, Saldo, Habilitado FROM proveedor;");
        while(resultado.next()){
            boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            if(habilitado) Pedidos.addRow(new String[] {resultado.getString("Nombre"), resultado.getString("Nit"), (resultado.getString("Credito").equals("1"))? "SI": "NO", resultado.getString("Saldo")});
        }
        conexion.close();
        return Pedidos;
    }
    public DefaultTableModel obtenerClientes() throws SQLException, NoSePuedeConectar{
        Clientes = null;
        iniciarTablaClientes();
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, NIT, Nombre, Apellido, Descuento, LimiteCredito, Saldo FROM cliente;");
        while(resultado.next()){
            //boolean habilitado = (resultado.getString("Habilitado").equals("1"));
            /*if(habilitado)*/ Clientes.addRow(new String[] {resultado.getInt("id")+ "", resultado.getString("NIT"), (resultado.getString("Apellido") == null)? resultado.getString("Nombre"): resultado.getString("Nombre") + " " + resultado.getString("Apellido"), resultado.getString("Descuento"), resultado.getString("LimiteCredito"), resultado.getString("Saldo")});
        }
        conexion.close();
        return Clientes;
    }
    public int numeroPedido() throws SQLException, NoSePuedeConectar{
        int numero = 0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT AUTO_INCREMENT Nuevo FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'sce' AND TABLE_NAME = 'compra';");
        while (resultado.next()) {
            numero = resultado.getInt("Nuevo");
        }
        conexion.close();
        return numero;
    }
    public void insertarDetallePedido(int idCodigo, int idProve, int Pedido, float Precio, float Cantidad) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        
        instruccion.executeUpdate("INSERT INTO detalle_compra (Producto_id, Compra_id, proveedor_id, Precio) VALUES (" + idCodigo + ", " + (Pedido - 1) + ", " + idProve + ", " + Precio + ");");
        
        conexion.close();
    }
     public void insertarDetalleCompra(int Producto_id, int Ventas_id, float Cantidad, float PrecioVenta, float Descuento) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        
        instruccion.executeUpdate("INSERT INTO detalledeventa (Producto_id, Ventas_id, Cantidad, PrecioVenta, Descuento) VALUES (" + Producto_id + ", " + Ventas_id + ", " + Cantidad + ", " + PrecioVenta + ", " + Descuento + ");");
        
        conexion.close();
    }
    public int idCodigo(String codigo) throws SQLException, NoSePuedeConectar{
        int numero = 0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id FROM Producto WHERE Codigo = '" + codigo +"';");
        while (resultado.next()) {
            numero = resultado.getInt("id");
        }
        conexion.close();
        return numero;
    }
    public int idProve(String Nombre) throws SQLException, NoSePuedeConectar{
        int numero = 0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id FROM proveedor WHERE Nombre = '" + Nombre +"';");
        while (resultado.next()) {
            numero = resultado.getInt("id");
        }
        conexion.close();
        return numero;
    }
    private float existencias(int id) throws SQLException, NoSePuedeConectar{
       float existencias = 0;
       
            conectar();
            Statement instruccion = conexion.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT Existencia FROM Existencia WHERE Producto_id = " + id + ";");
            while (resultado.next()){
                existencias += resultado.getFloat("Existencia");
            }
            conexion.close();
       
       return existencias;
    }
    public float existencias(String Codigo) throws SQLException, NoSePuedeConectar{
       float existencias = 0;
       
            conectar();
            Statement instruccion = conexion.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT e.Existencia from existencia e INNER JOIN producto p ON e.Producto_id = p.id WHERE p.Codigo = '" + Codigo + "';");
            while (resultado.next()){
                existencias += resultado.getFloat("Existencia");
            }
            conexion.close();
       
       return existencias;
    }
    public int cantidadProveedores() throws SQLException, NoSePuedeConectar{
        int cant = 0;
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT COUNT(*) Cantidad FROM proveedor;");
        while(resultado.next()){
            cant = resultado.getInt("Cantidad");
        }
        conexion.close();
        return cant;
    }
    public int siguienteCotizacion() throws SQLException, NoSePuedeConectar{
        int cant = 0;
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT MAX(Numero) max FROM ventas;");
        while(resultado.next()){
            cant = resultado.getInt("max") +1;
        }
        conexion.close();
        return cant;
    } 
    /**
     * EJEMPLO DE COMO USAR ESTA CLASE
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public void ejemploDeUso() throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select * from unidad"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            System.out.println(resultado.getInt(1)+"    "+resultado.getString("Nombre"));
            //para extraer los datos del resultado de la instruccion, pueden obtenerse en diferentes tipos de variable
            //Y pueden buscarse por numero de columna (iniciando en 1) o nombre de columna
        }
        conexion.close();
    }
    /**
     * Invoca a un procedimiento almacenado en la BD para hacer login al programa
     * @param usuario nombre del usuario
     * @param pass contraeña
     * @return retorna 1 si los datos son correctos, 0 de lo contrario
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int login(String usuario, String pass) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT login('"+usuario+"','"+claveCifradoBase+"','"+pass+"') R"); //se guarda el resultado de la instruccion
        int res=-1;
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            res= resultado.getInt(1);
        }
        conexion.close();
        return res;
    }
    public String obtenerUsuario (int Trabajador_id) throws NoSePuedeConectar, SQLException{
        String User = "";
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT u.Usuario U FROM trabajador t INNER JOIN usuario u ON t.Usuario_id = u.id WHERE t.id = " + Trabajador_id +";");
        while (resultado.next()) {            
            User = resultado.getString("U");
        }
        conexion.close();        
        return User;
    }
    /**
     * Invoca un procedimiento almacenado en la BD para crear un nuevo usuario
     * @param usuario usuario nuevo
     * @param pass contraseña del usuario
     * @param trabajadorID id del trabajador asociado a este usuario (puede ser nulo si no esta asociado a ninguno)
     * @param nivelAcceso id del nivel de acceso del usuario
     * @return 1 si se ha creado el usuario, 0 de lo contrario
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int crearUsuario(String usuario, String pass, int trabajadorID, int nivelAcceso) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT creacionUsuarioVacio('"+usuario+"','"+pass+"','"+claveCifradoBase+"',"+trabajadorID+","+nivelAcceso+") R"); //se guarda el resultado de la instruccion
        int res=-1;
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            res= resultado.getInt(1);
        }
        conexion.close();
        return res;
    }
    /**
     * Elimina un usuario
     * @param usuario usuario a eliminar
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public void eliminarUsuario(String usuario) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM usuario WHERE Usuario='"+usuario+"'"); //se guarda el resultado de la instruccion
        conexion.close();
    }
    /**
     * Obtiene la lista de usuarios
     * @return un arreglo con la lista de usuarios
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public ArrayList obtenerUsuarios() throws SQLException, NoSePuedeConectar{
        ArrayList users=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT Usuario FROM usuario"); //se guarda el resultado de la instruccion
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            users.add(resultado.getString(1));
        }
        conexion.close();
        return users;
    }
    public void insertarFormaPagoFac(float cantidad, String Descripccion,int  Tipo_id, int Fact_id) throws NoSePuedeConectar, SQLException{
        conectar();
        
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("INSERT INTO formapago (Cantidad, Descripcion, Tipo_id, Factura_id) VALUES (" + cantidad + ", '" + Descripccion + "', "+ Tipo_id + ", " +Fact_id + ");");
        conexion.close();
    }
    public int obtenerFacid(String Numero, String Serie, int Sucursal_id) throws NoSePuedeConectar, SQLException{
        int id= 0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id FROM factura WHERE Numero ='"+ Numero + "' AND Sucursales_id = " + Sucursal_id + " AND Serie = '" +Serie + "';");
        while (resultado.next()) {            
            id = resultado.getInt("id");
        }
        conexion.close();
        return id;
    }
    /**
     * Metodo que inserta un nuevo producto en la base
     * @param codigo codigo interno
     * @param codBarras codigo de barras del producto
     * @param descrip //descripcion del profucto
     * @param venta //precio de venta
     * @param costo //Precio de compra
     * @param estanteria //estanteria donde se encuentra
     * @param columna //columna de estanteria
     * @param fila //fila de estanteria
     * @param marca //marca del producto
     * @param unidad //id de la unidad de medicion
     * @param sucursal //sucursal a la que se quiere agregar
     * @param existencia
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public void insertarProducto (String codigo, String codBarras,String descrip, double venta,
            double costo, String estanteria, String columna, String fila, String marca, String unidad, 
            int sucursal, double existencia) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        int marcaId=0;
        if(estanteria.isEmpty())
            estanteria=null;
        if(columna.isEmpty())
            columna=null;
        if(fila.isEmpty())
            fila=null;
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select id from marca where Nombre= '"+marca.toUpperCase()+"';"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        if(resultado.next())
            marcaId=resultado.getInt(1);
        else
        {
            instruccion=conexion.createStatement();
            instruccion.executeUpdate("insert into marca (Nombre) values ('"+marca.toUpperCase()+"');");
             resultado = instruccion.executeQuery("select id from marca where Nombre= '"+marca.toUpperCase()+"';");
            if(resultado.next())
            {   
                marcaId=resultado.getInt(1);      
            }
        }
        instruccion=conexion.createStatement();
        instruccion.executeUpdate("insert into producto (codigo,codigo_barras,descripcion,precio_venta,precio_costo,Estanteria,"
                +"Columna,Fila,marca_id,unidad_id) values ('"+codigo+"','"+codBarras+"','"+descrip+"',"+venta+","+costo+","+estanteria+","+
                columna+","+fila+","+marcaId+","+unidad+");");
        instruccion=conexion.createStatement();
        resultado = instruccion.executeQuery("select max(id) from producto;");
        instruccion=conexion.createStatement();
        if(resultado.next())
            instruccion.executeUpdate("insert into existencia (sucursales_id,producto_id,existencia) values ("
                +sucursal+","+resultado.getInt(1)+","+existencia+");");
        conexion.close();
    }
    public void modificarProducto(int id,String codigo, String codBarras,String descrip, double venta, 
            double costo, String estanteria, String columna, String fila, String marca) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        String marcaId;
        //int fraccion=0;
        if(estanteria.isEmpty())
            estanteria=null;
        if(columna.isEmpty())
            columna=null;
        if(fila.isEmpty())
            fila=null;
        /*if(unidad!=null)
            fraccion=1;*/
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado;
        if(marca.isEmpty())
            marcaId=null;
        else{
            resultado = instruccion.executeQuery("select id from marca where Nombre= '"+marca.toUpperCase()+"';"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
            if(resultado.next())
                marcaId=resultado.getString(1);
            else
            {
                instruccion=conexion.createStatement();
                instruccion.executeUpdate("insert into marca (Nombre) values ('"+marca.toUpperCase()+"');");
                resultado = instruccion.executeQuery("select id from marca where Nombre= '"+marca.toUpperCase()+"';");
                marcaId=resultado.getString(1);       
            }
        }
        instruccion.executeUpdate("update producto set codigo='"+codigo+"',codigo_barras='"+codBarras+"',descripcion='"+descrip+
                "',precio_venta="+venta+",precio_costo="+costo+",Estanteria="+estanteria+",Columna="+columna+
                ",Fila="+fila+",marca_id="+marcaId+" where id="+id+";");
        conexion.close();
    }
    /**
     * Funcion que retorna guarda las sucursales y su id en una matriz, tambien guarda las unidades 
     * registradas en la base de datos y su id
     * @return matriz dinamica de tamaño 2xN que alamcena la sucursal y su id, y esta matriz tambien incluye otra de 2xN
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public ArrayList[] obtener_Sucursales_Unidades() throws SQLException, NoSePuedeConectar{
        ArrayList[] matriz=new ArrayList[4];
        matriz[0]=new ArrayList();
        matriz[1]=new ArrayList();
        matriz[2]=new ArrayList();
        matriz[3]=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select id,Nombre from sucursales"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            matriz[0].add(resultado.getInt(1));
            matriz[1].add(resultado.getString(2));
        }
        resultado = instruccion.executeQuery("select id,Nombre from unidad"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            matriz[2].add(resultado.getInt(1));
            matriz[3].add(resultado.getString(2));
        }
        conexion.close();
        return matriz;
    }
    /**
     * 
     * @param id Recibe una id del producto a buscar
     * @param sucursal Id de la sucursal en donde se busca
     * @return un arreglo con todos los atributos del producti
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public ArrayList obtener_detalleProducto(int id,int sucursal) throws SQLException, NoSePuedeConectar
    {
        ArrayList atributo=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select p.id, p.codigo, codigo_barras,descripcion, precio_venta, "+
                "precio_costo,estanteria, columna, fila,u.id ,m.Nombre from producto p left join "+
                "unidad u on u.id=p.Unidad_id left join marca m ON m.id=p.Marca_id where p.id="+id+";"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        if(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            for(int i=1;i<12;i++)
            {
                atributo.add(resultado.getString(i));
            }
        }   
        conexion.close();
        return atributo;
    }
      /**
     * Funcion que obtiene todos los productos contenidos en la base de datos, su descripcion, codiogs y su id en una matriz
     * @return Matriz con atributos de todos los productos
     * @throws SQLException 
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
   public ArrayList[] obtener_productos() throws SQLException, NoSePuedeConectar{
        ArrayList[] matriz=new ArrayList[4];
        matriz[0]=new ArrayList();
        matriz[1]=new ArrayList();
        matriz[2]=new ArrayList();
        matriz[3]=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select id,codigo,codigo_barras,descripcion from producto where habilitado=1;"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            matriz[0].add(resultado.getInt(1));
            matriz[1].add(resultado.getString(2));
            matriz[2].add(resultado.getString(3));
            matriz[3].add(resultado.getString(4));
        }
        conexion.close();
        return matriz;
    }
    public ArrayList obtener_detalleProducto(String des, String codigo, String codigo_barras) throws SQLException, NoSePuedeConectar{
        ArrayList atributo=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("select p.id, p.codigo, codigo_barras,descripcion, precio_venta, "+
                "precio_costo,estanteria, columna, fila,u.id ,m.Nombre from producto p left join "+
                "unidad u on u.id=p.Unidad_id left join marca m ON m.id=p.Marca_id where p.codigo='"+codigo+
                "' and p.codigo_barras='"+codigo_barras+"' and p.descripcion='"+des+"';"); //se guarda el resultado de la instruccion, en esta ocasion, es una consulta
        if(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            for(int i=1;i<12;i++)
            {
                atributo.add(resultado.getString(i));
            }
        }   
        conexion.close();
        return atributo;
    }
   /**
    * metodo que deshabilita productos en la base de datos
    * @param id del producto a deshabilitar
    * @throws SQLException 
    */
   public void deshabilitarProducto(int id) throws SQLException, NoSePuedeConectar
   {
       if(id>0)
       {
           conectar();
           Statement instruccion=conexion.createStatement();
           instruccion.executeUpdate("update producto set habilitado=0 where id="+id+";");//se actualiza el campo habilitado como 0
           conexion.close();
       }
   }
   public void crearFactura(String Numero, String Serie, float sub, float iva, int cliente_id, int Usuario_id, int Sucursal_id, int Ventas_id, String comentario) throws SQLException, NoSePuedeConectar{
      conectar();
       Statement instruccion = conexion.createStatement();
       instruccion.executeUpdate("INSERT INTO factura (Numero, Serie, Subtotal, IVA, Total, Cliente_id, Trabajador_id, Sucursales_id, Ventas_id, Comentario) VALUES ('" + Numero + "', '"+ Serie+ "', "+sub+ ", " + iva + ", " + (sub+iva) +", "
               +  cliente_id+ ", " + Usuario_id + ", " + Sucursal_id+ ", "+ Ventas_id + ", '" + comentario+ "');");

       conexion.close();
   }
   /**
    * Funcion que ingresa una nueva cotizacion para un cliente ya registrado
    * retorna un arreglo con la información principal del cliente
    * @param idCliente id del cliente
    * @param id_usuario id del usuario que ingreso la cotizacion
    * @return ArrayList que contiene 0.-id de cotizacion, 1.-numero de cotizacion, 2.- id del cliente.- 3 total
    * @throws SQLException 
    */
   public ArrayList insertarCotizacion(int idCliente,int id_usuario) throws SQLException, NoSePuedeConectar{
       ArrayList lista=new ArrayList();
        conectar();
        Statement instruccion=conexion.createStatement();
        instruccion.executeUpdate("insert into ventas (Cliente_id, Trabajador_id) values ("+idCliente+","+id_usuario+");");//se inseta el cloente
        int id=0;
        ResultSet resultado=instruccion.executeQuery("select id from ventas where Cliente_id="+idCliente+" and Trabajador_id="+id_usuario+" and date(NOW())=date(fecha);");//se obtiene el cliente insertado
        while(resultado.next())
        { 
           id=resultado.getInt(1);
        }
        lista.add(id);
        resultado=instruccion.executeQuery("select numero, cliente_id,total from ventas where id="+id+";");//se guardan los datos de la cotizacion
        if(resultado.next())
        {
            lista.add(resultado.getInt(1));
            lista.add(resultado.getString(2));
            lista.add(resultado.getDouble(3));
        }
        conexion.close();
        return lista;
   }
   public void modificarCotizacion(int id, int tiempo, float total) throws SQLException, NoSePuedeConectar{
       conectar();
       Statement instruccion = conexion.createStatement();
       instruccion.executeUpdate("UPDATE ventas SET Tiempo = " + tiempo + ", Total = " + total + ", Fecha = NOW() WHERE id = " + id + ";");
       
       conexion.close();
   }
   /**
    * Funcion que Inserta una nueva cotizacion para un cliente no registrado
    * y retorna los datos de la nueva cotizacion
    * @param nombre Nombre que tendra la cotizacion
    * @param id_usuario usuario que realizo la cotizacion
    * @return ArrayList que contiene 0.-id de cotizacion, 1.-numero de cotizacion, 2.- nombre del cliente.- 3 total
    */
   public ArrayList insertarCotizacion(String nombre,int id_usuario) throws SQLException, NoSePuedeConectar
   {
        ArrayList lista=new ArrayList();
        conectar();
        Statement instruccion=conexion.createStatement();
        instruccion.executeUpdate("insert into ventas (Nombre,Usuario_id) values ('"+nombre+"',"+id_usuario+");");//se inseta el cloente
        int id=0;
        ResultSet resultado=instruccion.executeQuery("select id from ventas where Cliente_id='"+nombre+"' and Usuario_id="+id_usuario+" and date(NOW())=date(fecha);");//se obtiene el cliente insertado
        while(resultado.next())
        { 
           id=resultado.getInt(1);
        }
        lista.add(id);
        resultado=instruccion.executeQuery("select numero,nombre,total from ventas where id="+id+";");//se guardan los datos de la cotizacion
        if(resultado.next())
        {
            lista.add(resultado.getInt(1));
            lista.add(resultado.getString(2));
            lista.add(resultado.getDouble(3));
        }
        conexion.close();
        return lista;
   }

    public DefaultTableModel obtenerClientes_venta() throws SQLException, NoSePuedeConectar{
        Clientes=null;
        iniciarTablaClientes();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Nombre, Apellido, Nit from cliente;");
        while(resultado.next()){
            Clientes.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3)});
        }
        conexion.close();
        return Clientes;
    }
    public int obtenerExistencia(int sucursalId, int productoId) throws SQLException, NoSePuedeConectar{
        int existencia=0;
        conectar();
        Statement instruccion = conexion.createStatement();
         ResultSet resultado;
        if(sucursalId>0)
        {
            resultado = instruccion.executeQuery("select e.Existencia from existencia e where e.Sucursales_id="+sucursalId+" e.Producto_id="+productoId+";");
        }
        else
        {
            resultado = instruccion.executeQuery("select SUM(e.Existencia) from existencia e where  e.Producto_id="+productoId+";");
        }
        if(resultado.next())
        {
            existencia=resultado.getInt(1);
        }
        return existencia;
        }
   public ArrayList[] obtenerSucursales() throws SQLException, NoSePuedeConectar{
      
       ArrayList[] Sucursales = new ArrayList[3];
       Sucursales[0] = new ArrayList();
       Sucursales[1] = new ArrayList();
       Sucursales[2] = new ArrayList();
       
       conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT Nombre, NumeroFac, SerieFact FROM sucursales");
       while (resultado.next()) {     
           Sucursales[0].add(resultado.getString("Nombre"));
           Sucursales[1].add(resultado.getString("NumeroFac"));
           Sucursales[2].add((resultado.getString("SerieFact") == null) ? "": resultado.getString("SerieFact"));
       }
       
       conexion.close();
       return Sucursales;
   }
   
   public String[] obtenerSucursal(String Nombre) throws SQLException, NoSePuedeConectar{
      
       String[] Sucursales = null;

       
       conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT id, NumeroFac, SerieFact FROM sucursales WHERE Nombre='" + Nombre + "';");
       while (resultado.next()) {     
           Sucursales = (new String[] {resultado.getString("id"), resultado.getString("NumeroFac"), (resultado.getString("SerieFact") == null) ? "": resultado.getString("SerieFact")});
       }
       
       conexion.close();
       return Sucursales;
   }
   
   public String fecha()throws SQLException, NoSePuedeConectar{
       String Fecha = "";
       conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT obtenerFecha() Fecha;");
       while (resultado.next()) {     
           Fecha = resultado.getString("Fecha");
       }
       
       conexion.close();
       return Fecha;
   }
   public String[] obtenerCliente(int id) throws SQLException, NoSePuedeConectar{
        String[]  Cliente= null;
               
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT NIT, Nombre, Apellido, Descuento, Direccion, LimiteCredito FROM cliente WHERE id = " + id + ";");
        while(resultado.next()){
             Cliente = (new String[] {id + "", resultado.getString("NIT"), resultado.getString("Nombre"), (resultado.getString("Apellido") == null) ? "" : resultado.getString("Apellido"), resultado.getString("Descuento"), resultado.getString("Direccion"), resultado.getFloat("LimiteCredito") + ""});
        }
        conexion.close();
        return Cliente;
   }
   public String[] obtenerCliente(String nit) throws SQLException, NoSePuedeConectar{
        String[] Cliente = null;

        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre, Apellido, Descuento, Direccion, LimiteCredito FROM cliente WHERE NIT = '" + nit + "';");
        while(resultado.next()){
            Cliente = (new String[] {resultado.getInt("id")+ "", nit, resultado.getString("Nombre"), (resultado.getString("Apellido") == null) ? "" : resultado.getString("Apellido"), resultado.getString("Descuento"), resultado.getString("Direccion"), resultado.getFloat("LimiteCredito") + ""});
        }
        conexion.close();
        return Cliente;
   }
   public String[] obtenerProducto(String Codigo) throws SQLException, NoSePuedeConectar{
        String[] Cliente = null;

        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Descripcion, Precio_Venta Precio, Descuento FROM producto WHERE Codigo = '" + Codigo+ "';");
        while(resultado.next()){
            Cliente = (new String[] {resultado.getInt("id")+ "", Codigo,resultado.getString("Descripcion"), resultado.getFloat("Precio") + "", resultado.getString("Descuento")});
        }
        conexion.close();
        return Cliente;
   }
   public int obtenerProductoID(String Codigo) throws SQLException, NoSePuedeConectar{
        int id = 0;

        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id FROM producto WHERE Codigo = '" + Codigo+ "';");
        while(resultado.next()){
            id =resultado.getInt("id");
        }
        conexion.close();
        return id;
   }
   
   public boolean existeCliente(String nit) throws SQLException, NoSePuedeConectar{      
       conectar();
       Statement instruccion = conexion.createStatement();
       ResultSet resultado = instruccion.executeQuery("SELECT COUNT(*) cant FROM cliente WHERE NIT = '" + nit + "';");
       
       while (resultado.next()) {           
           if (resultado.getInt("cant") > 0) {
               conexion.close();
               return true;
           }
       }
       
       conexion.close();
       return false;   
   }

    public DefaultTableModel obtenerProductos_vista(int filtro, String criterio) throws SQLException, NoSePuedeConectar{
         Productos = null;
         String texto_busqueda="";
         if(!criterio.isEmpty())
         {
             texto_busqueda="and ";
             switch(filtro){
                 case 0:
                     texto_busqueda+="codigo ";
                     break;
                 case 1:
                     texto_busqueda+="codigo_barras ";
                     break;
                 default:
                     texto_busqueda+="descripcion ";
                     break;
             }
             texto_busqueda+="like concat('%','"+criterio+"','%')";
                     
         }
         iniciarTablaProductos();
        Productos.setColumnCount(3);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Codigo, Codigo_Barras, Descripcion FROM producto where habilitado=1 "+texto_busqueda+";");
        while(resultado.next()){
            Productos.addRow(new String[] {resultado.getString("Codigo"), resultado.getString("Codigo_Barras"), resultado.getString("Descripcion")});
        }
        conexion.close();
        return Productos;
    }
    
    public DefaultTableModel Nuevo_obtenerProductos(int filtro, String criterio) throws SQLException, NoSePuedeConectar{
         Productos = null;
         String texto_busqueda="";
         if(!criterio.isEmpty())
         {
             texto_busqueda="and ";
             switch(filtro){
                 case 0:
                     texto_busqueda+="codigo ";
                     break;
                 case 1:
                     texto_busqueda+="codigo_barras ";
                     break;
                 default:
                     texto_busqueda+="descripcion ";
                     break;
             }
             texto_busqueda+="like concat('%','"+criterio+"','%')";
                     
         }
         iniciarTablaProductos();
        Productos.setColumnCount(11);
        
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT Codigo, Codigo_Barras, Descripcion, Precio_Venta, Precio_Costo, Estanteria, Columna, Fila, Marca_id, Unidad_id FROM producto where habilitado=1 "+texto_busqueda+";");
        while(resultado.next()){
            Productos.addRow(new String[] {resultado.getString("Codigo"), resultado.getString("Codigo_Barras"), resultado.getString("Descripcion"), resultado.getString("Precio_Venta"), resultado.getString("Precio_Costo"), resultado.getString("Estanteria"), resultado.getString("Columna"), resultado.getString("Fila")
            , resultado.getString("Marca_id"), resultado.getString("Unidad_id")});
        }
        conexion.close();
        return Productos;
    }
    
    public DefaultTableModel obtenerFacturasConsulta(String fecha,String nombre,String dpi) throws SQLException, NoSePuedeConectar
    {
        DefaultTableModel facturas=null;
        facturas = new DefaultTableModel(null, new String[]{"Numero","Nombre","Apellido","NIT","Vendedor","Serie","Fecha","Sucursal"}){
                boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false,false,false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
                }
            };
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select f.Numero,c.Nombre,c.Apellido,c.NIT,t.Nombre,f.Serie,date(f.Fecha),"
                + "s.Nombre from factura f left join trabajador t on t.id=f.Trabajador_id left join cliente c on c.id=f.Cliente_id "
                + "left join sucursales s on s.id=f.Sucursales_id;");
        while(resultado.next())
        {
            String[] fila=new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3),
                resultado.getString(4),resultado.getString(5),resultado.getString(6),
                resultado.getString(7),resultado.getString(8)};
            if(fila[2]==null)
                fila[2]="N/A";
            if(fila[3]==null)
                fila[3]="N/A";
            facturas.addRow(fila);
        }
        conexion.close();
        return facturas;
    }
    public ArrayList id_ventas(String fecha,String nombre,String dpi) throws SQLException, NoSePuedeConectar{
        ArrayList ids=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select v.id from factura f left join trabajador t on "
                + "t.id=f.Trabajador_id left join cliente c on c.id=f.Cliente_id left join sucursales s on "
                + "s.id=f.Sucursales_id left join ventas v on v.Trabajador_id=t.id;");
        while(resultado.next())
        {
            ids.add(resultado.getInt(1));
        }
        conexion.close();
        return ids;
    }
    public DefaultTableModel obtenerDetalleFactura(int id) throws SQLException, NoSePuedeConectar
    {
        DefaultTableModel facturas=null;
        facturas = new DefaultTableModel(null, new String[]{"Producto","Cantidad","Descuento","Precio Venta","Sub-total"}){
                boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false,false,false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
                }
            };
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select p.Descripcion,d.Cantidad,"+
                "d.Descuento,d.PrecioVenta from detalledeventa d left join ventas v on "+
                "d.Ventas_id=v.id left join producto p on p.id=d.Producto_id where v.id="+id+"; ");
        while(resultado.next())
        {
            String[] fila=new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3),
                resultado.getString(4),((double)resultado.getInt(2)*resultado.getDouble(4)*(100-resultado.getInt(3))/100)+""};
            if(fila[2]==null)
                fila[2]="N/A";
            if(fila[3]==null)
                fila[3]="N/A";
            facturas.addRow(fila);
        }
        conexion.close();
        return facturas;
    }
   /**
     * Metodo que regresa la lista de clientes como un arreglo
     * @return una DefaultTableModel con los clientes en la BD
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public DefaultTableModel obtenerClientesJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo = null;
        modelo=inicializarTablaClientes(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, NIT, Nombre, Apellido, Descuento, Direccion, LimiteCredito, Saldo, Cheque, Habilitado FROM cliente;");
        while(resultado.next()){
            if(resultado.getString("Habilitado").equals("1"))
                modelo.addRow(new String[] {resultado.getString("id"),resultado.getString("NIT"), resultado.getString("Nombre"), resultado.getString("Apellido"), resultado.getString("Descuento"),resultado.getString("Direccion"),resultado.getString("LimiteCredito"),resultado.getString("Saldo"), (resultado.getString("Cheque").equals("1")? "SI": "NO")});
        }
        conexion.close();
        return modelo;
    }
    /**
     * Crea una nuevo DefaultTableModel para clientes
     * @param modelo el modelo para la JTable, vacio o con otros datos
     * @return el modelo para la JTable, inicializado
     */
    private DefaultTableModel inicializarTablaClientes(DefaultTableModel modelo) {
//        
        modelo = new DefaultTableModel(null, new String[]{"ID", "NIT", "Nombre", "Apellido", "% descuento","Dirección","Limite de Crédito","Saldo Actual","¿Puede darnos cheque?"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Invoca a una funcion almacenada en la BD para crear un nuevo cliente
     * @param nombre nombre del cliente
     * @param apellido apellido del cliente
     * @param descuento % de descuento del cliente
     * @param direccion dirección del cliente
     * @param limCredito limite de credito del cliente
     * @param saldo saldo inicial del cliente
     * @param NIT NIT del cliente
     * @param cheque si se le acepta cheque al cliente o no
     * @return 1 en caso de que se inserte, 0 de lo contrario
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int crearCliente(String nombre, String apellido, long descuento, String direccion, long limCredito, float saldo, String NIT, boolean cheque) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT creaClientes('"+(nombre.equals("")? (NIT.equals("")?"Consumidor Final":"N/A"):nombre)+"','"+(apellido.equals("")? (NIT.equals("")?"":"N/A"):apellido)+"',"+descuento+",'"+(direccion.equals("")? "N/A":direccion)+"',"+limCredito+","+saldo+",'"+(NIT.equals("")? "C/F":NIT)+"',"+(cheque? 1:0)+") R"); //se guarda el resultado de la instruccion
        int res=-1;
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            res= resultado.getInt(1);
        }
        conexion.close();
        return res;
    }
    /**
     * Actualiza los datos de un cliente
     * @param id ID del cliente a actualizar
     * @param nombre nuevo nombre
     * @param apellido nuevo apellido
     * @param descuento nuevo descuento
     * @param direccion nueva dirección
     * @param limCredito nuevo limite de credito
     * @param saldo nuevo saldo
     * @param NIT nuevo NIT del cliente
     * @param cheque si se le acepta cheque al cliente, o no
     * @return numero de filas en la BD que han sido modificadas (Debe ser 1)
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int modificarCliente(int id, String nombre, String apellido, long descuento, String direccion, long limCredito, float saldo, String NIT, boolean cheque) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Cliente SET nombre='"+(nombre.equals("")? "N/A":nombre)+"', apellido='"+(apellido.equals("")? "N/A":apellido)+"',descuento="+descuento+",direccion='"+(direccion.equals("")? "N/A":direccion)+"',limitecredito="+limCredito+",saldo="+saldo+",nit='"+(NIT.equals("")? "N/A":NIT)+"',cheque="+(cheque? 1:0)+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Elimina un cliente de la BD
     * @param id id del cliente a borrar
     * @return el numero de filas afectadas (debe ser 1)
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int eliminarCliente(int id) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        //int resultado = instruccion.executeUpdate("DELETE FROM Cliente WHERE id="+id+";"); //se guarda el resultado de la instruccion
        int resultado = instruccion.executeUpdate("UPDATE Cliente SET habilitado=0 WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Metodo que genera la conexion para saber si puede conectarse a la BD
     * @return true si se puede conectar a la base con los datos ingresados, false de lo contrario
     */
    public boolean probarConexion ()
    {
       conexion=null;
        try{
            try {
                Class.forName(driver);//Se utiliza el driver de conexion
            }catch (ClassNotFoundException ex) {
                return false;
            }
            conexion=DriverManager.getConnection(url+ip+"/"+nombreBD,user,pass);//Se conecta con la base de datos enviando
            //los parametros url, user, pass,
            conexion.close();
            return true;
        }catch (SQLException ex) {
            return false;
        }
        
    }
    
     public void facturar(int id) throws SQLException, NoSePuedeConectar{
        conectar();
        
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE ventas SET Vendida = 1 WHERE id =" + id + ";");
        conexion.close();
    }
     /**
     * Metodo que regresa la lista de trabajadores como un arreglo
     * @return una DefaultTableModel con los trabajadores en la BD
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public DefaultTableModel obtenerTrabajadoresJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo = null;
        modelo=inicializarTablaTrabajadores(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, DPI, Nombre, Apellido, Telefono, Comision, Direccion, SalarioBase, BonoIncentivo, FechaDeInicio, FechaBono, Habilitado FROM Trabajador;");
        while(resultado.next()){
            if(resultado.getString("Habilitado").equals("1")){
                String fechaInicio=resultado.getString("FechaDeInicio"),fechaBono=resultado.getString("FechaBono");
                modelo.addRow(new String[] {resultado.getString("id"),resultado.getString("DPI"), resultado.getString("Nombre"), resultado.getString("Apellido"), resultado.getString("Telefono"),resultado.getString("Comision"),resultado.getString("Direccion"),resultado.getString("SalarioBase"), resultado.getString("BonoIncentivo"),(fechaInicio==null?"N/A":fechaInicio) ,(fechaBono==null?"N/A":fechaBono)});
            }
        }
        conexion.close();
        return modelo;
    }
    /**
     * Crea una nuevo DefaultTableModel para trabajadores
     * @param modelo el modelo para la JTable, vacio o con otros datos
     * @return el modelo para la JTable, inicializado
     */
    private DefaultTableModel inicializarTablaTrabajadores(DefaultTableModel modelo) {
//        
        modelo = new DefaultTableModel(null, new String[]{"ID", "DPI", "Nombre", "Apellido", "Telefono", "Comision", "Direccion", "Salario Base", "Bono", "Inicio", "Fecha de Bono"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false,false,false,false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Invoca una funcion almacenada que ingresa trabajadores
     * @param DPI DPI del trabajador
     * @param nombre nombre del trabajador
     * @param apellido apellido del trabajador
     * @param telefono telefono del trabajador
     * @param comision comisión del trabajador
     * @param direccion dirección del trabajador
     * @param salario salario del trabajador
     * @param bono bono incentivo del trabajador
     * @param fechaInicio fecha en que inició a trabajar
     * @param fechaBono fecha para el bono incentivo
     * @return 1 si se insertó, 0 de lo contrario
     * @throws SQLException
     * @throws NoSePuedeConectar 
     */
    public int crearTrabajador(String DPI, String nombre, String apellido, String telefono, float comision, String direccion, float salario, float bono, String fechaInicio, String fechaBono) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT creaTrabajadores('"+(DPI.equals("")? "N/A":DPI)+"','"+(nombre.equals("")? "N/A":nombre)+"','"+(apellido.equals("")? "N/A":apellido)+"','"+(telefono.equals("")? "N/A":telefono)+"',"+comision+",'"+(direccion.equals("")? "N/A":direccion)+"',"+salario+","+bono+","+(fechaInicio.equals("")?"NULL":"'"+fechaInicio+"'")+","+(fechaBono.equals("")?"NULL":"'"+fechaBono+"'")+") R"); //se guarda el resultado de la instruccion
        int res=-1;
        while(resultado.next())//Es una funcion booleana que mueve el cursor del resultado, si este es TRUE, aun hay registros de resultado
        {
            res= resultado.getInt(1);
        }
        conexion.close();
        return res;
    }
    /**
     * Modifica los datos de un trabajador
     * @param id ID del trabajador
     * @param DPI DPI del trabajador
     * @param nombre nombre del trabajador
     * @param apellido apellido del trabajador
     * @param telefono telefono del trabajador
     * @param comision comisión del trabajador
     * @param direccion dirección del trabajador
     * @param salario salario del trabajador
     * @param bono bono incentivo del trabajador
     * @param fechaInicio fecha en que inició a trabajar
     * @param fechaBono fecha para el bono incentivo
     * @return el número de filas afectadas
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int modificarTrabajador(int id, String DPI, String nombre, String apellido, String telefono, float comision, String direccion, float salario, float bono, String fechaInicio, String fechaBono) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Trabajador SET DPI='"+(DPI.equals("")? "N/A":DPI)+"',Nombre='"+(nombre.equals("")? "N/A":nombre)+"',Apellido='"+(apellido.equals("")? "N/A":apellido)+"',Telefono='"+(telefono.equals("")? "N/A":telefono)+"',Comision="+comision+",Direccion='"+(direccion.equals("")? "N/A":direccion)+"',SalarioBase="+salario+",BonoIncentivo="+bono+",FechaDeInicio="+(fechaInicio.equals("")?"NULL":"'"+fechaInicio+"'")+",FechaBono="+(fechaBono.equals("")?"NULL":"'"+fechaBono+"'")+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Elimina un trabajador de la BD
     * @param id id del trabajador a borrar
     * @return el numero de filas afectadas (debe ser 1)
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public int eliminarTrabajador(int id) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Trabajador SET habilitado=0 WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene una lista de todos los trabajadores para el módulo de ausencias
     * @return ArrayList con los trabajadores habilitados, con el formato "Nombre Apellido-ID"
     * @throws NoSePuedeConectar error al conectar a la BD
     * @throws SQLException en caso de error
     */
    public ArrayList obtenerTrabajadoresParaAusencias() throws NoSePuedeConectar, SQLException{
        ArrayList lista=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre, Apellido, Habilitado FROM Trabajador;");
        while(resultado.next()){
            if(resultado.getString("Habilitado").equals("1"))
                lista.add(resultado.getString("Nombre")+" "+ resultado.getString("Apellido")+"-"+resultado.getString("id"));
        }
        conexion.close();
        return lista;
    }
    /**
     * Ingresa una nueva ausencia en la BD
     * @param idTrabajador ID del trabajador que se ausentó
     * @param fecha fecha en que ausentó
     * @param descripcion el motivo por el cual se ausentó
     * @param autorizada si su ausencia se autorizó o no
     * @return número de filas afectadas, debe ser 1
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int ingresarAusencia(int idTrabajador, String fecha, String descripcion, boolean autorizada) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("INSERT INTO Ausencia (Trabajador_id, Fecha, Descripcion, Autorizada) VALUES ("+idTrabajador+","+(fecha.equals("")?"NOW()":"'"+fecha+"'")+",'"+(descripcion.equals("")?"N/A":descripcion)+"',"+(autorizada?"1":"0")+");"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
     /**
     * Modifica una ausencia en la BD
     * @param id ID de la ausencia
     * @param idTrabajador ID del trabajador que se ausentó
     * @param fecha fecha en que ausentó
     * @param descripcion el motivo por el cual se ausentó
     * @param autorizada si su ausencia se autorizó o no
     * @return número de filas afectadas, debe ser 1
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int modificarAusencia(int id,int idTrabajador, String fecha, String descripcion, boolean autorizada) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Ausencia SET Trabajador_id="+idTrabajador+",Fecha="+(fecha.equals("")?"NOW()":"'"+fecha+"'")+",Descripcion='"+(descripcion.equals("")?"N/A":descripcion)+"',Autorizada="+(autorizada?"1":"0")+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Metodo que regresa la lista de ausencias como un arreglo
     * @return una DefaultTableModel con las ausencias en la BD
     * @throws SQLException en caso de error
     * @throws Excepciones.NoSePuedeConectar en caso de que no se pueda conectar a la BD
     */
    public DefaultTableModel obtenerAusencias() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo = null;
        modelo=inicializarTablaAusencias(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Trabajador_id, Fecha, Descripcion, Autorizada FROM Ausencia;");
        while(resultado.next()){
            String trabActualID=resultado.getString("Trabajador_id"), autorizada=resultado.getString("Autorizada");
            Statement consultaTrabajador=conexion.createStatement();
            ResultSet resultadoT=consultaTrabajador.executeQuery("SELECT Nombre, Apellido FROM Trabajador WHERE id="+trabActualID);
            if(resultadoT.next())
                modelo.addRow(new String[] {resultado.getString("id"),resultadoT.getString(1)+" "+resultadoT.getString(2)+"-"+trabActualID, resultado.getString("Fecha"), resultado.getString("Descripcion"), (autorizada.equals("1")?"SI":"NO")});
        }
        conexion.close();
        return modelo;
    }
    /**
     * Crea una nuevo DefaultTableModel para trabajadores
     * @param modelo el modelo para la JTable, vacio o con otros datos
     * @return el modelo para la JTable, inicializado
     */
    private DefaultTableModel inicializarTablaAusencias(DefaultTableModel modelo) {
//        
        modelo = new DefaultTableModel(null, new String[]{"ID", "Trabajador", "Fecha", "Descripción", "Autorizada"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    public int eliminarAusencia(int id) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM Ausencia WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene una lista de todos los trabajadores para el módulo de usuarios
     * @return ArrayList con los trabajadores habilitados, con el formato "Nombre Apellido-ID"
     * @throws NoSePuedeConectar error al conectar a la BD
     * @throws SQLException en caso de error
     */
    public ArrayList obtenerTrabajadoresParaUsuarios() throws NoSePuedeConectar, SQLException{
        ArrayList lista=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre, Apellido, Habilitado FROM Trabajador WHERE Usuario_id IS NULL AND Habilitado=1;");
        while(resultado.next()){
            lista.add(resultado.getString("Nombre")+" "+ resultado.getString("Apellido")+"-"+resultado.getString("id"));
        }
        conexion.close();
        return lista;
    }
    public String obtenerNumeroFac(int id) throws NoSePuedeConectar, SQLException{
        String numero = "";        
        conectar();
            Statement instruccion = conexion.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT NumeroFac FROM sucursales WHERE id = " + id + ";");
            while (resultado.next()) {            
                numero = resultado.getString("NumeroFac");
            }
        conexion.close();
        return numero;
    }
    private DefaultTableModel iniciarTablaTrabajador_planilla() {
        DefaultTableModel tabla;
        tabla = new DefaultTableModel(null, new String[]{"DPI","Nombre","Salario","Bono","Comision"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
        return tabla;
    }
        private DefaultTableModel iniciarTablaTrabajador_planillaMeses(int mes) {
        DefaultTableModel tabla;
        if(mes>0){
            String mesNombre;
            switch (mes) {
                case 1:
                    mesNombre="Enero";
                    break;
                case 2:
                    mesNombre="Febrero";
                    break;
                case 3:
                    mesNombre="Marzo";
                    break;
                case 4:
                    mesNombre="Abril";
                    break;
                case 5:
                    mesNombre="Mayo";
                    break;
                case 6:
                    mesNombre="Junio";
                    break;
                case 7:
                    mesNombre="Julio";
                    break;
                case 8:
                    mesNombre="Agosto";
                    break;
                case 9:
                    mesNombre="Septiembre";
                    break;
                case 10:
                    mesNombre="Octubre";
                    break;
                case 11:
                    mesNombre="Noviembre";
                    break;
                default:
                    mesNombre="Diciembre";
                    break;
            }
            String[] campos;
            boolean[] editable;
            if(mes==6)
            {
                campos=new String[]{"DPI","Nombre","Prestamo","Salario",mesNombre,"Anticipo","Comision",
                    "Bono 14","Pago Total","Alertas"};
                editable=new boolean [] {false, false, false, false,false,false,false,false,false,false};
            }
            else if(mes==12)
            {
                campos=new String[]{"DPI","Nombre","Prestamo","Salario",mesNombre,"Anticipo","Comision",
                    "Aguinaldo","Pago Total","Alertas"};
                editable=new boolean [] {false, false, false, false,false,false,false,false,false,false};
            }
            else
            {
                campos=new String[]{"DPI","Nombre","Prestamo","Salario",mesNombre,"Anticipo","Comision","Pago Total","Alertas"};
                editable=new boolean [] {false, false, false, false,false,false,false,false,false};
            }
            tabla = new DefaultTableModel(null,campos){
                boolean[] canEdit = editable;
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
        }
        else
        {
            tabla = new DefaultTableModel(null, new String[]{"DPI","Nombre","Salario","Bono","Comision"}){
                boolean[] canEdit = new boolean [] {false, false, false, false,false};
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
        }
        return tabla;
    }
    public DefaultTableModel obtenerListado_Trabajadores() throws SQLException, NoSePuedeConectar{
        DefaultTableModel trabajadores=null;
        trabajadores = iniciarTablaTrabajador_planilla();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select t.DPI, t.Nombre, t.SalarioBase, "+
                "t.BonoIncentivo, t.Comision from trabajador t;");
        while(resultado.next())
        {
            String[] fila=new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3),
                resultado.getString(4),resultado.getString(5)};
            trabajadores.addRow(fila);
        }
        conexion.close();
        return trabajadores;
        
    }
    /**
     * Funcion que retorna los datos de un trabajador buscandolo con su nombre y dpi
     * @param dpi dpi en una cadena de texto
     * @param nombre nombre en una cadena de texto
     * @return arreglo de cadenas con la id,nombre,dpi,apellido,salario,bono y comision
     * @throws SQLException error al conectar con la base de datos
     */
    public String[] obtenerTrabajadorDeuda(String dpi, String nombre) throws SQLException, NoSePuedeConectar
    {
        String[] datos=null;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select t.id, t.apellido,t.SalarioBase,"+
                "t.BonoIncentivo, t.Comision from trabajador t where dpi='"+dpi+"' and nombre='"+nombre+"';");
        if(resultado.next())
        {
            datos=new String[]{resultado.getString(1),dpi,nombre,resultado.getString(2),resultado.getString(3),
                resultado.getString(4),resultado.getString(5)};
        }
        conexion.close();
        return datos;
    }
    /**
     * Metodo que inserta una deuda al trabajador en la tabla prestamos
     * @param trabajador id del trabajador
     * @param cantidad monto de la deuda
     * @param anticipo bandera que indica si la deuda es un anticipo o prestamo
     */
    public void insertarAdelanto(int trabajador, double cantidad, boolean anticipo) throws SQLException, NoSePuedeConectar
    {
        conectar();
        Statement instruccion = conexion.createStatement();
        if(anticipo)
            instruccion.executeUpdate("insert into prestamos (Trabajador_id,Fecha,Cantidad,Saldo,Anticipo) values ("+
                    trabajador+",now(),"+cantidad+","+cantidad+",1);");
        else
            instruccion.executeUpdate("insert into prestamos (Trabajador_id,Fecha,Cantidad,Saldo,Anticipo) values ("+
                    trabajador+",now(),"+cantidad+","+cantidad+",0);");
        conexion.close();
    }
    /**
     * funcion entera que retorna el mes del servidor como un numero
     * @return
     * @throws SQLException 
     */
    public int obtenerMesActual() throws SQLException, NoSePuedeConectar{
        int mes=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select month(now());");
        if(resultado.next())
        {
            mes=resultado.getInt(1);
        }
        conexion.close();
        return mes;
    }
    public double obtenerPrestamoMes(int trabajador, boolean anticipo, boolean total) throws SQLException, NoSePuedeConectar{
        double prestamo=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado;
        if(anticipo)
            resultado=instruccion.executeQuery("select sum(saldo) from prestamos where trabajador_id="+trabajador+
                    " and anticipo=1 and month(fecha)=month(now()) and year(fecha)=year(now());");
        else
        {
            if(total)
                resultado=instruccion.executeQuery("select sum(saldo) from prestamos where trabajador_id="+trabajador+
                        " and anticipo=0;");
            else
                 resultado=instruccion.executeQuery("select sum(saldo) from prestamos where trabajador_id="+trabajador+
                        " and anticipo=0 and month(fecha)=month(now()) and year(fecha)=year(now());"); 
        }
        if(resultado.next())
        {
            prestamo=resultado.getDouble(1);
        }
        conexion.close();
        return prestamo;
    }
    /**
     * MEtodo que modifica los prestamos
     * @param trabajador id del trabajador
     * @param cantidad monto que se añadira
     * @param anticipo bandera que indica si es un prestamo o un anticipo TRUE-anticipo, FALSE-prestamo
     * @throws SQLException 
     */
    public void modificarAdelanto(int trabajador, double cantidad, boolean anticipo) throws SQLException, NoSePuedeConectar
    {
        conectar();
        Statement instruccion = conexion.createStatement();
        if(anticipo)
            instruccion.executeUpdate("update prestamos set cantidad=cantidad+"+cantidad+", saldo=saldo+"+cantidad
                    +" where month(fecha)=month(now()) and year(fecha)=year(now()) and anticipo=1 and Trabajador_id="+trabajador+";");
        else
            instruccion.executeUpdate("update prestamos set cantidad=cantidad+"+cantidad+", saldo=saldo+"+cantidad
                    +" where month(fecha)=month(now()) and year(fecha)=year(now()) and anticipo=0 and Trabajador_id="+trabajador+";");
        conexion.close();
    }
    public DefaultTableModel obtenerPlanilla(int mes,int idPlanilla) throws SQLException, NoSePuedeConectar{
        DefaultTableModel trabajadores=null;
        trabajadores = iniciarTablaTrabajador_planillaMeses(mes);
        conectar();
        Statement instruccion = conexion.createStatement();
        String consulta="select t.DPI,t.Nombre,apellido,prestamoTrabajador("+mes+",t.id,0),"
                + "(t.SalarioBase+t.BonoIncentivo),(d.monto+d.bonoincentivo-adelanto),adelanto,"
                + "t.comision,";
        if(mes==6)
        {
            consulta+="d.bono14,round(d.monto+d.bono14,2)";
        }
        else if(mes==12)
            consulta+="d.aguinaldo,round(d.monto+d.aguinaldo,2)";
        else
            consulta+="d.monto";
        consulta+=" from trabajador t left join detalleplanilla d on "
                + "d.Trabajador_id=t.id inner join planillas p on p.id=d.Planillas_id where habilitado=1 "
                + "and p.id="+idPlanilla+";";
        ResultSet resultado=instruccion.executeQuery(consulta);
        if(mes==6 || mes==12)
        {
            while(resultado.next())
            {
                String[] fila=new String[]{resultado.getString(1),resultado.getString(2)+" "+resultado.getString(3),
                    resultado.getString(4), resultado.getString(5),resultado.getString(6),resultado.getString(7),
                    resultado.getString(8),resultado.getString(9),resultado.getString(10)};
                trabajadores.addRow(fila);
            }
        }
        else
        {
            while(resultado.next())
            {
                String[] fila=new String[]{resultado.getString(1),resultado.getString(2)+resultado.getString(3),
                    resultado.getString(4), resultado.getString(5),resultado.getString(6),resultado.getString(7),
                    resultado.getString(8),resultado.getString(9)};
                trabajadores.addRow(fila);
            }
        }
        conexion.close();
        return trabajadores;
    }
    public int obtenerIdPlanilla(int mes,int anio) throws SQLException, NoSePuedeConectar{
        int idPlanilla=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("call generarPlanilla(1);");
        ResultSet resultado=instruccion.executeQuery("select id from planillas where "
                + "month(fecha)="+mes+" and "+anio+"=year(fecha);");
        if(resultado.next())
        {
            idPlanilla=resultado.getInt(1);
            conexion.close();
        }
        return idPlanilla;
    }
    public void actualizarPlanilla(int id) throws SQLException, NoSePuedeConectar
    {
       conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("update planillas set total=-1 where id="+id+";");
        conexion.close(); 
    }
    public int obtenerDia() throws SQLException, NoSePuedeConectar{
        int dia=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select day(now());");
        if(resultado.next())
        {
            dia=resultado.getInt(1);
        }
        conexion.close();
        return dia;
    }
    public int obtenerAnio() throws SQLException, NoSePuedeConectar{
        int dia=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select year(now());");
        if(resultado.next())
        {
            dia=resultado.getInt(1);
        }
        conexion.close();
        return dia;
    }
    /**
     * Metodo que inserta pagos a trabajadores por sus prestamos a la empresa
     * @param idTrabajador id del ttrabajador que esta pagando
     * @param cantidad cantidad de efectivo que esta pagando
     * @throws SQLException
     * @throws NoSePuedeConectar 
     */
    public void insertarPagoTrabajador(int idTrabajador,double cantidad) throws SQLException, NoSePuedeConectar
    {
       conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("insert into pago (Fecha, Cantidad,Saldo, Trabajador_id) values (now(),"+
                cantidad+","+cantidad+","+idTrabajador+");");
        conexion.close(); 
    }

      /**
     * Metodo que inserta pagos a trabajadores por sus prestamos a la empresa
     * @param idTrabajador id del ttrabajador que esta pagando
     * @param cantidad cantidad de efectivo que esta pagando
     * @throws SQLException
     * @throws NoSePuedeConectar 
     */
   
    public ArrayList[] obtenerEmpresas() throws NoSePuedeConectar,SQLException{
        ArrayList[] lista=new ArrayList[2];
        lista[0]=new ArrayList();
        lista[1]=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select id,empresa from sucursales where sucursales_id is null;");
        while(resultado.next())
        {
            lista[0].add(resultado.getString(1));
            lista[1].add(resultado.getString(2));
        }
        conexion.close(); 
        return lista;
    }
    public void ingresarVehiculo(String Descripcion, String Marca, String Linea, String Tipo, String Modelo, String VIN, String Identificador, float Precio, String Fecha, String Placa, String Factura, int Sucursal) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("INSERT INTO vehiculo (Descripcion, Marca, Linea, Tipo, Modelo, VIN, Identificador, "
                + "PrecioCompra, FechaCompra, Placa, NoFactura, Sucursales_id) VALUES ('" + Descripcion + "', '" + Marca + "', '" + Linea + "', "
                        + "'" + Tipo + "', '" + Modelo +"', '" + VIN + "', '" + Identificador + "', " + Precio + ", '" + Fecha +"', '" + Placa +"', " + ((Factura.equals("")) ? "null": Factura)+ ", "+ Sucursal + ");");
        conexion.close();
    }
    public void ingresarMaquinaria(String Descripcion, String Marca, String Linea, String Tipo, String Modelo, String VIN, float Precio, String Fecha, String Factura, int Sucursal) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("INSERT INTO maquinaria (Descripcion, Marca, Linea, Tipo, Modelo, VIN,  "
                + "PrecioCompra, FechaCompra, NoFactura, Sucursales_id) VALUES ('" + Descripcion + "', '" + Marca + "', '" + Linea + "', "
                        + "'" + Tipo + "', '" + Modelo +"', '" + VIN + "', " + Precio + ", '" + Fecha +"', " + ((Factura.equals("")) ? "null": Factura)+ ", "+ Sucursal + ");");
        conexion.close();
    }
    public void ingresarComputo(String Descripcion, String Marca, String Tipo, String Modelo, String Key, String SN, float Precio, String Fecha, String Factura, int Sucursal, float Valres, String Identificador, String SO ) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("INSERT INTO equipodecomputo (Descripcion, Marca, Tipo, Modelo, PKey, PrecioCompra, FechaCompra, NoFactura, Sucursales_id, SN, SistemaOperativo,"
                + " ValorResidual, Identificador) VALUES ('" + Descripcion + "', '" + Marca + "', "
                        + "'" + Tipo + "', '" + Modelo +"', '" + Key + "', " + Precio + ", '" + Fecha +"', " + ((Factura.equals("")) ? "null": Factura)+ ", "+ Sucursal + ", '" + SN + "', '" + SO + "', "+ Valres +", '"+ Identificador+ "');");
        conexion.close();
    }
    public String[] obtenerDatosVehiculo(String Placa) throws SQLException, NoSePuedeConectar{
    String[] Datos = null;
    conectar();
    Statement instruccion = conexion.createStatement();
    ResultSet resultado=instruccion.executeQuery("SELECT v.PrecioCompra PrecioCompra, v.NoFactura NoFactura, v.FechaCompra FechaCompra, s.Nombre Nombre FROM vehiculo v INNER JOIN sucursales s ON v.Sucursales_id = s.id WHERE Placa = '" + Placa + "';");
    if(resultado.next())
    {
        Datos = new String[] {resultado.getFloat("PrecioCompra") + "", resultado.getInt("NoFactura") + "", resultado.getString("FechaCompra"), resultado.getString("Nombre")};
    }
    conexion.close();
    return Datos;
    }
    public String[] obtenerDatosMaquinaria(int id) throws SQLException, NoSePuedeConectar{
    String[] Datos = null;
    conectar();
    Statement instruccion = conexion.createStatement();
    ResultSet resultado=instruccion.executeQuery("SELECT v.PrecioCompra PrecioCompra, v.NoFactura NoFactura, v.FechaCompra FechaCompra, s.Nombre Nombre FROM maquinaria v INNER JOIN sucursales s ON v.Sucursales_id = s.id WHERE v.id = " + id + ";");
    if(resultado.next())
    {
        Datos = new String[] {resultado.getFloat("PrecioCompra") + "", resultado.getInt("NoFactura") + "", resultado.getString("FechaCompra"), resultado.getString("Nombre")};
    }
    conexion.close();
    return Datos;
    }
    public String[] obtenerDatosComputo(String id) throws SQLException, NoSePuedeConectar{
    String[] Datos = null;
    conectar();
    Statement instruccion = conexion.createStatement();
    ResultSet resultado=instruccion.executeQuery("SELECT v.PrecioCompra PrecioCompra, v.NoFactura NoFactura, v.FechaCompra FechaCompra, s.Nombre, v.PKey, v.ValorResidual FROM equipodecomputo v INNER JOIN sucursales s ON v.Sucursales_id = s.id WHERE v.Identificador = '" + id + "';");
    if(resultado.next())
    {
        Datos = new String[] {resultado.getFloat("PrecioCompra") + "", resultado.getInt("NoFactura") + "", resultado.getString("FechaCompra"), resultado.getString("Nombre"),  resultado.getString("PKey"), resultado.getFloat("ValorResidual")+ ""};
    }
    conexion.close();
    return Datos;
    }
    public void eliminarVehiculo(String Placa) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE vehiculo SET  habilitado = 0 WHERE Placa = '" + Placa  + "';");
        conexion.close();
    }public void eliminarMaquinaria(int id) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE maquinaria SET  habilitado = 0 WHERE id = " + id  + ";");
        conexion.close();
    } 
    public void eliminarComputo(String id) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("DELETE FROM equipodecomputo WHERE Identificador = '" + id  + "';");
        conexion.close();
    } 
    
     public int obteneridMaquinaria() throws SQLException, NoSePuedeConectar{
        int id=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("SELECT MAX(id) FROM maquinaria;");
        if(resultado.next())
        {
            id=resultado.getInt(1) + 1;
        }
        conexion.close();
        return id;
    }
     public int obteneridComputo() throws SQLException, NoSePuedeConectar{
        int id=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("SELECT MAX(id) FROM equipodecomputo;");
        if(resultado.next())
        {
            id=resultado.getInt(1) + 1;
        }
        conexion.close();
        return id;
    }
    public void modificarVehiculo(String Descripcion, String Marca, String Linea, String Tipo, String Modelo, String VIN, String Identificador, float Precio, String Fecha, String Placa, String Factura, int Sucursal) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE  vehiculo SET Descripcion = '" + Descripcion + "', Marca = '" + Marca + "', Linea = '" + Linea + "', "
        + "Tipo = '" + Tipo + "', Modelo = '" + Modelo +"', VIN = '" + VIN + "', Identificador = '" + Identificador + "', PrecioCompra = " + Precio + ", "
        + "FechaCompra = '" + Fecha +"', Placa = '" + Placa +"', NoFactura = " + ((Factura.equals("")) ? "null": Factura)+ ", Sucursales_id =  "+ Sucursal + " WHERE Placa = '" + Placa +"';");
        conexion.close();
    }
    public void modificarMaquinaria(String Descripcion, String Marca, String Linea, String Tipo, String Modelo, String VIN, float Precio, String Fecha, String Factura, int Sucursal, int id) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE  maquinaria SET Descripcion = '" + Descripcion + "', Marca = '" + Marca + "', Linea = '" + Linea + "', "
        + "Tipo = '" + Tipo + "', Modelo = '" + Modelo +"', VIN = '" + VIN + "', PrecioCompra = " + Precio + ", "
        + "FechaCompra = '" + Fecha +"', NoFactura = " + ((Factura.equals("")) ? "null": Factura)+ ", Sucursales_id =  "+ Sucursal + " WHERE id = " + id +";");
        conexion.close();
    }
    public void modificarComputo(String Descripcion, String Marca, String Tipo, String Modelo, String Key, String SN, float Precio, String Fecha, String Factura, int Sucursal, float Valres, String Identificador, String SO ) throws SQLException, NoSePuedeConectar{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("UPDATE equipodecomputo  SET Descripcion = '" + Descripcion + "', Marca = '" + Marca + "', Tipo = '" + Tipo + "', Modelo =  '" + Modelo +"',"
                + " PKey = '" + Key + "', PrecioCompra = " + Precio + ", FechaCompra = '" + Fecha +"', NoFactura = " + ((Factura.equals("")) ? "null": Factura)+ ", "
                + "Sucursales_id = "+ Sucursal + ", SN = '" + SN + "', SistemaOperativo =  '" + SO + "', ValorResidual = "+ Valres +" WHERE Identificador = '"+ Identificador+ "';");
        conexion.close();
    }
    /**
     * Hace un ingreso de mobiliario
     * @param marca marca
     * @param modelo modelo
     * @param tipo tipo de mobiliario
     * @param precioCompra precio de compra
     * @param depreciacion valor que se ha depreciado
     * @param valorResidual valor residual
     * @param fechaCompra fecha de compra
     * @param noFactura factura de compra
     * @param sucursalID id de la sucursal
     * @return no. de filas afectadas
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int ingresoMobiliario(String marca, String modelo, String tipo, float precioCompra, float depreciacion, float valorResidual, String fechaCompra, int noFactura, int sucursalID) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("INSERT INTO MobiliarioYEquipo (Marca, Modelo, Tipo, PrecioCompra, Depreciacion, ValorResidual, FechaCompra, NoFactura, Sucursales_id) VALUES ('"+(marca.equals("")?"N/A":marca)+"','"+(modelo.equals("")?"N/A":modelo)+"','"+(tipo.equals("")?"N/A":tipo)+"',"+precioCompra+","+depreciacion+","+valorResidual+","+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+","+noFactura+","+sucursalID+");"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene un listado de sucursales
     * @return array de sucursales
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     * @throws SQLException en caso de error
     */
    public ArrayList obtenerSucursalesJP() throws NoSePuedeConectar, SQLException{
        ArrayList listado=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre FROM Sucursales;"); //se guarda el resultado de la instruccion
        while(resultado.next()){
            listado.add(resultado.getString(2)+"-"+resultado.getString(1));
        }
        conexion.close();
        return listado;
    }
    
    /**
     * Hace una modificación de mobiliario
     * @param id id del mobiliario
     * @param marca marca
     * @param modelo modelo
     * @param tipo tipo de mobiliario
     * @param precioCompra precio de compra
     * @param depreciacion valor que se ha depreciado
     * @param valorResidual valor residual
     * @param fechaCompra fecha de compra
     * @param noFactura factura de compra
     * @param sucursalID id de la sucursal
     * @return no. de filas afectadas
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int modificacionMobiliario(int id,String marca, String modelo, String tipo, float precioCompra, float depreciacion, float valorResidual, String fechaCompra, int noFactura, int sucursalID) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE MobiliarioYEquipo SET Marca='"+(marca.equals("")?"N/A":marca)+"', Modelo='"+(modelo.equals("")?"N/A":modelo)+"', Tipo='"+(tipo.equals("")?"N/A":tipo)+"', PrecioCompra="+precioCompra+", Depreciacion="+depreciacion+", ValorResidual="+valorResidual+", FechaCompra="+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+", NoFactura="+noFactura+", Sucursales_id="+sucursalID+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene una tabla con el mobiliario
     * @return modelo de tabla con el mobiliario
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public DefaultTableModel obtenerMobiliarioJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo=null;
        modelo=inicializarTablaMobiliario(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Marca, Modelo, Tipo, PrecioCompra, Depreciacion, ValorResidual, FechaCompra, NoFactura, Sucursales_id FROM MobiliarioYEquipo;");
        while(resultado.next()){
            String sucursalActualID=resultado.getString("Sucursales_id");
            Statement consultaSucursal=conexion.createStatement();
            ResultSet resultadoSuc=consultaSucursal.executeQuery("SELECT Nombre FROM Sucursales WHERE id="+sucursalActualID);
            String fechaCompra=resultado.getString(8);
            if(resultadoSuc.next())
                modelo.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7), (fechaCompra==null?"N/A":fechaCompra),resultado.getString(9),resultadoSuc.getString(1)+"-"+sucursalActualID});
        }
        conexion.close();
        return modelo;
    }
    /**
     * Inicializa la tabla para mobiliario
     * @param modelo modelo de tabla
     * @return modelo inicializado
     */
    private DefaultTableModel inicializarTablaMobiliario(DefaultTableModel modelo) {
        modelo = new DefaultTableModel(null, new String[]{"ID", "Marca", "Modelo", "Tipo", "Precio de Compra", "Depreciado", "Valor Residual", "Fecha de Compra", "Factura", "Sucursal"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false,false,false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Hace una eliminacion de mobiliario
     * @param id id del mobiliario
     * @return no. de filas afectadas
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public int eliminacionMobiliario(int id) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM MobiliarioYEquipo WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Ingreso de utiles y enseres
     * @param descripcion descripcion
     * @param marca marca
     * @param cantidad cantidad adquirida
     * @param unidad unidad de medida
     * @param precioCompra precio de compra
     * @param fechaCompra fecha de compra
     * @param noFactura factura
     * @param sucursalID ID de la sucursal en donde de compró
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int ingresoUtiles(String descripcion, String marca, float cantidad, String unidad, float precioCompra, String fechaCompra, int noFactura, int sucursalID) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("INSERT INTO UtilesYEnseres (Descripcion, Marca, Cantidad, Unidad, PrecioCompra, FechaCompra, NoFactura, Sucursales_id) VALUES ('"+(descripcion.equals("")?"N/A":descripcion)+"','"+(marca.equals("")?"N/A":marca)+"',"+cantidad+",'"+(unidad.equals("")?"N/A":unidad)+"',"+precioCompra+","+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+","+noFactura+","+sucursalID+");"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Modificación de utiles y enseres
     * @param id del util
     * @param descripcion descripcion
     * @param marca marca
     * @param cantidad cantidad adquirida
     * @param unidad unidad de medida
     * @param precioCompra precio de compra
     * @param fechaCompra fecha de compra
     * @param noFactura factura
     * @param sucursalID ID de la sucursal en donde de compró
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int modificacionUtiles(int id, String descripcion, String marca, float cantidad, String unidad, float precioCompra, String fechaCompra, int noFactura, int sucursalID) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE UtilesYEnseres SET Descripcion='"+(descripcion.equals("")?"N/A":descripcion)+"', Marca='"+(marca.equals("")?"N/A":marca)+"', Cantidad="+cantidad+", Unidad='"+(unidad.equals("")?"N/A":unidad)+"', PrecioCompra="+precioCompra+", FechaCompra="+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+", NoFactura="+noFactura+", Sucursales_id="+sucursalID+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene una tabla con los utiles
     * @return modelo de tabla con los utiles
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public DefaultTableModel obtenerUtilesJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo=null;
        modelo=inicializarTablaUtileriaYPapeleria(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Descripcion, Marca, Cantidad, Unidad, PrecioCompra, FechaCompra, NoFactura, Sucursales_id FROM UtilesYEnseres;");
        while(resultado.next()){
            String sucursalActualID=resultado.getString("Sucursales_id");
            Statement consultaSucursal=conexion.createStatement();
            ResultSet resultadoSuc=consultaSucursal.executeQuery("SELECT Nombre FROM Sucursales WHERE id="+sucursalActualID);
            String fechaCompra=resultado.getString(7);
            if(resultadoSuc.next()){
                modelo.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6), (fechaCompra==null?"N/A":fechaCompra),resultado.getString(8),resultadoSuc.getString(1)+"-"+sucursalActualID});
            }
        }
        conexion.close();
        return modelo;
    }
    /**
     * Inicializa la tabla para "utiles y enseres" y "papelería y utiles"
     * @param modelo modelo de tabla
     * @return modelo inicializado
     */
    private DefaultTableModel inicializarTablaUtileriaYPapeleria(DefaultTableModel modelo) {
        modelo = new DefaultTableModel(null, new String[]{"ID", "Descripcion", "Marca", "Cantidad", "Unidad", "Precio de Compra", "Fecha de Compra", "Factura", "Sucursal"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false,false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Eliminación de utiles y enseres
     * @param id del util
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int eliminacionUtiles(int id) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM UtilesYEnseres WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Ingreso de papeleria y utiles
     * @param descripcion descripcion
     * @param marca marca
     * @param cantidad cantidad adquirida
     * @param unidad unidad de medida
     * @param precioCompra precio de compra
     * @param fechaCompra fecha de compra
     * @param noFactura factura
     * @param sucursalID ID de la sucursal en donde de compró
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int ingresoPapeleria(String descripcion, String marca, float cantidad, String unidad, float precioCompra, String fechaCompra, int noFactura, int sucursalID) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("INSERT INTO PapeleriaYUtiles (Descripcion, Marca, Cantidad, Unidad, PrecioCompra, FechaCompra, NoFactura, Sucursales_id) VALUES ('"+(descripcion.equals("")?"N/A":descripcion)+"','"+(marca.equals("")?"N/A":marca)+"',"+cantidad+",'"+(unidad.equals("")?"N/A":unidad)+"',"+precioCompra+","+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+","+noFactura+","+sucursalID+");"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Modificación de papeleria y utiles
     * @param id del util
     * @param descripcion descripcion
     * @param marca marca
     * @param cantidad cantidad adquirida
     * @param unidad unidad de medida
     * @param precioCompra precio de compra
     * @param fechaCompra fecha de compra
     * @param noFactura factura
     * @param sucursalID ID de la sucursal en donde de compró
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int modificacionPapeleria(int id, String descripcion, String marca, float cantidad, String unidad, float precioCompra, String fechaCompra, int noFactura, int sucursalID) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE PapeleriaYUtiles SET Descripcion='"+(descripcion.equals("")?"N/A":descripcion)+"', Marca='"+(marca.equals("")?"N/A":marca)+"', Cantidad="+cantidad+", Unidad='"+(unidad.equals("")?"N/A":unidad)+"', PrecioCompra="+precioCompra+", FechaCompra="+(fechaCompra.equals("")?"NULL":"'"+fechaCompra+"'")+", NoFactura="+noFactura+", Sucursales_id="+sucursalID+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene una tabla con la papeleria
     * @return modelo de tabla con la papeleria
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public DefaultTableModel obtenerPapeleriaJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo=null;
        modelo=inicializarTablaUtileriaYPapeleria(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Descripcion, Marca, Cantidad, Unidad, PrecioCompra, FechaCompra, NoFactura, Sucursales_id FROM PapeleriaYUtiles;");
        while(resultado.next()){
            String sucursalActualID=resultado.getString("Sucursales_id");
            Statement consultaSucursal=conexion.createStatement();
            ResultSet resultadoSuc=consultaSucursal.executeQuery("SELECT Nombre FROM Sucursales WHERE id="+sucursalActualID);
            String fechaCompra=resultado.getString(7);
            if(resultadoSuc.next()){
                modelo.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6),(fechaCompra==null?"N/A":fechaCompra),resultado.getString(8),resultadoSuc.getString(1)+"-"+sucursalActualID});
            }
        }
        conexion.close();
        return modelo;
    }
    /**
     * Eliminación de papeleria y utiles
     * @param id del util
     * @return filas afectadas
     * @throws NoSePuedeConectar si no puede conectarse a la BD
     * @throws SQLException en caso de error
     */
    public int eliminacionPapeleria(int id) throws NoSePuedeConectar, SQLException{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM PapeleriaYUtiles WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Ingresa un gasto
     * @param descripcion descripcion
     * @param cantidad cantidad consumida (1 si es servicio)
     * @param monto gastado sin iva
     * @param iva iva del consumo
     * @param factura factura
     * @param NIT NIT de quien se le consumió
     * @param nombreFiscal nombre de quien se le consumió
     * @param fecha fecha de consumo
     * @param sucursalID id de la sucursal que consumió
     * @return filas afectadas (debe ser 1)
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int ingresoGasto(String descripcion, float cantidad, float monto, float iva, int factura, String NIT, String nombreFiscal, String fecha, int sucursalID) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("INSERT INTO  Gastos (Descripcion, Cantidad, Monto, IVACobrar, NoFactura, NIT, NombreFiscal,Fecha, Sucursales_id) VALUES ('"+(descripcion.equals("")?"N/A":descripcion)+"',"+cantidad+","+monto+","+iva+","+factura+",'"+(NIT.equals("")?"N/A":NIT)+"','"+(nombreFiscal.equals("")?"N/A":nombreFiscal)+"',"+(fecha.equals("")?"NULL":"'"+fecha+"'")+","+sucursalID+");"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
     /**
     * Inicializa la tabla para gastos
     * @param modelo modelo de tabla
     * @return modelo inicializado
     */
    private DefaultTableModel inicializarTablaGastosJP(DefaultTableModel modelo) {
        modelo = new DefaultTableModel(null, new String[]{"ID", "Descripcion", "Cantidad", "Monto", "IVA", "Factura", "NIT", "Nombre Fiscal", "Sucursal", "Fecha"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false,false,false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Obtiene una tabla con los gastos
     * @return modelo de tabla con los gastos
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public DefaultTableModel obtenerGastosJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo=null;
        modelo=inicializarTablaGastosJP(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Descripcion, Cantidad, Monto, IVACobrar, NoFactura, NIT, NombreFiscal, Sucursales_id, Fecha FROM Gastos;");
        while(resultado.next()){
            String sucursalActualID=resultado.getString("Sucursales_id");
            Statement consultaSucursal=conexion.createStatement();
            ResultSet resultadoSuc=consultaSucursal.executeQuery("SELECT Nombre FROM Sucursales WHERE id="+sucursalActualID);
            String fechaCompra=resultado.getString(10);
            if(resultadoSuc.next()){
                modelo.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),resultado.getString(8),resultadoSuc.getString(1)+"-"+sucursalActualID,(fechaCompra==null?"N/A":fechaCompra)});
            }
        }
        conexion.close();
        return modelo;
    }
    /**
     * Modifica un gasto
     * @param id id del gasto
     * @param descripcion descripcion
     * @param cantidad cantidad consumida (1 si es servicio)
     * @param monto gastado sin iva
     * @param iva iva del consumo
     * @param factura factura
     * @param NIT NIT de quien se le consumió
     * @param nombreFiscal nombre de quien se le consumió
     * @param fecha fecha de consumo
     * @param sucursalID id de la sucursal que consumió
     * @return filas afectadas (debe ser 1)
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int modificacionGasto(int id, String descripcion, float cantidad, float monto, float iva, int factura, String NIT, String nombreFiscal, String fecha, int sucursalID) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Gastos SET Descripcion='"+(descripcion.equals("")?"N/A":descripcion)+"', Cantidad="+cantidad+", Monto="+monto+", IVACobrar="+iva+", NoFactura="+factura+", NIT='"+(NIT.equals("")?"N/A":NIT)+"', NombreFiscal='"+(nombreFiscal.equals("")?"N/A":nombreFiscal)+"',Fecha="+(fecha.equals("")?"NULL":"'"+fecha+"'")+", Sucursales_id="+sucursalID+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Elimina un gasto
     * @param id id del gasto
     * @return filas afectadas (debe ser 1)
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int eliminacionGasto(int id) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("DELETE FROM Gastos WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
//        /**
//     * Metodo que inserta pagos a trabajadores por sus prestamos a la empresa
//     * @param idTrabajador id del ttrabajador que esta pagando
//     * @param cantidad cantidad de efectivo que esta pagando
//     * @throws SQLException
//     * @throws NoSePuedeConectar 
//     */
//    public void insertarPagoTrabajador(int idTrabajador,double cantidad) throws SQLException, NoSePuedeConectar
//    {
//       conectar();
//        Statement instruccion = conexion.createStatement();
//        instruccion.executeUpdate("insert into pago (Fecha, Cantidad,Saldo, Trabajador_id) values (now(),"+
//                cantidad+","+cantidad+","+idTrabajador+");");
//        conexion.close(); 
//    }
//    public ArrayList[] obtenerEmpresas() throws NoSePuedeConectar,SQLException{
//        ArrayList[] lista=new ArrayList[2];
//        lista[0]=new ArrayList();
//        lista[1]=new ArrayList();
//        conectar();
//        Statement instruccion = conexion.createStatement();
//        ResultSet resultado=instruccion.executeQuery("select id,empresa from sucursales where sucursales_id is null;");
//        while(resultado.next())
//        {
//            lista[0].add(resultado.getString(1));
//            lista[1].add(resultado.getString(2));
//        }
//        conexion.close(); 
//        return lista;
//    }
    /**
     * funcion que obtiene la id del ultimo balance, si no hay, retorna 0
     * @return
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public int obtenerUltimoBalance() throws NoSePuedeConectar, SQLException{
        int id=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select max(id) from balancegeneral;");
        if(resultado.next())
        {
            id=resultado.getInt(1);
        }
        conexion.close(); 
        return id;
    }
    /**
     * metodo que inserta el primer balance con el efectivo de bancos para iniciar con las transacciones
     * @param idSucursal
     * @param bancos
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public void insertarPrimerBalance(int idSucursal,double bancos) throws NoSePuedeConectar, SQLException{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("insert into balancegeneral (fecha,sucursales_id,bancos) values (now(),"+idSucursal
                +","+bancos+");");
        conexion.close(); 
    }
    public void generarBalance(int idSucursal,double bancos) throws NoSePuedeConectar, SQLException{
        conectar();
        Statement instruccion = conexion.createStatement();
        instruccion.executeUpdate("call generarBalance("+idSucursal+","+bancos+");");
        conexion.close(); 
    }
    public DefaultTableModel obtenerBalance(int sucursal,int id) throws NoSePuedeConectar, SQLException
    {
        DefaultTableModel tabla;
       tabla = new DefaultTableModel(null, new String[]{"Concepto", "Debe","Haber"}){
            boolean[] canEdit = new boolean [] {
        false, false,false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
            }
        };
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select caja,bancos,mercaderia,clientes,deudores,vehiculos,"
                + "mobiliarioyequipo,sistemadecomputo,utilesyenseres,maquinaria,ivacobrar,residualvehiculo,"
                + "residualmobiliario,residualcomputo,residualmaquinaria,proveedores,ivapagar,depreciacionvehiculo,"
                + "depreciacionmobiliario,depreciacioncomputo,depreciacionmaquinaria,residualavehiculo,"
                + "residualamobiliario,residualacomputo,residualamaquinaria,coutapatronal,cuotalaboral "
                + "from balancegeneral where sucursales_id="+sucursal+" and id="+id+";");
        if(resultado.next())
        {
            tabla.addRow(new String[]{"Caja",resultado.getString(1)," "});
            tabla.addRow(new String[]{"Bancos",resultado.getString(2)," "});
            tabla.addRow(new String[]{"Mercaderia",resultado.getString(3)," "});
            tabla.addRow(new String[]{"Clientes",resultado.getString(4)," "});
            tabla.addRow(new String[]{"Deudores",resultado.getString(5)," "});
            tabla.addRow(new String[]{"Vehiculos",resultado.getString(6)," "});
            tabla.addRow(new String[]{"Mobiliario y Equipo",resultado.getString(7)," "});
            tabla.addRow(new String[]{"Equipo de Computo",resultado.getString(8)," "});
            tabla.addRow(new String[]{"Utiles y Enseres",resultado.getString(9)," "});
            tabla.addRow(new String[]{"Maquinaria",resultado.getString(10)," "});
            tabla.addRow(new String[]{"IVA por Cobrar",resultado.getString(11)," "});
            tabla.addRow(new String[]{"Valor Residual Vehiculo",resultado.getString(12)," "});
            tabla.addRow(new String[]{"Valor Residual Mobiliario",resultado.getString(13)," "});
            tabla.addRow(new String[]{"Valor Residual Computo",resultado.getString(14)," "});
            tabla.addRow(new String[]{"Valor Redidual Maquinaria",resultado.getString(15)," "});
            tabla.addRow(new String[]{"Proveedores"," ",resultado.getString(16)});
            tabla.addRow(new String[]{"IVA por Pagar"," ",resultado.getString(17)});
            tabla.addRow(new String[]{"Depreciacion Acumulada Vehiculos"," ",resultado.getString(18)});
            tabla.addRow(new String[]{"Depreciacion Acumulada Mobiliario"," ",resultado.getString(19)});
            tabla.addRow(new String[]{"Depreciacion Acumulada Computo"," ",resultado.getString(20)});
            tabla.addRow(new String[]{"Depreciacion Acumulada Maquinaria"," ",resultado.getString(21)});
            tabla.addRow(new String[]{"Valor Residual Acumulado Vehiculos"," ",resultado.getString(22)});
            tabla.addRow(new String[]{"Valor Residual Acumulado Mobiliario"," ",resultado.getString(23)});
            tabla.addRow(new String[]{"Valor Residual Acumulado Computo"," ",resultado.getString(24)});
            tabla.addRow(new String[]{"Valor Residual Acumulado Maquinaria"," ",resultado.getString(25)});
            tabla.addRow(new String[]{"Cuota Patronal IGSS"," ",resultado.getString(26)});
            tabla.addRow(new String[]{"Cuota Laboral IGSS"," ",resultado.getString(27)});
            double capital=0,total;
            for(int i=1;i<=15;i++)
            {
                capital=capital+resultado.getDouble(i);
            }
            total=capital;
            for(int i=16;i<=27;i++)
            {
                capital=capital-resultado.getDouble(i);
            }
            tabla.addRow(new String[]{"Capital"," ",capital+""});
            tabla.addRow(new String[]{"SUMA ACTIVO Y PASIVO",total+"",total+""});
        }
        conexion.close(); 
        return tabla;
    }
    /**
     * Metodo que inserta una partida y su detalle dentro de la base de datos
     * @param cuentasId ArrayList que contiene las ID de las cuentas contenidas en la partida
     * @param montos ArrayList que contiene los MONTOS, las posiciones deben coincidir con las del otro ArrayList - Si el monto es positivo es del lado de deber
     * de lo contrario es del lado del haber
     * @param signo Bandera que indica de que lado va el saldo 1 esta en el debe - (-1) esta en el haber
     * @param descripcion Cadena que contiene la descripcion para la partida (Si la partida ya existe se concatenaran)
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public int insertarPartida(ArrayList cuentasId, ArrayList montos,ArrayList signo, String descripcion, int idSucursal) throws NoSePuedeConectar, SQLException
    {
        int idPartida=0;
        if(generarPartidaInicalMes(idSucursal)==0)
            return 0;
        conectar();
        Statement instruccion=conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select id from partida where fecha=date(now()) "
                + "and anulada=0 and sucursales_id="+idSucursal+";");
        if(resultado.next())
            idPartida=resultado.getInt(1);
        if(idPartida==0)
        {
            resultado=instruccion.executeQuery("select max(id),numero from partida where sucursales_id="+idSucursal+";");
            if(resultado.next())
                idPartida=resultado.getInt(2)+1;
            instruccion.executeUpdate("insert into partida (fecha,descripcion,Sucursales_id,numero) values"
                    + " (now(),'"+descripcion+"',"+idSucursal+","+idPartida+");");
            resultado=instruccion.executeQuery("select id from partida where fecha=date(now()) and anulada=0;");
            if(resultado.next())
                idPartida=resultado.getInt(1);
        }
        else if(!descripcion.isEmpty())
            instruccion.executeUpdate("update partida set descripcion=concat(descripcion,'"+descripcion+"') where id="+idPartida+";");
        for(int i=0;i<cuentasId.size();i++)
        {
            if(Double.parseDouble(montos.get(i).toString())>0)
            instruccion.executeUpdate("call insertarPartidas("+idPartida+","+cuentasId.get(i)+","+montos.get(i)+","+signo.get(i)+");");
        }        
        conexion.close();
        return 0;
    }
    /**
     * Funcion que retorna un arreglo de ArrayList con el detalle de la partida, segun la fecha que se solicita
     * @param fecha fecha de la partida 
     * @param sucursal_id id de la sucrusal de la partida
     * @return arreglo de ArrayList con los detalles de partida: [0] Monto del detalle - [1] Signo del monto - [2] Id de la cuenta - [3] Nombre de la cuenta
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public ArrayList[] obtenerPartida(String fecha,int sucursal_id) throws NoSePuedeConectar, SQLException{
        ArrayList[] detallePartida=new ArrayList[5];
        detallePartida[0]=new ArrayList();
        detallePartida[1]=new ArrayList();
        detallePartida[2]=new ArrayList();
        detallePartida[3]=new ArrayList();
        detallePartida[4]=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select d.Monto,d.signo,Cuentas_id,c.Nombre,c.clasificacion "
                + "from detalle_partida d inner join partida p on p.id=d.Partida_id inner join cuentas "
                + "c on c.id=d.Cuentas_id where p.Fecha='"+fecha+"' and p.sucursales_id="+sucursal_id+" and anulada=0;");
        while(resultado.next())
        {
            String clase="";
            switch(resultado.getInt(5)){
                case 1: clase="ACTIVO CORRIENTE"; break;
                case 2: clase="ACTIVO NO CORRIENTE"; break;
                case 3: clase="PASIVO CORRIENTE"; break;
                case 4: clase="PASIVO NO CORRIENTE"; break;
                case 5: clase="GANANCIA"; break;
                case 6: clase="PERDIDA"; break;
                case 7: clase="PATRIMONIO"; break;
            }
            detallePartida[0].add(resultado.getString(1));
            detallePartida[1].add(resultado.getString(2));
            detallePartida[2].add(resultado.getString(3));
            detallePartida[3].add(resultado.getString(4));
            detallePartida[4].add(clase);
        }
        conexion.close(); 
        return detallePartida;
    } 
    /**
     * Este metodo elimina los detalles de una partida, pero no elimina la partida como tal, solo los detalles
     * @param fecha fecha de la partida a la que desea eliminarse los datos
     * @throws NoSePuedeConectar
     * @throws SQLException
     * @throws NoPuedeEliminarsePartida en caso no exista una partida para la fecha solicitada, arroja una excepcion
     */
    public void eliminarDetallePartida(Date fecha) throws NoSePuedeConectar, SQLException, NoPuedeEliminarsePartida{
        int idPartida=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select id from partida where fecha='"+fecha+"' and anulada=0;");
        if(resultado.next())
            idPartida=resultado.getInt(1);
        if(idPartida==0)
            throw new NoPuedeEliminarsePartida("No existe una partida para esta fecha");
        else
            instruccion.executeUpdate("delete from detalle_partida where planilla_id="+idPartida+";");
        conexion.close();
    }
    /**
     * 
     * @param fecha Fecha de la partida que desea anularse
     * @param idSucursal id de la sucursal a la que pertenece la partida
     * @param comentario Comentario de la anulacion de la partida
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public void anularPartida(String fecha, int idSucursal,String comentario) throws NoSePuedeConectar, SQLException
    {
        int idPartida=0;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select id from partida where fecha='"+fecha+"' and anulada=0"
                + " and sucursales_id="+idSucursal+";");
        if(resultado.next())
        {
            idPartida=resultado.getInt(1);
        }
        if(idPartida>0)
        {
            instruccion.executeUpdate("update partida set anulada=1,descripcion='"+comentario+"' where id="+idPartida+";");
        }
        conexion.close();
    }
    /**
     * Metodo que genera la partida para una nueva venta
     * @param facturaId id de la factura de la nueva venta
     * @param idSucursal id de la sucursal donde se realiza la venta
     * @param subtotal subtotal de la venta
     * @param ivapagar  iva de la venta
     * @param caja  monto pagado en efectivo
     * @param clientes  monto al credito
     * @param bancos    monto pagado con cheques
     * @param otros monto para otras cuentas
     * @throws SQLException
     * @throws NoSePuedeConectar 
     */
    public void insertarPartidaVentas(int facturaId,int idSucursal, double subtotal, double ivapagar,double caja, 
        double clientes, double bancos, double otros) throws SQLException, NoSePuedeConectar{
        ArrayList cuentas=new ArrayList(),montos=new ArrayList(),signos=new ArrayList();
        int idEmpresa=idSucursal;
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select sucursales_id from sucursales where id="+idSucursal+";");
        //comprueba que la id sea de una Empresa y no una sucursal, si es una sucursal, busca la empresa a la que pertenece
        if(resultado.next())
        {
          idEmpresa=resultado.getInt(1);//añade la empresa
        }
        cuentas.add(43);//genera los inserts utilizando la id de las cuentas
        //ventas (43),ivaporpagar(34), caja (1),clientes (4),bancos(3) y cuentas por cobrar (7) 
        cuentas.add(34);
        cuentas.add(1);
        cuentas.add(4);
        cuentas.add(3);
        cuentas.add(7);
        //crea un ArrayList con los montos de las cuentas del ArrayList cuentas
        montos.add(subtotal);
        montos.add(ivapagar);
        montos.add(caja);
        montos.add(clientes);
        montos.add(bancos);
        montos.add(otros);
        //crea un arrayList con los signos (para debe o haber) 
        signos.add(-1);
        signos.add(-1);
        signos.add(1);
        signos.add(1);
        signos.add(1);
        signos.add(1);
        //llama al metodo de insercion de partidas enviando los tres arrayList como parametros
        insertarPartida(cuentas, montos, signos,"Ventas", idEmpresa);
    }
    public DefaultTableModel obtenerCuentas(String filtro) throws NoSePuedeConectar, SQLException{
        DefaultTableModel tabla=new DefaultTableModel(null, new String[]{"Numero","Cuenta","Tipo"}){
            boolean[] canEdit = new boolean [] {
        false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        String criterio="";
        if(!filtro.isEmpty())
            criterio="and nombre like concat('%','"+filtro+"','%')";
        conectar();
        Statement instruccion=conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select * from cuentas c where c.id!=43 and "
                + "c.id!=58 "+criterio+";");
        while(resultado.next())
        {
            criterio="";
            switch(resultado.getInt(3)){
                case 1: criterio="ACTIVO CORRIENTE"; break;
                case 2: criterio="ACTIVO NO CORRIENTE"; break;
                case 3: criterio="PASIVO CORRIENTE"; break;
                case 4: criterio="PASIVO NO CORRIENTE"; break;
                case 5: criterio="GANANCIA"; break;
                case 6: criterio="PERDIDA"; break;
                case 7: criterio="PATRIMONIO"; break;
            }
            tabla.addRow(new String[]{resultado.getString(1),resultado.getString(2),criterio});
        }
        return tabla;
    }
    public void insertarCuenta(String nombre, int clasificacion) throws NoSePuedeConectar,SQLException{
        conectar();
        Statement instruccion=conexion.createStatement();
        instruccion.executeUpdate("insert into cuentas (nombre,clasificacion) values ('"+nombre+"',"+clasificacion+");");
        conexion.close();
    }
    public void modificarCuenta(int id,String nombre, int tipo) throws NoSePuedeConectar,SQLException{
        if(id>73){
            conectar();
            Statement instruccion=conexion.createStatement();
            instruccion.executeUpdate("update cuentas set nombre='"+nombre+"',clasificacion="+tipo+" where id="+id+";");
            conexion.close();
        }
    }
    public int generarPartidaInicalMes(int sucursal) throws SQLException, NoSePuedeConectar{
        int totalPartidas=0;//entero que guarda el numero de partidas guardadas
        String[] fecha=fecha().split("-");//Array de Strings que guardara anio,mes y dia
        conectar();
        Statement instruccion=conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select count(*) from partida where sucursales_id="+sucursal+";");
        if(resultado.next())
            totalPartidas=resultado.getInt(1);
        conexion.close();
        if(totalPartidas==0)
            return 0;
        int mes,anio,idPartida=0;
        mes=Integer.parseInt(fecha[1]);
        anio=Integer.parseInt(fecha[0]);
        instruccion.executeUpdate("call generarPartidaInicial('"+anio+"-"+mes+"-01');");
        return 1;
    }
    /**
     * Funcion que retorna una tabla con las facturas aun no canceladas
     * @param cliente id del cliente 
     * @param sucursal_id id de la sucursal donde se facturo
     * @return tabla con la informacion de facutras
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public DefaultTableModel obtenerFacturasClientes(int cliente, int sucursal_id) throws NoSePuedeConectar, SQLException{
        DefaultTableModel tabla=new DefaultTableModel(null, new String[]{"Numero","Saldo","Sucursal"}){
            boolean[] canEdit = new boolean [] {
        false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select f.Numero,s.Nombre,f.Saldo from factura "
                + "f inner join cliente c on c.id=f.Cliente_id inner join sucursales s on s.id=f.Sucursales_id"
                + " where s.id="+sucursal_id+" and c.id="+cliente+" and f.saldo>0;");
        while(resultado.next())
        {
            tabla.addRow(new String[]{resultado.getString(1),resultado.getString(2),resultado.getString(3)});
        }
        return tabla;
    }
     /**
     * Funcion que retorna un arreglo de ArrayList con el detalle de la partida anulada, segun la fecha que se solicita
     * @param fecha fecha de la partida 
     * @return arreglo de ArrayList con los detalles de partida: [0] Monto del detalle - [1] Signo del monto - [2] Id de la cuenta - [3] Nombre de la cuenta
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public ArrayList[] obtenerPartidaAnulada(Date fecha,int sucursal_id) throws NoSePuedeConectar, SQLException{
        ArrayList[] detallePartida=new ArrayList[4];
        detallePartida[0]=new ArrayList();
        detallePartida[1]=new ArrayList();
        detallePartida[2]=new ArrayList();
        detallePartida[3]=new ArrayList();
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select d.Monto,d.d.signo,Cuentas_id,c.Nombre "
                + "from detalle_partida d inner join partida p on p.id=d.Partida_id inner join cuentas "
                + "c on c.id=d.Cuentas_id where p.Fecha='"+fecha+"' and p.sucursales_id="+sucursal_id+" and anulada=1"
                        + " order by id desc;");
        if(resultado.next())
        {
            detallePartida[0].add(resultado.getString(1));
            detallePartida[1].add(resultado.getString(2));
            detallePartida[2].add(resultado.getString(3));
            detallePartida[4].add(resultado.getString(4));
        }
        conexion.close(); 
        return detallePartida;
    } 
    public void insertarPartidaPlanilla(int planilla_id) throws NoSePuedeConectar, SQLException 
    {
        ArrayList monto= new ArrayList(),cuenta= new ArrayList(),signo= new ArrayList();
        conectar();
        Statement instruccion= conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select sum(d.Monto),sum(d.BonoIncentivo),"
                + "sum(d.Aguinaldo),sum(d.Bono14),p.Realizado from detalleplanilla d inner join planillas p"
                + " on p.id=d.Planillas_id where p.id="+planilla_id+";");
        int sucursal=0;
        if(resultado.next())
        {
            if(resultado.getInt(5)==3){    
                cuenta.add(50);//Salario BAse
                monto.add(resultado.getString(1));
                signo.add(1);
                cuenta.add(51);//Bono incnetivo
                monto.add(resultado.getString(2));
                signo.add(1);
                cuenta.add(52);//Aguinaldo
                monto.add(resultado.getString(3));
                signo.add(1);
                cuenta.add(53);//bono14
                monto.add(resultado.getString(4));
                signo.add(1);
                double cuotaPatronal=Math.round(resultado.getDouble(1)*12.67)/100,cuotaLaboral=Math.round(resultado.getDouble(1)*4.83)/100;
                cuenta.add(53);//cuota patronal
                monto.add(cuotaPatronal);
                signo.add(1);
                cuenta.add(35);//Cuota Patronal por Pagar
                monto.add(cuotaPatronal);
                signo.add(-1);
                cuenta.add(36);//cuota laboral por pagar
                monto.add(cuotaLaboral);
                signo.add(-1);
                cuenta.add(3);//bancos
                monto.add(resultado.getDouble(1)+resultado.getDouble(1)+resultado.getDouble(2)+
                        resultado.getDouble(3)+resultado.getDouble(4)-cuotaLaboral);
                signo.add(-1);
                sucursal=resultado.getInt(6);//sucursal
            }
        }
        insertarPartida(cuenta, monto, signo,"Pago de Planilla.", planilla_id);
        
    }
    
        /**
     * Metodo para insertar la partida inicial de la empresa (genera automaticamente el valor sobre mercaderia)
     * Si no existen productos no permite crear 
     * @param cuentasId array de las ids de las cuentas
     * @param montos array de los montos a cargar de las cuentas
     * @param signo numero que indica si se ecnuentra del lado del debe o el haber 
     * @param idSucursal id de la sucursal
     * @return 1 si pudo ingresarse y 0 si no
     * @throws NoSePuedeConectar
     * @throws SQLException 
     */
    public int generarPartidaInicial(ArrayList cuentasId, ArrayList montos,ArrayList signo,int idSucursal) throws NoSePuedeConectar, SQLException
    {
        int idPartida=0;
        conectar();
        Statement instruccion=conexion.createStatement();
        
        instruccion.executeUpdate("insert into partida (fecha,numero,sucursales_id, Descripcion) "
                + "values (concat(year(now()),'-',month(now()),'-01'),1,"+idSucursal+",'Partida Inicial');");
        ResultSet resultado=instruccion.executeQuery("select id from partida where fecha=concat(year(now()),'-',month(now()),'-01') "
                + "and anulada=0 and sucursales_id="+idSucursal+";");
        if(resultado.next())
            idPartida=resultado.getInt(1);
        for(int i=0;i<cuentasId.size();i++)
        {
            if(Double.parseDouble(montos.get(i).toString())>0)
            instruccion.executeUpdate("call insertarPartidas("+idPartida+","+cuentasId.get(i)+","+montos.get(i)+","+signo.get(i)+");");
        }   
        conexion.close();
        return 1;
    }
    public float mercaderia(int idSucursal) throws NoSePuedeConectar, SQLException{
        conectar();
        float mercaderia=0;
        Statement instruccion=conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("select sum(p.Precio_Venta*e.Existencia) from producto p inner join"
                + " existencia e on p.id = e.Producto_id=p.id where e.Sucursales_id="+idSucursal+";");
        if(resultado.next())
            mercaderia=resultado.getFloat(1);
        
        return mercaderia;
    }
    /**
     * Obtiene la lista de permisos para usuarios
     * @return array con los permisos para usuarios
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public ArrayList obtenerPermisos() throws SQLException, NoSePuedeConectar{
        ArrayList listado=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT Nombre FROM NivelAcceso;"); //se guarda el resultado de la instruccion
        while(resultado.next()){
            listado.add(resultado.getString(1));
        }
        conexion.close();
        return listado;
    }
    /**
     * Ingresa una nueva sucursal
     * @param nombre nombre de la nueva sucursal
     * @param noFactura número de factura en donde va el correlativo
     * @param serieFactura serie de factura
     * @param empresa nombre de empresa de la sucursal (opcional)
     * @param sucursalId id de la sucursal a la que pertenece (opcional)
     * @return numero de registros cambiados
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int ingresoSucursalJP(String nombre, int noFactura, String serieFactura, String empresa, int sucursalId) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccionLec = conexion.createStatement();
        ResultSet resultadoLec = instruccionLec.executeQuery("SELECT id, Nombre FROM Sucursales WHERE Nombre='"+nombre+"';");
        if(!resultadoLec.next()){
            Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
            int resultado = instruccion.executeUpdate("INSERT INTO Sucursales (Nombre, NumeroFac, SerieFact, Empresa, Sucursales_id) VALUES ('"+(nombre.equals("")?"N/A":nombre)+"','"+noFactura+"','"+(serieFactura.equals("")?"N/A":serieFactura)+"',"+(empresa.equals("")?"NULL":"'"+empresa+"'")+","+(empresa.equals("")?sucursalId:"NULL")+");"); //se guarda el resultado de la instruccion
            conexion.close();
            return resultado;
        }
        conexion.close();
        return -1;
    }
    /**
     * Obtiene un listado de sucursales que son empresas
     * @param idSucursalAModificar en caso de que NO vaya a modificarse una sucursal, envie cadena vacia ("")
     * @return array de sucursales empresas
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     * @throws SQLException en caso de error
     */
    public ArrayList obtenerSucursalesEmpresasJP(String idSucursalAModificar) throws NoSePuedeConectar, SQLException{
        ArrayList listado=new ArrayList();
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre FROM Sucursales WHERE Sucursales_id IS NULL"+(idSucursalAModificar.equals("")?"":(" AND id!="+idSucursalAModificar))+";"); //se guarda el resultado de la instruccion
        while(resultado.next()){
            listado.add(resultado.getString(2)+"-"+resultado.getString(1));
        }
        conexion.close();
        return listado;
    }
    /**
     * Inicializa la tabla para sucursales
     * @param modelo modelo de tabla
     * @return modelo inicializado
     */
    private DefaultTableModel inicializarTablaSucursalesJP(DefaultTableModel modelo) {
        modelo = new DefaultTableModel(null, new String[]{"ID", "Nombre", "Número de Factura", "Serie de Factura", "Nombre de Empresa", "Sucursal Central"}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false,false,false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
    /**
     * Obtiene una tabla con las sucursales
     * @return modelo de tabla con las sucursales
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de que no pueda conectarse a la BD
     */
    public DefaultTableModel obtenerTodasSucursalesJP() throws SQLException, NoSePuedeConectar{
        DefaultTableModel modelo=null;
        modelo=inicializarTablaSucursalesJP(modelo);
        conectar();
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Nombre, NumeroFac, SerieFact, Empresa, Sucursales_id FROM Sucursales WHERE Habilitado=1;");
        while(resultado.next()){
            String empresa=resultado.getString("Empresa");
            String sucursalCentral=resultado.getString("Sucursales_id");
            String sucursalActualID=sucursalCentral;
            if(sucursalCentral!=null){
                Statement consultaSucursal=conexion.createStatement();
                ResultSet resultadoSuc=consultaSucursal.executeQuery("SELECT Nombre FROM Sucursales WHERE id="+sucursalActualID);
                if(resultadoSuc.next()){
                    sucursalActualID=resultadoSuc.getString(1)+"-"+sucursalActualID;
                }
            }
            modelo.addRow(new String[] {resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), (empresa==null?"N/A":empresa),(sucursalCentral==null?"N/A":sucursalActualID)});
        }
        conexion.close();
        return modelo;
    }
    /**
     * Modifica una sucursal
     * @param id ID de la sucursal a modificar
     * @param nombre nombre de la nueva sucursal
     * @param noFactura número de factura en donde va el correlativo
     * @param serieFactura serie de factura
     * @param empresa nombre de empresa de la sucursal (opcional)
     * @param sucursalId id de la sucursal a la que pertenece (opcional)
     * @return numero de registros cambiados
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int modificacionSucursalJP(int id, String nombre, int noFactura, String serieFactura, String empresa, int sucursalId) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Sucursales SET Nombre='"+(nombre.equals("")?"N/A":nombre)+"', NumeroFac='"+noFactura+"', SerieFact='"+(serieFactura.equals("")?"N/A":serieFactura)+"', Empresa="+(empresa.equals("")?"NULL":"'"+empresa+"'")+", Sucursales_id="+(empresa.equals("")?sucursalId:"NULL")+" WHERE id="+id+";"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Modifica una sucursal
     * @param id ID de la sucursal a modificar
     * @return numero de registros cambiados
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int eliminacionSucursalJP(int id) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Sucursales SET Habilitado=0 WHERE id="+id/*"DELETE FROM Sucursales WHERE id="+id+";"*/); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Modifica un usuario
     * @param usuario nombre del usuario
     * @param nivelAcceso id del nivel de acceso
     * @return numero de registros cambiados
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int modificacionUsuario(String usuario, int nivelAcceso) throws SQLException, NoSePuedeConectar{
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        int resultado = instruccion.executeUpdate("UPDATE Usuario SET NivelAcceso_id="+nivelAcceso+" WHERE Usuario='"+usuario+"';"); //se guarda el resultado de la instruccion
        conexion.close();
        return resultado;
    }
    /**
     * Obtiene el ID de permiso de un usuario en especifico
     * @param usuario nombre del usuario a consultar
     * @return ID del permiso del usuario
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int obtenerPermisosDeUsuario(String usuario) throws SQLException, NoSePuedeConectar{
        int res=-1;
        conectar(); //permite la conexion con la base de datos
        Statement instruccion=conexion.createStatement(); //Crea una nueva instruccion para la base de datos
        ResultSet resultado = instruccion.executeQuery("SELECT NivelAcceso_id FROM Usuario WHERE Usuario='"+usuario+"';"); //se guarda el resultado de la instruccion
        if(resultado.next()){
            res=resultado.getInt(1);
        }
        conexion.close();
        return res;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    /**
     * Retorna el id de un usuario en base a su nombre de usuario
     * @param usuario nombre del usuario
     * @return id del usuario
     * @throws SQLException en caso de error
     * @throws NoSePuedeConectar en caso de no poder conectarse a la BD
     */
    public int obtenerIDUsuario(String usuario) throws SQLException, NoSePuedeConectar{
        int id=-1;
        conectar();
        Statement instruccion=conexion.createStatement();
        ResultSet resultado=instruccion.executeQuery("SELECT id FROM Usuario WHERE id='"+usuario+"';");
        if(resultado.next()){
            id=resultado.getInt(1);
        }
        conexion.close();
        return id;
    }
    /**
     * 
     * @param filtro
     * @param criterio
     * @param idSucursalDa
     * @param idSucursalRecibe
     * @return
     * @throws SQLException
     * @throws NoSePuedeConectar 
     */
    public DefaultTableModel obtenerProductosTraslados(int filtro, String criterio, int idSucursalDa, int idSucursalRecibe) throws SQLException, NoSePuedeConectar{
        String texto_busqueda="";
        if(!criterio.isEmpty()&&filtro!=0){
            texto_busqueda=" AND ";
            switch(filtro){
                case 1:
                    texto_busqueda+="Codigo ";
                    break;
                case 2:
                    texto_busqueda+="Descripcion ";
                    break;
                default:
                    break;
            }
            texto_busqueda+="LIKE CONCAT('%','"+criterio+"','%')";
        }
        DefaultTableModel modelo=null;
        conectar();
        String sucuDa="", sucuRecibe="";
        Statement sucuInstruccion=conexion.createStatement();
        ResultSet sucursal=sucuInstruccion.executeQuery("SELECT Nombre FROM Sucursales WHERE id='"+idSucursalDa+"';");
        if(sucursal.next())
            sucuDa=sucursal.getString(1);
        sucuInstruccion=conexion.createStatement();
        sucursal=sucuInstruccion.executeQuery("SELECT Nombre FROM Sucursales WHERE id='"+idSucursalRecibe+"';");
        if(sucursal.next())
            sucuRecibe=sucursal.getString(1);
        modelo=inicializarTablaTrasladosJP(modelo, sucuDa, sucuRecibe);
        
        Statement instruccion = conexion.createStatement();
        ResultSet resultado = instruccion.executeQuery("SELECT id, Codigo, Descripcion FROM producto WHERE Habilitado=1"+texto_busqueda+";");
        while(resultado.next()){
            String idProductoAct=resultado.getString(1);
            float exisSucuDa=0, exisSucuRecibe=0;
            sucuInstruccion=conexion.createStatement();
            ResultSet existencias=sucuInstruccion.executeQuery("SELECT Existencia FROM Existencia WHERE Sucursales_id="+idSucursalDa+" AND Producto_id="+idProductoAct);
            if(existencias.next())
                exisSucuDa=existencias.getFloat(1);
            sucuInstruccion=conexion.createStatement();
            existencias=sucuInstruccion.executeQuery("SELECT Existencia FROM Existencia WHERE Sucursales_id="+idSucursalRecibe+" AND Producto_id="+idProductoAct);
            if(existencias.next())
                exisSucuRecibe=existencias.getFloat(1);
            modelo.addRow(new String[]{idProductoAct,resultado.getString(2),resultado.getString(3), (""+exisSucuDa),(""+exisSucuRecibe)});
        }
        conexion.close();
        return modelo;
    }  
    /**
     * Inicializa la tabla para traslados de productos
     * @param modelo modelo de tabla
     * @return modelo inicializado
     */
    private DefaultTableModel inicializarTablaTrasladosJP(DefaultTableModel modelo, String nombreSucursalDa, String nombreSucursalRecibe) {
        modelo = new DefaultTableModel(null, new String[]{"ID", "Cod. Interno", "Descripción", "Existencia "+nombreSucursalDa, "Existencia "+nombreSucursalRecibe}){
            boolean[] canEdit = new boolean [] {
        false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        return modelo;
    } 
}