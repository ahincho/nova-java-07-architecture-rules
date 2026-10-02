package pe.edu.nova.java.archunit.fixtures.leaky.config;

/** Un paquete del propio servicio que no es una de las cinco capas. */
public final class Settings {

    /**
     * Un valor cualquiera, para que haya algo que llamar.
     *
     * @return el nombre de la configuración
     */
    public String name() {
        return "settings";
    }
}
