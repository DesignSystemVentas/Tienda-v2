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
     public void Agregar(Object p[]) throws Exception{

         try {
            cn.conectar();
            cn.UID("INSERT into producto(CodBarra,Nombre,Costo) Values("+p[0]+",'"+p[1]+"',"+p[2]+");");
            cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra ingresar producto");
         }finally{
         cn.desconectar();
         } 
         
     
     }
     
     public void Modificar(Object t[]) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE producto SET Nombre='" + t[1] + "',Costo='" + t[2]+"'  WHERE CodBarra='" + t[0] + "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra actualizar producto");
         }finally{
         cn.desconectar();
         }
     }
     
     public void Eliminar(Object p) throws  Exception{
         try {
         cn.conectar();
         cn.UID("DELETE FROM producto WHERE CodBarra='" + p + "'");
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
         d=cn.getValores("SELECT * FROM producto WHERE CodBarra='"+p+"'");    
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
         d=cn.getValores("SELECT * FROM producto WHERE CodBarra='"+p+"'");    
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
         d=cn.getValores("SELECT * FROM producto,inventario");    
         } catch (Exception e) {
             cn.desconectar();
             throw new ErrorTienda("no logra obtener datos producto ");  
         }finally{
         
         }
         return d;
     }
     //VIZCARRA//     
     public ResultSet buscarNYP(String CodBarra) throws ErrorTienda {
         Conexion cn = new Conexion();
        try{
            return (cn.getValores("SELECT * FROM producto WHERE CodBarra = '" + CodBarra + "'"));
        } catch (Exception ex){
            throw new ErrorTienda("Insertar" + ex.getMessage());
        }        
    }     
     //VIZCARRA//
 public ResultSet ObtenerSucursal(String p) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM producto WHERE CodBarra='"+p+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos producto ");  
         }finally{
         cn.desconectar();
         }
         return d;
     }





}

