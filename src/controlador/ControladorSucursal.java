package controlador;

import java.sql.ResultSet;


/**
 *
 * @author yisus
 */
public class ControladorSucursal {
 
    
    //ronald - metodo para rellenar combobox de compras    
   //metodo utilizado en compras, dont move
    public ResultSet Obtener() throws Exception{
        Conexion cn = new Conexion();
        ResultSet datos = null;
        try{
        cn.conectar();
        datos = cn.getValores("SELECT * FROM Sucursal");
        
        System.out.println("Exito en extraer datos de sucursales");
        } catch (Exception e){
            cn.desconectar();
            throw new ErrorTienda("no logra obtener datos de sucursales");        
        }        
        return datos;
    }
}
