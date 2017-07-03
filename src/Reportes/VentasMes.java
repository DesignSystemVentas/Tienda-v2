package Reportes;

public class VentasMes {
    String Dia, Documento, Cliente, NRC, Total;

    public VentasMes(String Dia, String Documento, String Cliente, String NRC, String Total) {
        this.Dia = Dia;
        this.Documento = Documento;
        this.Cliente = Cliente;
        this.NRC = NRC;
        this.Total = Total;
    }

    public String getDia() {
        return Dia;
    }

    public String getDocumento() {
        return Documento;
    }

    public String getCliente() {
        return Cliente;
    }

    public String getNRC() {
        return NRC;
    }

    public String getTotal() {
        return Total;
    }

    public void setDiao(String Dia) {
        this.Dia = Dia;
    }

    public void setDocumento(String Documento) {
        this.Documento = Documento;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public void setNRC(String NRC) {
        this.NRC = NRC;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }
       
}