package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import frame.JFRPrincipal;

/**
 *
 * @author yisus
 */
public class ControladorProveedor {
   
    //metodo utilizado en compras, dont move
    public ResultSet Obtener() throws Exception{
        Conexion cn = new Conexion();
        ResultSet datos = null;
        try{
        cn.conectar();     
        datos = cn.getValores("SELECT * FROM Proveedor");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("no logra obtener datos de proveedores");        
        } 
        return datos;
    }
    

Conexion cn= new Conexion();

    public ControladorProveedor() {
       List  Lista = new ArrayList<>();
    }

    public ControladorProveedor(int id, String nombre, String telefono, String direccion, String nit){
    
    }
    
public class ProveedorControlador {
 
}
       public List BuscarP(String codigo) throws Exception{   
        Conexion cn = new Conexion();
        List Lista = new ArrayList<>();
        try{
        cn.conectar();           
            ResultSet rs = cn.getValores("SELECT * FROM proveedor  where IdProveedor='"+codigo+"'");
            while (rs.next()) {
                int id = rs.getInt("IdProveedor");
                String nombre = rs.getString("Nombre");
                String telefono= rs.getString("Telefono");
                String direccion = rs.getString("Direccion");  
                String nit = rs.getString("NIT");       
                ControladorProveedor proveedor = new ControladorProveedor(id, nombre, telefono, direccion, nit);
                Lista.add(proveedor);
            }
        } catch (SQLException e){throw new ErrorTienda("se pordujo un error al obtener el proveedor");
        }finally{cn.desconectar();}
        return Lista; 

    }

    
       public void AgregarP(Object P[]) throws SQLException, ClassNotFoundException, ErrorTienda, ErrorTienda {
        Conexion cn = new Conexion();
        try{
        cn.conectar();  
        cn.UID("INSERT INTO proveedor(IdProveedor,Nombre,Telefono,Direccion,NIT) VALUES(" +P[0]+ "," + P[1] + ",'" + P[2] + "','" + P[3]+ "')");
        System.out.println("se agrego con exito");
        } catch (Exception ex){
            throw new ErrorTienda("Insertar" + ex.getMessage());
        }
        finally{cn.desconectar();}
//        
    }

    public void eliminarProveedor(Object P) throws SQLException, ClassNotFoundException, ErrorTienda, ErrorTienda {
        Conexion cn = new Conexion();
        try{
        cn.conectar();  
        cn.UID("DELETE FROM proveedor WHERE IdProveedor='" + P + "'");
        System.out.println("se elimino con exito");
        } catch (Exception ex){
            throw new ErrorTienda("Eliminar" + ex.getMessage());
        }finally{cn.desconectar();}
        
    }
    
    public void modificarP(Object P[]) throws SQLException, ClassNotFoundException, ErrorTienda, ErrorTienda {
        Conexion cn = new Conexion();
        try{
        cn.conectar();  
        cn.UID("UPDATE proveedor SET Nombre='" + P[1] + "',Telefono='" + P[2]+ "',Direccion='" + P[3] + "',NIT='" + P[4] + "' WHERE IdProveedor='" + P[0]+ "'");
    
        System.out.println("se modifico con exito");
        } catch (Exception ex){
            throw new ErrorTienda("Insertar" + ex.getMessage());
        }finally{cn.desconectar();}
        
    }
    
}



