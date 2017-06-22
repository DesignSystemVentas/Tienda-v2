
package controlador;

import java.sql.ResultSet;

/**
 *
 * @author Vizcarra
 */
public class ControladorTipoPrecio {
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
