/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facadeshop;

import controlador.ErrorTienda;
import frame.JFRPrincipal;
import java.sql.SQLException;
//import frame.Splash;


/**
 *
 * @author Jose Lopez Garcia
 */
public class FacadeShop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ErrorTienda, SQLException, ClassNotFoundException {
        JFRPrincipal sp= new JFRPrincipal();
        sp.setVisible(true);
        //Splash sp = new Splash();
        //sp.setVisible(true);
    }
    
}
