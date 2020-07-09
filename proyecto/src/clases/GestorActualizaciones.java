package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorActualizaciones {
    private List<Observador> observadores = new ArrayList<>();
    
        public void agregarObservador(Observador observador){
        observadores.add(observador);        
    }
    
    public void removerObservador(Observador observador){
        observadores.remove(observador);
    }
    
    public void notificar(String nombreTrabajador){
        observadores.forEach((observador) ->
            {observador.actualizar(nombreTrabajador);});
    }
    
}
