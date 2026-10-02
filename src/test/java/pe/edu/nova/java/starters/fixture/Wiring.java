package pe.edu.nova.java.starters.fixture;

/**
 * Hace las veces de un starter de Nova, que cablea el framework. Vive bajo
 * {@code pe.edu.nova.java.starters}, y las reglas no lo cuentan como una librería sin framework.
 */
public final class Wiring {

    private Wiring() {}

    /**
     * Un valor cualquiera, para que haya algo que llamar.
     *
     * @return el nombre del cableado
     */
    public static String name() {
        return "wiring";
    }
}
