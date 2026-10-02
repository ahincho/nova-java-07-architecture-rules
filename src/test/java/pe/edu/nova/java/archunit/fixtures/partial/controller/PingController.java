package pe.edu.nova.java.archunit.fixtures.partial.controller;

import pe.edu.nova.java.archunit.fixtures.partial.service.PingService;

/** Un controlador que llama al servicio. */
public final class PingController {

    private final PingService service;

    /**
     * Crea el controlador.
     *
     * @param service el servicio
     */
    public PingController(final PingService service) {
        this.service = service;
    }

    /**
     * Responde.
     *
     * @return la respuesta del servicio
     */
    public String ping() {
        return service.ping();
    }
}
