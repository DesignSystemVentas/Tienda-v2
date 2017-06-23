/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.ResultSet;


/**
 *
 * @author sigfrid
 */
public class Parametro {
    private int IdParametro;
    private String Nombre;
    private String Valor;

    /**
     * @return the IdParametro
     */
    public int getIdParametro() {
        return IdParametro;
    }

    /**
     * @param IdParametro the IdParametro to set
     */
    public void setIdParametro(int IdParametro) {
        this.IdParametro = IdParametro;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the Valor
     */
    public String getValor() {
        return Valor;
    }

    /**
     * @param Valor the Valor to set
     */
    public void setValor(String Valor) {
        this.Valor = Valor;
    }
    
    
    Conexion cn=new Conexion();
    
     
     public void Modificar(Parametro pr) throws Exception{
         
         try {
         cn.conectar();
         cn.UID("UPDATE Parametro SET Nombre='" + pr.getNombre() + "',Valor='" + pr.getValor()+"'  WHERE IdParametro='" + pr.getIdParametro()+ "'");
         cn.desconectar();   
         } catch (Exception e) {
             throw new ErrorTienda("no logra actualizar parametro");
         }finally{
         cn.desconectar();
         }
     }
     public ResultSet Obtener(String pr) throws Exception{
     ResultSet d=null;
         try {
         cn.conectar();
         d=cn.getValores("SELECT * FROM Parametro WHERE IdParametro='"+pr+"'");    
         } catch (Exception e) {
             throw new ErrorTienda("no logra obtener datos Parametro");  
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
        datos = cn.getValores("SELECT * FROM Parametro");
        
        System.out.println("Exito en extraer datos de parametro");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("no logra obtener datos de parametros");        
        }        
        return datos;
}
}
