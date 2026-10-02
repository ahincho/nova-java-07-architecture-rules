package pe.edu.nova.java.archunit.fixtures;

import com.tngtech.archunit.junit.AnalyzeClasses;
import pe.edu.nova.java.archunit.LayeredArchitectureTest;

/** Un servicio que solo tiene controlador y servicio: sin repositorio, entidad ni DTO. */
@AnalyzeClasses(packages = PartialServiceArchitecture.BASE_PACKAGE)
public class PartialServiceArchitecture extends LayeredArchitectureTest {

    /** El paquete del servicio de ejemplo. */
    public static final String BASE_PACKAGE = "pe.edu.nova.java.archunit.fixtures.partial";

    @Override
    protected String basePackage() {
        return BASE_PACKAGE;
    }
}
