/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import modelo.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import frame.JFRPrincipal;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ControladorProducto {
   Conexion cn= new Conexion();

    public ControladorProducto() {
       List  Lista = new ArrayList<>();
    }

    public ControladorProducto(int codigo, String inv, Double cos, String n){
    
    }
    
public class ProductoControlador {          
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
}