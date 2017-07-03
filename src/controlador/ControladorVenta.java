
package controlador;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ControladorVenta {
    
    public ResultSet llenarVenta(int IdSucursal) throws ErrorTienda{
         try  {
             Conexion cn = new Conexion();
             return (cn.getValores("SELECT IdVenta, IdSucursal, TipoVenta, IdTipoPrecio, Cliente, Fecha, IVA, TotalGravado, Total, Direccion, Giro, NIT, NRC, NDocumento FROM venta WHERE IdSucursal = '" + IdSucursal + "'"));
//                                                         return (cn.getValores("SELECT * FROM venta WHERE MONTH(Fecha) = '" + Fecha + "'"));
         }catch(ArithmeticException ex) {
             throw new ErrorTienda("Error al traer datos de la bs en la tabla ventas" + ex.getMessage());           
         }
        
    }
   
  public void Agregar(int IdVenta, int IdSucursal, int IdTipoPrecio, String TipoVenta, String Cliente, String Fecha, double IVA, double TotalGravado, double Total, String Direccion, String Giro, String NIT, String NRC, String NDocumento) throws ErrorTienda{
             try {         
             Conexion cn = new Conexion();
         cn.UID("INSERT INTO venta(IdVenta, IdSucursal, IdTipoPrecio, TipoVenta,  Cliente, Fecha, IVA, TotalGravado, Total,Direccion, Giro, NIT, NRC, NDocumento) "
                + "VALUES('" + IdVenta + "','" + IdSucursal + "','" + IdTipoPrecio + "','" + TipoVenta + "','" + Cliente + "','" + Fecha + "','" + IVA + "','" + TotalGravado + "','" + Total + "','" + Direccion + "','" + Giro + "','" + NIT + "','" + NRC + "','" + NDocumento + "')");
       } catch (ArithmeticException ex) {throw new ErrorTienda("Error al insertar la venta realizada" + ex.getMessage());}
    }
    
  public ResultSet ObtenerIdVenta() throws ErrorTienda {        
        
        try{
        Conexion cn = new Conexion();        
        return (cn.getValores("SELECT COUNT(IdVenta) FROM venta"));
        } catch (Exception ex){
            throw new ErrorTienda("Obtener IdVenta" + ex.getMessage());            
        }
        }

  
   public ResultSet ObtenerVentas(String Fecha) throws ErrorTienda {        
       try{
        Conexion cn = new Conexion();        
        return (cn.getValores("SELECT * FROM venta WHERE MONTH(Fecha) = '" + Fecha + "'"));
        } catch (Exception ex){
            throw new ErrorTienda("Obtener IdVenta" + ex.getMessage());            
        }
   }
public ResultSet NDocumento(String TipoVenta) throws ErrorTienda {        
        
        try{
        Conexion cn = new Conexion();        
        return (cn.getValores("SELECT COUNT(NDocumento) FROM venta WHERE TipoVenta = '" + TipoVenta + "'"));
        } catch (Exception ex){
            throw new ErrorTienda("Obtener IdVenta" + ex.getMessage());            
        }
        }    
}
