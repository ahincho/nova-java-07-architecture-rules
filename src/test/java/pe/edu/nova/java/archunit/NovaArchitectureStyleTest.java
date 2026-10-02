package pe.edu.nova.java.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de {@link NovaArchitectureStyle} y del cableado de
 * {@link LayeredArchitectureTest}.
 *
 * <p>No evalúan las reglas de ArchUnit: verifican que la enumeración
 * expone los identificadores correctos y que la clase de prueba abstracta
 * se puede heredar sin efectos inesperados. Las reglas se evalúan en
 * {@link LayeredArchitectureRulesTest}.
 */
class NovaArchitectureStyleTest {

    @Test
    void identifiersMatchCliConvention() {
        assertEquals("layered", NovaArchitectureStyle.LAYERED.identifier());
        assertEquals("clean", NovaArchitectureStyle.CLEAN.identifier());
        assertEquals("hexagonal", NovaArchitectureStyle.HEXAGONAL.identifier());
    }

    @Test
    void allStylesAreExposed() {
        final NovaArchitectureStyle[] values = NovaArchitectureStyle.values();
        assertEquals(3, values.length, "expected exactly 3 architectural styles");
        assertNotNull(NovaArchitectureStyle.valueOf("LAYERED"));
        assertNotNull(NovaArchitectureStyle.valueOf("CLEAN"));
        assertNotNull(NovaArchitectureStyle.valueOf("HEXAGONAL"));
    }

    @Test
    void abstractClassCanBeImportedByArchUnit() {
        final JavaClasses imported = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("pe.edu.nova.java.archunit");
        assertTrue(imported.size() >= 2,
                "expected at least LayeredArchitectureTest and "
                        + "NovaArchitectureStyle to be visible to ArchUnit");
    }
}