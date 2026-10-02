package pe.edu.nova.java.archunit.fixtures.compliant.controller;

import pe.edu.nova.java.archunit.fixtures.compliant.dto.ItemResponse;
import pe.edu.nova.java.archunit.fixtures.compliant.entity.Item;
import pe.edu.nova.java.archunit.fixtures.compliant.service.ItemService;

/** Solo traduce: llama al servicio y arma la respuesta. */
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
        final Item item = service.find(id);
        return new ItemResponse(item.id(), item.quantity());
    }
}
