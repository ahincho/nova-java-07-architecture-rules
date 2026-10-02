package pe.edu.nova.java.archunit.fixtures.partial.service;

/** Un servicio sin persistencia, que no tiene repositorio ni entidad. */
public final class PingService {

    private final String name;

    /**
     * Crea el servicio.
     *
     * @param name el nombre con el que responde
     */
    public PingService(final String name) {
        this.name = name;
    }

    /**
     * Responde.
     *
     * @return la respuesta
     */
    public String ping() {
        return "pong " + name;
    }
}
