/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author sigfrid
 */
public class TipoPrecio {
    
    private int IdTipoPrecio;
    private String Nombre;
    private String Utilidad;

    /**
     * @return the IdTipoPrecio
     */
    public int getIdTipoPrecio() {
        return IdTipoPrecio;
    }

    /**
     * @param IdTipoPrecio the IdTipoPrecio to set
     */
    public void setIdTipoPrecio(int IdTipoPrecio) {
        this.IdTipoPrecio = IdTipoPrecio;
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
     * @return the Utilidad
     */
    public String getUtilidad() {
        return Utilidad;
    }

    /**
     * @param Utilidad the Utilidad to set
     */
    public void setUtilidad(String Utilidad) {
        this.Utilidad = Utilidad;
    }
    
}
