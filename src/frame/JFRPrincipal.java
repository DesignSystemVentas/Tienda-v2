
package frame;

import controlador.Conexion;
import controlador.ControladorCompra;
import controlador.ControladorProducto;
import controlador.ControladorProveedor;
import controlador.ControladorSucursal;
import controlador.ControladorTipoPrecio;
import controlador.ControladorVenta;
import controlador.ErrorTienda;
import controlador.Parametro;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author Jose Lopez Garcia
 */
public final class JFRPrincipal extends javax.swing.JFrame {
          //vizcarra//
    Date date = new Date();
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = new GregorianCalendar();       
    Calendar calendar = Calendar.getInstance();  
    
    int dia,mes,anio,fila=0,venta=0,sucursal,tipoventa,nitVenta,giroVenta,nDocumentoVenta,sabercmbUtilidadVenta,sabercmbIdSucursal,sabercmbTipoFactura,saberMesParaGenerarReporteVenta;
    double saberTipoUtilidadVenta=0;
    int SaberSucursalVentas=0;
    double Punitario = 0, TotalGravadoVenta=0, TotalVenta=0, SubTotalVenta;   
    boolean encontrar;
    ResultSet rstControladorVenta = null;
    ResultSet rstControladorProducto = null;
    ResultSet rstCSucursal = null;
    ResultSet rstTipoPrecio = null;
    ControladorVenta controladorventa = new ControladorVenta();
    ControladorProducto cp = new ControladorProducto();
    ControladorSucursal cSucursal = new ControladorSucursal();
    ControladorTipoPrecio cTipoPrecio = new ControladorTipoPrecio();
    String datosVenta[] = new String[7], CodigoBarraVender = "";
    String GReporteVenta[] = new String[5];
    DecimalFormat df = new DecimalFormat("00.00");
    //vaizcarra//

    //Todas las variables que agregue-Daniel
    boolean cargarSucursalesFC, agregarDetalleModelo, validarPC, agregarProductoBD, agregarSubtotalDC, add, addC;
    //Todos estos son para el momento de la compra con sus detalles
    Conexion cn = new Conexion();
    String costoUC="", IdCompraPC="",CodBarraPC="",NombrePC="",CantidadPC="", 
            CostoUnitarioPC="",IdSucursalPC="",sucursalPC="",SubtotalPC="", 
            SubtotalCompra="", tipoCompra="", IdSucursalGC="", SucursalGC="",
            IdProveedorGC="", ProveedorGC="", ivaCompra="", percepcionCompra=""
            , TotalCompra="", ivaAnterior="", percepcionAnterior="";
    String detalleCompra[] = new String[5];
    String detalleCompraC[] = new String[5];
    int CantidadAnteriorPC, filaEliminar;
    double SubtotalAnteriorPC;
    //--
    ResultSet rsFiltroCompra, rsCompra, rsSucursalFC, rsMayorIdC, rsNameProd, rsDC;
    DefaultComboBoxModel modeloSucursalFC = new DefaultComboBoxModel();//Combo filtro sucursal
    
    //--------------------------------------------------------


    boolean ventas, compras, productos, proveedores, administradores;
    boolean apagado, principal,modificarTipoPrecio,modificarSucursal,modificarParametro;
    int x,y;
    JTableHeader tHeadVentas,tHeadCompras,tHeadProductos,tHeadCompra,tHeadProveedores,tHeadDetalleCompra;  //agregar sigfid si es ncesario
    JTableHeader tHeadTipoPrecio,tHeadSucursal,tHeadParametro,tHeadProducto;  //agregar sigfid si es ncesario
    DefaultTableModel modeloCompra = new DefaultTableModel();
    DefaultTableModel modeloAddCompra = new DefaultTableModel();
    DefaultTableModel modeloDetalleCompra = new DefaultTableModel();
    
    DefaultTableModel modeloTipoPrecio = new DefaultTableModel();
    DefaultTableModel modeloSucursal = new DefaultTableModel();
    DefaultTableModel modeloParametro = new DefaultTableModel();
    DefaultTableModel modeloProducto = new DefaultTableModel();
   
    //****** COMPRAS *******
    boolean cargarSucursalesC=false;
    boolean cargarProveedoresC=false;
    ResultSet rsProveedorC = null;
    ResultSet rsSucursalC = null;
    DefaultComboBoxModel modeloProveedorC = new DefaultComboBoxModel();
    DefaultComboBoxModel  modeloSucursalC = new DefaultComboBoxModel();
    
    
    //vizcarra//
    DefaultTableModel MenuVenta = new DefaultTableModel();
    DefaultTableModel mAgregarDVenta = new DefaultTableModel();
    DefaultTableModel ReporteVenta = new DefaultTableModel();
    DefaultComboBoxModel mSucursal = new DefaultComboBoxModel();
    DefaultComboBoxModel mLlenarIdS = new DefaultComboBoxModel();
    DefaultComboBoxModel mUtilidad = new DefaultComboBoxModel(); 
    DefaultComboBoxModel mLlenarPoU = new DefaultComboBoxModel(); 
    DefaultComboBoxModel modeloSucursalRV = new DefaultComboBoxModel(); 
    //vizcarra//

    private boolean modificarProducto;

    
    
    public JFRPrincipal() {
        initComponents();
        tHeadVentas = tblMenuVentas.getTableHeader();
        tHeadCompras=tblCompras.getTableHeader();
        tHeadProductos=jtblProductos.getTableHeader();
        tHeadCompra=tblCompra.getTableHeader();
        tHeadProveedores=tblProveedores.getTableHeader();
        tHeadDetalleCompra=tblDetalleCompra.getTableHeader();
        tHeadTipoPrecio = jtblTipoDePrecio.getTableHeader();
        tHeadParametro = jtblParametros.getTableHeader();
        tHeadSucursal = jtblSucursales.getTableHeader();
        tHeadProducto = jtblProductos.getTableHeader();
        
        
        cabezera();
        ventas = compras = productos = proveedores = apagado = false;
        btnVentas.setBorder(null);
        btnCompras.setBorder(null);
        btnProductos.setBorder(null);
        btnAdministacion.setBorder(null);
        apagado2();
        Principal(true);
        Compras(false);
        Ventas(false);
        Productos(false);
        Proveedores(false);
        
               //VIZCARRA//
         //llenado de tabla MenuVenta
        MenuVenta.addColumn("IdVenta");        
        MenuVenta.addColumn("TipoVenta");        
        MenuVenta.addColumn("Cliente");
        MenuVenta.addColumn("Fecha");        
        MenuVenta.addColumn("TotalGravado");       
        MenuVenta.addColumn("Direccion");
        MenuVenta.addColumn("Documento");
        tblMenuVentas.setModel(MenuVenta);
        //finalizado "llenado de tabla MenuVenta"//
        //llenado de tabla Registro Venta (detalleventa)
        mAgregarDVenta.addColumn("Codigo Barra");
        mAgregarDVenta.addColumn("Producto");
        mAgregarDVenta.addColumn("Cantidad");
        mAgregarDVenta.addColumn("Costo");
        mAgregarDVenta.addColumn("SubTotal");        
        tblRegistrarVenta.setModel(mAgregarDVenta);
        //para ReporteVenta
        ReporteVenta.addColumn("Dia");
        ReporteVenta.addColumn("Documento");
        ReporteVenta.addColumn("Cliente");
        ReporteVenta.addColumn("NRC");
        ReporteVenta.addColumn("TotalVentas");        
        tblReporteVentas.setModel(ReporteVenta);
        //VIZCARRA//

                
        //llenado de tabla Compra  
        modeloCompra.addColumn("ID Compra");
        modeloCompra.addColumn("Tipo de Compra");
        modeloCompra.addColumn("Proveedor");
        modeloCompra.addColumn("Total");
        //llenado de tabla Registro Compra
        modeloAddCompra.addColumn("Código Barra");
        modeloAddCompra.addColumn("Producto");
        modeloAddCompra.addColumn("Cantidad");
        modeloAddCompra.addColumn("Costo");
        modeloAddCompra.addColumn("SubTotal");
        //lleando de tabla Detalle Compra
        modeloDetalleCompra.addColumn("Código Barra");
        modeloDetalleCompra.addColumn("Producto");
        modeloDetalleCompra.addColumn("Cantidad");
        modeloDetalleCompra.addColumn("Costo");
        modeloDetalleCompra.addColumn("SubTotal");
        //llenado de tabla parametro  
        modeloParametro.addColumn("ID Parametro");
        modeloParametro.addColumn("Nombre");
        modeloParametro.addColumn("Valor");
        //llenado de tabla tipoPrecio  
        modeloTipoPrecio.addColumn("IDTipoPrecio");
        modeloTipoPrecio.addColumn("Nombre");
        modeloTipoPrecio.addColumn("Utilidad");
        //llenado de tabla sucursal  
        modeloSucursal.addColumn("ID Sucursal");
        modeloSucursal.addColumn("Nombre");
        modeloSucursal.addColumn("Direccion");
        modeloSucursal.addColumn("Telefono");
        //llenado de tabla producto  
        modeloProducto.addColumn("CodBarra");
        modeloProducto.addColumn("Nombre");
        modeloProducto.addColumn("Costo");
        modeloProducto.addColumn("Sucursal");
        modeloProducto.addColumn("Existencia");
        
        
        
        tblCompra.setModel(modeloAddCompra);
        tblCompras.setModel(modeloCompra);
        tblDetalleCompra.setModel(modeloDetalleCompra);
        jtblTipoDePrecio.setModel(modeloTipoPrecio);
        jtblParametros.setModel(modeloParametro);
        jtblSucursales.setModel(modeloSucursal);
        jtblProductos.setModel(modeloProducto);
        
            modificarTipoPrecio=false;
            txtIdTipoPrecio.setEditable(false);
            txtNombreTipoPrecio.setEditable(false);
            txtUtilidadTipoPrecio.setEditable(false);
            
            txtIdParametro.setEditable(false);
            txtNombreParametro.setEditable(false);
            txtParametroParametro.setEditable(false);
            modificarParametro=false;
            
            txtIdSucursal.setEditable(false);
            txtNombreSucursal.setEditable(false);
            txtDireccionSucursal.setEditable(false);
            txtTelefonoSucursal.setEditable(false);
            modificarSucursal=false;                                             
    }
    
    /*  ---- Color a las cabeceras de las tablas ----  */
    public void cabezera(){
        Font fuente = new Font("Tahoma", Font.BOLD, 12);
        tHeadVentas.setBackground(jpnBarraMenu.getBackground());
        tHeadVentas.setForeground(Color.WHITE);
        tHeadVentas.setFont(fuente);
        
        tHeadCompras.setBackground(jpnBarraMenu.getBackground());
        tHeadCompras.setForeground(Color.WHITE);
        tHeadCompras.setFont(fuente);
        
        tHeadProductos.setBackground(jpnBarraMenu.getBackground());
        tHeadProductos.setForeground(Color.WHITE);
        tHeadProductos.setFont(fuente);
        
        tHeadCompra.setBackground(jpnBarraMenu.getBackground());
        tHeadCompra.setForeground(Color.WHITE);
        tHeadCompra.setFont(fuente);
        
        tHeadProveedores.setBackground(jpnBarraMenu.getBackground());
        tHeadProveedores.setForeground(Color.WHITE);
        tHeadProveedores.setFont(fuente);
        
        tHeadDetalleCompra.setBackground(jpnBarraMenu.getBackground());
        tHeadDetalleCompra.setForeground(Color.WHITE);
        tHeadDetalleCompra.setFont(fuente);
        
        tHeadTipoPrecio.setBackground(jpnBarraMenu.getBackground());
        tHeadTipoPrecio.setForeground(Color.WHITE);
        tHeadTipoPrecio.setFont(fuente);
        
        tHeadSucursal.setBackground(jpnBarraMenu.getBackground());
        tHeadSucursal.setForeground(Color.WHITE);
        tHeadSucursal.setFont(fuente);
        
        tHeadParametro.setBackground(jpnBarraMenu.getBackground());
        tHeadParametro.setForeground(Color.WHITE);
        tHeadParametro.setFont(fuente);
        
        tHeadProducto.setBackground(jpnBarraMenu.getBackground());
        tHeadProducto.setForeground(Color.WHITE);
        tHeadProducto.setFont(fuente);
                
        
    }
 
/*  ---- Visualización de imágenes en Menú ----  */
    public void Principal(boolean estado){
        if(!jpnProveedores.isVisible()){
        jpnPrimero.setVisible(estado);
        }else{
      }
    }
    public void Compras(boolean estado){
        if(!jpnProveedores.isVisible()){
        jpnSegundo.setVisible(estado);
        }else{
      }
    }
    public void Ventas(boolean estado){
        if(!jpnProveedores.isVisible()){
        jpnTercero.setVisible(estado);
        }else{
      }
    }
    public void Productos(boolean estado){
        if(!jpnProveedores.isVisible()){
        jpnCuarto.setVisible(estado);
        }else{
      }
    }
    public void Proveedores(boolean estado){
        if(!jpnProveedores.isVisible()){
        jpnQuinto.setVisible(estado);
        }else{
      }
    }
    public void apagado(){
        apagado = true;
        jpnPrincipal.setVisible(false);  
    }
    public void apagado2(){
       jpnProveedores.setVisible(false);
        jpnAgregarProv.setVisible(false);
        jpnModificarProveedor.setVisible(false);
        jpnMenuVentas.setVisible(false);
        jpnProductos.setVisible(false);
        jpnNuevoProducto.setVisible(false);
        jpnRegistroCompra.setVisible(false);
        jpnCompras.setVisible(false);
        jpnDetalleCompra.setVisible(false);
        jpnRegistrarVenta.setVisible(false);
        jpnAdministracion.setVisible(false);
        jpnRegistrarVenta.setVisible(false);
        jpnReporteVentas.setVisible(false);
        jpnUtilidadMenuVentasParametros.setVisible(false);
        jpnVentasReporteParametro.setVisible(false);
        jpnVerVentasporSucursal.setVisible(false);
    }
       //VIZCARRA//
    public void saberCodigoVenta(){
        int cantidad, mayor=0;           
        try {
            rstControladorVenta = controladorventa.ObtenerIdVenta();
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} 
        
        try {                    
           while (rstControladorVenta.next()) {
                cantidad = rstControladorVenta.getInt(1);
                if (cantidad != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.ObtenerIdVenta();
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayor = rstControladorVenta.getInt(1) + 1;                                                     
                        txtIdVenta.setText(""+mayor);                        
                    }
                } else if (cantidad == 0) {
                    txtIdVenta.setText("1");
                }
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
    }
    //VIZCARRA//    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngFiltroProductos = new javax.swing.ButtonGroup();
        jpnBarraSuperior = new javax.swing.JPanel();
        lblBotonCerrar = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        jpnBarraMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        jpnSubMenu = new javax.swing.JPanel();
        btnCompras = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnAdministacion = new javax.swing.JButton();
        btnProveedores1 = new javax.swing.JButton();
        btnHome = new javax.swing.JLabel();
        jpnPrincipal = new javax.swing.JPanel();
        jpnPrimero = new javax.swing.JPanel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl7 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        lblMitad = new javax.swing.JLabel();
        jpnSegundo = new javax.swing.JPanel();
        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl14 = new javax.swing.JLabel();
        lbl15 = new javax.swing.JLabel();
        lblMitad2 = new javax.swing.JLabel();
        jpnTercero = new javax.swing.JPanel();
        lbl21 = new javax.swing.JLabel();
        lbl22 = new javax.swing.JLabel();
        lbl23 = new javax.swing.JLabel();
        lbl24 = new javax.swing.JLabel();
        lbl25 = new javax.swing.JLabel();
        lblMitad3 = new javax.swing.JLabel();
        jpnCuarto = new javax.swing.JPanel();
        lbl31 = new javax.swing.JLabel();
        lbl32 = new javax.swing.JLabel();
        lbl33 = new javax.swing.JLabel();
        lbl34 = new javax.swing.JLabel();
        lbl35 = new javax.swing.JLabel();
        lblMitad4 = new javax.swing.JLabel();
        jpnQuinto = new javax.swing.JPanel();
        lbl41 = new javax.swing.JLabel();
        lbl42 = new javax.swing.JLabel();
        lbl43 = new javax.swing.JLabel();
        lbl44 = new javax.swing.JLabel();
        lbl45 = new javax.swing.JLabel();
        lblMitad5 = new javax.swing.JLabel();
        jpnProveedores = new javax.swing.JPanel();
        btnEliminarProveedor = new javax.swing.JButton();
        btnAgregarProveedor = new javax.swing.JButton();
        btnModificarProveedor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        jPanel42 = new javax.swing.JPanel();
        jSeparator20 = new javax.swing.JSeparator();
        lblProveedores6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        txtProductosBuscar1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator38 = new javax.swing.JSeparator();
        jpnAgregarProv = new javax.swing.JPanel();
        btnGuardarProveedor = new javax.swing.JButton();
        btnAtrasProveedores = new javax.swing.JButton();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtNIT = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIDProveedor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jSeparator19 = new javax.swing.JSeparator();
        jpnModificarProveedor = new javax.swing.JPanel();
        btnGuardarModificarProveedor = new javax.swing.JButton();
        btnAtrasModificarProveedor = new javax.swing.JButton();
        txtNuevoDireccionProveedor = new javax.swing.JTextField();
        txtNuevoNIT = new javax.swing.JTextField();
        txtNuevoNombreProveedor = new javax.swing.JTextField();
        txtNuevoTelefonoProveedor = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jSeparator40 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtIDProveedor1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator41 = new javax.swing.JSeparator();
        jSeparator42 = new javax.swing.JSeparator();
        jSeparator43 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtNitActualProveedor = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtNombreActualProveedor1 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtTelefonoActualProveedor = new javax.swing.JLabel();
        txtDireccionActualProveedor = new javax.swing.JLabel();
        jpnMenuVentas = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        lblProveedores5 = new javax.swing.JLabel();
        btnHacerVenta = new javax.swing.JButton();
        btnVerVentas = new javax.swing.JButton();
        btnVerReporteVentas = new javax.swing.JButton();
        jpnCompras = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        btnAgregarCompra = new javax.swing.JButton();
        btnVerDetalle = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jSeparator27 = new javax.swing.JSeparator();
        lblProveedores3 = new javax.swing.JLabel();
        lblListadoCompras = new javax.swing.JLabel();
        jSeparator35 = new javax.swing.JSeparator();
        cmbFiltroSucursalCompra = new javax.swing.JComboBox<String>();
        lblFiltrarCompra = new javax.swing.JLabel();
        jpnRegistroCompra = new javax.swing.JPanel();
        btnGuardarCompra = new javax.swing.JButton();
        btnCancelarCompra = new javax.swing.JButton();
        cmbProveedorCompra = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCompra = new javax.swing.JTable();
        txtTotalCompra = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        cmbSucursalCompra = new javax.swing.JComboBox<String>();
        lblSucursalCompra = new javax.swing.JLabel();
        txtIdCompra = new javax.swing.JTextField();
        lblIdCompra = new javax.swing.JLabel();
        lblProveedor = new javax.swing.JLabel();
        lblSubtotalCompra = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        lblCodBarraProd = new javax.swing.JLabel();
        txtCodBarraCompra = new javax.swing.JTextField();
        lblNomProd = new javax.swing.JLabel();
        txtNombreProductoCompra = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantidadCompra = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator36 = new javax.swing.JSeparator();
        lblCostoProductoCompra = new javax.swing.JLabel();
        txtCostoProductoCompra = new javax.swing.JTextField();
        txtIvaCompra = new javax.swing.JTextField();
        lblIvaCompra = new javax.swing.JLabel();
        lblPercepcionCompra = new javax.swing.JLabel();
        txtPercepcionCompra = new javax.swing.JTextField();
        txtSubtotalCompra = new javax.swing.JTextField();
        lblTotalCompra = new javax.swing.JLabel();
        txtNumDocumento = new javax.swing.JTextField();
        lbltxtTipoCompra = new javax.swing.JLabel();
        lblFechaCompra = new javax.swing.JLabel();
        txtFechaCompra = new javax.swing.JTextField();
        jpnProductos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        btnNuevoProducto = new javax.swing.JButton();
        btnBuscarProducto = new javax.swing.JButton();
        btnModificarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jSeparator14 = new javax.swing.JSeparator();
        lblProveedores4 = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtProductosBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator37 = new javax.swing.JSeparator();
        jpnNuevoProducto = new javax.swing.JPanel();
        btnAgregarNuevoProducto = new javax.swing.JButton();
        btnSalirProductos = new javax.swing.JButton();
        txtCodBarraProductos = new javax.swing.JTextField();
        txtNombreProductos = new javax.swing.JTextField();
        txtPrecioProductos = new javax.swing.JTextField();
        jPanel46 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator34 = new javax.swing.JSeparator();
        jSeparator39 = new javax.swing.JSeparator();
        jpnDetalleCompra = new javax.swing.JPanel();
        txtCodBarraProductos1 = new javax.swing.JTextField();
        txtNombreProductos1 = new javax.swing.JTextField();
        jPanel47 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jSeparator31 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblDetalleCompra = new javax.swing.JTable();
        jSeparator32 = new javax.swing.JSeparator();
        jLabel37 = new javax.swing.JLabel();
        jSeparator33 = new javax.swing.JSeparator();
        txtTotalCompraDetalle = new javax.swing.JTextField();
        btnAtrasDetalleCompra = new javax.swing.JButton();
        jpnAdministracion = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jSeparator47 = new javax.swing.JSeparator();
        lblProveedores8 = new javax.swing.JLabel();
        tpnlAdministracion = new javax.swing.JTabbedPane();
        tjpnlTipoPrecio = new javax.swing.JPanel();
        jscpTipoDePrecio = new javax.swing.JScrollPane();
        jtblTipoDePrecio = new javax.swing.JTable();
        btnNuevoTipoPrecio = new javax.swing.JButton();
        btnEliminarTipoPrecio = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtIdTipoPrecio = new javax.swing.JTextField();
        txtNombreTipoPrecio = new javax.swing.JTextField();
        txtUtilidadTipoPrecio = new javax.swing.JTextField();
        btnGuardarTipoPrecio = new javax.swing.JButton();
        btnModificarTipoPrecio = new javax.swing.JButton();
        btnCancelarTipoPrecio = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        tjpnlParametros = new javax.swing.JPanel();
        jscpTablaParametros = new javax.swing.JScrollPane();
        jtblParametros = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtIdParametro = new javax.swing.JTextField();
        txtNombreParametro = new javax.swing.JTextField();
        txtParametroParametro = new javax.swing.JTextField();
        btnGuardarParametro = new javax.swing.JButton();
        btnModificarParametro = new javax.swing.JButton();
        btnCancelarParametro = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        tjpnlSucursales = new javax.swing.JPanel();
        jscpSucursales = new javax.swing.JScrollPane();
        jtblSucursales = new javax.swing.JTable();
        btnNuevoSucursales = new javax.swing.JButton();
        btnEliminarSucursales = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnGuardarSucursal = new javax.swing.JButton();
        btnModificarSucursal = new javax.swing.JButton();
        btnCancelarSucursal = new javax.swing.JButton();
        txtIdSucursal = new javax.swing.JTextField();
        txtNombreSucursal = new javax.swing.JTextField();
        txtDireccionSucursal = new javax.swing.JTextField();
        txtTelefonoSucursal = new javax.swing.JTextField();
        jpnRegistrarVenta = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        lblProveedores7 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtIdVenta = new javax.swing.JTextField();
        lblFechaVentas = new javax.swing.JLabel();
        lblFechaVentaMostrar = new javax.swing.JTextField();
        txtNuDocumentoVenta = new javax.swing.JTextField();
        jSeparator44 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegistrarVenta = new javax.swing.JTable();
        btnVender = new javax.swing.JButton();
        txtNombreProductoVender = new javax.swing.JTextField();
        txtCantidadVender = new javax.swing.JTextField();
        btnAgregarProductoVenta = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        txtCodigoBarraVender = new javax.swing.JTextField();
        lblNRCventa = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        lblIVA = new javax.swing.JLabel();
        txtIVA = new javax.swing.JTextField();
        txtSumas = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtTotalventaGravado = new javax.swing.JTextField();
        txtClienteVenta = new javax.swing.JTextField();
        txtNRCventa = new javax.swing.JTextField();
        txtNITventa = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        lblNITventa = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtGiroVenta = new javax.swing.JTextField();
        lblGiroVenta = new javax.swing.JLabel();
        btnRegresarPaeametroVentas = new javax.swing.JButton();
        jpnReporteVentas = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblReporteVentas = new javax.swing.JTable();
        jPanel51 = new javax.swing.JPanel();
        lblProveedores9 = new javax.swing.JLabel();
        lblIVA1 = new javax.swing.JLabel();
        txtImpuestosVentas = new javax.swing.JTextField();
        txtVentasNetas = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtVentasGravadas = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jpnUtilidadMenuVentasParametros = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        lblProveedores11 = new javax.swing.JLabel();
        cmbTipoFacturaParametro = new javax.swing.JComboBox();
        cmbSucursalParametro = new javax.swing.JComboBox();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        Utilidad1 = new javax.swing.JLabel();
        cmbUtilidadParametro = new javax.swing.JComboBox();
        btnHacerNuevaVenta = new javax.swing.JButton();
        txtUtilidadVentaParametro = new javax.swing.JTextField();
        txtSucursalVentaParametro = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jpnVentasReporteParametro = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        lblProveedores13 = new javax.swing.JLabel();
        cmbFechasVenta = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jpnVerVentasporSucursal = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        lblProveedores12 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblMenuVentas = new javax.swing.JTable();
        lblSucursalMenuVenta = new javax.swing.JLabel();
        cmbSucursalReporteVenta = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/iconos/lanzador.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnBarraSuperior.setBackground(new java.awt.Color(0, 0, 0));
        jpnBarraSuperior.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jpnBarraSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpnBarraSuperiorMouseDragged(evt);
            }
        });
        jpnBarraSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnBarraSuperiorMousePressed(evt);
            }
        });
        jpnBarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBotonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/exit32.png"))); // NOI18N
        lblBotonCerrar.setToolTipText("Salir");
        lblBotonCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBotonCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBotonCerrarMouseClicked(evt);
            }
        });
        jpnBarraSuperior.add(lblBotonCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 30, 50));

        lblLogo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lanzador.png"))); // NOI18N
        lblLogo.setText("Tienda ABC");
        lblLogo.setToolTipText("");
        jpnBarraSuperior.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 180, 50));

        getContentPane().add(jpnBarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 55));

        jpnBarraMenu.setBackground(new java.awt.Color(176, 6, 6));
        jpnBarraMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMenu.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        lblMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/mas32.png"))); // NOI18N
        lblMenu.setText("Menu");
        jpnBarraMenu.add(lblMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 15, 90, 50));

        jpnSubMenu.setBackground(new java.awt.Color(102, 0, 0));
        jpnSubMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnSubMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/compras.png"))); // NOI18N
        btnCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComprasMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnComprasMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnComprasMouseEntered(evt);
            }
        });
        jpnSubMenu.add(btnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 20, 180, 40));

        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProductosMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProductosMouseEntered(evt);
            }
        });
        jpnSubMenu.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 120, 180, 40));

        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ventas.png"))); // NOI18N
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVentasMouseExited(evt);
            }
        });
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 70, 180, 40));

        btnAdministacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/configuraciones.png"))); // NOI18N
        btnAdministacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdministacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdministacionMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdministacionMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdministacionMouseEntered(evt);
            }
        });
        jpnSubMenu.add(btnAdministacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 220, 180, 40));

        btnProveedores1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/proveedores.png"))); // NOI18N
        btnProveedores1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProveedores1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProveedores1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProveedores1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProveedores1MouseEntered(evt);
            }
        });
        btnProveedores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedores1ActionPerformed(evt);
            }
        });
        jpnSubMenu.add(btnProveedores1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-126, 170, 180, 40));

        jpnBarraMenu.add(jpnSubMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 77, 190, 280));

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Home48.png"))); // NOI18N
        btnHome.setToolTipText("Inicio");
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        jpnBarraMenu.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, -1, -1));

        getContentPane().add(jpnBarraMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 190, 600));

        jpnPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jpnPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnPrimero.setBackground(new java.awt.Color(0, 0, 0));
        jpnPrimero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e776(0)_64.png"))); // NOI18N
        jpnPrimero.add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 70, 60));

        lbl4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl4.setForeground(new java.awt.Color(255, 255, 255));
        lbl4.setText("Tienda ABC");
        jpnPrimero.add(lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 120, -1));

        lbl7.setBackground(new java.awt.Color(153, 153, 153));
        lbl7.setForeground(new java.awt.Color(102, 102, 102));
        lbl7.setText("Versión 2.0");
        jpnPrimero.add(lbl7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, 100, -1));

        lbl5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl5.setForeground(new java.awt.Color(102, 102, 102));
        lbl5.setText("Te damos la bienvenida a tu");
        jpnPrimero.add(lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, -1, -1));

        lbl6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl6.setForeground(new java.awt.Color(102, 102, 102));
        lbl6.setText("nuevo sistema de Tienda.");
        jpnPrimero.add(lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, 20));

        lblMitad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad.jpg"))); // NOI18N
        jpnPrimero.add(lblMitad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        jpnPrincipal.add(jpnPrimero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnSegundo.setBackground(new java.awt.Color(0, 0, 0));
        jpnSegundo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl11.setForeground(new java.awt.Color(255, 255, 255));
        lbl11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e73d(0)_32.png"))); // NOI18N
        lbl11.setText("Compras");
        jpnSegundo.add(lbl11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 120, -1));

        lbl12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl12.setForeground(new java.awt.Color(102, 102, 102));
        lbl12.setText("Podrás realizar compras y ");
        jpnSegundo.add(lbl12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, -1, -1));

        lbl13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl13.setForeground(new java.awt.Color(102, 102, 102));
        lbl13.setText("abastecer tu Tienda.");
        jpnSegundo.add(lbl13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, -1, 30));

        lbl14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl14.setForeground(new java.awt.Color(102, 102, 102));
        lbl14.setText("Usa esta opción para manejar");
        jpnSegundo.add(lbl14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 230, -1));

        lbl15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl15.setForeground(new java.awt.Color(102, 102, 102));
        lbl15.setText("el sistema de Compras.");
        jpnSegundo.add(lbl15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, 30));

        lblMitad2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad2.jpg"))); // NOI18N
        jpnSegundo.add(lblMitad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 370, 600));

        jpnPrincipal.add(jpnSegundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnTercero.setBackground(new java.awt.Color(0, 0, 0));
        jpnTercero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl21.setForeground(new java.awt.Color(255, 255, 255));
        lbl21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e789(2)_32.png"))); // NOI18N
        lbl21.setText("Ventas");
        jpnTercero.add(lbl21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 120, -1));

        lbl22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl22.setForeground(new java.awt.Color(102, 102, 102));
        lbl22.setText("Podrás manejar los ingresos");
        jpnTercero.add(lbl22, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, -1, -1));

        lbl23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl23.setForeground(new java.awt.Color(102, 102, 102));
        lbl23.setText("que obtiene tu tienda.");
        jpnTercero.add(lbl23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 260, -1, 30));

        lbl24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl24.setForeground(new java.awt.Color(102, 102, 102));
        lbl24.setText("Usa esta opción y maneja");
        jpnTercero.add(lbl24, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 200, -1));

        lbl25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl25.setForeground(new java.awt.Color(102, 102, 102));
        lbl25.setText("el sistema de Ventas.");
        jpnTercero.add(lbl25, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 310, -1, 30));

        lblMitad3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad3.jpg"))); // NOI18N
        lblMitad3.setText("jLabel2");
        jpnTercero.add(lblMitad3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        jpnPrincipal.add(jpnTercero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnCuarto.setBackground(new java.awt.Color(0, 0, 0));
        jpnCuarto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl31.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl31.setForeground(new java.awt.Color(255, 255, 255));
        lbl31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e738(1)_32.png"))); // NOI18N
        lbl31.setText("Productos");
        jpnCuarto.add(lbl31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 130, -1));

        lbl32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl32.setForeground(new java.awt.Color(102, 102, 102));
        lbl32.setText("Podrás manejar los productos");
        jpnCuarto.add(lbl32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 220, -1));

        lbl33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl33.setForeground(new java.awt.Color(102, 102, 102));
        lbl33.setText("de tu sistema de Tienda.");
        jpnCuarto.add(lbl33, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 260, 190, 30));

        lbl34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl34.setForeground(new java.awt.Color(102, 102, 102));
        lbl34.setText("Usa esta opción para modificar,");
        jpnCuarto.add(lbl34, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 240, -1));

        lbl35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl35.setForeground(new java.awt.Color(102, 102, 102));
        lbl35.setText("agregar o eliminar Productos.");
        jpnCuarto.add(lbl35, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 220, 30));

        lblMitad4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad4.jpg"))); // NOI18N
        jpnCuarto.add(lblMitad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 360, 600));

        jpnPrincipal.add(jpnCuarto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        jpnQuinto.setBackground(new java.awt.Color(0, 0, 0));
        jpnQuinto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl41.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl41.setForeground(new java.awt.Color(255, 255, 255));
        lbl41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Entypo_e78e(0)_32.png"))); // NOI18N
        lbl41.setText("Proveedores");
        jpnQuinto.add(lbl41, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, 160, -1));

        lbl42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl42.setForeground(new java.awt.Color(102, 102, 102));
        lbl42.setText("¿Deseas nuevas alianzas?");
        jpnQuinto.add(lbl42, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, -1, -1));

        lbl43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl43.setForeground(new java.awt.Color(102, 102, 102));
        lbl43.setText("¡Estamos para eso!");
        jpnQuinto.add(lbl43, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, -1, 30));

        lbl44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl44.setForeground(new java.awt.Color(102, 102, 102));
        lbl44.setText("Usa esta opción y agrega");
        jpnQuinto.add(lbl44, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 200, -1));

        lbl45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl45.setForeground(new java.awt.Color(102, 102, 102));
        lbl45.setText("a tus nuevos proveedores.");
        jpnQuinto.add(lbl45, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, 30));

        lblMitad5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mitad5.jpg"))); // NOI18N
        jpnQuinto.add(lblMitad5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 600));

        jpnPrincipal.add(jpnQuinto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 730, 600));

        getContentPane().add(jpnPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 55, 750, 595));

        jpnProveedores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEliminarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProveedorMouseEntered(evt);
            }
        });
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 110, 30));

        btnAgregarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregarprov.png"))); // NOI18N
        btnAgregarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarProveedor.setFocusCycleRoot(true);
        btnAgregarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProveedorMouseEntered(evt);
            }
        });
        btnAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnAgregarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, 110, 30));

        btnModificarProveedor.setBackground(new java.awt.Color(0, 0, 0));
        btnModificarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnModificarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProveedorMouseEntered(evt);
            }
        });
        btnModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProveedorActionPerformed(evt);
            }
        });
        jpnProveedores.add(btnModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 110, 30));

        tblProveedores =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "idProveedor", "Nombre", "Teléfono", "Dirección", "NIT"
            }
        ));
        tblProveedores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProveedores);

        jpnProveedores.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 670, 260));

        jPanel42.setBackground(new java.awt.Color(0, 0, 0));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator20.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel42.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblProveedores6.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores6.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores6.setText("Proveedores");
        jPanel42.add(lblProveedores6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 30));

        jpnProveedores.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Listado de los Proveedores actuales:");
        jpnProveedores.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 175, -1, -1));
        jpnProveedores.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 230, -1));
        jpnProveedores.add(txtProductosBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 670, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Proveedor a buscar:");
        jpnProveedores.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 85, -1, -1));
        jpnProveedores.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 120, 20));

        getContentPane().add(jpnProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnAgregarProv.setPreferredSize(new java.awt.Dimension(95, 95));
        jpnAgregarProv.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarProveedorMouseEntered(evt);
            }
        });
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });
        jpnAgregarProv.add(btnGuardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 480, 110, 30));

        btnAtrasProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtrasProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasProveedoresMouseEntered(evt);
            }
        });
        btnAtrasProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasProveedoresActionPerformed(evt);
            }
        });
        jpnAgregarProv.add(btnAtrasProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, 110, 30));

        txtDireccionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionProveedorActionPerformed(evt);
            }
        });
        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 410, 30));

        txtNIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNITKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtNIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 230, 30));

        txtNombreProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtNombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProveedorActionPerformed(evt);
            }
        });
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 410, 30));

        txtTelefonoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProveedorActionPerformed(evt);
            }
        });
        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jpnAgregarProv.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 230, 30));

        jPanel45.setBackground(new java.awt.Color(0, 0, 0));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator16.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel45.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Agrega un nuevo proveedor:");
        jPanel45.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ID:");
        jPanel45.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        txtIDProveedor.setEditable(false);
        jPanel45.add(txtIDProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 90, 30));

        jpnAgregarProv.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Nombre:");
        jpnAgregarProv.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Teléfono:");
        jpnAgregarProv.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Dirección:");
        jpnAgregarProv.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("NIT:");
        jpnAgregarProv.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, 20));
        jpnAgregarProv.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 30, 10));
        jpnAgregarProv.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 50, 10));
        jpnAgregarProv.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 60, 10));
        jpnAgregarProv.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 60, 10));

        getContentPane().add(jpnAgregarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnModificarProveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarModificarProveedorMouseEntered(evt);
            }
        });
        btnGuardarModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificarProveedorActionPerformed(evt);
            }
        });
        jpnModificarProveedor.add(btnGuardarModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, 110, 30));

        btnAtrasModificarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasModificarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtrasModificarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasModificarProveedorMouseEntered(evt);
            }
        });
        btnAtrasModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasModificarProveedorActionPerformed(evt);
            }
        });
        jpnModificarProveedor.add(btnAtrasModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 500, 110, 30));

        txtNuevoDireccionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoDireccionProveedorActionPerformed(evt);
            }
        });
        txtNuevoDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoDireccionProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 410, 30));

        txtNuevoNIT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNITKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoNIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 230, 30));

        txtNuevoNombreProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtNuevoNombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoNombreProveedorActionPerformed(evt);
            }
        });
        txtNuevoNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoNombreProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 410, 30));

        txtNuevoTelefonoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoTelefonoProveedorActionPerformed(evt);
            }
        });
        txtNuevoTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoTelefonoProveedorKeyTyped(evt);
            }
        });
        jpnModificarProveedor.add(txtNuevoTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 230, 30));

        jPanel48.setBackground(new java.awt.Color(0, 0, 0));
        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator40.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel48.add(jSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Modifica un proveedor:");
        jPanel48.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 170, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("ID:");
        jPanel48.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        txtIDProveedor1.setEditable(false);
        jPanel48.add(txtIDProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 90, 30));

        jpnModificarProveedor.add(jPanel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nombre:");
        jpnModificarProveedor.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Teléfono:");
        jpnModificarProveedor.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, -1, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Dirección:");
        jpnModificarProveedor.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("NIT:");
        jpnModificarProveedor.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, -1, 20));
        jpnModificarProveedor.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 30, 10));
        jpnModificarProveedor.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 50, 10));
        jpnModificarProveedor.add(jSeparator42, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 60, 10));
        jpnModificarProveedor.add(jSeparator43, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 60, 10));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Actual:");
        jpnModificarProveedor.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 440, -1, -1));

        txtNitActualProveedor.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtNitActualProveedor.setForeground(new java.awt.Color(102, 0, 0));
        txtNitActualProveedor.setText("0210-300496-102-2");
        jpnModificarProveedor.add(txtNitActualProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 437, 370, 20));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 0, 0));
        jLabel32.setText("Actual:");
        jpnModificarProveedor.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, -1));

        txtNombreActualProveedor1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtNombreActualProveedor1.setForeground(new java.awt.Color(102, 0, 0));
        txtNombreActualProveedor1.setText("Juanito Martinez");
        jpnModificarProveedor.add(txtNombreActualProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 167, 370, 20));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 0, 0));
        jLabel38.setText("Actual:");
        jpnModificarProveedor.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 0, 0));
        jLabel39.setText("Actual:");
        jpnModificarProveedor.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, -1));

        txtTelefonoActualProveedor.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtTelefonoActualProveedor.setForeground(new java.awt.Color(102, 0, 0));
        txtTelefonoActualProveedor.setText("755555555");
        jpnModificarProveedor.add(txtTelefonoActualProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 257, 370, 20));

        txtDireccionActualProveedor.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        txtDireccionActualProveedor.setForeground(new java.awt.Color(102, 0, 0));
        txtDireccionActualProveedor.setText("Urb. Altos del Palmar Block D. Pasaje 8, Casa 17");
        jpnModificarProveedor.add(txtDireccionActualProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 347, 370, 20));

        getContentPane().add(jpnModificarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnMenuVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel44.setBackground(new java.awt.Color(0, 0, 0));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores5.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores5.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores5.setText("MENÚ VENTAS");
        jPanel44.add(lblProveedores5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 170, 50));

        jpnMenuVentas.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 70));

        btnHacerVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/VentaHacer.png"))); // NOI18N
        btnHacerVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHacerVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHacerVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHacerVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHacerVentaMouseExited(evt);
            }
        });
        btnHacerVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHacerVentaActionPerformed(evt);
            }
        });
        jpnMenuVentas.add(btnHacerVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 140, 140));

        btnVerVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/VentaVer.png"))); // NOI18N
        btnVerVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerVentasMouseExited(evt);
            }
        });
        btnVerVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerVentasActionPerformed(evt);
            }
        });
        jpnMenuVentas.add(btnVerVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 140, 140));

        btnVerReporteVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/VentaReporte.png"))); // NOI18N
        btnVerReporteVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerReporteVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerReporteVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerReporteVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerReporteVentasMouseExited(evt);
            }
        });
        btnVerReporteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerReporteVentasActionPerformed(evt);
            }
        });
        jpnMenuVentas.add(btnVerReporteVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, 140, 140));

        getContentPane().add(jpnMenuVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnCompras.setName("jpnCompras"); // NOI18N
        jpnCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCompras =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblCompras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Compra 1", "Pollo Indio", "12/Febrero/17", "$23.00"},
                {"Compra 2", null, null, null},
                {"Compra 3", null, null, null},
                {"Compra 4", null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        tblCompras.getTableHeader().setReorderingAllowed(false);
        tblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComprasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblCompras);

        jpnCompras.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 660, 310));

        btnAgregarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        btnAgregarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarCompraMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarCompraMouseEntered(evt);
            }
        });
        btnAgregarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCompraActionPerformed(evt);
            }
        });
        jpnCompras.add(btnAgregarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 490, 110, 30));

        btnVerDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/detalles2.png"))); // NOI18N
        btnVerDetalle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerDetalleMouseEntered(evt);
            }
        });
        btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleActionPerformed(evt);
            }
        });
        jpnCompras.add(btnVerDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 490, 110, 30));

        jPanel37.setBackground(new java.awt.Color(0, 0, 0));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator27.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel37.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, 50));

        lblProveedores3.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores3.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores3.setText("Compras");
        jPanel37.add(lblProveedores3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 90, 30));

        jpnCompras.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        lblListadoCompras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblListadoCompras.setText("Compras por sucursal");
        jpnCompras.add(lblListadoCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 200, -1));
        jpnCompras.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 117, 200, 10));

        cmbFiltroSucursalCompra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cmbFiltroSucursalCompra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFiltroSucursalCompraItemStateChanged(evt);
            }
        });
        cmbFiltroSucursalCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbFiltroSucursalCompraFocusGained(evt);
            }
        });
        jpnCompras.add(cmbFiltroSucursalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 200, -1));

        lblFiltrarCompra.setText("Filtrar:");
        jpnCompras.add(lblFiltrarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, -1));

        getContentPane().add(jpnCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnRegistroCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCompraMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCompraMouseEntered(evt);
            }
        });
        btnGuardarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCompraActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(btnGuardarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 540, 110, 30));

        btnCancelarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnCancelarCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarCompraMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarCompraMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarCompraMouseEntered(evt);
            }
        });
        jpnRegistroCompra.add(btnCancelarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, 110, 30));

        cmbProveedorCompra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProveedorCompraItemStateChanged(evt);
            }
        });
        cmbProveedorCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbProveedorCompraFocusGained(evt);
            }
        });
        jpnRegistroCompra.add(cmbProveedorCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 180, 30));

        tblCompra =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ));
        tblCompra.getTableHeader().setReorderingAllowed(false);
        tblCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCompraMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblCompra);

        jpnRegistroCompra.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 660, 210));

        txtTotalCompra.setText("$");
        txtTotalCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCompraActionPerformed(evt);
            }
        });
        jpnRegistroCompra.add(txtTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 560, 100, 40));

        jPanel39.setBackground(new java.awt.Color(0, 0, 0));
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbSucursalCompra.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSucursalCompraItemStateChanged(evt);
            }
        });
        cmbSucursalCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbSucursalCompraFocusGained(evt);
            }
        });
        jPanel39.add(cmbSucursalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 160, 30));

        lblSucursalCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSucursalCompra.setForeground(new java.awt.Color(254, 254, 254));
        lblSucursalCompra.setText("Sucursal:");
        jPanel39.add(lblSucursalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 70, 30));

        txtIdCompra.setEditable(false);
        jPanel39.add(txtIdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 60, 30));

        lblIdCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdCompra.setForeground(new java.awt.Color(254, 254, 254));
        lblIdCompra.setText("Id Compra:");
        jPanel39.add(lblIdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 80, 30));

        jpnRegistroCompra.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        lblProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProveedor.setForeground(new java.awt.Color(1, 1, 1));
        lblProveedor.setText("Proveedor:");
        jpnRegistroCompra.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 90, 30));

        lblSubtotalCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSubtotalCompra.setForeground(new java.awt.Color(1, 4, 4));
        lblSubtotalCompra.setText("SubTotal:");
        jpnRegistroCompra.add(lblSubtotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 510, -1, 40));
        jpnRegistroCompra.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 580, 50, 40));

        lblCodBarraProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCodBarraProd.setForeground(new java.awt.Color(1, 1, 1));
        lblCodBarraProd.setText("Código:");
        jpnRegistroCompra.add(lblCodBarraProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 80, 30));

        txtCodBarraCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarraCompraKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarraCompraKeyPressed(evt);
            }
        });
        jpnRegistroCompra.add(txtCodBarraCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 120, 30));

        lblNomProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNomProd.setForeground(new java.awt.Color(2, 13, 16));
        lblNomProd.setText("Producto:");
        jpnRegistroCompra.add(lblNomProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 70, 30));

        txtNombreProductoCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoCompraKeyPressed(evt);
            }
        });
        jpnRegistroCompra.add(txtNombreProductoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 140, 30));

        lblCantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(0, 4, 5));
        lblCantidad.setText("Cantidad:");
        jpnRegistroCompra.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 70, 30));

        txtCantidadCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyPressed(evt);
            }
        });
        jpnRegistroCompra.add(txtCantidadCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 40, 30));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jpnRegistroCompra.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 750, 10));

        jSeparator36.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator36.setForeground(new java.awt.Color(0, 0, 0));
        jpnRegistroCompra.add(jSeparator36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 750, 10));

        lblCostoProductoCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCostoProductoCompra.setForeground(new java.awt.Color(1, 1, 1));
        lblCostoProductoCompra.setText("Costo:");
        jpnRegistroCompra.add(lblCostoProductoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 194, 70, 20));

        txtCostoProductoCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoProductoCompraKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoProductoCompraKeyPressed(evt);
            }
        });
        jpnRegistroCompra.add(txtCostoProductoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 80, 30));

        txtIvaCompra.setText("$");
        jpnRegistroCompra.add(txtIvaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, 80, -1));

        lblIvaCompra.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        lblIvaCompra.setForeground(new java.awt.Color(1, 4, 4));
        lblIvaCompra.setText("IVA:");
        jpnRegistroCompra.add(lblIvaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, -1, -1));

        lblPercepcionCompra.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        lblPercepcionCompra.setForeground(new java.awt.Color(1, 4, 4));
        lblPercepcionCompra.setText("Percepción:");
        jpnRegistroCompra.add(lblPercepcionCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 560, -1, -1));

        txtPercepcionCompra.setText("$");
        jpnRegistroCompra.add(txtPercepcionCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 560, 80, -1));

        txtSubtotalCompra.setText("$");
        jpnRegistroCompra.add(txtSubtotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 520, 90, -1));

        lblTotalCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalCompra.setForeground(new java.awt.Color(1, 4, 4));
        lblTotalCompra.setText("TOTAL:");
        jpnRegistroCompra.add(lblTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, 50, 40));

        txtNumDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumDocumentoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumDocumentoKeyPressed(evt);
            }
        });
        jpnRegistroCompra.add(txtNumDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 160, -1));

        lbltxtTipoCompra.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        lbltxtTipoCompra.setForeground(new java.awt.Color(1, 13, 17));
        lbltxtTipoCompra.setText("Tipo Compra:");
        jpnRegistroCompra.add(lbltxtTipoCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        lblFechaCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFechaCompra.setForeground(new java.awt.Color(1, 1, 1));
        lblFechaCompra.setText("Fecha:");
        jpnRegistroCompra.add(lblFechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 60, 30));
        jpnRegistroCompra.add(txtFechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 170, -1));

        getContentPane().add(jpnRegistroCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblProductos =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código de Barra", "Nombre", "Costo", "Inventario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jtblProductos);

        jpnProductos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 650, 260));

        btnNuevoProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevoProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoProductoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoProductoMouseEntered(evt);
            }
        });
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 510, 110, 30));

        btnBuscarProducto.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        btnBuscarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarProductoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarProductoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarProductoMouseEntered(evt);
            }
        });
        jpnProductos.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 110, 30));

        btnModificarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProductoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProductoMouseEntered(evt);
            }
        });
        btnModificarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnModificarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, 110, 30));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProductoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProductoMouseEntered(evt);
            }
        });
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jpnProductos.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 510, 110, 30));

        jPanel43.setBackground(new java.awt.Color(0, 0, 0));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel43.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        lblProveedores4.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores4.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores4.setText("Productos");
        jPanel43.add(lblProveedores4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 30));

        jpnProductos.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 50));
        jpnProductos.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 186, 160, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Listado de los Productos:");
        jpnProductos.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        txtProductosBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductosBuscarActionPerformed(evt);
            }
        });
        txtProductosBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductosBuscarKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductosBuscarKeyReleased(evt);
            }
        });
        jpnProductos.add(txtProductosBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 430, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Producto a buscar:");
        jpnProductos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        jpnProductos.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 96, 120, 20));

        getContentPane().add(jpnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnNuevoProducto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarNuevoProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarNuevoProductoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarNuevoProductoMouseEntered(evt);
            }
        });
        btnAgregarNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNuevoProductoActionPerformed(evt);
            }
        });
        jpnNuevoProducto.add(btnAgregarNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 500, 110, 30));

        btnSalirProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnSalirProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalirProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirProductosMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirProductosMouseEntered(evt);
            }
        });
        btnSalirProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirProductosActionPerformed(evt);
            }
        });
        jpnNuevoProducto.add(btnSalirProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 500, 110, 30));

        txtCodBarraProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarraProductosKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarraProductosKeyPressed(evt);
            }
        });
        jpnNuevoProducto.add(txtCodBarraProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 220, 30));

        txtNombreProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductosKeyPressed(evt);
            }
        });
        jpnNuevoProducto.add(txtNombreProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 270, 30));

        txtPrecioProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductosKeyTyped(evt);
            }
        });
        jpnNuevoProducto.add(txtPrecioProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 80, 30));

        jPanel46.setBackground(new java.awt.Color(0, 0, 0));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(240, 240, 240));
        jLabel34.setText("Agregar un nuevo producto:");
        jPanel46.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 30));

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel46.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnNuevoProducto.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Código de barra:");
        jpnNuevoProducto.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, -1, 20));

        jLabel29.setBackground(new java.awt.Color(0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Nombre:");
        jpnNuevoProducto.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, -1, 20));

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Costo:");
        jpnNuevoProducto.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 60, 40));
        jpnNuevoProducto.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 100, 10));
        jpnNuevoProducto.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 315, 70, 20));
        jpnNuevoProducto.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 40, 30));
        jpnNuevoProducto.add(jSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 50, 20));

        getContentPane().add(jpnNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnDetalleCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jpnDetalleCompra.add(txtCodBarraProductos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 230, 30));
        jpnDetalleCompra.add(txtNombreProductos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 390, 30));

        jPanel47.setBackground(new java.awt.Color(0, 0, 0));
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(240, 240, 240));
        jLabel36.setText("Detalle de la Compra:");
        jPanel47.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 30));

        jSeparator28.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel47.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        jpnDetalleCompra.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        jLabel28.setBackground(new java.awt.Color(0, 0, 0));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("ID Compra:");
        jpnDetalleCompra.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, 20));

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Tipo de compra:");
        jpnDetalleCompra.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 30));
        jpnDetalleCompra.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 70, 10));
        jpnDetalleCompra.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 185, 110, 10));

        tblDetalleCompra =new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblDetalleCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Producto", "Producto", "Cantidad", "Costo", "SubTotal"
            }
        ));
        tblDetalleCompra.setEnabled(false);
        tblDetalleCompra.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblDetalleCompra);

        jpnDetalleCompra.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 670, 230));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jpnDetalleCompra.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 232, 750, 10));

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Total:");
        jpnDetalleCompra.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 540, -1, -1));
        jpnDetalleCompra.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 556, 40, -1));

        txtTotalCompraDetalle.setText("$");
        jpnDetalleCompra.add(txtTotalCompraDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, 100, 40));

        btnAtrasDetalleCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnAtrasDetalleCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtrasDetalleCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAtrasDetalleCompraMouseEntered(evt);
            }
        });
        btnAtrasDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasDetalleCompraActionPerformed(evt);
            }
        });
        jpnDetalleCompra.add(btnAtrasDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 535, 110, 30));

        getContentPane().add(jpnDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnAdministracion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel50.setBackground(new java.awt.Color(0, 0, 0));
        jPanel50.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator47.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel50.add(jSeparator47, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 20, 50));

        lblProveedores8.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores8.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores8.setText("administracion");
        jPanel50.add(lblProveedores8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 180, 30));

        jpnAdministracion.add(jPanel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 740, 50));

        tjpnlTipoPrecio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblTipoDePrecio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jscpTipoDePrecio.setViewportView(jtblTipoDePrecio);

        tjpnlTipoPrecio.add(jscpTipoDePrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 160));

        btnNuevoTipoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnNuevoTipoPrecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoTipoPrecioMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoTipoPrecioMouseEntered(evt);
            }
        });
        btnNuevoTipoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoPrecioActionPerformed(evt);
            }
        });
        tjpnlTipoPrecio.add(btnNuevoTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 100, 30));

        btnEliminarTipoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarTipoPrecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarTipoPrecioMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarTipoPrecioMouseEntered(evt);
            }
        });
        btnEliminarTipoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTipoPrecioActionPerformed(evt);
            }
        });
        tjpnlTipoPrecio.add(btnEliminarTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 100, 30));

        jLabel42.setText("Tipos de precio Tienda-ABC");
        tjpnlTipoPrecio.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        jLabel43.setText("IdTipoPrecio:");
        tjpnlTipoPrecio.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel44.setText("Nombre:");
        tjpnlTipoPrecio.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jLabel45.setText("Utilidad:");
        tjpnlTipoPrecio.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        txtIdTipoPrecio.setFocusable(false);
        tjpnlTipoPrecio.add(txtIdTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 250, -1));
        tjpnlTipoPrecio.add(txtNombreTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 250, -1));
        tjpnlTipoPrecio.add(txtUtilidadTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 250, -1));

        btnGuardarTipoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarTipoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTipoPrecioActionPerformed(evt);
            }
        });
        tjpnlTipoPrecio.add(btnGuardarTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 100, 30));

        btnModificarTipoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarTipoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTipoPrecioActionPerformed(evt);
            }
        });
        tjpnlTipoPrecio.add(btnModificarTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 110, 30));

        btnCancelarTipoPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnCancelarTipoPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarTipoPrecioActionPerformed(evt);
            }
        });
        tjpnlTipoPrecio.add(btnCancelarTipoPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 110, 30));
        tjpnlTipoPrecio.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 790, 10));

        tpnlAdministracion.addTab("Tipos de precio", tjpnlTipoPrecio);

        tjpnlParametros.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblParametros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jscpTablaParametros.setViewportView(jtblParametros);

        tjpnlParametros.add(jscpTablaParametros, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 170));

        jLabel1.setText("idParamatro:");
        tjpnlParametros.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));
        tjpnlParametros.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel33.setText("Nombre:");
        tjpnlParametros.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        jLabel40.setText("Parametros de tienda.");
        tjpnlParametros.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

        jLabel41.setText("Parametro:");
        tjpnlParametros.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        txtIdParametro.setEditable(false);
        tjpnlParametros.add(txtIdParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 150, -1));
        tjpnlParametros.add(txtNombreParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 150, -1));
        tjpnlParametros.add(txtParametroParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 150, -1));

        btnGuardarParametro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarParametroActionPerformed(evt);
            }
        });
        tjpnlParametros.add(btnGuardarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 100, 30));

        btnModificarParametro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarParametroActionPerformed(evt);
            }
        });
        tjpnlParametros.add(btnModificarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 100, 30));

        btnCancelarParametro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnCancelarParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarParametroActionPerformed(evt);
            }
        });
        tjpnlParametros.add(btnCancelarParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 100, 30));
        tjpnlParametros.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 790, -1));

        tpnlAdministracion.addTab("Parametros", tjpnlParametros);

        tjpnlSucursales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblSucursales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jscpSucursales.setViewportView(jtblSucursales);

        tjpnlSucursales.add(jscpSucursales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 160));

        btnNuevoSucursales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo3.png"))); // NOI18N
        btnNuevoSucursales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoSucursalesMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoSucursalesMouseEntered(evt);
            }
        });
        btnNuevoSucursales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoSucursalesActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(btnNuevoSucursales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 100, 30));

        btnEliminarSucursales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminarSucursales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarSucursalesMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarSucursalesMouseEntered(evt);
            }
        });
        btnEliminarSucursales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSucursalesActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(btnEliminarSucursales, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 100, 30));
        tjpnlSucursales.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 790, 10));

        jLabel46.setText("Sucursales Tienda-ABC");
        tjpnlSucursales.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, -1, -1));

        jLabel47.setText("IdSucursal:");
        tjpnlSucursales.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel48.setText("Nombre:");
        tjpnlSucursales.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel49.setText("Direccion:");
        tjpnlSucursales.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel50.setText("Telefono:");
        tjpnlSucursales.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        btnGuardarSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardarprov.png"))); // NOI18N
        btnGuardarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSucursalActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(btnGuardarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 110, 40));

        btnModificarSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btnModificarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarSucursalActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(btnModificarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 100, 30));

        btnCancelarSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnCancelarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarSucursalActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(btnCancelarSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 100, 40));
        tjpnlSucursales.add(txtIdSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 350, -1));
        tjpnlSucursales.add(txtNombreSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 350, -1));

        txtDireccionSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionSucursalActionPerformed(evt);
            }
        });
        tjpnlSucursales.add(txtDireccionSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 350, -1));
        tjpnlSucursales.add(txtTelefonoSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, 350, -1));

        tpnlAdministracion.addTab("Sucursales", tjpnlSucursales);

        jpnAdministracion.add(tpnlAdministracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 720, 520));

        getContentPane().add(jpnAdministracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 650));

        jpnRegistrarVenta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel49.setBackground(new java.awt.Color(0, 0, 0));
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores7.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores7.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores7.setText("Registrar Ventas");
        jPanel49.add(lblProveedores7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 190, 30));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Código");
        jPanel49.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 100, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Documento");
        jPanel49.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 100, -1));

        txtIdVenta.setEditable(false);
        txtIdVenta.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtIdVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jPanel49.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 100, 30));

        lblFechaVentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFechaVentas.setForeground(new java.awt.Color(255, 255, 255));
        lblFechaVentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaVentas.setText("Fecha");
        jPanel49.add(lblFechaVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 80, -1));

        lblFechaVentaMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblFechaVentaMostrarActionPerformed(evt);
            }
        });
        jPanel49.add(lblFechaVentaMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 80, 30));

        txtNuDocumentoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuDocumentoVentaKeyTyped(evt);
            }
        });
        jPanel49.add(txtNuDocumentoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 100, 30));

        jpnRegistrarVenta.add(jPanel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 100));

        jSeparator44.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator44.setForeground(new java.awt.Color(0, 0, 0));
        jpnRegistrarVenta.add(jSeparator44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 750, 10));

        tblRegistrarVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblRegistrarVenta.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblRegistrarVenta);

        jpnRegistrarVenta.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 710, 230));

        btnVender.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/vender.png"))); // NOI18N
        btnVender.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVenderMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVenderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVenderMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnVenderMousePressed(evt);
            }
        });
        btnVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVenderActionPerformed(evt);
            }
        });
        jpnRegistrarVenta.add(btnVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 540, 110, 30));

        txtNombreProductoVender.setEditable(false);
        txtNombreProductoVender.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnRegistrarVenta.add(txtNombreProductoVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 300, 40));

        txtCantidadVender.setText("1");
        txtCantidadVender.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCantidadVender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVenderActionPerformed(evt);
            }
        });
        txtCantidadVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVenderKeyTyped(evt);
            }
        });
        jpnRegistrarVenta.add(txtCantidadVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 80, 40));

        btnAgregarProductoVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregarProductoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar2.png"))); // NOI18N
        btnAgregarProductoVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarProductoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarProductoVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarProductoVentaMouseExited(evt);
            }
        });
        btnAgregarProductoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoVentaActionPerformed(evt);
            }
        });
        jpnRegistrarVenta.add(btnAgregarProductoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 110, 40));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("Producto");
        jpnRegistrarVenta.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, -1, 10));

        txtCodigoBarraVender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoBarraVenderKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoBarraVenderKeyPressed(evt);
            }
        });
        jpnRegistrarVenta.add(txtCodigoBarraVender, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 120, 40));

        lblNRCventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNRCventa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNRCventa.setText("NRC");
        jpnRegistrarVenta.add(lblNRCventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(655, 110, 70, -1));

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setText("Código de Barra");
        jpnRegistrarVenta.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 10));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Cantidad");
        jpnRegistrarVenta.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, -1, 10));

        lblIVA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIVA.setText("IVA");
        jpnRegistrarVenta.add(lblIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 540, -1, -1));
        jpnRegistrarVenta.add(txtIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 540, 90, -1));
        jpnRegistrarVenta.add(txtSumas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 510, 90, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("TOTAL");
        jpnRegistrarVenta.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, -1, -1));

        txtTotalventaGravado.setEditable(false);
        txtTotalventaGravado.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalventaGravado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnRegistrarVenta.add(txtTotalventaGravado, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, 90, 20));
        jpnRegistrarVenta.add(txtClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 250, 30));

        txtNRCventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNRCventaActionPerformed(evt);
            }
        });
        txtNRCventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNRCventaKeyTyped(evt);
            }
        });
        jpnRegistrarVenta.add(txtNRCventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 80, 30));

        txtNITventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNITventaKeyTyped(evt);
            }
        });
        jpnRegistrarVenta.add(txtNITventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 80, 30));

        lblCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCliente.setText("Cliente");
        jpnRegistrarVenta.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 110, 250, -1));

        lblNITventa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNITventa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNITventa.setText("NIT");
        jpnRegistrarVenta.add(lblNITventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 70, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("SUMAS");
        jpnRegistrarVenta.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 510, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDireccion.setText("Direccion");
        jpnRegistrarVenta.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 110, 220, -1));
        jpnRegistrarVenta.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 230, 30));
        jpnRegistrarVenta.add(txtGiroVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 90, 30));

        lblGiroVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblGiroVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGiroVenta.setText("Giro");
        jpnRegistrarVenta.add(lblGiroVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 90, -1));

        btnRegresarPaeametroVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/atras.png"))); // NOI18N
        btnRegresarPaeametroVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarPaeametroVentasActionPerformed(evt);
            }
        });
        jpnRegistrarVenta.add(btnRegresarPaeametroVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 110, 40));

        getContentPane().add(jpnRegistrarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnReporteVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jpnReporteVentas.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 750, 10));

        tblReporteVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblReporteVentas.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblReporteVentas);

        jpnReporteVentas.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 740, 190));

        jPanel51.setBackground(new java.awt.Color(0, 0, 0));
        jPanel51.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores9.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores9.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores9.setText("Reporte de ventas por mes");
        jPanel51.add(lblProveedores9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 260, 50));

        jpnReporteVentas.add(jPanel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 70));

        lblIVA1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIVA1.setText("13% impuestos");
        jpnReporteVentas.add(lblIVA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 430, -1, -1));
        jpnReporteVentas.add(txtImpuestosVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 430, 90, -1));
        jpnReporteVentas.add(txtVentasNetas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 90, -1));

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setText("Total Ventas Gravadas");
        jpnReporteVentas.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 460, -1, -1));

        txtVentasGravadas.setEditable(false);
        txtVentasGravadas.setBackground(new java.awt.Color(255, 255, 255));
        txtVentasGravadas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpnReporteVentas.add(txtVentasGravadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 460, 90, 20));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel64.setText("Ventas netas gravadas locales");
        jpnReporteVentas.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, -1, -1));

        getContentPane().add(jpnReporteVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnUtilidadMenuVentasParametros.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel52.setBackground(new java.awt.Color(0, 0, 0));
        jPanel52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores11.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores11.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores11.setText("Parámetros ventas");
        jPanel52.add(lblProveedores11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 180, 50));

        jpnUtilidadMenuVentasParametros.add(jPanel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 70));

        cmbTipoFacturaParametro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FACTURA", "CREDITO FISCAL", "LIBRE" }));
        cmbTipoFacturaParametro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoFacturaParametroItemStateChanged(evt);
            }
        });
        jpnUtilidadMenuVentasParametros.add(cmbTipoFacturaParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 200, 40));

        cmbSucursalParametro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSucursalParametroItemStateChanged(evt);
            }
        });
        jpnUtilidadMenuVentasParametros.add(cmbSucursalParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 200, 40));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Porcentaje");
        jpnUtilidadMenuVentasParametros.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 80, 40));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Tipo factura");
        jpnUtilidadMenuVentasParametros.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 200, 40));

        Utilidad1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Utilidad1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Utilidad1.setText("Utilidad");
        jpnUtilidadMenuVentasParametros.add(Utilidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 200, 40));

        cmbUtilidadParametro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbUtilidadParametroItemStateChanged(evt);
            }
        });
        jpnUtilidadMenuVentasParametros.add(cmbUtilidadParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 200, 40));

        btnHacerNuevaVenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnHacerNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/carrito48.png"))); // NOI18N
        btnHacerNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHacerNuevaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHacerNuevaVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHacerNuevaVentaMouseExited(evt);
            }
        });
        btnHacerNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHacerNuevaVentaActionPerformed(evt);
            }
        });
        jpnUtilidadMenuVentasParametros.add(btnHacerNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 480, 50, 40));

        txtUtilidadVentaParametro.setEditable(false);
        jpnUtilidadMenuVentasParametros.add(txtUtilidadVentaParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 390, 60, 40));

        txtSucursalVentaParametro.setEditable(false);
        jpnUtilidadMenuVentasParametros.add(txtSucursalVentaParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 70, 40));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Sucursal");
        jpnUtilidadMenuVentasParametros.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 200, 40));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Código sucursal");
        jpnUtilidadMenuVentasParametros.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 110, 40));

        getContentPane().add(jpnUtilidadMenuVentasParametros, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnVentasReporteParametro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel54.setBackground(new java.awt.Color(0, 0, 0));
        jPanel54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores13.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores13.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores13.setText("Mes");
        jPanel54.add(lblProveedores13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 150, 50));

        jpnVentasReporteParametro.add(jPanel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 70));

        cmbFechasVenta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" }));
        jpnVentasReporteParametro.add(cmbFechasVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 120, 40));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Selecciones mes");
        jpnVentasReporteParametro.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 120, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Ver reporte");
        jpnVentasReporteParametro.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 110, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar48.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jpnVentasReporteParametro.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, 110, 40));

        getContentPane().add(jpnVentasReporteParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        jpnVerVentasporSucursal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel53.setBackground(new java.awt.Color(0, 0, 0));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProveedores12.setBackground(new java.awt.Color(255, 255, 255));
        lblProveedores12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblProveedores12.setForeground(new java.awt.Color(255, 255, 255));
        lblProveedores12.setText("Ventas efectuadas");
        jPanel53.add(lblProveedores12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 190, 50));

        jpnVerVentasporSucursal.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 70));

        tblMenuVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblMenuVentas.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tblMenuVentas);

        jpnVerVentasporSucursal.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 610, 230));

        lblSucursalMenuVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSucursalMenuVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSucursalMenuVenta.setText("Sucursal");
        jpnVerVentasporSucursal.add(lblSucursalMenuVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 180, -1));

        cmbSucursalReporteVenta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSucursalReporteVentaItemStateChanged(evt);
            }
        });
        jpnVerVentasporSucursal.add(cmbSucursalReporteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 180, -1));

        getContentPane().add(jpnVerVentasporSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 730, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblBotonCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBotonCerrarMouseClicked
       System.exit(0);
    }//GEN-LAST:event_lblBotonCerrarMouseClicked

    /*  ---- Animaciones de los botones del menú ----  */
    private void btnComprasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseEntered
    /*  ---- Animación compras, mover ----  */
        if(!compras)
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnCompras);
        Principal(false);
        Compras(true);
    }//GEN-LAST:event_btnComprasMouseEntered

    private void btnComprasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseExited
    /*  ---- Animación compras, volver posición anterior ----  */
        if(!compras)
        Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnCompras);
        Principal(true);
        Compras(false);
    }//GEN-LAST:event_btnComprasMouseExited

    private void btnVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseEntered
        if(!ventas)
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnVentas);
            Principal(false);
            Ventas(true);                            
    }//GEN-LAST:event_btnVentasMouseEntered

    private void btnVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseExited
        if(!ventas)
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnVentas);  
            Principal(true);
            Ventas(false); 
    }//GEN-LAST:event_btnVentasMouseExited

    private void btnProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseEntered
        if(!productos)
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProductos);   
            Principal(false);
            Productos(true);
    }//GEN-LAST:event_btnProductosMouseEntered

    private void btnProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseExited
        if(!productos)
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnProductos); 
            Principal(true);
            Productos(false);
    }//GEN-LAST:event_btnProductosMouseExited

    private void btnAdministacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdministacionMouseEntered
        if(!administradores)
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnAdministacion);   
            Principal(true);
           // Proveedores(true);
    }//GEN-LAST:event_btnAdministacionMouseEntered

    private void btnAdministacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdministacionMouseExited
        if(!administradores)
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnAdministacion); 
            Principal(true);
            Proveedores(false);         
    }//GEN-LAST:event_btnAdministacionMouseExited

    private void btnAdministacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdministacionMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnAdministacion);  
        apagado2();
        jpnAdministracion.setVisible(true); 
        try {
            llenarTipoPrecio();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No logro poner el modelo TP");
        }
        try {
            llenarSucursal();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No logro poner el modelo S");
        }
        try {
            llenarParametro();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No logro poner el modelo Parametro");
        }
    }//GEN-LAST:event_btnAdministacionMouseClicked

    /*  ---- Acción de botones, cambiar de pantallas (Paneles) ----  */
    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        apagado2();
        apagado = false;
        jpnPrincipal.setVisible(true);
        Principal(true);
        Compras(false);
        Ventas(false);
        Productos(false);
        Proveedores(false); 
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnAgregarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseClicked
        jpnProveedores.setVisible(false);
        jpnAgregarProv.setVisible(true);
    }//GEN-LAST:event_btnAgregarProveedorMouseClicked

    private void btnAtrasProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseClicked
        jpnAgregarProv.setVisible(false);
        jpnProveedores.setVisible(true);
    }//GEN-LAST:event_btnAtrasProveedoresMouseClicked

    private void btnVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentasMouseClicked
              apagado();      
              apagado2();
        jpnMenuVentas.setVisible(true);                
    }//GEN-LAST:event_btnVentasMouseClicked

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        
        modificarProducto=false;
        jpnProductos.setVisible(false);
        jpnNuevoProducto.setVisible(true);
        txtCodBarraProductos.setText("");
        txtNombreProductos.setText("");
        txtPrecioProductos.setText("");
        
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnSalirProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirProductosActionPerformed
            jpnNuevoProducto.setVisible(false);
            jpnProductos.setVisible(true);
             txtCodBarraProductos.setText("");
        txtNombreProductos.setText("");
        txtPrecioProductos.setText("");
    }//GEN-LAST:event_btnSalirProductosActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProductos);  
        apagado2();
        jpnProductos.setVisible(true);
         try {
            llenarProducto();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No logro poner el modelo producto");
        }
    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnAgregarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCompraActionPerformed
        cargarProveedoresC=true;
        cargarSucursalesC = true;
        cmbSucursalCompra.requestFocus(); 
        //Aca escribo para elegir antes el tipo de compra
        //Daniel-Inicio
        String[] list = {"Factura", "Crédito fiscal", "Libre"};
        JComboBox jcb = new JComboBox(list);
        jcb.setEditable(false);
        JOptionPane.showMessageDialog( null, jcb, "Selecciona el tipo de compra", JOptionPane.QUESTION_MESSAGE);
        int tipo= jcb.getSelectedIndex();
        
        switch(tipo){
            
            case 0:
                tipoCompra = "F" ;
                bloquearIvaPercepcion();
                lbltxtTipoCompra.setText("Factura N°");
                break;
            
            case 1:
                tipoCompra = "C" ;
                lblPercepcionCompra.setVisible(true);
                lblIvaCompra.setVisible(true);
                txtIvaCompra.setVisible(true);
                txtPercepcionCompra.setVisible(true);
                lblSubtotalCompra.setVisible(true);
                txtSubtotalCompra.setVisible(true);
                lbltxtTipoCompra.setText("Crédito Fiscal N°");
                break;
                
            case 2: 
                tipoCompra = "L" ;
                bloquearIvaPercepcion();
                lbltxtTipoCompra.setText("Libre N°");
                break;
        }
        //Daniel-Fin        
        jpnRegistroCompra.setVisible(true);
        jpnCompras.setVisible(false);
        
        //Para autogenerar el id de la compra
        ControladorCompra cp = new ControladorCompra();
        try {
            rsMayorIdC = cp.mayorRegistro();
            if(rsMayorIdC.next()) {
            txtIdCompra.setText(""+(rsMayorIdC.getInt(1)+1));
}
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        limpiarTablaCompra();
        //-----------------------------
        //Para poner la fecha
        dia = calendar.get(Calendar.DATE);
        mes = calendar.get(Calendar.MONTH)+1;
        anio = calendar.get(Calendar.YEAR);
        txtFechaCompra.setText(anio+"/"+mes+"/"+dia);
        //------------------
        
        
    }//GEN-LAST:event_btnAgregarCompraActionPerformed
    
    
    public void bloquearIvaPercepcion(){//Solo para la interfaz de Compra-Factura
            lblPercepcionCompra.setVisible(false);
            lblSubtotalCompra.setVisible(false);
            txtSubtotalCompra.setVisible(false);
            lblIvaCompra.setVisible(false);
            txtIvaCompra.setVisible(false);
            txtPercepcionCompra.setVisible(false);
            
        } 
    private void btnVerDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseClicked
        jpnDetalleCompra.setVisible(true);
        jpnCompras.setVisible(false);
    }//GEN-LAST:event_btnVerDetalleMouseClicked

    private void btnCancelarCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarCompraMouseClicked
        jpnCompras.setVisible(true);
        jpnRegistroCompra.setVisible(false);
    }//GEN-LAST:event_btnCancelarCompraMouseClicked

    private void btnComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComprasMouseClicked
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnCompras);  
        apagado2();
        jpnCompras.setVisible(true);
        btnVerDetalle.setEnabled(false);
        cargarSucursalesFC = true;
        cmbFiltroSucursalCompra.requestFocus();
    }//GEN-LAST:event_btnComprasMouseClicked

    /*  ---- Mover barra ----  */
    private void jpnBarraSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnBarraSuperiorMousePressed
        x = evt.getX(); 
        y = evt.getY();
    }//GEN-LAST:event_jpnBarraSuperiorMousePressed

    private void jpnBarraSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnBarraSuperiorMouseDragged
         this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jpnBarraSuperiorMouseDragged

    private void btnAtrasDetalleCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseClicked
        jpnDetalleCompra.setVisible(false);
        jpnCompras.setVisible(true);
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseClicked

    /*  ---- Cambio de color a los botones ----  */
    private void btnAgregarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseEntered
        // Cambio del botón Agregar Proveedor a negro:
        btnAgregarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarprovB.png")));
    }//GEN-LAST:event_btnAgregarProveedorMouseEntered

    private void btnAgregarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProveedorMouseExited
        // Cambio del botón Agregar Proveedor a blanco:
        btnAgregarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarprov.png")));
    }//GEN-LAST:event_btnAgregarProveedorMouseExited

    private void btnModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseEntered
        // Cambio del botón Modificar Proveedor a negro:
        btnModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/modificarB.png")));
    }//GEN-LAST:event_btnModificarProveedorMouseEntered

    private void btnModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseExited
        // Cambio del botón Modificar Proveedor a blanco:
        btnModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/modificar.png")));
    }//GEN-LAST:event_btnModificarProveedorMouseExited

    private void btnEliminarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedorMouseEntered
        btnEliminarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarProveedorMouseEntered

    private void btnEliminarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedorMouseExited
        btnEliminarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarProveedorMouseExited

    private void btnAtrasProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseEntered
        btnAtrasProveedores.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasProveedoresMouseEntered

    private void btnAtrasProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresMouseExited
        btnAtrasProveedores.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasProveedoresMouseExited

    private void btnGuardarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProveedorMouseEntered
        btnGuardarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprovB.png")));
    }//GEN-LAST:event_btnGuardarProveedorMouseEntered

    private void btnGuardarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProveedorMouseExited
        btnGuardarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprov.png")));
    }//GEN-LAST:event_btnGuardarProveedorMouseExited

    private void btnCancelarCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarCompraMouseEntered
        btnCancelarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnCancelarCompraMouseEntered

    private void btnCancelarCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarCompraMouseExited
        btnCancelarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnCancelarCompraMouseExited

    private void btnGuardarCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCompraMouseEntered
        btnGuardarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprovB.png")));
    }//GEN-LAST:event_btnGuardarCompraMouseEntered

    private void btnGuardarCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCompraMouseExited
        btnGuardarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprov.png")));
    }//GEN-LAST:event_btnGuardarCompraMouseExited

    private void btnNuevoProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoProductoMouseEntered
        btnNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3B.png")));
    }//GEN-LAST:event_btnNuevoProductoMouseEntered

    private void btnNuevoProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoProductoMouseExited
        btnNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3.png")));
    }//GEN-LAST:event_btnNuevoProductoMouseExited

    private void btnBuscarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProductoMouseEntered
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/buscarB.png")));
    }//GEN-LAST:event_btnBuscarProductoMouseEntered

    private void btnBuscarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProductoMouseExited
        btnBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/buscar.png")));
    }//GEN-LAST:event_btnBuscarProductoMouseExited

    private void btnModificarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductoMouseEntered
        btnModificarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/modificarB.png")));
    }//GEN-LAST:event_btnModificarProductoMouseEntered

    private void btnModificarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductoMouseExited
        btnModificarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/modificar.png")));
    }//GEN-LAST:event_btnModificarProductoMouseExited

    private void btnEliminarProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoMouseEntered
        btnEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarProductoMouseEntered

    private void btnEliminarProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoMouseExited
        btnEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarProductoMouseExited

    private void btnSalirProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirProductosMouseEntered
        btnSalirProductos.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnSalirProductosMouseEntered

    private void btnSalirProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirProductosMouseExited
        btnSalirProductos.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnSalirProductosMouseExited

    private void btnAgregarNuevoProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoMouseEntered
        btnAgregarNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/agregarB.png")));
    }//GEN-LAST:event_btnAgregarNuevoProductoMouseEntered

    private void btnAgregarNuevoProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoMouseExited
        btnAgregarNuevoProducto.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar.png")));
    }//GEN-LAST:event_btnAgregarNuevoProductoMouseExited

    private void btnAgregarCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCompraMouseEntered
        btnAgregarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2B.png")));
    }//GEN-LAST:event_btnAgregarCompraMouseEntered

    private void btnAgregarCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCompraMouseExited
        btnAgregarCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar2.png")));
    }//GEN-LAST:event_btnAgregarCompraMouseExited

    private void btnVerDetalleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseEntered
        btnVerDetalle.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2B.png")));
    }//GEN-LAST:event_btnVerDetalleMouseEntered

    private void btnVerDetalleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerDetalleMouseExited
        btnVerDetalle.setIcon(new ImageIcon(getClass().getResource("/iconos/detalles2.png")));
    }//GEN-LAST:event_btnVerDetalleMouseExited

    private void btnAtrasDetalleCompraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseEntered
        btnAtrasDetalleCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseEntered

    private void btnAtrasDetalleCompraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraMouseExited
        btnAtrasDetalleCompra.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasDetalleCompraMouseExited

    private void btnGuardarModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorMouseEntered
        btnGuardarModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprovB.png")));
    }//GEN-LAST:event_btnGuardarModificarProveedorMouseEntered

    private void btnGuardarModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorMouseExited
        btnGuardarModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/guardarprov.png")));
    }//GEN-LAST:event_btnGuardarModificarProveedorMouseExited

    private void btnAtrasModificarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseClicked
        jpnModificarProveedor.setVisible(false);
        jpnProveedores.setVisible(true);
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseClicked

    private void btnAtrasModificarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseEntered
        btnAtrasModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/atrasB.png")));
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseEntered

    private void btnAtrasModificarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorMouseExited
        btnAtrasModificarProveedor.setIcon(new ImageIcon(getClass().getResource("/iconos/atras.png")));
    }//GEN-LAST:event_btnAtrasModificarProveedorMouseExited

    private void btnModificarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedorMouseClicked
        jpnProveedores.setVisible(false);
        jpnModificarProveedor.setVisible(true);
    }//GEN-LAST:event_btnModificarProveedorMouseClicked

    private void btnProveedores1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedores1MouseClicked
                                                    
        apagado();
        Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProveedores1);  
        apagado2();
        jpnProveedores.setVisible(true); 
    }//GEN-LAST:event_btnProveedores1MouseClicked

    private void btnProveedores1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedores1MouseEntered
                                                      
        if(!proveedores)
            Animacion.Animacion.mover_derecha(-126, 0, 1, 2, btnProveedores1);   
            Principal(false);
            Proveedores(true);
                  

    
    }//GEN-LAST:event_btnProveedores1MouseEntered

    private void btnProveedores1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProveedores1MouseExited
       
                                                
        if(!proveedores)
            Animacion.Animacion.mover_izquierda(0, -126, 1, 2, btnProveedores1); 
            Principal(true);
            Proveedores(false);         
                                 
    }//GEN-LAST:event_btnProveedores1MouseExited

    private void btnProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedores1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProveedores1ActionPerformed
             

    private void txtProductosBuscar1KeyTyped(java.awt.event.KeyEvent evt) {                                             
        char c=evt.getKeyChar();
       if(Character.isDigit(c)){
       
       
       }else{
       evt.consume();
       }     
            
    }                                            

    private void btnEliminarTipoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTipoPrecioActionPerformed
        ControladorTipoPrecio cpp= new ControladorTipoPrecio();
        TableModel tableModel = jtblProductos.getModel();
    Object mostrar=modeloTipoPrecio.getValueAt(jtblTipoDePrecio.getSelectedRow(), 0);     
//    Object P[]={mostrar};
    
int decide=JOptionPane.showConfirmDialog(null, "Desea borrar el tipo precio:" +modeloTipoPrecio.getValueAt(jtblTipoDePrecio.getSelectedRow(), 1));


if(decide==0){
 try {
               cpp.EliminarTipoPrecio(mostrar);
               JOptionPane.showMessageDialog(null,"Se elimino el Tipo Precio" +mostrar);
               limpiarTablaProducto();
           } catch (SQLException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ErrorTienda ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }    catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

}else{
}
        try {
            llenarTipoPrecio();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarTipoPrecioActionPerformed

    private void btnNuevoTipoPrecioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoTipoPrecioMouseEntered
         btnNuevoTipoPrecio.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3B.png")));
    }//GEN-LAST:event_btnNuevoTipoPrecioMouseEntered

    private void btnNuevoTipoPrecioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoTipoPrecioMouseExited
        btnNuevoTipoPrecio.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3.png")));
    }//GEN-LAST:event_btnNuevoTipoPrecioMouseExited

    private void btnEliminarTipoPrecioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTipoPrecioMouseEntered
        btnEliminarTipoPrecio.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarTipoPrecioMouseEntered

    private void btnEliminarTipoPrecioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTipoPrecioMouseExited
        btnEliminarTipoPrecio.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarTipoPrecioMouseExited

    private void btnNuevoSucursalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoSucursalesMouseEntered
         btnNuevoSucursales.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3B.png")));
    }//GEN-LAST:event_btnNuevoSucursalesMouseEntered

    private void btnNuevoSucursalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoSucursalesMouseExited
        btnNuevoSucursales.setIcon(new ImageIcon(getClass().getResource("/iconos/nuevo3.png")));
    }//GEN-LAST:event_btnNuevoSucursalesMouseExited

    private void btnEliminarSucursalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarSucursalesMouseEntered
        btnEliminarSucursales.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminarB.png")));
    }//GEN-LAST:event_btnEliminarSucursalesMouseEntered

    private void btnEliminarSucursalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarSucursalesMouseExited
       btnEliminarSucursales.setIcon(new ImageIcon(getClass().getResource("/iconos/eliminar.png")));
    }//GEN-LAST:event_btnEliminarSucursalesMouseExited

    private void cmbProveedorCompraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProveedorCompraItemStateChanged
    int posicionProv=cmbProveedorCompra.getSelectedIndex();
    txtNumDocumento.requestFocus();

    }//GEN-LAST:event_cmbProveedorCompraItemStateChanged

    private void cmbProveedorCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbProveedorCompraFocusGained
        if (cargarProveedoresC==true){
        txtNumDocumento.requestFocus();
        ControladorProveedor p = new ControladorProveedor();
        modeloProveedorC.removeAllElements();
        
                 //Llenando el cmbCargos mediante un modelo
            try{
            rsProveedorC = p.Obtener();
            while (rsProveedorC.next()) {
                modeloProveedorC.addElement(rsProveedorC.getString(2));
                
            }
            cmbProveedorCompra.setModel(modeloProveedorC);
           // lblCargo.setText(String.valueOf(modeloCargos.getElementAt(0)));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        cargarProveedoresC=false;    
   
        //}
    }//GEN-LAST:event_cmbProveedorCompraFocusGained

    private void cmbSucursalCompraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSucursalCompraItemStateChanged
        int posicionSucur=cmbSucursalCompra.getSelectedIndex();
                txtNumDocumento.requestFocus();

    }//GEN-LAST:event_cmbSucursalCompraItemStateChanged

    private void cmbSucursalCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbSucursalCompraFocusGained
       if (cargarSucursalesC==true){
       cmbProveedorCompra.requestFocus(); 
       ControladorSucursal p = new ControladorSucursal();
       modeloSucursalC.removeAllElements();
        
        //Llenando el cmbCargos mediante un modelo
            try{
            rsSucursalC = p.Obtener();
            while (rsSucursalC.next()) {
                modeloSucursalC.addElement(rsSucursalC.getString(2));
                
            }
            cmbSucursalCompra.setModel(modeloSucursalC);
           // lblCargo.setText(String.valueOf(modeloCargos.getElementAt(0)));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }  catch (Exception ex) {  
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }  
       }  
        cargarSucursalesC=false;
    }//GEN-LAST:event_cmbSucursalCompraFocusGained

    private void txtTotalCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCompraActionPerformed

    private void btnCancelarTipoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarTipoPrecioActionPerformed
modificarTipoPrecio=false;
      txtIdTipoPrecio.setEditable(false);
            txtNombreTipoPrecio.setEditable(false);
            txtUtilidadTipoPrecio.setEditable(false); 
            txtIdTipoPrecio.setText("");
            txtNombreTipoPrecio.setText("");
            txtUtilidadTipoPrecio.setText("");
    }//GEN-LAST:event_btnCancelarTipoPrecioActionPerformed

    private void txtDireccionSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionSucursalActionPerformed

    private void btnModificarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarSucursalActionPerformed
       ControladorTipoPrecio cp= new ControladorTipoPrecio();
               modificarSucursal=true;
                String id = modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 0).toString();
                String nombre = modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 1).toString();
                String direccion= modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 2).toString();
                String telefono= modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 3).toString();
                
             txtIdSucursal.setEditable(true);
            txtNombreSucursal.setEditable(true);
            txtDireccionSucursal.setEditable(true);
            txtTelefonoSucursal.setEditable(true);   
            txtIdSucursal.setText(""+id);
            txtNombreSucursal.setText(""+nombre);
            txtDireccionSucursal.setText(""+direccion); 
            txtTelefonoSucursal.setText(""+telefono); 
            
    }//GEN-LAST:event_btnModificarSucursalActionPerformed

    private void btnModificarTipoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTipoPrecioActionPerformed
ControladorTipoPrecio cp= new ControladorTipoPrecio();
               modificarTipoPrecio=true;
                String id = modeloTipoPrecio.getValueAt(jtblTipoDePrecio.getSelectedRow(), 0).toString();
                String nombre = modeloTipoPrecio.getValueAt(jtblTipoDePrecio.getSelectedRow(), 1).toString();
                String utilidad= modeloTipoPrecio.getValueAt(jtblTipoDePrecio.getSelectedRow(), 2).toString();
                txtIdTipoPrecio.setEditable(true);
            txtNombreTipoPrecio.setEditable(true);
            txtUtilidadTipoPrecio.setEditable(true);
            txtIdTipoPrecio.setText(""+id);
            txtNombreTipoPrecio.setText(""+nombre);
            txtUtilidadTipoPrecio.setText(""+utilidad);       
    }//GEN-LAST:event_btnModificarTipoPrecioActionPerformed

    private void btnModificarParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarParametroActionPerformed
        ControladorTipoPrecio cp= new ControladorTipoPrecio();
               modificarParametro=true;
                String id = modeloParametro.getValueAt(jtblParametros.getSelectedRow(), 0).toString();
                String nombre = modeloParametro.getValueAt(jtblParametros.getSelectedRow(), 1).toString();
                String valor= modeloParametro.getValueAt(jtblParametros.getSelectedRow(), 2).toString();
               txtIdParametro.setEditable(true);
            txtNombreParametro.setEditable(true);
            txtParametroParametro.setEditable(true); 
            txtIdParametro.setText(""+id);
            txtNombreParametro.setText(""+nombre);
            txtParametroParametro.setText(""+valor);
    }//GEN-LAST:event_btnModificarParametroActionPerformed

    private void btnNuevoTipoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoPrecioActionPerformed
       modificarTipoPrecio=false;
       txtIdTipoPrecio.setEditable(true);
            txtNombreTipoPrecio.setEditable(true);
            txtUtilidadTipoPrecio.setEditable(true);
    }//GEN-LAST:event_btnNuevoTipoPrecioActionPerformed

    private void btnGuardarTipoPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTipoPrecioActionPerformed
        ControladorTipoPrecio ctp= new ControladorTipoPrecio();

                String id = txtIdTipoPrecio.getText();
                String nombre = txtNombreTipoPrecio.getText();
                Double utilidad= Double.parseDouble(txtUtilidadTipoPrecio.getText());
                
                Object P[]={id,nombre,utilidad};
        
         
            if(modificarTipoPrecio==true){
                try {
                    ctp.ModificarTipoPrecio(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtIdTipoPrecio.setText("");
            txtNombreTipoPrecio.setText("");
            txtUtilidadTipoPrecio.setText("");
            JOptionPane.showMessageDialog(null, "modificado con exito");
            
            
            
            
            }else{
                try {
                    ctp.AgregarTipoPrecio(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtIdTipoPrecio.setText("");
            txtNombreTipoPrecio.setText("");
            txtUtilidadTipoPrecio.setText("");
            JOptionPane.showMessageDialog(null, "agregado con exito");
            
            
            }
             
             
            try {
                llenarTipoPrecio();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
      modificarTipoPrecio=false;
      txtIdTipoPrecio.setEditable(false);
            txtNombreTipoPrecio.setEditable(false);
            txtUtilidadTipoPrecio.setEditable(false);
    }//GEN-LAST:event_btnGuardarTipoPrecioActionPerformed

    private void btnGuardarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSucursalActionPerformed
        ControladorSucursal ctp= new ControladorSucursal();

                String id = txtIdSucursal.getText();
                String nombre = txtNombreSucursal.getText();
                String direccion = txtDireccionSucursal.getText();
                String telefono = txtTelefonoSucursal.getText();
               
                
                Object P[]={id,nombre,direccion,telefono};
        
         
            if(modificarSucursal==true){
                try {
                    ctp.ModificarSucursal(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtIdSucursal.setText("");
            txtNombreSucursal.setText("");
            txtDireccionSucursal.setText("");
            txtTelefonoSucursal.setText("");
            JOptionPane.showMessageDialog(null, "sucursal modificada con exito");
            
            
            
            
            }else{
                try {
                    ctp.AgregarSucursal(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtIdSucursal.setText("");
            txtNombreSucursal.setText("");
            txtDireccionSucursal.setText("");
            txtTelefonoSucursal.setText("");
            JOptionPane.showMessageDialog(null, "sucursal agregada con exito");
            
            
            }
             
             
            try {
                llenarSucursal();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
      modificarSucursal=false;
            txtIdSucursal.setEditable(false);
            txtNombreSucursal.setEditable(false);
            txtDireccionSucursal.setEditable(false);
            txtTelefonoSucursal.setEditable(false);
    }//GEN-LAST:event_btnGuardarSucursalActionPerformed

    private void btnNuevoSucursalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoSucursalesActionPerformed
        modificarSucursal=false;
         
            txtNombreSucursal.setEditable(true);
            txtDireccionSucursal.setEditable(true);
            txtTelefonoSucursal.setEditable(true);
    }//GEN-LAST:event_btnNuevoSucursalesActionPerformed

    private void btnEliminarSucursalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSucursalesActionPerformed
      ControladorSucursal cpp= new ControladorSucursal();
        TableModel tableModel = jtblProductos.getModel();
    Object mostrar=modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 0);     
//    Object P[]={mostrar};
    
int decide=JOptionPane.showConfirmDialog(null, "Desea borrar al sucursal" +modeloSucursal.getValueAt(jtblSucursales.getSelectedRow(), 1));


if(decide==0){
 try {
               cpp.EliminarSucursal(mostrar);
               JOptionPane.showMessageDialog(null,"Se elimino la sucursal" +mostrar);
               limpiarTablaProducto();
           } catch (SQLException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ErrorTienda ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }    catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

}else{
}
        try {
            llenarSucursal();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }//GEN-LAST:event_btnEliminarSucursalesActionPerformed

    private void btnGuardarParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarParametroActionPerformed
        Parametro p= new Parametro();

                String id = txtIdParametro.getText();
                String nombre = txtNombreParametro.getText();
                String valor=txtParametroParametro.getText();
                
                Object P[]={id,nombre,valor};
        
         
            if(modificarParametro==true){
                try {
                    p.Modificar(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtIdParametro.setText("");
            txtNombreParametro.setText("");
            txtParametroParametro.setText("");
            JOptionPane.showMessageDialog(null, "modificado con exito");
            
            
            }
             
             
            try {
                llenarParametro();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
             modificarParametro=false;
          txtIdParametro.setEditable(false);
            txtNombreParametro.setEditable(false);
            txtParametroParametro.setEditable(false);
    }//GEN-LAST:event_btnGuardarParametroActionPerformed

    private void btnCancelarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarSucursalActionPerformed
            txtIdSucursal.setEditable(false);
            txtNombreSucursal.setEditable(false);
            txtDireccionSucursal.setEditable(false);
            txtTelefonoSucursal.setEditable(false);
            modificarSucursal=false;
             txtIdSucursal.setText("");
            txtNombreSucursal.setText("");
            txtDireccionSucursal.setText("");
            txtTelefonoSucursal.setText("");
    }//GEN-LAST:event_btnCancelarSucursalActionPerformed

    private void btnCancelarParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarParametroActionPerformed
       txtIdParametro.setEditable(false);
            txtNombreParametro.setEditable(false);
            txtParametroParametro.setEditable(false);
            modificarParametro=false;
             txtIdParametro.setText("");
            txtNombreParametro.setText("");
            txtParametroParametro.setText("");
    }//GEN-LAST:event_btnCancelarParametroActionPerformed

    private void btnModificarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProductoActionPerformed
        ControladorProducto cpp= new ControladorProducto();
               modificarProducto=true;
              
                String id = modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 0).toString();
                //String inventario = modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 1).toString();
                String nombre= modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 1).toString();
                String costo = modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 2).toString();
            txtCodBarraProductos.setText(""+id);
            txtNombreProductos.setText(""+nombre);
            
            txtPrecioProductos.setText(""+costo);
                jpnProductos.setVisible(false);
                jpnNuevoProducto.setVisible(true);
    }//GEN-LAST:event_btnModificarProductoActionPerformed

    private void txtProductosBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductosBuscarActionPerformed
       if(!txtCantidadCompra.getText().isEmpty()){
       
       String C=txtPrecioProductos.getText();
        try {
            buscarProducto(C);
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Se cago al obtener la busqueda");
        }
       }
        

    }//GEN-LAST:event_txtProductosBuscarActionPerformed

    private void txtProductosBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductosBuscarKeyReleased
        char c=evt.getKeyChar();
        if(txtProductosBuscar.getText().isEmpty()|| c==8 || c==13){ }
        else{
            limpiarTablaProducto();
             
            try {
                buscarProducto(txtProductosBuscar.getText());
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Se cago buscnado el producto");
            }
            
        
            
        
        }
    }//GEN-LAST:event_txtProductosBuscarKeyReleased

    private void txtProductosBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductosBuscarKeyTyped
       char c=evt.getKeyChar();
        
        if(c==8 || c==32 || c==13 || Character.isLetterOrDigit(c)){
        
//        }else if(c==13){
//        limpiarTablaProducto();
//             
//            try {
//                buscarProducto(txtProductosBuscar.getText());
//            } catch (Exception ex) {
//                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("Se cago buscnado el producto");
//            }
}
        else{
        evt.consume();
        }
    }//GEN-LAST:event_txtProductosBuscarKeyTyped

    private void btnAgregarNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNuevoProductoActionPerformed
        ControladorProducto ctp= new ControladorProducto();

                String id = txtCodBarraProductos.getText();
                String nombre = txtNombreProductos.getText();
                String direccion = txtPrecioProductos.getText();
                ;
               
                
                Object P[]={id,nombre,direccion};
        
         
            if(modificarProducto==true){
                try {
                    ctp.Modificar(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtCodBarraProductos.setText("");
             txtNombreProductos.setText("");
             txtPrecioProductos.setText("");
            JOptionPane.showMessageDialog(null, "producto modificada con exito");
            
            
            
            
            }else{
                try {
                    ctp.Agregar(P);
                } catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            txtCodBarraProductos.setText("");
             txtNombreProductos.setText("");
             txtPrecioProductos.setText("");
            JOptionPane.showMessageDialog(null, "producto agregada con exito");
            
            
            }
             
             
            try {
                llenarProducto();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
      modificarProducto=false;
           txtCodBarraProductos.setEditable(false);
             txtNombreProductos.setEditable(false);
             txtPrecioProductos.setEditable(false);
    }//GEN-LAST:event_btnAgregarNuevoProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
            ControladorProducto cpp= new ControladorProducto();
        TableModel tableModel = jtblProductos.getModel();
    Object mostrar=modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 0);     
//    Object P[]={mostrar};
    
int decide=JOptionPane.showConfirmDialog(null, "Desea borrar el producto:" +modeloProducto.getValueAt(jtblProductos.getSelectedRow(), 1));


if(decide==0){
 try {
               cpp.Eliminar(mostrar);
               JOptionPane.showMessageDialog(null,"Se elimino el producto" +mostrar);
               limpiarTablaProducto();
           } catch (SQLException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ErrorTienda ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }    catch (Exception ex) {
                    Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

}else{
}

    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProveedorActionPerformed
        jpnProveedores.setVisible(false);
                jpnAgregarProv.setVisible(true);
    }//GEN-LAST:event_btnAgregarProveedorActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
         ControladorProveedor cpp= new ControladorProveedor();

//        int id = Integer.parseInt(txtIDProveedor.getText());
        String nombre  = txtNombreProveedor.getText();
        String telefono= txtTelefonoProveedor.getText();
        String direccion = txtDireccionProveedor.getText();
        String NIT = txtNIT.getText();
//        Object P[]={id,nombre,telefono, direccion, NIT};
        Object P[]={nombre,telefono, direccion, NIT};
        try {

            cpp.AgregarP(P);
            txtNombreProveedor.setText("");
            txtTelefonoProveedor.setText("");
            txtTelefonoProveedor.setText("");
            txtDireccionProveedor.setText("");
            txtNIT.setText("");
            JOptionPane.showMessageDialog(null, "agregado con exito");
            jpnAgregarProv.setVisible(false);
            jpnProveedores.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        llenarTablaProveedoresP();

        }

        public void llenarTablaProveedoresP()
        {
//            limpiarTablaProveedores();
            Conexion cn = new Conexion();
            cn.conectar();

            String proveedor[]= new String[5];
//            rsProveedor=cn.getValores("SELECT*FROM proveedor");
//            try {
//                while(rsProveedor.next()){
//                    proveedor[0]=rsProveedor.getString(1);
//                    proveedor[1]=rsProveedor.getString(2);
//                    proveedor[2]=rsProveedor.getString(3);
//                    proveedor[3]=rsProveedor.getString(4);
//                    proveedor[4]=rsProveedor.getString(5);
//
//                    modeloTablaProveedor.addRow(proveedor);
//                    tblProveedores.setModel(modeloTablaProveedor);
//
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void txtNombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProveedorActionPerformed
        evt.setSource((char)KeyEvent.VK_CLEAR);
        txtTelefonoProveedor.requestFocus();  
    }//GEN-LAST:event_txtNombreProveedorActionPerformed

    private void btnAtrasProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasProveedoresActionPerformed
                txtIDProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtNIT.setText("");
        jpnAgregarProv.setVisible(false);
        jpnProveedores.setVisible(true);
 
    }//GEN-LAST:event_btnAtrasProveedoresActionPerformed

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        char c=evt.getKeyChar();
        if((Character.isLetter(c) || c==8 || c==32) && txtNombreProveedor.getText().length()<30 ){
       //(Character.isDigit(c) || c==8 ) && txtCpu.getText().length()<2
       }else{
       evt.consume();
       }
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorActionPerformed
        evt.setSource((char)KeyEvent.VK_CLEAR);
txtDireccionProveedor.requestFocus(); 
    }//GEN-LAST:event_txtTelefonoProveedorActionPerformed

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        char c=evt.getKeyChar();
        if((Character.isDigit(c) || c==8)&& txtTelefonoProveedor.getText().length()<30 ){
       //(Character.isDigit(c) || c==8 ) && txtCpu.getText().length()<2
       }else{
       evt.consume();
       }
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionProveedorActionPerformed
         evt.setSource((char)KeyEvent.VK_CLEAR);
txtNIT.requestFocus();  
    }//GEN-LAST:event_txtDireccionProveedorActionPerformed

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        char c=evt.getKeyChar();
        if(txtDireccionProveedor.getText().length()<30 || c==32 || c==8){
       //(Character.isDigit(c) || c==8 ) && txtCpu.getText().length()<2
       }else{
       evt.consume();
       }
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtNITKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITKeyTyped
char c=evt.getKeyChar();
        if((Character.isDigit(c) || c==8)&& txtTelefonoProveedor.getText().length()<13 ){
       //(Character.isDigit(c) || c==8 ) && txtCpu.getText().length()<2
       }else{
       evt.consume();
       }        
    }//GEN-LAST:event_txtNITKeyTyped

    private void btnAtrasModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasModificarProveedorActionPerformed
         txtIDProveedor1.setText("");
        txtNuevoNombreProveedor.setText("");
        txtNuevoTelefonoProveedor.setText("");
        txtNuevoDireccionProveedor.setText("");
        txtNuevoNIT.setText("");
        jpnModificarProveedor.setVisible(false);
        jpnProveedores.setVisible(true);
    }//GEN-LAST:event_btnAtrasModificarProveedorActionPerformed

    private void txtNuevoNombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoNombreProveedorActionPerformed
        evt.setSource((char)KeyEvent.VK_CLEAR);
txtNuevoTelefonoProveedor.requestFocus();  
    }//GEN-LAST:event_txtNuevoNombreProveedorActionPerformed

    private void txtNuevoNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNombreProveedorKeyTyped
         char c=evt.getKeyChar();
       if((Character.isLetter(c) || c==8 || c==32) && txtNuevoNombreProveedor.getText().length()<30 ){
       //(Character.isDigit(c) || c==8 ) && txtCpu.getText().length()<2
       }else{
       evt.consume();
       }
    }//GEN-LAST:event_txtNuevoNombreProveedorKeyTyped

    private void txtNuevoTelefonoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoTelefonoProveedorActionPerformed
        evt.setSource((char)KeyEvent.VK_CLEAR);
txtNuevoDireccionProveedor.requestFocus();  

    }//GEN-LAST:event_txtNuevoTelefonoProveedorActionPerformed

    private void txtNuevoTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoTelefonoProveedorKeyTyped
        char c=evt.getKeyChar();
        if(txtNuevoTelefonoProveedor.getText().length()<8 && (Character.isDigit(c) || c==8) ){
        }
        else{evt.consume();}
    }//GEN-LAST:event_txtNuevoTelefonoProveedorKeyTyped

    private void txtNuevoDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoDireccionProveedorActionPerformed
        evt.setSource((char)KeyEvent.VK_CLEAR);
txtNuevoNIT.requestFocus();  
   
    }//GEN-LAST:event_txtNuevoDireccionProveedorActionPerformed

    private void txtNuevoDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoDireccionProveedorKeyTyped
        char c=evt.getKeyChar();
        if(txtNuevoDireccionProveedor.getText().length()<40 || c==8 || c==32){
        
        }else{evt.consume();}

    }//GEN-LAST:event_txtNuevoDireccionProveedorKeyTyped

    private void txtNuevoNITKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoNITKeyTyped
         char c=evt.getKeyChar();
        if(txtNuevoNIT.getText().length()<14 && (Character.isDigit(c) || c==8)){
        }
        else{evt.consume();}

    }//GEN-LAST:event_txtNuevoNITKeyTyped

    private void btnGuardarModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificarProveedorActionPerformed
                        ControladorProveedor cpp= new ControladorProveedor();

        int id = Integer.parseInt(txtIDProveedor.getText());
        String nombre  = txtNombreProveedor.getText();
        String telefono= txtTelefonoProveedor.getText();
        String direccion = txtDireccionProveedor.getText();
        String NIT = txtNIT.getText();
        Object P[]={id,nombre,telefono, direccion, NIT};

        try {

            cpp.modificarP(P);
            txtIDProveedor.setText("");
            txtNombreProveedor.setText("");
            txtTelefonoProveedor.setText("");
            txtDireccionProveedor.setText("");
            txtNIT.setText("");
            JOptionPane.showMessageDialog(null, "modificado con exito");
            jpnModificarProveedor.setVisible(false);
            jpnProveedores.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        llenarTablaProveedoresP();

    }//GEN-LAST:event_btnGuardarModificarProveedorActionPerformed

    private void btnModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProveedorActionPerformed
        jpnProveedores.setVisible(false);
        jpnModificarProveedor.setVisible(true);
    }//GEN-LAST:event_btnModificarProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
   
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed


    private void btnBuscarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarProductoMouseClicked
        if(!txtProductosBuscar.getText().isEmpty()){
            try {
                buscarProducto(txtProductosBuscar.getText());
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Se cago buscnado el producto");
            }
            
        }
    }//GEN-LAST:event_btnBuscarProductoMouseClicked
//----------------------------------------------COMPRAS-------------------------------------------------------------------
    private void cmbFiltroSucursalCompraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFiltroSucursalCompraItemStateChanged

        if(evt.getStateChange()==ItemEvent.SELECTED){
            try {
                llenarComprasRealizadasFiltro();
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            btnVerDetalle.setEnabled(false);
        }

    }//GEN-LAST:event_cmbFiltroSucursalCompraItemStateChanged
//Llenado de tabla Compras, que contiene todas las compras registradas en la base de datos.

public void llenarComprasRealizadasFiltro() throws Exception{
    rsFiltroCompra=null;
    rsCompra=null;
    ControladorCompra cp=new ControladorCompra();
    ControladorProveedor cP = new ControladorProveedor();
    ControladorSucursal cS = new ControladorSucursal();
    limpiarTablaComprasRealizadas();
    String Prov = "";
    String Sucur = "";
    int IdSucursal = 0;  
    String sucursal =   (String)cmbFiltroSucursalCompra.getSelectedItem();
    rsFiltroCompra = cS.ObtenerId(sucursal);
        
    while (rsFiltroCompra.next()) {
    IdSucursal = rsFiltroCompra.getInt("IdSucursal");    
    }
    rsFiltroCompra = null;
    rsFiltroCompra = cp.ObtenerComprasFiltro(IdSucursal);

    if (!rsFiltroCompra.isBeforeFirst()) { 

             System.out.println("No existe");
}    else{
         try {
            while (rsFiltroCompra.next()) {
                String IdCompra = rsFiltroCompra.getString("IdCompra");
                String Tipo = rsFiltroCompra.getString("TipoCompra");
                String Total = rsFiltroCompra.getString("Total");
                String IdProveedor = rsFiltroCompra.getString("IdProveedor");
                
                rsCompra = cP.buscarProveedor(IdProveedor);
                while(rsCompra.next()){
                    Prov = rsCompra.getString("Nombre");
                }
                if(Tipo.equals("F")){
                    Tipo = "Factura";
                }
                else if(Tipo.equals("C")){
                    Tipo = "Crédito Fiscal";
                }
                else if(Tipo.equals("L")){
                    Tipo = "Libre";
                }
                
                modeloCompra.addRow(new Object[]{IdCompra,Tipo,Prov,Total});
                
            }
        } catch (SQLException e) {
            throw  new ErrorTienda("No logra poner el modelo");
        }
        
       tblCompras.setModel(modeloCompra);

        
        }
}

public void limpiarTablaComprasRealizadas(){
 for (int i = 0; i < tblCompras.getRowCount(); i++) {
           modeloCompra.removeRow(i);
           i-=1;
       }
}

public void limpiarTablaCompra(){
 for (int i = 0; i < tblCompra.getRowCount(); i++) {
           modeloAddCompra.removeRow(i);
           i-=1;
       }
}
    
    private void cmbFiltroSucursalCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbFiltroSucursalCompraFocusGained
        if (cargarSucursalesFC==true){//Este metodo es la copia del otro que llena el combo con las sucursales, solo que en este caso sirve para filtrar las compras por sucursal -LoL-

            ControladorSucursal p = new ControladorSucursal();
            modeloSucursalFC.removeAllElements();

            try{
                rsSucursalFC = p.Obtener();
                while (rsSucursalFC.next()) {
                    modeloSucursalFC.addElement(rsSucursalFC.getString(2));
                }
                cmbFiltroSucursalCompra.setModel(modeloSucursalFC);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
            }  catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cargarSucursalesFC=false;

        try {
            llenarComprasRealizadasFiltro();
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
         btnVerDetalle.setEnabled(false);


    }//GEN-LAST:event_cmbFiltroSucursalCompraFocusGained

    private void txtNumDocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocumentoKeyPressed

        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            txtCodBarraCompra.requestFocus();
        }

    }//GEN-LAST:event_txtNumDocumentoKeyPressed

    private void txtCodBarraCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraCompraKeyPressed

          //Método para autogenerar el nombre del producto de acuerdo al id
        String codigo = txtCodBarraCompra.getText();
        String nombre = "";
        ControladorProducto cp = new ControladorProducto();
          if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
           
                          
            
            for(int i=0; i<modeloAddCompra.getRowCount();i++){
                              
                                //Validar que el producto ya este en la tabla
                                if(modeloAddCompra.getValueAt(i,0).toString().equals(txtCodBarraCompra.getText())){
                                    txtNombreProductoCompra.setText(modeloAddCompra.getValueAt(i,1).toString());
                                    break;
                                }
                            }
            
            
            try {
                rsNameProd = cp.BuscarProducto(codigo);
                
            } catch (Exception ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
               
                while (rsNameProd.next()){
                    
                    nombre = rsNameProd.getString("Nombre");
                    costoUC = rsNameProd.getString("Costo");
                }
            } catch (SQLException ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(nombre.isEmpty()){
                txtCantidadCompra.setText("1");
                txtNombreProductoCompra.requestFocus();
            }else{
                txtNombreProductoCompra.setText(nombre);
                txtCantidadCompra.setText("1");  
            }
          
            if(txtNombreProductoCompra.getText().isEmpty()){
                txtNombreProductoCompra.requestFocus();
            }
            else{
                txtCantidadCompra.requestFocus();
            }
            
            
        } else if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_DELETE){
        txtCodBarraCompra.setText("");
        txtNombreProductoCompra.setText("");                          
        }
      

    }//GEN-LAST:event_txtCodBarraCompraKeyPressed

    private void txtCostoProductoCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoProductoCompraKeyPressed
        agregarDetalleModelo = true;
        if(agregarDetalleModelo==true){
        
            ControladorSucursal cs = new ControladorSucursal();
            
                    if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
                        IdCompraPC = txtIdCompra.getText();
                        CodBarraPC = txtCodBarraCompra.getText();
                        NombrePC = txtNombreProductoCompra.getText();
                        CantidadPC = txtCantidadCompra.getText();
                        CostoUnitarioPC = txtCostoProductoCompra.getText();
                        IdSucursalPC = "";
                        sucursalPC = (String) cmbSucursalCompra.getSelectedItem();
                        
                        
                        try {
                             rsDC = cs.ObtenerId(sucursalPC);
                        } catch (Exception ex) {
                              Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        try {
                            while(rsDC.next()){
                                IdSucursalPC = rsDC.getString("IdSucursal");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     
                        //Solo para el primer registro
                        if(modeloAddCompra.getRowCount()==0){
                        agregarDetalle();
                        SubtotalPC = String.valueOf(df.format(Double.parseDouble(CostoUnitarioPC)*Double.parseDouble(CantidadPC)));
                       //Credito
                        if(tipoCompra.equals("C")){
                        ivaCompra = String.valueOf(df.format(Double.parseDouble(SubtotalPC)-(Double.parseDouble(SubtotalPC)/1.13)));
                        percepcionCompra = String.valueOf(df.format(Double.parseDouble(SubtotalPC)-(Double.parseDouble(SubtotalPC)/1.01))); 
                        SubtotalPC = String.valueOf(df.format(Double.parseDouble(SubtotalPC)-Double.parseDouble(ivaCompra)-Double.parseDouble(percepcionCompra)));
                        detalleCompra[4] = SubtotalPC;
                        SubtotalCompra = SubtotalPC;
                        TotalCompra = String.valueOf(df.format(Double.parseDouble(ivaCompra)+Double.parseDouble(percepcionCompra)+Double.parseDouble(SubtotalCompra)));
                        addC = true;
                        }
                        //---------------
                        else{
                        detalleCompra[4] = SubtotalPC;
                        SubtotalCompra = SubtotalPC;
                        add = true;
                           
                        }
                        
                        
                        }
                        //---------------------------------------------------------------------------------------------------
                        else{
                            int validar = 0;
                            for(int i=0; i<modeloAddCompra.getRowCount();i++){
                                //Validar que el producto ya este en la tabla
                                if(modeloAddCompra.getValueAt(i,0).toString().equals(txtCodBarraCompra.getText())){
                                    validarPC = true;
                                    validar = i;
                                    agregarSubtotalDC = false;
                                    break;
                                }
                                //Si no solo agrego otro detalle compra
                                else{
                                    agregarSubtotalDC = true;
                                }
                            }

                            if(validarPC==true){
                               if(modeloAddCompra.getValueAt(validar, 3).toString().equals(txtCostoProductoCompra.getText())){
                                    agregarDetalle();
                                    CantidadAnteriorPC = Integer.parseInt(modeloAddCompra.getValueAt(validar, 2).toString());
                                    modeloAddCompra.setValueAt((CantidadAnteriorPC + Integer.parseInt(detalleCompra[2])), validar, 2);
                                    SubtotalAnteriorPC = Double.parseDouble(modeloAddCompra.getValueAt(validar, 4).toString());
                                    modeloAddCompra.setValueAt(SubtotalAnteriorPC + (Double.parseDouble(detalleCompra[2])*Double.parseDouble(detalleCompra[3]))/1.13, validar, 4);
                                    SubtotalPC = String.valueOf((SubtotalAnteriorPC + (Double.parseDouble(detalleCompra[2])*Double.parseDouble(detalleCompra[3])))/1.13);
                                    System.out.println("SubtotalPC:"+SubtotalPC);
                                    //Credito
                                    if(tipoCompra.equals("C")){
                                    ivaCompra = String.valueOf(df.format((Double.parseDouble(modeloAddCompra.getValueAt(validar, 2).toString())*Double.parseDouble(modeloAddCompra.getValueAt(validar, 3).toString())-(Double.parseDouble(SubtotalPC)))));
                                        System.out.println("iva:"+ivaCompra);
                                    percepcionCompra = String.valueOf(df.format((Double.parseDouble(modeloAddCompra.getValueAt(validar, 2).toString())*Double.parseDouble(modeloAddCompra.getValueAt(validar, 3).toString())- (Double.parseDouble(modeloAddCompra.getValueAt(validar, 2).toString())*Double.parseDouble(modeloAddCompra.getValueAt(validar, 3).toString())/1.01))));
                                        System.out.println("per:"+percepcionCompra);
                                    SubtotalCompra = String.valueOf(Double.parseDouble(SubtotalCompra) - SubtotalAnteriorPC + Double.parseDouble(SubtotalPC));
                                        System.out.println("Subtotal:"+SubtotalCompra);
                                    TotalCompra = String.valueOf(df.format(Double.parseDouble(TotalCompra)+(Double.parseDouble(ivaCompra)+Double.parseDouble(percepcionCompra)+Double.parseDouble(SubtotalCompra))));
                                        System.out.println("Total:"+TotalCompra);
                                    }
                                    //---------------------------------------------------------------------
                                  
                                    
                                    agregarSubtotalDC = false;
                                    limpiarDetalle();
                                    add = false;
                                    addC = false;
                                    validarPC = false;
                               }
                               
                               else{
                                   JOptionPane.showMessageDialog(null, "El producto esta registrado con el costo de $"+modeloAddCompra.getValueAt(validar, 3).toString(), "EL COSTO DEL PRODUCTO NO COINCIDE",JOptionPane.ERROR_MESSAGE);
                                   add = false;
                                   addC = false;
                               }                               
                                
                            }
                            //Complemento para agregar el detalle compra de otro producto al modelo
                            if(agregarSubtotalDC==true){
                                agregarDetalle();
                                SubtotalPC = String.valueOf(df.format(Double.parseDouble(CostoUnitarioPC)* Double.parseDouble(CantidadPC)));
                                detalleCompra[4] = SubtotalPC;
                                SubtotalCompra = String.valueOf(df.format(Double.parseDouble(SubtotalCompra) + Double.parseDouble(SubtotalPC)));
                                agregarSubtotalDC = false;
                                add = true;
                            }
                        }
                        


                        if(tipoCompra.equals("C")){
                            if(addC==true){
                        modeloAddCompra.addRow(detalleCompra); 
                        tblCompra.setModel(modeloAddCompra);
                        txtPercepcionCompra.setText(percepcionCompra);
                        txtIvaCompra.setText(ivaCompra);
                        txtSubtotalCompra.setText(SubtotalCompra);//Pongo el subtotal actualizado
                        txtTotalCompra.setText(TotalCompra);
                        limpiarCompra();
                        limpiarDetalle();
                        }
                            else{
                                  tblCompra.setModel(modeloAddCompra);
                                  txtPercepcionCompra.setText(percepcionCompra);
                                  txtIvaCompra.setText(ivaCompra);
                                  txtSubtotalCompra.setText(SubtotalCompra);//Pongo el subtotal actualizado
                                  txtTotalCompra.setText(TotalCompra);//Pongo el total actualizado
                                  limpiarCompra();
                                  limpiarDetalle();
                                
                                
                            }
                        }
                        else{
                              if(add==true){
                                  modeloAddCompra.addRow(detalleCompra); 
                                  tblCompra.setModel(modeloAddCompra);
                                  txtTotalCompra.setText(SubtotalCompra);//Pongo el total actualizado
                                  limpiarCompra();
                                  limpiarDetalle();
                              }
                              else{
                                  tblCompra.setModel(modeloAddCompra);
                                  txtTotalCompra.setText(SubtotalCompra);//Pongo el total actualizado
                                  limpiarCompra();
                                  limpiarDetalle();
                              }  
                        
                        }
                     
                        
                        
                       
                          
                        
                        
                        
                        
                       }
                       
                       
                         
                       
                       
                       
                       }
                       
                       
                       
        
                  
                        
                
                    
                    

    }//GEN-LAST:event_txtCostoProductoCompraKeyPressed

    private void tblCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCompraMouseClicked
      if(evt.getClickCount()== 2){
     
          filaEliminar = tblCompra.getSelectedRow();
         
          if(txtCodBarraCompra.getText().isEmpty()){
                txtCodBarraCompra.setText(modeloAddCompra.getValueAt(filaEliminar, 0).toString());
                txtNombreProductoCompra.setText(modeloAddCompra.getValueAt(filaEliminar, 1).toString());
                txtCantidadCompra.setText(modeloAddCompra.getValueAt(filaEliminar, 2).toString());
                txtCostoProductoCompra.setText(modeloAddCompra.getValueAt(filaEliminar, 3).toString());
                SubtotalCompra = String.valueOf(df.format(Double.parseDouble(SubtotalCompra) - Double.parseDouble(modeloAddCompra.getValueAt(filaEliminar, 4).toString())));
                txtTotalCompra.setText(SubtotalCompra);
                modeloAddCompra.removeRow(filaEliminar);
                tblCompra.setModel(modeloAddCompra);   
      }
      else{
          JOptionPane.showMessageDialog(null, "Agregue el producto actual antes de eliminar otro");
          txtCostoProductoCompra.requestFocus();
      }
        
      }
      
      
    }//GEN-LAST:event_tblCompraMouseClicked

    private void btnGuardarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCompraActionPerformed

        if(modeloAddCompra.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "No puede guardar una compra vacia","ERROR", JOptionPane.ERROR_MESSAGE);
            txtNumDocumento.requestFocus();
        }
        else{
       ControladorCompra cpp= new ControladorCompra();
       ControladorProducto cpr = new ControladorProducto();
       ControladorProveedor cpv = new ControladorProveedor();
       ControladorSucursal cs = new ControladorSucursal();
       rsDC = null;
       if(tipoCompra.equals("F")||tipoCompra.equals("L")){
           ivaCompra = "0.0";
           percepcionCompra = "0.0";
       } 
        
       //Encontrando el Id de la sucursal por medio del cmb
        IdSucursalGC = "";
        SucursalGC = (String) cmbSucursalCompra.getSelectedItem();
        try {
           rsDC = cs.ObtenerId(SucursalGC);
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while(rsDC.next()){
                IdSucursalGC = rsDC.getString("IdSucursal");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //-----------------------
        
        rsDC = null;
        //Encontrando el IdProveedor por medio del cmb
        IdProveedorGC = "";
        ProveedorGC = (String) cmbProveedorCompra.getSelectedItem();
        try {
           rsDC = cpv.ObtenerId(ProveedorGC);
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while(rsDC.next()){
                IdProveedorGC = rsDC.getString("IdProveedor");
                System.out.println(""+IdProveedorGC);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //---------------------------
        //Guardar la Compra 
        Object P[]={txtIdCompra.getText(), IdProveedorGC, IdSucursalGC, txtFechaCompra.getText(), tipoCompra, txtNumDocumento.getText(), txtTotalCompra.getText(), ivaCompra, percepcionCompra, txtTotalCompra.getText()};
        try {
            cpp.Agregar(P);
            //Calcular la fecha y hora de nuevo
            JOptionPane.showMessageDialog(null, "Compra agregado con éxito");
            txtFechaCompra.setText("");
            jpnCompras.setVisible(true);
            cmbFiltroSucursalCompra.requestFocus();
        } catch (SQLException | ClassNotFoundException | ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        //----------------------------
        
      //----------------------------
      
      
      String vectorCodigo[] = new String [modeloAddCompra.getRowCount()];
       rsDC=null;
       ResultSet r = null;
       int filas = modeloAddCompra.getRowCount();
       int avance=0;
       //Cargo todos los CodBarra en el vector
      for(int i=0;i<modeloAddCompra.getRowCount();i++){
      vectorCodigo[i] = modeloAddCompra.getValueAt(i, 0).toString();
      }
      //------------------------
      
       while(filas>avance){
                     
           try {
               agregarProductoBD = validarProductoRegistrado(vectorCodigo[avance]);
           } catch (Exception ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
           //Para obtener el CostoUnitarioPC
           r = null;
           try {
               r = cpr.Obtener(vectorCodigo[avance]);
           } catch (Exception ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
    
           try {
               while(r.next()){
                   CostoUnitarioPC = r.getString("Costo");
               }
           } catch (SQLException ex) {
               Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
           //-----------------------------------
           
           //Si hay que agregar un nuevo producto a la base de datos
           if(agregarProductoBD==true){
               Object Pr[] = {modeloAddCompra.getValueAt(avance,0).toString(),modeloAddCompra.getValueAt(avance,1).toString(),modeloAddCompra.getValueAt(avance,3).toString()};
               try {
                   cpr.Agregar(Pr);
               } catch (Exception ex) {
                   Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
               }
               
               Object Ps[] = {IdSucursalGC,modeloAddCompra.getValueAt(avance,0).toString(),modeloAddCompra.getValueAt(avance,2).toString()};
               cn.UID("Insert into inventario(IdSucursal, CodBarra, Cantidad) values ("+Ps[0]+",'"+Ps[1]+"',"+Ps[2]+")");
               
               Object Pdc[] = {IdCompraPC, modeloAddCompra.getValueAt(avance,0).toString(), modeloAddCompra.getValueAt(avance,2).toString(), modeloAddCompra.getValueAt(avance,3).toString()};
               cn.UID("Insert into detallecompra(IdCompra, CodBarra, Cantidad, CostoUnitario) values ("+Pdc[0]+",'"+Pdc[1]+"',"+Pdc[2]+","+Pdc[3]+")");

               agregarProductoBD=false;
           }
           //Si hay que modificar solo las cantidades y el costo
           else{
            r = null;
            r = cn.getValores("Select * from inventario where CodBarra='"+modeloAddCompra.getValueAt(avance, 0).toString()+"'");
            
            try {
                while(r.next()){
                   CantidadPC = r.getString("Cantidad");
                   IdSucursalPC = r.getString("IdSucursal");
                }
            } catch (SQLException ex) {
                Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //El producto solo se debe actualizar en la tabla inventario.Cantidad, la tabla producto y detallecompra
            if(IdSucursalPC.equals(IdSucursalGC)){
                CantidadPC = String.valueOf(Double.parseDouble(CantidadPC)+Double.parseDouble(modeloAddCompra.getValueAt(avance, 2).toString()));
                cn.UID("UPDATE inventario SET Cantidad='" + CantidadPC + "'WHERE CodBarra='" + modeloAddCompra.getValueAt(avance, 0) + "' AND IdSucursal='"+IdSucursalPC+"';");
              
                Object Pdc[] = {IdCompraPC, modeloAddCompra.getValueAt(avance,0).toString(), modeloAddCompra.getValueAt(avance,2).toString(), modeloAddCompra.getValueAt(avance,3).toString()};
                cn.UID("Insert into detallecompra(IdCompra, CodBarra, Cantidad, CostoUnitario) values ("+Pdc[0]+",'"+Pdc[1]+"',"+Pdc[2]+","+Pdc[3]+")");

                //para modificar el CostoUnitario de un producto
                if(!CostoUnitarioPC.equals(modeloAddCompra.getValueAt(avance,3).toString())){
                    CostoUnitarioPC = String.valueOf(df.format((Double.parseDouble(CostoUnitarioPC)+Double.parseDouble(modeloAddCompra.getValueAt(avance,3).toString()))/2));
                    Object Prm[] = {modeloAddCompra.getValueAt(avance,0).toString(),modeloAddCompra.getValueAt(avance,1).toString(),CostoUnitarioPC};
                    try {  
                        cpr.Modificar(Prm);
                    } catch (Exception ex) {
                        Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //------------------------------------------------
            }
            
            //----------------------------------------------------------------------------------------------------------------
            //Si es distinta sucursal
            else{
                cn.UID("Insert into inventario(IdSucursal, CodBarra, Cantidad) values ('"+IdSucursalGC+"','"+modeloAddCompra.getValueAt(avance, 0)+"','"+modeloAddCompra.getValueAt(avance, 2)+"')");
                Object Pdc[] = {IdCompraPC, modeloAddCompra.getValueAt(avance,0).toString(), modeloAddCompra.getValueAt(avance,2).toString(), modeloAddCompra.getValueAt(avance,3).toString()};
                cn.UID("Insert into detallecompra(IdCompra, CodBarra, Cantidad, CostoUnitario) values ("+Pdc[0]+",'"+Pdc[1]+"',"+Pdc[2]+","+Pdc[3]+")");
               // para modificar el CostoUnitario de un producto
                if(!CostoUnitarioPC.equals(modeloAddCompra.getValueAt(avance,3).toString())){
                    CostoUnitarioPC = String.valueOf(df.format((Double.parseDouble(CostoUnitarioPC)+Double.parseDouble(modeloAddCompra.getValueAt(avance,3).toString()))/2));
                    Object Prm[] = {modeloAddCompra.getValueAt(avance,0).toString(),modeloAddCompra.getValueAt(avance,1).toString(),CostoUnitarioPC};
                    try {  
                        cpr.Modificar(Prm);
                    } catch (Exception ex) {
                        Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
           }
        avance++;
       }
            
        }
        
    }//GEN-LAST:event_btnGuardarCompraActionPerformed

       
    private void btnHacerVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHacerVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHacerVentaMouseClicked

    private void btnHacerVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHacerVentaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHacerVentaMouseEntered

    private void btnHacerVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHacerVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHacerVentaMouseExited

    private void btnHacerVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHacerVentaActionPerformed
        jpnMenuVentas.setVisible(false);
        jpnUtilidadMenuVentasParametros.setVisible(true);
    }//GEN-LAST:event_btnHacerVentaActionPerformed

    private void btnVerVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerVentasMouseClicked

    private void btnVerVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerVentasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerVentasMouseEntered

    private void btnVerVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerVentasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerVentasMouseExited

    private void btnVerVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerVentasActionPerformed
        for(int i=0;i < MenuVenta.getRowCount();i++){
            MenuVenta.removeRow(i);
            i-=1;
        }

        jpnMenuVentas.setVisible(false);
        jpnVerVentasporSucursal.setVisible(true);
        int IdSucursal = cmbSucursalReporteVenta.getSelectedIndex() + 1;
        try {
            rstControladorVenta = controladorventa.llenarVenta(1);

        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
        try {
            while (rstControladorVenta.next()) {//tablas base de datos
                //tabla de compra
                datosVenta[0] = rstControladorVenta.getString(1);
                datosVenta[1] = rstControladorVenta.getString(3);
                datosVenta[2] = rstControladorVenta.getString(5);
                datosVenta[3] = rstControladorVenta.getString(6);
                datosVenta[4] = rstControladorVenta.getString(8);
                datosVenta[5] = rstControladorVenta.getString(10);
                datosVenta[6] = rstControladorVenta.getString(14);
                MenuVenta.addRow(datosVenta);
            }
        } catch (SQLException ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_btnVerVentasActionPerformed

    private void btnVerReporteVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerReporteVentasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerReporteVentasMouseClicked

    private void btnVerReporteVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerReporteVentasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerReporteVentasMouseEntered

    private void btnVerReporteVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerReporteVentasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerReporteVentasMouseExited

    private void btnVerReporteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerReporteVentasActionPerformed
        jpnMenuVentas.setVisible(false);
        jpnVentasReporteParametro.setVisible(true);
    }//GEN-LAST:event_btnVerReporteVentasActionPerformed

    private void cmbTipoFacturaParametroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoFacturaParametroItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoFacturaParametroItemStateChanged

    private void cmbSucursalParametroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSucursalParametroItemStateChanged
        int posicion=cmbSucursalParametro.getSelectedIndex();
        int lugar = cmbSucursalParametro.getItemCount();
        txtSucursalVentaParametro.setText(String.valueOf(mLlenarIdS.getElementAt(posicion)));
        if (lugar>0) {
        SaberSucursalVentas = Integer.parseInt(String.valueOf(mLlenarIdS.getElementAt(posicion)));            
        }
    }//GEN-LAST:event_cmbSucursalParametroItemStateChanged

    private void cmbUtilidadParametroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbUtilidadParametroItemStateChanged
        int posicion=cmbUtilidadParametro.getSelectedIndex();
        int lugar = cmbUtilidadParametro.getItemCount();
        if (lugar>0) {
                    double PorcentajeVenta=0;
        PorcentajeVenta = Double.parseDouble((String.valueOf(mLlenarPoU.getElementAt(posicion))));
        saberTipoUtilidadVenta = PorcentajeVenta;
        PorcentajeVenta = PorcentajeVenta *100;
        txtUtilidadVentaParametro.setText(String.valueOf(PorcentajeVenta)+"%");
        }
    }//GEN-LAST:event_cmbUtilidadParametroItemStateChanged

    private void btnHacerNuevaVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHacerNuevaVentaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHacerNuevaVentaMouseEntered

    private void btnHacerNuevaVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHacerNuevaVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHacerNuevaVentaMouseExited

    private void btnHacerNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHacerNuevaVentaActionPerformed
//Para poner la fecha
        dia = calendar.get(Calendar.DATE);
        mes = calendar.get(Calendar.MONTH)+1;
        anio = calendar.get(Calendar.YEAR);
        lblFechaVentaMostrar.setText(anio+"/"+mes+"/"+dia);
        //------------------
        saberCodigoVenta();
        jpnUtilidadMenuVentasParametros.setVisible(false);
        jpnRegistrarVenta.setVisible(true);

        if (cmbTipoFacturaParametro.getSelectedIndex()==1) {
            txtGiroVenta.setVisible(true);
            txtNITventa.setVisible(true);
            txtNRCventa.setVisible(true);
            lblGiroVenta.setVisible(true);
            lblNITventa.setVisible(true);
            lblNRCventa.setVisible(true);
            lblIVA.setVisible(true);
            txtIVA.setVisible(true);
        } else {
            txtGiroVenta.setVisible(false);
            txtNITventa.setVisible(false);
            txtNRCventa.setVisible(false);
            lblGiroVenta.setVisible(false);
            lblNITventa.setVisible(false);
            lblNRCventa.setVisible(false);
            lblIVA.setVisible(false);
            txtIVA.setVisible(false);            
        }
        int cantidadN=0, mayorN=0;           
        int SaberNDocumento = cmbTipoFacturaParametro.getSelectedIndex();               
        
        if (SaberNDocumento==0) {
            try {           
            rstControladorVenta = controladorventa.NDocumento("FACTURA");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} 
        
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("FACTURA");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
        } else if (SaberNDocumento==1) {
            try {           
            rstControladorVenta = controladorventa.NDocumento("CREDITO FISCAL");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} 
        
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("CREDITO FISCAL");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
 
        } else if (SaberNDocumento==2) {            
            try {           
            rstControladorVenta = controladorventa.NDocumento("LIBRE");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}         
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("LIBRE");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
        }
    }//GEN-LAST:event_btnHacerNuevaVentaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jpnReporteVentas.setVisible(true);
        jpnVentasReporteParametro.setVisible(false);
        int Generar=0;
        if (cmbFechasVenta.getSelectedIndex()==0) {
            Generar =1;
        }
        if (cmbFechasVenta.getSelectedIndex()==1) {
            Generar =2;
        }
        if (cmbFechasVenta.getSelectedIndex()==2) {
            Generar =3;
        }
        if (cmbFechasVenta.getSelectedIndex()==3) {
            Generar =4;
        }
        if (cmbFechasVenta.getSelectedIndex()==4) {
            Generar =5;
        }
        if (cmbFechasVenta.getSelectedIndex()==5) {
            Generar =6;
        }
        if (cmbFechasVenta.getSelectedIndex()==6) {
            Generar =7;
        }
        if (cmbFechasVenta.getSelectedIndex()==7) {
            Generar =8;
        }
        if (cmbFechasVenta.getSelectedIndex()==8) {
            Generar =9;
        }
        if (cmbFechasVenta.getSelectedIndex()==9) {
            Generar =10;
        }
        if (cmbFechasVenta.getSelectedIndex()==10) {
            Generar =11;
        }
        if (cmbFechasVenta.getSelectedIndex()==11) {
            Generar =12;
        }

        try {
            rstControladorVenta = controladorventa.ObtenerVentas(String.valueOf(Generar));

        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}

        try {
            while (rstControladorVenta.next()) {//tablas base de datos
                //tabla de compra
                GReporteVenta[0] = rstControladorVenta.getString(6);
                GReporteVenta[1] = rstControladorVenta.getString(14);
                GReporteVenta[2] = rstControladorVenta.getString(5);
                GReporteVenta[3] = rstControladorVenta.getString(13);
                GReporteVenta[4] = rstControladorVenta.getString(8);
                ReporteVenta.addRow(GReporteVenta);
            }
        } catch (SQLException ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}

        int filas = tblReporteVentas.getRowCount(), iteracion=0;
        double  TotalReporteVentas = 0, TotalReporteVentaIVA = 0, TotalReporteVentasGravadas = 0;
        while (iteracion<filas){
            TotalReporteVentas = TotalReporteVentas + Double.parseDouble(String.valueOf(tblReporteVentas.getValueAt(iteracion, 4)));
            iteracion++;
        }
        TotalReporteVentaIVA = TotalReporteVentas * 1.13;
        TotalReporteVentaIVA = TotalReporteVentaIVA - TotalReporteVentas;
        TotalReporteVentasGravadas = TotalReporteVentaIVA + TotalReporteVentas;

        txtVentasNetas.setText("$"+ (df.format(TotalReporteVentas)));
        txtImpuestosVentas.setText("$"+ (df.format(TotalReporteVentaIVA)));
        txtVentasGravadas.setText("$"+ (df.format(TotalReporteVentasGravadas)));
        jpnMenuVentas.setVisible(false);
        jpnReporteVentas.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmbSucursalReporteVentaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSucursalReporteVentaItemStateChanged
        rstControladorVenta = null;
        for(int i=0;i < MenuVenta.getRowCount();i++){
            MenuVenta.removeRow(i);
            i-=1;
        }
        int IdSucursal = cmbSucursalReporteVenta.getSelectedIndex() + 1;
        try {
            rstControladorVenta = controladorventa.llenarVenta(IdSucursal);

        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
        try {
            while (rstControladorVenta.next()) {//tablas base de datos
                //tabla de compra
                datosVenta[0] = rstControladorVenta.getString(1);
                datosVenta[1] = rstControladorVenta.getString(3);
                datosVenta[2] = rstControladorVenta.getString(5);
                datosVenta[3] = rstControladorVenta.getString(6);
                datosVenta[4] = rstControladorVenta.getString(8);
                datosVenta[5] = rstControladorVenta.getString(10);
                datosVenta[6] = rstControladorVenta.getString(14);
                MenuVenta.addRow(datosVenta);
            }
        } catch (SQLException ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_cmbSucursalReporteVentaItemStateChanged

    private void lblFechaVentaMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblFechaVentaMostrarActionPerformed

    }//GEN-LAST:event_lblFechaVentaMostrarActionPerformed

    private void btnVenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMouseClicked

    }//GEN-LAST:event_btnVenderMouseClicked

    private void btnVenderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVenderMouseEntered

    private void btnVenderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMouseExited

    }//GEN-LAST:event_btnVenderMouseExited

    private void btnVenderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVenderMousePressed

    }//GEN-LAST:event_btnVenderMousePressed

    private void btnVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVenderActionPerformed
         //Agrega cada item de detalle compra
        int IdTipoPrecio = sabercmbUtilidadVenta + 1;
        int IdSucursal = sabercmbIdSucursal + 1;
        int IdVenta = Integer.parseInt(txtIdVenta.getText());

        if (cmbTipoFacturaParametro.getSelectedIndex()==0) {
            for(int i=0;i < tblRegistrarVenta.getRowCount();i++){
                double posicionprecio = (Double.parseDouble(tblRegistrarVenta.getValueAt(i, 3).toString() )) / 1.13;
                posicionprecio = Double.parseDouble(df.format(posicionprecio));
                Conexion cn = new Conexion();//
                cn.UID("INSERT INTO detalleventa (IdVenta, CodBarra, Cantidad, PrecioUnitario) VALUES ('" +IdVenta+ "','" +tblRegistrarVenta.getValueAt(i, 0)+ "','"
                    +tblRegistrarVenta.getValueAt(i, 2)+ "','" + posicionprecio + "')");
                cn.UID("UPDATE inventario SET Cantidad = Cantidad - '" +tblRegistrarVenta.getValueAt(i, 2)+ "'  WHERE IdSucursal='" + IdSucursal + "'");
            }
        }else{
            for(int i=0;i < tblRegistrarVenta.getRowCount();i++){
                Conexion cn = new Conexion();//
                cn.UID("INSERT INTO detalleventa (IdVenta, CodBarra, Cantidad, PrecioUnitario) VALUES ('" +IdVenta+ "','" +tblRegistrarVenta.getValueAt(i, 0)+ "','"
                    +tblRegistrarVenta.getValueAt(i, 2)+ "','" +tblRegistrarVenta.getValueAt(i, 3)+  "')");
                cn.UID("UPDATE inventario SET Cantidad = Cantidad - '" +tblRegistrarVenta.getValueAt(i, 2)+ "'  WHERE CodBarra='" +tblRegistrarVenta.getValueAt(i, 0)+ "'");
            }
        }
        //TERMINAR DE AGREGAR//

        String Fecha="";
        Fecha=anio+"-"+mes+"-"+dia;
        String Giro = "";
        String NIT = "";
        String NRC = "";
        String TipoVenta = "";
        String Cliente = txtClienteVenta.getText();
        String NDocumento = txtNuDocumentoVenta.getText();
        String Direcion = txtDireccion.getText();
        int filas = tblRegistrarVenta.getRowCount(), iteracion=0;
        double  TotalVentas = 0;
        while (iteracion<filas){
            TotalVentas = TotalVentas + Double.parseDouble(String.valueOf(tblRegistrarVenta.getValueAt(iteracion, 4)));
            iteracion++;
        }

        double IVA=0, TotalGravado=0, Total=0;

        if (cmbTipoFacturaParametro.getSelectedIndex()==0) {
            TipoVenta = "FACTURA";
            double saberiva = TotalVentas/1.13;
            IVA = TotalVentas - saberiva;
            IVA = Double.parseDouble(df.format(IVA));
            Total = saberiva;
            Total = Double.parseDouble(df.format(Total));
            TotalGravado = TotalVentas;
            TotalGravado = Double.parseDouble(df.format(TotalGravado));

        }
        if (cmbTipoFacturaParametro.getSelectedIndex()==1) {
            Giro = txtClienteVenta.getText();
            NIT = txtNITventa.getText();
            NRC = txtNRCventa.getText();
            TipoVenta = "CREDITO FISCAL";
            IVA = TotalVentas*1.13;
            IVA = IVA - TotalVentas;
            IVA = Double.parseDouble(df.format(IVA));
            Total = TotalVentas;
            Total = Double.parseDouble(df.format(Total));
            TotalGravado = TotalVentas + IVA;
            TotalGravado = Double.parseDouble(df.format(TotalGravado));
        }

        if (cmbTipoFacturaParametro.getSelectedIndex()==2) {
            TipoVenta = "LIBRE";
            IVA = 0;
            Total = TotalGravado;
            IVA = TotalVentas*1.13;
            IVA = IVA - TotalVentas;
            IVA = Double.parseDouble(df.format(IVA));
            Total = TotalVentas;
            Total = Double.parseDouble(df.format(Total));
            TotalGravado = TotalVentas + IVA;
            TotalGravado = Double.parseDouble(df.format(TotalGravado));
        }
        try {
            controladorventa.Agregar(IdVenta, IdSucursal, IdTipoPrecio, TipoVenta, Cliente, Fecha, IVA, TotalGravado, Total, Direcion, Giro, NIT, NRC, NDocumento);
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
        //limpiar la tabla
        for(int i=0;i < mAgregarDVenta.getRowCount();i++){
            mAgregarDVenta.removeRow(i);
            i-=1;
        }
        txtCodigoBarraVender.setText("");
        txtNombreProductoVender.setText("");
        txtCantidadVender.setText("");
        txtDireccion.setText("");
        txtSumas.setText("");
        txtIVA.setText("");
        txtTotalventaGravado.setText("");
        txtIdVenta.setText("");
        txtNuDocumentoVenta.setText("");
        txtClienteVenta.setText("");
        txtNITventa.setText("");
        txtNRCventa.setText("");
        txtClienteVenta.setText("");
        txtCodigoBarraVender.requestFocus();
        saberCodigoVenta();
        //Para poner la fecha
        dia = calendar.get(Calendar.DATE);
        mes = calendar.get(Calendar.MONTH)+1;
        anio = calendar.get(Calendar.YEAR);
        lblFechaVentaMostrar.setText(anio+"/"+mes+"/"+dia);
        
        int cantidadN=0, mayorN=0;           
        int SaberNDocumento = cmbTipoFacturaParametro.getSelectedIndex();        
        
        if (SaberNDocumento==0) {
            try {           
            rstControladorVenta = controladorventa.NDocumento("FACTURA");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} 
        
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("FACTURA");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
        } else if (SaberNDocumento==1) {
            try {           
            rstControladorVenta = controladorventa.NDocumento("CREDITO FISCAL");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} 
        
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("FACTURA");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
        } else if (SaberNDocumento==2) {            
            try {           
            rstControladorVenta = controladorventa.NDocumento("FACTURA");
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}         
        try {                    
           while (rstControladorVenta.next()) {
                cantidadN = rstControladorVenta.getInt(1);
                if (cantidadN != 0) {
                    rstControladorVenta = null;
                    try {
                        //método en clase ventas
                        rstControladorVenta = controladorventa.NDocumento("LIBRE");
                    } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}                    
                    while (rstControladorVenta.next()) {
                        mayorN = rstControladorVenta.getInt(1) + 1;                                                     
                        txtNuDocumentoVenta.setText(""+mayorN);                        
                    }
                } else if (cantidadN == 0) {
                    txtNuDocumentoVenta.setText("1");
                }                
            }
        } catch (SQLException ex) {JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "AVISO DEL SISTEMA", 0);}//TERMINA METODO PARA BUSCAR IDCOMPRA       
        }
    }//GEN-LAST:event_btnVenderActionPerformed

    private void btnAgregarProductoVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoVentaMouseEntered

    private void btnAgregarProductoVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoVentaMouseExited

    private void btnAgregarProductoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoVentaActionPerformed
        tipoventa = 1;
        CodigoBarraVender = txtCodigoBarraVender.getText();
        int posicioncmbTipoFactura=cmbTipoFacturaParametro.getSelectedIndex();
        //CON FACTURA//
        if (posicioncmbTipoFactura==0) {
            // para saber si se repite el producto
            if (tblRegistrarVenta.getRowCount()>0) {
                int i = 0;
                while (encontrar==false&&i<tblRegistrarVenta.getRowCount()) {
                    encontrar = tblRegistrarVenta.getValueAt(i, 0).equals(CodigoBarraVender);
                    i=1+1;
                }
            }
            //si no hay producTO repetido se realiza esta opcion//
            if (encontrar == false) {
                Punitario = (Punitario*1.13)/(1 - saberTipoUtilidadVenta);
                SubTotalVenta = Punitario * (Double.parseDouble(txtCantidadVender.getText()));

                String AgregarDVenta[] = new String[5];
                AgregarDVenta[0] = txtCodigoBarraVender.getText();
                AgregarDVenta[1] = txtNombreProductoVender.getText();
                AgregarDVenta[2] = txtCantidadVender.getText();
                AgregarDVenta[3] = String.valueOf((df.format(Punitario)));
                AgregarDVenta[4] = String.valueOf((df.format(SubTotalVenta)));
                mAgregarDVenta.addRow(AgregarDVenta);

                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }
            //si el producto se repite hace esta opcion
            else {
                boolean buscar = false;
                int j=0, CantidadActualizada;
                double NuevoValor;
                while (buscar == false) {
                    buscar = tblRegistrarVenta.getValueAt(j, 0).equals(CodigoBarraVender);
                    j++;
                }

                CantidadActualizada = (Integer.parseInt(tblRegistrarVenta.getValueAt(j-1, 2).toString())) + (Integer.parseInt(txtCantidadVender.getText()));
                Punitario = (Punitario*1.13)/(1 - saberTipoUtilidadVenta);

                NuevoValor = (Punitario * Double.parseDouble(txtCantidadVender.getText())) + (Double.parseDouble(tblRegistrarVenta.getValueAt(j-1, 4).toString()));
                NuevoValor = Double.parseDouble(df.format(NuevoValor));
                tblRegistrarVenta.setValueAt(CantidadActualizada, j-1, 2);
                tblRegistrarVenta.setValueAt(NuevoValor, j-1, 4);

                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }//finaliza la actualizacion de tupla
            encontrar=false;
            int filas = tblRegistrarVenta.getRowCount(), iteracion=0;
            double total=0;
            while (iteracion<filas){
                total+=Double.parseDouble(String.valueOf(tblRegistrarVenta.getValueAt(iteracion, 4)));
                iteracion++;
            }
            txtSumas.setText("$"+total);
            txtTotalventaGravado.setText("$"+total);
        }
        //(TIPO FACTURA)//
        //***************************************************************************//
        //ICREDITO  FISCAL//
        if (posicioncmbTipoFactura==1) {
            // para saber si se repite el producto
            if (tblRegistrarVenta.getRowCount()>0) {
                int i = 0;
                while (encontrar==false&&i<tblRegistrarVenta.getRowCount()) {
                    encontrar = tblRegistrarVenta.getValueAt(i, 0).equals(CodigoBarraVender);
                    i=i+1;
                }
            }
            //si no hay producto repetido se realiza esta opcion//
            if (encontrar == false) {
                Punitario = Punitario /(1 - saberTipoUtilidadVenta);
                SubTotalVenta = Punitario * (Double.parseDouble(txtCantidadVender.getText()));

                String AgregarDVenta[] = new String[5];
                AgregarDVenta[0] = txtCodigoBarraVender.getText();
                AgregarDVenta[1] = txtNombreProductoVender.getText();
                AgregarDVenta[2] = txtCantidadVender.getText();
                AgregarDVenta[3] = String.valueOf((df.format(Punitario)));
                AgregarDVenta[4] = String.valueOf((df.format(SubTotalVenta)));
                mAgregarDVenta.addRow(AgregarDVenta);

                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }
            //si el producto se repite hace esta opcion
            else {
                boolean buscar = false;
                int j=0, CantidadActualizada, CantidadAntigua, CantidadNueva;
                double NuevoValor;
                while (buscar == false) {
                    buscar = tblRegistrarVenta.getValueAt(j, 0).equals(CodigoBarraVender);
                    j++;
                }
                Punitario = (Punitario)/(1 - saberTipoUtilidadVenta);
                SubTotalVenta = Punitario * (Double.parseDouble(txtCantidadVender.getText()));

                CantidadActualizada = (Integer.parseInt(tblRegistrarVenta.getValueAt(j-1, 2).toString())) + (Integer.parseInt(txtCantidadVender.getText()));
                NuevoValor = (Punitario * Double.parseDouble(txtCantidadVender.getText())) + (Double.parseDouble(tblRegistrarVenta.getValueAt(j-1, 4).toString()));
                NuevoValor = Double.parseDouble(df.format(NuevoValor));
                tblRegistrarVenta.setValueAt(CantidadActualizada, j-1, 2);
                tblRegistrarVenta.setValueAt(NuevoValor, j-1, 4);

                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }//finaliza la actualizacion de tupla

            encontrar=false;
            int filas = tblRegistrarVenta.getRowCount(), iteracion=0;
            double total=0;
            while (iteracion<filas){
                total+=Double.parseDouble(String.valueOf(tblRegistrarVenta.getValueAt(iteracion, 4)));
                iteracion++;
            }
            double TotalGravadoVentaIVA=0, TotalGravadoVentamasIVA=0, TotalVentaGravadoAgregar=0;
            TotalGravadoVentaIVA = total * 1.13;
            TotalGravadoVentaIVA = TotalGravadoVentaIVA - total;
            TotalGravadoVentaIVA = Double.parseDouble(df.format(TotalGravadoVentaIVA));
            TotalVentaGravadoAgregar = total + TotalGravadoVentaIVA;
            TotalVentaGravadoAgregar = Double.parseDouble(df.format(TotalVentaGravadoAgregar));

            txtSumas.setText("$"+total);
            txtIVA.setText("$"+TotalGravadoVentaIVA);
            txtTotalventaGravado.setText("$"+TotalVentaGravadoAgregar);
        }
        //FINALIZADO (CREDITOFISCAL)//
        //*************************************************************************//\
        //INICIA LIBRE//
        if (posicioncmbTipoFactura==2) {
            // para saber si se repite el producto
            if (tblRegistrarVenta.getRowCount()>0) {
                int i = 0;
                while (encontrar==false&&i<tblRegistrarVenta.getRowCount()) {
                    encontrar = tblRegistrarVenta.getValueAt(i, 0).equals(CodigoBarraVender);
                    i=i+1;
                }
            }
            //si no hay produc repetido se realiza esta opcion//
            if (encontrar == false) {
                Punitario = (Punitario)/(1 - saberTipoUtilidadVenta);
                SubTotalVenta = Punitario * (Double.parseDouble(txtCantidadVender.getText()));

                String AgregarDVenta[] = new String[5];
                AgregarDVenta[0] = txtCodigoBarraVender.getText();
                AgregarDVenta[1] = txtNombreProductoVender.getText();
                AgregarDVenta[2] = txtCantidadVender.getText();
                AgregarDVenta[3] = String.valueOf((df.format(Punitario)));
                AgregarDVenta[4] = String.valueOf((df.format(SubTotalVenta)));
                mAgregarDVenta.addRow(AgregarDVenta);
                TotalVenta = TotalVenta +(SubTotalVenta);

                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }
            //si el producto se repite hace esta opcion
            else {
                boolean buscar = false;
                int j=0, CantidadActualizada;
                double NuevoValor;
                while (buscar == false) {
                    buscar = tblRegistrarVenta.getValueAt(j, 0).equals(CodigoBarraVender);
                    j++;
                }

                Punitario = (Punitario)/(1 - saberTipoUtilidadVenta);
                SubTotalVenta = Punitario * (Double.parseDouble(txtCantidadVender.getText()));

                CantidadActualizada = (Integer.parseInt(tblRegistrarVenta.getValueAt(j-1, 2).toString())) + (Integer.parseInt(txtCantidadVender.getText()));
                NuevoValor = (Punitario * Double.parseDouble(txtCantidadVender.getText())) + (Double.parseDouble(tblRegistrarVenta.getValueAt(j-1, 4).toString()));
                NuevoValor = Double.parseDouble(df.format(NuevoValor));
                tblRegistrarVenta.setValueAt(CantidadActualizada, j-1, 2);
                tblRegistrarVenta.setValueAt(NuevoValor, j-1, 4);
                //hacer que al dar clip se pase al txtCodigoBarraVender
                txtCodigoBarraVender.requestFocus();
                txtCodigoBarraVender.setText("");
                txtNombreProductoVender.setText("");
                txtCantidadVender.setText("");
            }//finaliza la actualizacion de tupla
            encontrar=false;
            int filas = tblRegistrarVenta.getRowCount(), iteracion=0;
            double total=0;
            while (iteracion<filas){
                total+=Double.parseDouble(String.valueOf(tblRegistrarVenta.getValueAt(iteracion, 4)));
                iteracion++;
            }
            double TotalGravadoFiscalIVA=0, TotalGravadoFiscalP=0;
            TotalGravadoFiscalIVA = total * 1.13;
            TotalGravadoFiscalIVA = TotalGravadoFiscalIVA - total;
            txtSumas.setText("$"+total);
            txtTotalventaGravado.setText("$"+total);
        }
        //FINALIZADO (LIBRE)//
    }//GEN-LAST:event_btnAgregarProductoVentaActionPerformed

    private void txtCodigoBarraVenderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarraVenderKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            //busacado de nombre y precio//
            try {
                rstControladorProducto = cp.buscarNYP((String)txtCodigoBarraVender.getText());
            } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
            try {
                while (rstControladorProducto.next()){
                    //guardar en una variable en precio del producto buscado desde la base de datos
                    String PrecioUnitario = rstControladorProducto.getString("Costo");
                    Punitario = Double.parseDouble(PrecioUnitario);
                    txtNombreProductoVender.setText(rstControladorProducto.getString("Nombre"));
                }
            } catch (SQLException ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}
            txtCantidadVender.requestFocus();
        }//finalizado (buscado de nombre y precio)//
    }//GEN-LAST:event_txtCodigoBarraVenderKeyPressed

    private void txtNRCventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNRCventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNRCventaActionPerformed

    private void btnRegresarPaeametroVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarPaeametroVentasActionPerformed
        jpnUtilidadMenuVentasParametros.setVisible(true);
        jpnRegistrarVenta.setVisible(false);
        lblFechaVentaMostrar.setText("");
        txtIdVenta.setText("");
        txtNuDocumentoVenta.setText("");
        txtClienteVenta.setText("");
        txtDireccion.setText("");
        txtGiroVenta.setText("");
        txtNITventa.setText("");
        txtNRCventa.setText("");
        txtCodigoBarraVender.setText("");
        txtNombreProductoVender.setText("");
        txtCantidadVender.setText("");
        txtSumas.setText("");
        txtIVA.setText("");
        txtTotalventaGravado.setText("");

        //limpiar la tabla
        for(int i=0;i < mAgregarDVenta.getRowCount();i++){
            mAgregarDVenta.removeRow(i);
            i-=1;
        }
    }//GEN-LAST:event_btnRegresarPaeametroVentasActionPerformed

    private void tblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComprasMouseClicked
        btnVerDetalle.setEnabled(true);
    }//GEN-LAST:event_tblComprasMouseClicked

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
    //Método para cargar todos los detalleCompras  
    ResultSet dc = null;
    ResultSet dcNom = null;
    String Nom = "";
     ControladorCompra cp = new ControladorCompra();
     ControladorProducto cP = new ControladorProducto();
                String IdCompra = modeloCompra.getValueAt(tblCompras.getSelectedRow(), 0).toString();
                String IdProveedor = modeloCompra.getValueAt(tblCompras.getSelectedRow(), 1).toString();
                String Fecha= modeloCompra.getValueAt(tblCompras.getSelectedRow(), 2).toString();
                String Total = modeloCompra.getValueAt(tblCompras.getSelectedRow(), 3).toString();
            txtCodBarraProductos1.setText(""+IdCompra);
            txtNombreProductos1.setText(""+IdProveedor);
            txtTotalCompraDetalle.setText(""+Total);
     
        try {
            dc = cp.ObtenerDetalles(IdCompra);
        } catch (ErrorTienda ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            while(dc.next()){
                String Cod = dc.getString("CodBarra");
                String Can = dc.getString("Cantidad");
                String Cos = dc.getString("CostoUnitario");
               
                dcNom= cP.BuscarProducto(Cod);
               
                while(dcNom.next()){
                Nom = dcNom.getString("Nombre");
                }
                String Subt = String.valueOf((Double.parseDouble(Can))*(Double.parseDouble(Cos)));
                
                modeloDetalleCompra.addRow(new Object[]{Cod,Nom,Can,Cos,Subt});
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
            
         tblDetalleCompra.setModel(modeloDetalleCompra);

    
    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void btnAtrasDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasDetalleCompraActionPerformed
btnVerDetalle.setEnabled(false);
    }//GEN-LAST:event_btnAtrasDetalleCompraActionPerformed


    private void txtNombreProductoCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoCompraKeyPressed
          if(evt.getKeyCode()== KeyEvent.VK_ENTER){
          txtCantidadCompra.requestFocus();
      }
    }//GEN-LAST:event_txtNombreProductoCompraKeyPressed

    private void txtCantidadCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCompraKeyPressed
      if(evt.getKeyCode()== KeyEvent.VK_ENTER){
          txtCostoProductoCompra.requestFocus();
      }
    }//GEN-LAST:event_txtCantidadCompraKeyPressed

    private void txtCantidadVenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVenderActionPerformed

    private void txtCodBarraCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraCompraKeyTyped
       char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtCodBarraCompraKeyTyped

    private void txtCantidadCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCompraKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtCantidadCompraKeyTyped

    private void txtCostoProductoCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoProductoCompraKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
        getToolkit().beep();
        evt.consume();
        }
        if (c == '.' && txtCostoProductoCompra.getText().contains(".")) {
        evt.consume();
        }
    }//GEN-LAST:event_txtCostoProductoCompraKeyTyped

    private void txtNumDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocumentoKeyTyped
          char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    
    }//GEN-LAST:event_txtNumDocumentoKeyTyped

    private void txtCodBarraProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraProductosKeyTyped
         char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    
    }//GEN-LAST:event_txtCodBarraProductosKeyTyped

    private void txtPrecioProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductosKeyTyped
      char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
        getToolkit().beep();
        evt.consume();
        }
        if (c == '.' && txtPrecioProductos.getText().contains(".")) {
        evt.consume();
        }
    }//GEN-LAST:event_txtPrecioProductosKeyTyped

    private void txtNuDocumentoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuDocumentoVentaKeyTyped
      char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtNuDocumentoVentaKeyTyped

    private void txtCodigoBarraVenderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarraVenderKeyTyped
       char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtCodigoBarraVenderKeyTyped

    private void txtCantidadVenderKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVenderKeyTyped
       char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtCantidadVenderKeyTyped

    private void txtNITventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNITventaKeyTyped
      char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtNITventaKeyTyped

    private void txtNRCventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNRCventaKeyTyped
      char c = evt.getKeyChar();
        if (!Character.isDigit(c) ) {
        getToolkit().beep();
        evt.consume();
        }
    }//GEN-LAST:event_txtNRCventaKeyTyped

    private void txtCodBarraProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraProductosKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
        txtNombreProductos.requestFocus();
        }
    }//GEN-LAST:event_txtCodBarraProductosKeyPressed

    private void txtNombreProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductosKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
        txtPrecioProductos.requestFocus();
        }
    }//GEN-LAST:event_txtNombreProductosKeyPressed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        cmbSucursalReporteVenta.removeAllItems();
            cmbSucursalParametro.removeAllItems();
            cmbUtilidadParametro.removeAllItems(); 
                     try {
            rstCSucursal = cSucursal.Obtener();
            rstTipoPrecio = cTipoPrecio.ObtenerTipoPrecio();             
        } catch (ErrorTienda ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);} catch (Exception ex) {           
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }           

        try {
            String IdSucursal = "";
            int IdSucursalVenta = 0;
            //Sucursal
            while (rstCSucursal.next()) {
                mSucursal.addElement(rstCSucursal.getString(2));                 
                IdSucursal = IdSucursal + " "+rstCSucursal.getString(1); 
                mLlenarIdS.addElement(rstCSucursal.getString(1));  
                modeloSucursalRV.addElement(rstCSucursal.getString(2));
            }
            cmbSucursalParametro.setModel(mSucursal);
            cmbSucursalReporteVenta.setModel(modeloSucursalRV); 
            txtSucursalVentaParametro.setText(String.valueOf(mLlenarIdS.getElementAt(0))); 
            IdSucursalVenta = Integer.parseInt(String.valueOf(mLlenarIdS.getElementAt(0)));
            SaberSucursalVentas = IdSucursalVenta;
            //Utilidad
            String Porcentaje = "";
            while (rstTipoPrecio.next()) {
                mUtilidad.addElement(rstTipoPrecio.getString(2)); 
                Porcentaje = Porcentaje + " "+rstTipoPrecio.getString(3); 
                mLlenarPoU.addElement(rstTipoPrecio.getString(3));          
            }
            double PorcentajeVenta=0;
            PorcentajeVenta = Double.parseDouble((String.valueOf(mLlenarPoU.getElementAt(0))));
            saberTipoUtilidadVenta = PorcentajeVenta;
            PorcentajeVenta = PorcentajeVenta *100;
             txtUtilidadVentaParametro.setText(String.valueOf(PorcentajeVenta)+"%");
            cmbUtilidadParametro.setModel(mUtilidad);
        } catch (SQLException ex) {Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);}//finalizado (recorrer)//
        //finalizar()/llenado cmbSucursal, IdVenta y cmbUtilidad/             
    }//GEN-LAST:event_btnVentasActionPerformed


    public void agregarDetalle(){
        detalleCompra[0] = CodBarraPC;    
        detalleCompra[1] = NombrePC;
        detalleCompra[2] = CantidadPC;
        detalleCompra[3] = CostoUnitarioPC; 
    }
    
    public void agregarDetalleCredito(){
        detalleCompraC[0] = CodBarraPC;    
        detalleCompraC[1] = NombrePC;
        detalleCompraC[2] = CantidadPC;
        detalleCompraC[3] = CostoUnitarioPC; 
    }
    
    public void limpiarDetalle(){
        detalleCompra[0] = "";
        detalleCompra[1] = "";
        detalleCompra[2] = "";
        detalleCompra[3] = "";
        detalleCompra[4] = "";
        
    }
    
    public void limpiarCompra(){
        txtCodBarraCompra.setText("");
        txtNombreProductoCompra.setText("");
        txtCantidadCompra.setText("");
        txtCostoProductoCompra.setText("");
        txtCodBarraCompra.requestFocus();
    }
    
        
public boolean validarProductoRegistrado(String CodBarra) throws Exception{
    boolean v = false;
    String c = "";
    ResultSet r = null;
    ControladorProducto cP = new ControladorProducto();
    r = cP.Obtener(CodBarra);
    
    while(r.next()){
        c = r.getString("CodBarra");
    }
    if(c.isEmpty()){
        v = true;
    }else if (!c.isEmpty()){
        v = false;
    }
    return v;
}  
//---------------------------------------------------Compras-----------------------------------------------------------------------    
    public void buscarProductos(){
    
    if(txtProductosBuscar.getText().isEmpty()){
        JOptionPane.showMessageDialog(null, "Ingrese un codigo de producto a buscar");
        txtProductosBuscar.requestFocus();
        }else{
        String codigo=txtProductosBuscar.getText();
        ControladorProducto cp= new ControladorProducto();
        try {
           cp.Obtener(codigo); 
           
           llenarProducto();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "no logra obtener el producto");
            Logger.getLogger(JFRPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    }

    
public void llenarTipoPrecio() throws Exception{
    ControladorTipoPrecio tp=new ControladorTipoPrecio();
    limpiarTablaTipoPrecio();
    ResultSet rs=null;
    rs=tp.ObtenerTipoPrecio();
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe");; 
}    else{         
         try {
            while (rs.next()) {
                String IdTipoPrecio = rs.getString("IdTipoPrecio");
                String Nombre = rs.getString("Nombre");
                String Utilidad = rs.getString("Utilidad");
                modeloTipoPrecio.addRow(new String[]{IdTipoPrecio,Nombre,Utilidad});
                System.out.println("puso el modelo");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo TP");
        }
        jtblTipoDePrecio.setModel(modeloTipoPrecio);
         }    
}  

public void limpiarTablaTipoPrecio(){
 for (int i = 0; i < jtblTipoDePrecio.getRowCount(); i++) {
           modeloTipoPrecio.removeRow(i);
           i-=1;
       }
}
    
public void llenarSucursal() throws Exception{
    ControladorSucursal tp=new ControladorSucursal();
    limpiarTablaSucursal();
    ResultSet rs=null;
    rs=tp.Obtener();
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe"); 
}    else{
         try {
            while (rs.next()) {
                String IdSucursal = rs.getString("IdSucursal");
                String Nombre = rs.getString("Nombre");
                String Direccion = rs.getString("Direccion");
                String Telefono = rs.getString("Telefono");
                modeloSucursal.addRow(new String[]{IdSucursal,Nombre,Direccion,Telefono});
                System.out.println("puso el modelo S");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo S");
        }
        jtblSucursales.setModel(modeloSucursal);
         }   
}  

public void limpiarTablaSucursal(){
 for (int i = 0; i < jtblSucursales.getRowCount(); i++) {
           modeloSucursal.removeRow(i);
           i-=1;
       }
}

public void llenarParametro() throws Exception{
    Parametro tp=new Parametro();
    limpiarTablaParametro();
    ResultSet rs=null;
    rs=tp.Obtener();
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe"); 
}    else{
         try {
            while (rs.next()) {
                String IdSucursal = rs.getString("IdParametro");
                String Nombre = rs.getString("Nombre");
                String Valor = rs.getString("Valor");
                modeloParametro.addRow(new String[]{IdSucursal,Nombre,Valor});
                System.out.println("puso el modelo Param");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo Param");
        }
        jtblParametros.setModel(modeloParametro);
         }   
}  

public void limpiarTablaParametro(){
 for (int i = 0; i < jtblParametros.getRowCount(); i++) {
           modeloParametro.removeRow(i);
           i-=1;
       }
}

public void llenarProducto() throws Exception{
    ControladorProducto tp=new ControladorProducto();
    limpiarTablaProducto();
    ResultSet rs=null;
    ResultSet rs2=null;
    rs=tp.Obtener();
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe"); 
}    else{
         try {
            while (rs.next()) {
                String CodBarra = rs.getString("CodBarra");
                String Nombre = rs.getString("Nombre");
                String Costo = rs.getString("Costo");
                String sucu=rs.getString("IdSucursal");
                String existencia=rs.getString("Cantidad");
//                
//                rs2=tp.ObtenerSucursal(sucu);
//                String nombreSucursal=rs2.getString("nombre");
                modeloProducto.addRow(new String[]{CodBarra,Nombre,Costo,sucu,existencia});
                System.out.println("puso el modelo Producto");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo Producto()");
        }
        jtblProductos.setModel(modeloProducto);
         }   
}  
public void llenarProducto(String producto) throws Exception{
    ControladorProducto tp=new ControladorProducto();
    limpiarTablaProducto();
    ResultSet rs=null;
    rs=tp.Obtener(producto);
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe"); 
}    else{
         try {
            while (rs.next()) {
                String CodBarra = rs.getString("CodBarra");
                String Nombre = rs.getString("Nombre");
                String Costo = rs.getString("Costo");
                 String sucu=rs.getString("IdSucursal");
                String existencia=rs.getString("Cantidad");
                modeloProducto.addRow(new String[]{CodBarra,Nombre,Costo,sucu,existencia});
                System.out.println("puso el modelo Producto");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo Producto de llenar producto(param)");
        }
        jtblProductos.setModel(modeloProducto);
         }   
} 

public void buscarProducto(String C) throws Exception{
 ControladorProducto tp=new ControladorProducto();
    limpiarTablaProducto();
    ResultSet rs=null;
    rs=tp.Buscar(C);
    if (!rs.isBeforeFirst()) { 
             System.out.println("No existe"); 
}    else{
         try {
            while (rs.next()) {
                String CodBarra = rs.getString("CodBarra");
                String Nombre = rs.getString("Nombre");
                String Costo = rs.getString("Costo");
                 String sucu=rs.getString("IdSucursal");
                String existencia=rs.getString("Cantidad");
                modeloProducto.addRow(new String[]{CodBarra,Nombre,Costo,sucu,existencia});
                System.out.println("puso el modelo Producto");       
            }
        } catch (Exception e) {
            throw  new ErrorTienda("No logra poner el modelo Producto del metodo buscarpro(param)");
        }
        jtblProductos.setModel(modeloProducto);
         }
}

public void limpiarTablaProducto(){
 for (int i = 0; i < jtblProductos.getRowCount(); i++) {
           modeloProducto.removeRow(i);
           i-=1;
       }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFRPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFRPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Utilidad1;
    private javax.swing.JButton btnAdministacion;
    private javax.swing.JButton btnAgregarCompra;
    private javax.swing.JButton btnAgregarNuevoProducto;
    private javax.swing.JButton btnAgregarProductoVenta;
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnAtrasDetalleCompra;
    private javax.swing.JButton btnAtrasModificarProveedor;
    private javax.swing.JButton btnAtrasProveedores;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelarCompra;
    private javax.swing.JButton btnCancelarParametro;
    private javax.swing.JButton btnCancelarSucursal;
    private javax.swing.JButton btnCancelarTipoPrecio;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarSucursales;
    private javax.swing.JButton btnEliminarTipoPrecio;
    private javax.swing.JButton btnGuardarCompra;
    private javax.swing.JButton btnGuardarModificarProveedor;
    private javax.swing.JButton btnGuardarParametro;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnGuardarSucursal;
    private javax.swing.JButton btnGuardarTipoPrecio;
    private javax.swing.JButton btnHacerNuevaVenta;
    private javax.swing.JButton btnHacerVenta;
    private javax.swing.JLabel btnHome;
    private javax.swing.JButton btnModificarParametro;
    private javax.swing.JButton btnModificarProducto;
    private javax.swing.JButton btnModificarProveedor;
    private javax.swing.JButton btnModificarSucursal;
    private javax.swing.JButton btnModificarTipoPrecio;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoSucursales;
    private javax.swing.JButton btnNuevoTipoPrecio;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores1;
    private javax.swing.JButton btnRegresarPaeametroVentas;
    private javax.swing.JButton btnSalirProductos;
    private javax.swing.JButton btnVender;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton btnVerReporteVentas;
    private javax.swing.JButton btnVerVentas;
    private javax.swing.ButtonGroup btngFiltroProductos;
    private javax.swing.JComboBox cmbFechasVenta;
    private javax.swing.JComboBox<String> cmbFiltroSucursalCompra;
    private javax.swing.JComboBox cmbProveedorCompra;
    private javax.swing.JComboBox<String> cmbSucursalCompra;
    private javax.swing.JComboBox cmbSucursalParametro;
    private javax.swing.JComboBox cmbSucursalReporteVenta;
    private javax.swing.JComboBox cmbTipoFacturaParametro;
    private javax.swing.JComboBox cmbUtilidadParametro;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator42;
    private javax.swing.JSeparator jSeparator43;
    private javax.swing.JSeparator jSeparator44;
    private javax.swing.JSeparator jSeparator47;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JPanel jpnAdministracion;
    private javax.swing.JPanel jpnAgregarProv;
    private javax.swing.JPanel jpnBarraMenu;
    private javax.swing.JPanel jpnBarraSuperior;
    private javax.swing.JPanel jpnCompras;
    private javax.swing.JPanel jpnCuarto;
    private javax.swing.JPanel jpnDetalleCompra;
    private javax.swing.JPanel jpnMenuVentas;
    private javax.swing.JPanel jpnModificarProveedor;
    private javax.swing.JPanel jpnNuevoProducto;
    private javax.swing.JPanel jpnPrimero;
    private javax.swing.JPanel jpnPrincipal;
    private javax.swing.JPanel jpnProductos;
    private javax.swing.JPanel jpnProveedores;
    private javax.swing.JPanel jpnQuinto;
    private javax.swing.JPanel jpnRegistrarVenta;
    private javax.swing.JPanel jpnRegistroCompra;
    private javax.swing.JPanel jpnReporteVentas;
    private javax.swing.JPanel jpnSegundo;
    private javax.swing.JPanel jpnSubMenu;
    private javax.swing.JPanel jpnTercero;
    private javax.swing.JPanel jpnUtilidadMenuVentasParametros;
    private javax.swing.JPanel jpnVentasReporteParametro;
    private javax.swing.JPanel jpnVerVentasporSucursal;
    private javax.swing.JScrollPane jscpSucursales;
    private javax.swing.JScrollPane jscpTablaParametros;
    private javax.swing.JScrollPane jscpTipoDePrecio;
    private javax.swing.JTable jtblParametros;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTable jtblSucursales;
    private javax.swing.JTable jtblTipoDePrecio;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl21;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl23;
    private javax.swing.JLabel lbl24;
    private javax.swing.JLabel lbl25;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl31;
    private javax.swing.JLabel lbl32;
    private javax.swing.JLabel lbl33;
    private javax.swing.JLabel lbl34;
    private javax.swing.JLabel lbl35;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl41;
    private javax.swing.JLabel lbl42;
    private javax.swing.JLabel lbl43;
    private javax.swing.JLabel lbl44;
    private javax.swing.JLabel lbl45;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lblBotonCerrar;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCodBarraProd;
    private javax.swing.JLabel lblCostoProductoCompra;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFechaCompra;
    private javax.swing.JTextField lblFechaVentaMostrar;
    private javax.swing.JLabel lblFechaVentas;
    private javax.swing.JLabel lblFiltrarCompra;
    private javax.swing.JLabel lblGiroVenta;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblIVA1;
    private javax.swing.JLabel lblIdCompra;
    private javax.swing.JLabel lblIvaCompra;
    private javax.swing.JLabel lblListadoCompras;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMitad;
    private javax.swing.JLabel lblMitad2;
    private javax.swing.JLabel lblMitad3;
    private javax.swing.JLabel lblMitad4;
    private javax.swing.JLabel lblMitad5;
    private javax.swing.JLabel lblNITventa;
    private javax.swing.JLabel lblNRCventa;
    private javax.swing.JLabel lblNomProd;
    private javax.swing.JLabel lblPercepcionCompra;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblProveedores11;
    private javax.swing.JLabel lblProveedores12;
    private javax.swing.JLabel lblProveedores13;
    private javax.swing.JLabel lblProveedores3;
    private javax.swing.JLabel lblProveedores4;
    private javax.swing.JLabel lblProveedores5;
    private javax.swing.JLabel lblProveedores6;
    private javax.swing.JLabel lblProveedores7;
    private javax.swing.JLabel lblProveedores8;
    private javax.swing.JLabel lblProveedores9;
    private javax.swing.JLabel lblSubtotalCompra;
    private javax.swing.JLabel lblSucursalCompra;
    private javax.swing.JLabel lblSucursalMenuVenta;
    private javax.swing.JLabel lblTotalCompra;
    private javax.swing.JLabel lbltxtTipoCompra;
    private javax.swing.JTable tblCompra;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblDetalleCompra;
    private javax.swing.JTable tblMenuVentas;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTable tblRegistrarVenta;
    private javax.swing.JTable tblReporteVentas;
    private javax.swing.JPanel tjpnlParametros;
    private javax.swing.JPanel tjpnlSucursales;
    private javax.swing.JPanel tjpnlTipoPrecio;
    private javax.swing.JTabbedPane tpnlAdministracion;
    private javax.swing.JTextField txtCantidadCompra;
    private javax.swing.JTextField txtCantidadVender;
    private javax.swing.JTextField txtClienteVenta;
    private javax.swing.JTextField txtCodBarraCompra;
    private javax.swing.JTextField txtCodBarraProductos;
    private javax.swing.JTextField txtCodBarraProductos1;
    private javax.swing.JTextField txtCodigoBarraVender;
    private javax.swing.JTextField txtCostoProductoCompra;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JLabel txtDireccionActualProveedor;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDireccionSucursal;
    private javax.swing.JTextField txtFechaCompra;
    private javax.swing.JTextField txtGiroVenta;
    private javax.swing.JTextField txtIDProveedor;
    private javax.swing.JTextField txtIDProveedor1;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtIdCompra;
    private javax.swing.JTextField txtIdParametro;
    private javax.swing.JTextField txtIdSucursal;
    private javax.swing.JTextField txtIdTipoPrecio;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtImpuestosVentas;
    private javax.swing.JTextField txtIvaCompra;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNITventa;
    private javax.swing.JTextField txtNRCventa;
    private javax.swing.JLabel txtNitActualProveedor;
    private javax.swing.JLabel txtNombreActualProveedor1;
    private javax.swing.JTextField txtNombreParametro;
    private javax.swing.JTextField txtNombreProductoCompra;
    private javax.swing.JTextField txtNombreProductoVender;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtNombreProductos1;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNombreSucursal;
    private javax.swing.JTextField txtNombreTipoPrecio;
    private javax.swing.JTextField txtNuDocumentoVenta;
    private javax.swing.JTextField txtNuevoDireccionProveedor;
    private javax.swing.JTextField txtNuevoNIT;
    private javax.swing.JTextField txtNuevoNombreProveedor;
    private javax.swing.JTextField txtNuevoTelefonoProveedor;
    private javax.swing.JTextField txtNumDocumento;
    private javax.swing.JTextField txtParametroParametro;
    private javax.swing.JTextField txtPercepcionCompra;
    private javax.swing.JTextField txtPrecioProductos;
    private javax.swing.JTextField txtProductosBuscar;
    private javax.swing.JTextField txtProductosBuscar1;
    private javax.swing.JTextField txtSubtotalCompra;
    private javax.swing.JTextField txtSucursalVentaParametro;
    private javax.swing.JTextField txtSumas;
    private javax.swing.JLabel txtTelefonoActualProveedor;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtTelefonoSucursal;
    private javax.swing.JTextField txtTotalCompra;
    private javax.swing.JTextField txtTotalCompraDetalle;
    private javax.swing.JTextField txtTotalventaGravado;
    private javax.swing.JTextField txtUtilidadTipoPrecio;
    private javax.swing.JTextField txtUtilidadVentaParametro;
    private javax.swing.JTextField txtVentasGravadas;
    private javax.swing.JTextField txtVentasNetas;
    // End of variables declaration//GEN-END:variables

    private void setVisible(JPopupMenu MenuEmergente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
