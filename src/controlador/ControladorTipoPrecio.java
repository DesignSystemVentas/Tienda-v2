/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.ResultSet;
import modelo.TipoPrecio;

/**
 *
 * @author sigfrid
 */
public class ControladorTipoPrecio {
     Conexion cn=new Conexion();
     
     public void AgregarTipoPrecio(Object t[]) throws Exception{

         try {
            cn.conectar();
            cn.UID("INSERT into tipoprecio(Nombre,Utilidad) Values('"+t[1]+"',"+t[2]+");");
            cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra ingresar tipoPrecio"); 
         }finally{
         cn.desconectar();
         } 
         
     
     }
     
     public void ModificarTipoPrecio(Object t[]) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE tipoprecio SET Nombre='" + t[1] + "',Utilidad='" + t[2]+"' WHERE IdTipoPrecio='" + t[0] + "'");
         cn.desconectar();   
         } catch (Exception e) {
              throw new ErrorTienda("no logra actualizar tipoPrecio"); 
         }finally{
         cn.desconectar();
         }
     }
     
     public void EliminarTipoPrecio(TipoPrecio t) throws  Exception{
         try {
         cn.conectar();
         cn.UID("DELETE FROM tipoprecio WHERE IdTipoPrecio='" + t.getIdTipoPrecio() + "'");
         cn.desconectar();   
         } catch (Exception e) {
            throw new ErrorTienda("no logra liminar tipoPrecio"); 
         }finally{
         cn.desconectar();
         }
     }
     
     public ResultSet Buscar(int t) throws Exception{
         ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM tipoprecio WHERE IdTipoPrecio='"+t+"'");    
         } catch (Exception e) {
              throw new ErrorTienda("no logra obtener datos de tipoPrecio");   
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     public ResultSet ObtenerTipoPrecio(int t) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM tipoprecio WHERE IdTipoPrecio='"+t+"'");    
         } catch (Exception e) {
              throw new ErrorTienda("no logra obtener dato de tipoPrecio");   
         }finally{
         cn.desconectar();
         }
         return d;
     }
     
     public ResultSet ObtenerTipoPrecio() throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM tipoprecio");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos de tipoPrecio"); 
         }finally{
         //cn.desconectar();
         }
         return d;
     }
     //vizcarra//
    public ResultSet llenarUtilidad() throws ErrorTienda {
         Conexion cn = new Conexion();
         try{
        return (cn.getValores("SELECT * FROM tipoprecio"));
        } catch (Exception ex){
            throw new ErrorTienda("Error al traer utilidad al combobox" + ex.getMessage());
        }  
    }
    //vizcarra//
     
}
