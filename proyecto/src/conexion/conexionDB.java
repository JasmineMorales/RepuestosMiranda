package conexion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author manue
 */
public class conexionDB {

    private static EntityManagerFactory emf;

    private conexionDB() {
    }

    public static EntityManagerFactory obtenerConexion() {

        if (conexionDB.emf == null) {
            conexionDB.emf = Persistence.createEntityManagerFactory("proyectoPU");
        }
        return emf;
    }
}

