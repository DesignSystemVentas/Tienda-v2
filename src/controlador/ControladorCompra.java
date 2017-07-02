package controlador;
// @author Daniel Murillo

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Compra;

public class ControladorCompra {
    Conexion cn = new Conexion();
    
     
     //Metodo para filtrar las compras de acuerdo a la sucursal
    public ResultSet ObtenerComprasFiltro(int IdSucursal) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM compra Where IdSucursal='"+IdSucursal+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("No logra obtener datos de Compras"); 
         }finally{
         //cn.desconectar();
         }
         return d;
     }
     
     
    public ResultSet mayorRegistro() throws ErrorTienda {
         ResultSet mayor = null;
        try{
        cn.conectar();     
        mayor = cn.getValores("SELECT MAX(IdCompra) FROM compra");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("No logra obtener datos de proveedores");        
        } 
        return mayor;
                
    }
    
    
    public void Agregar(Object p[]) throws SQLException, ClassNotFoundException, ErrorTienda, ErrorTienda {
        
        try{
        cn.conectar();      
        cn.UID("INSERT INTO compra(IdCompra,IdProveedor,IdSucursal,Fecha,TipoCompra,NumDocumento,Subtotal,IVA,Percepcion,Total) VALUES(" + p[0] + "," + p[1] + "," + p[2] + ",'" + p[3] + "','" + p[4] +"','" + p[5] +"'," + p[6] +"," + p[7] +"," + p[8] +"," + p[9] +")");
        } catch (Exception ex){
            throw new ErrorTienda("Insertar" + ex.getMessage());
        }finally{
            cn.desconectar();    
        }
        
    }
   /* 
    public static void Agregar(Compra){
        
        
    }
    
    public static void ActualizarInventario(Compra){//Actualiza las existencias de productos en la base de 
                                                    //datos a partir de una compra nueva realizada
                                                    //y en la sucursal específica seleccionada.

        
        
    }
    
    public static void Actualizar(DetalleCompra[]){//Actualiza los precios promedios de los productos 
                                                   //detallados en la lista de compras y 
                                                   //que este método recibe como parametro.

        
    }
    
    public static int ObtenerCompra(){
        
        return c;
    }
    
    */
}
