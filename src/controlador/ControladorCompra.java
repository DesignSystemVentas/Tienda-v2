package controlador;
// @author Daniel Murillo

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Compra;

public class ControladorCompra {
  
    
  
  
    public void Agregar(Object p[]) throws SQLException, ClassNotFoundException, ErrorTienda, ErrorTienda {
        Conexion cn = new Conexion();
          try{
        cn.conectar();      
        cn.UID("INSERT INTO Compra(IdCompra,Fecha,IdProveedor,IdSucursal,TipoCompra,NumDocumento,Subtotal,IVA,Percepcion,Total) VALUES('" + p[0] + "','" + p[1] + "','" + p[2] + "','" + p[3] + "','" + p[4] +"','" + p[5] +"','" + p[6] +"','" + p[7] +"','" + p[8] +"')");
       // cn.desconectar();
        } catch (Exception ex){
            throw new ErrorTienda("Insertar" + ex.getMessage());
        }finally{cn.desconectar();        }
        
        
        
        
    }
    
    public static void ActualizarInventario(){//Actualiza las existencias de productos en la base de 
                                                    //datos a partir de una compra nueva realizada
                                                    //y en la sucursal específica seleccionada.

        
        
    }
    
    public static void Actualizar(){//Actualiza los precios promedios de los productos 
                                                   //detallados en la lista de compras y 
                                                   //que este método recibe como parametro.

        
    }
    
  
    
   
        
}