package pe.edu.nova.java.archunit.fixtures.compliant.service;

import pe.edu.nova.java.archunit.fixtures.compliant.entity.Item;
import pe.edu.nova.java.archunit.fixtures.compliant.repository.ItemRepository;
import pe.edu.nova.java.libs.fixture.FixtureError;

/** El caso de uso. Lanza lo que salió mal como un error de la librería de Nova, no como un status HTTP. */
public final class ItemService {

    private final ItemRepository items;

    /**
     * Crea el servicio.
     *
     * @param items los ítems guardados
     */
    public ItemService(final ItemRepository items) {
        this.items = items;
    }

    /**
     * Busca un ítem.
     *
     * @param id el identificador
     * @return el ítem
     * @throws FixtureError si el ítem no existe
     */
    public Item find(final String id) throws FixtureError {
        final Item item = items.findById(id);
        if (item == null) {
            throw FixtureError.of("el ítem " + id + " no existe");
        }
        return item;
    }
}
