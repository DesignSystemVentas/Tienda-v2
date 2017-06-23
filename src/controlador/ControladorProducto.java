/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.ResultSet;
import modelo.Producto;

/**
 *
 * @author sigfrid
 */
public class ControladorProducto {
    Conexion cn=new Conexion();
     public void Agregar(Producto p) throws Exception{

         try {
            cn.conectar();
            cn.UID("INSERT into Producto(CodBarra,Nombre,Costo) Values("+p.getCodBarra()+","+p.getNombre()+","+p.getCosto()+");");
            cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra ingresar producto");
         }finally{
         cn.desconectar();
         } 
         
     
     }
     
     public void Modificar(Producto p) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE Producto SET Nombre='" + p.getNombre() + "',Costo='" + p.getCosto()+"'  WHERE CodBarra='" + p.getCodBarra() + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra actualizar producto");
         }finally{
         cn.desconectar();
         }
     }
     
     public void Eliminar(Producto p) throws  Exception{
         try {
         cn.conectar();
         cn.UID("DELETE FROM Producto WHERE CodBarra='" + p.getCodBarra() + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra eliminar producto");
         }finally{
         cn.desconectar();
         }
     }
     
     public ResultSet Buscar(String p) throws Exception{
         ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Producto WHERE CodBarra='"+p+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos producto");  
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     public ResultSet Obtener(String p) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Producto WHERE CodBarra='"+p+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos producto ");  
         }finally{
         cn.desconectar();
         }
         return d;
     }
     public ResultSet Obtener() throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Producto");    
         } catch (Exception e) {
             cn.desconectar();
             throw new ErrorTienda("no logra obtener datos producto ");  
         }finally{
         
         }
         return d;
     }
}
