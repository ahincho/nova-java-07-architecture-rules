package pe.edu.nova.java.archunit.fixtures.compliant.dto;

/**
 * La respuesta de un ítem.
 *
 * @param id el identificador
 * @param quantity las unidades
 */
public record ItemResponse(String id, int quantity) {}
