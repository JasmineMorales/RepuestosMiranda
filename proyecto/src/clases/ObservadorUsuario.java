package clases;

import interaz.DialogodeMensaje;

public class ObservadorUsuario implements Observador{
    
    @Override
    public void actualizar(String nombreTrabajador) {
        DialogodeMensaje dialogo = new DialogodeMensaje();
        dialogo.setContenido("Notificación", "Agregar usuario de "+ nombreTrabajador, 2);
        dialogo.setVisible(true);
    }
}