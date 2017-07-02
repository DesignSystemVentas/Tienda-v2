package controlador;
// @author Daniel Murillo

import java.sql.ResultSet;
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
