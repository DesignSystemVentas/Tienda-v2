package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    
}

