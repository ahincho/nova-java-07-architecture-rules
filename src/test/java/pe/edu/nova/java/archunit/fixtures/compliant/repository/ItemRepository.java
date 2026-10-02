package pe.edu.nova.java.archunit.fixtures.compliant.repository;

import pe.edu.nova.java.archunit.fixtures.compliant.entity.Item;
import pe.edu.nova.java.libs.fixture.FixtureError;

/** Los ítems guardados. Traduce el fallo del almacén a un error de la librería de Nova. */
public final class ItemRepository {

    /**
     * Busca un ítem.
     *
     * @param id el identificador
     * @return el ítem
     */
    public Item findById(final String id) {
        if (id.isEmpty()) {
            throw FixtureError.of("el almacén no responde");
        }
        return new Item(id, 1);
    }
}
