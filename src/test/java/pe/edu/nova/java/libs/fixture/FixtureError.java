package pe.edu.nova.java.libs.fixture;

/**
 * Hace las veces de una librería de Nova sin framework, como el módulo de errores por capas de ADR-031
 * ({@code pe.edu.nova.java.libs.api.standard.error}). Vive bajo {@code pe.edu.nova.java.libs}, que es lo
 * que reconocen las reglas, y se escribe aquí para que la prueba no dependa de una librería publicada.
 */
public final class FixtureError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private FixtureError(final String message) {
        super(message);
    }

    /**
     * Crea el error con una fábrica, como las de {@code DomainError} y {@code ApplicationError}.
     *
     * @param message el mensaje para la persona
     * @return el error
     */
    public static FixtureError of(final String message) {
        return new FixtureError(message);
    }
}
