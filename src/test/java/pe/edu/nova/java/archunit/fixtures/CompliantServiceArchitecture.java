package pe.edu.nova.java.archunit.fixtures;

import com.tngtech.archunit.junit.AnalyzeClasses;
import pe.edu.nova.java.archunit.LayeredArchitectureTest;

/** Un servicio con sus cinco capas que cumple todas las reglas. */
@AnalyzeClasses(packages = CompliantServiceArchitecture.BASE_PACKAGE)
public class CompliantServiceArchitecture extends LayeredArchitectureTest {

    /** El paquete del servicio de ejemplo. */
    public static final String BASE_PACKAGE = "pe.edu.nova.java.archunit.fixtures.compliant";

    @Override
    protected String basePackage() {
        return BASE_PACKAGE;
    }
}
