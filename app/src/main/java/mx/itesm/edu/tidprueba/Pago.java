package mx.itesm.edu.tidprueba;

/**
 * Created by Luck on 5/4/2015.
 */
public class Pago {
    private String cantidad;
    private String desc;
    private String fecha;
    private String id;

        public static Pago creaPagoFechaDescCantId(String fecha,String desc,String cantidad,String id){
        return new Pago(cantidad,desc,fecha,id);
    }
    private Pago(String cantidad, String desc,String fecha,String id) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.desc = desc;
        this.id=id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
