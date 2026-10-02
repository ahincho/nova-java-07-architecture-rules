package pe.edu.nova.java.archunit.fixtures.compliant.entity;

import pe.edu.nova.java.libs.fixture.FixtureError;

/** Un ítem que defiende su propio invariante con un error de la librería de Nova, sin framework. */
public record Item(String id, int quantity) {

    /**
     * Crea el ítem.
     *
     * @param id el identificador
     * @param quantity las unidades, que no pueden ser negativas
     */
    public Item {
        if (quantity < 0) {
            throw FixtureError.of("la cantidad no puede ser negativa");
        }
    }
}
