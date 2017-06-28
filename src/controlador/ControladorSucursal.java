package controlador;

import java.sql.ResultSet;
import modelo.Sucursal;


/**
 *
 * @author yisus
 */
public class ControladorSucursal {
 
    Conexion cn=new Conexion();
     public void AgregarSucursal(Sucursal s) throws Exception{

         try {
            cn.conectar();
            cn.UID("INSERT into Sucursal(Nombre,Direccion,Telefono) Values("+s.getNombre()+","+s.getDireccion()+","+s.getTelefono()+");");
            cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra ingresar sucursal");
         }finally{
         cn.desconectar();
         } 
         
     
     }
     
     public void ModificarSucursal(Sucursal s) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE Sucursal SET Nombre='" + s.getNombre() + "',Direccion='" + s.getDireccion()+"',Telefono='" + s.getTelefono()+"'  WHERE IdTipoPrecio='" + s.getIdSucursal() + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra actualizar sucursal");
         }finally{
         cn.desconectar();
         }
     }
     
     public void EliminarSucursal(Sucursal s) throws  Exception{
         try {
         cn.conectar();
         cn.UID("DELETE FROM Sucursal WHERE IdSucursal='" + s.getIdSucursal() + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra eliminar sucursal");
         }finally{
         cn.desconectar();
         }
     }
     
     public ResultSet BuscarSucursal(String s) throws Exception{
         ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Sucursal WHERE IdSucursal='"+s+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos sucursal");  
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     public ResultSet ObtenerSucursal(int t) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Sucursal WHERE IdSucursal='"+t+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos sucursal ");  
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     
     
    //ronald - metodo para rellenar combobox de compras    
   //metodo utilizado en compras, dont move
    public ResultSet Obtener() throws Exception{
        Conexion cn = new Conexion();
        ResultSet datos = null;
        try{
        cn.conectar();
        datos = cn.getValores("SELECT * FROM Sucursal");
        
        System.out.println("Exito en extraer datos de sucursales");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("no logra obtener datos de sucursales");        
        }        
        return datos;
    }
    //vizcarra
    public ResultSet llenarSucursal() throws ErrorTienda {
         Conexion cn = new Conexion();
         try{
        return (cn.getValores("SELECT * FROM sucursal"));
        } catch (Exception ex){
            throw new ErrorTienda("Error al traer sucursal al combobox" + ex.getMessage());
        }  
    }
    //vizcarra
}
