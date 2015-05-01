package mx.itesm.edu.tidprueba;

/**
 * Created by Luca on 13/02/15.
 */
public class EventoDental {
    private String evento;
    private String fecha;
    private String id;

    public static EventoDental creaEventoFechaEventoId(String fecha,String evento,String id){
        return new EventoDental(evento,fecha,id);
    }
    private EventoDental(String evento, String fecha,String id) {
        this.evento = evento;
        this.fecha = fecha;
        this.id=id;
    }

    public String getEvento() {
        return evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setEvento(String evento) {
        this.evento = evento;
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
