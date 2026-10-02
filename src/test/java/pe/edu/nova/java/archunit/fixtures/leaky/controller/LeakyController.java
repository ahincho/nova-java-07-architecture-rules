package pe.edu.nova.java.archunit.fixtures.leaky.controller;

import pe.edu.nova.java.archunit.fixtures.leaky.config.Settings;
import pe.edu.nova.java.starters.fixture.Wiring;

/** Un controlador que se sale de las capas: llega a un paquete propio y a un starter de Nova. */
public final class LeakyController {

    /**
     * Describe algo con lo que no debería tocar.
     *
     * @return un texto
     */
    public String describe() {
        return new Settings().name() + Wiring.name();
    }
}
