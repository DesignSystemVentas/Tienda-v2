
package controlador;

import java.sql.ResultSet;

public class ControladorSucursal {
  
    
     public ResultSet llenarSucursal() throws ErrorTienda {
         Conexion cn = new Conexion();
         try{
        return (cn.getValores("SELECT * FROM sucursal"));
        } catch (Exception ex){
            throw new ErrorTienda("Error al traer sucursal al combobox" + ex.getMessage());
        }  
    }
}
