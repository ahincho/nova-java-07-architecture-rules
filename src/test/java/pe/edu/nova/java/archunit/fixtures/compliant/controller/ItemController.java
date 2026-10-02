package pe.edu.nova.java.archunit.fixtures.compliant.controller;

import pe.edu.nova.java.archunit.fixtures.compliant.dto.ItemResponse;
import pe.edu.nova.java.archunit.fixtures.compliant.entity.Item;
import pe.edu.nova.java.archunit.fixtures.compliant.service.ItemService;
import pe.edu.nova.java.libs.fixture.FixtureError;

/** Rechaza la entrada con un error de la librería de Nova, como pide ADR-031, y deja el resto al servicio. */
public final class ItemController {

    private final ItemService service;

    /**
     * Crea el controlador.
     *
     * @param service el servicio de ítems
     */
    public ItemController(final ItemService service) {
        this.service = service;
    }

    /**
     * Devuelve un ítem.
     *
     * @param id el identificador
     * @return el ítem
     */
    public ItemResponse find(final String id) {
        if (id.isBlank()) {
            throw FixtureError.of("el id es obligatorio");
        }
        final Item item = service.find(id);
        return new ItemResponse(item.id(), item.quantity());
    }
}
