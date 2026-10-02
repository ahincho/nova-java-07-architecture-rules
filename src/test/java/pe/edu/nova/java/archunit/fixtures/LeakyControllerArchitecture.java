package pe.edu.nova.java.archunit.fixtures;

import com.tngtech.archunit.junit.AnalyzeClasses;
import pe.edu.nova.java.archunit.LayeredArchitectureTest;

/** Un servicio cuyo controlador incumple la lista de permitidos. Solo tiene esa capa. */
@AnalyzeClasses(packages = LeakyControllerArchitecture.BASE_PACKAGE)
public class LeakyControllerArchitecture extends LayeredArchitectureTest {

    /** El paquete del servicio de ejemplo. */
    public static final String BASE_PACKAGE = "pe.edu.nova.java.archunit.fixtures.leaky";

    @Override
    protected String basePackage() {
        return BASE_PACKAGE;
    }
}
