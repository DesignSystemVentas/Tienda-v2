package modelo;

import java.util.Date; //JDBC

/**
 *
 * @author yisus
 */
public class Compra {

    private int IdCompra;
    private Date Fecha;
    private Proveedor PROVEEDOR;
    private int IdSucursal;
    private char TipoCompra;
    private String NomDocumento;
    private double SubTotal;
    private double IVA;
    private double Percepcion;
    private double Total;
    private DetalleCompra []ARTICULOS;

    public int getIdCompra() {
        return IdCompra;
    }

    public void setIdCompra(int IdCompra) {
        this.IdCompra = IdCompra;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Proveedor getPROVEEDOR() {
        return PROVEEDOR;
    }

    public void setPROVEEDOR(Proveedor PROVEEDOR) {
        this.PROVEEDOR = PROVEEDOR;
    }

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int IdSucursal) {
        this.IdSucursal = IdSucursal;
    }

    public char getTipoCompra() {
        return TipoCompra;
    }

    public void setTipoCompra(char TipoCompra) {
        this.TipoCompra = TipoCompra;
    }

    public String getNomDocumento() {
        return NomDocumento;
    }

    public void setNomDocumento(String NomDocumento) {
        this.NomDocumento = NomDocumento;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double SubTotal) {
        this.SubTotal = SubTotal;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double IVA) {
        this.IVA = IVA;
    }

    public double getPercepcion() {
        return Percepcion;
    }

    public void setPercepcion(double Percepcion) {
        this.Percepcion = Percepcion;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public DetalleCompra[] getARTICULOS() {
        return ARTICULOS;
    }

    public void setARTICULOS(DetalleCompra[] ARTICULOS) {
        this.ARTICULOS = ARTICULOS;
    }
    
    
    //MËTODOS
    
    public void CalcularTotal(){//Calcula el monto a pagar por una compra realizada
        
      
        
        
    }
    

    /*public void AgregarItem(DetalleCompra){//Agrega un artículo con sus detalles a través de un objeto 
                                           //DetalleCompra a la lista de 
                                           //objetos de tipo DetalleCompra de la 
                                           //Compra en proceso.
     
    }
    */
    
    
    public void CalcularIva(){//Calcula el monto total de impuesto IVA que 
                              //se pagará por una compra realizada.
        
        
    }
    
    
    public void CalcularPercepcion(){//Calcula el monto total de impuesto de 
                                     //percepción que se pagará por una compra realizada.
        
        
    }
    
    
    
    
    
}
