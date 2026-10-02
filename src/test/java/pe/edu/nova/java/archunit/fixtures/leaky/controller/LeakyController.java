package pe.edu.nova.java.archunit.fixtures.leaky.controller;

import pe.edu.nova.java.archunit.fixtures.leaky.config.Settings;
import pe.edu.nova.java.libs.fixture.FixtureError;
import pe.edu.nova.java.starters.fixture.Wiring;

/**
 * Un controlador que se sale de las capas: llega a un paquete propio y a un starter de Nova. También usa la
 * librería de Nova, que sí puede.
 */
public final class LeakyController {

    /**
     * Describe algo con lo que no debería tocar.
     *
     * @param id el identificador
     * @return un texto
     */
    public String describe(final String id) {
        if (id.isBlank()) {
            throw FixtureError.of("el id es obligatorio");
        }
        return new Settings().name() + Wiring.name();
    }
}
