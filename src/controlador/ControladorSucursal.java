package controlador;

import java.sql.ResultSet;
import modelo.Sucursal;


/**
 *
 * @author yisus
 */
public class ControladorSucursal {
 
    Conexion cn=new Conexion();
     public void AgregarSucursal(Object t[]) throws Exception{

         try {
            cn.conectar();
            cn.UID("INSERT into sucursal(Nombre,Direccion,Telefono) Values('"+t[1]+"','"+t[2]+"','"+t[3]+"');");
            cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra ingresar sucursal");
         }finally{
         cn.desconectar();
         } 
         
     
     }
     
     public void ModificarSucursal(Object t[]) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE sucursal SET Nombre='" + t[1]+ "',Direccion='" + t[2]+"',Telefono='" + t[3]+"'  WHERE IdSucursal='" + t[0] + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra actualizar sucursal");
         }finally{
         cn.desconectar();
         }
     }
     
     public void EliminarSucursal(Object s) throws  Exception{
         try {
         cn.conectar();
         cn.UID("DELETE FROM sucursal WHERE IdSucursal='" + s + "'");
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
         d=cn.getValores("SELECT * FROM sucursal WHERE IdSucursal='"+s+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos sucursal");  
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     
     public ResultSet ObtenerId(String s) throws Exception{
         ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM sucursal WHERE Nombre='"+s+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos sucursal");  
         }finally{
        // cn.desconectar();
         }
         return d;
     }
     
     
     
     public ResultSet ObtenerSucursal(int t) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM sucursal WHERE IdSucursal='"+t+"'");    
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
        datos = cn.getValores("SELECT * FROM sucursal");
        
        System.out.println("Exito en extraer datos de sucursales");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("no logra obtener datos de sucursales");        
        }        
        return datos;
    }
  }
