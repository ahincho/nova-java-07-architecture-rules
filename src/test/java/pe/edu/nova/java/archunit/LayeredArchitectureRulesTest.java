package pe.edu.nova.java.archunit;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.testkit.engine.EngineTestKit;
import pe.edu.nova.java.archunit.fixtures.CompliantServiceArchitecture;
import pe.edu.nova.java.archunit.fixtures.LeakyControllerArchitecture;
import pe.edu.nova.java.archunit.fixtures.PartialServiceArchitecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * Evalúa las reglas de {@link LayeredArchitectureTest} sobre servicios de ejemplo, con el motor de ArchUnit,
 * y lee el resultado de cada una.
 *
 * <p>Los servicios de ejemplo están en {@code fixtures}. Unos incumplen las reglas a propósito, así que
 * Gradle no los corre como pruebas: los corre esta clase, uno por uno, y mira qué regla falló y por qué.
 */
class LayeredArchitectureRulesTest {

    private static final String CONTROLLER_ALLOW_LIST = "controllers_depend_only_on_allowed_layers";

    private static final String NO_CLASSES_TO_CHECK = "failed to check any classes";

    @Test
    void aServiceThatFollowsTheLayersPassesEveryRule() {
        final Map<String, TestExecutionResult> rules = evaluate(CompliantServiceArchitecture.class);

        assertTrue(rules.containsKey(CONTROLLER_ALLOW_LIST), "the rules did not run: " + rules.keySet());
        assertEquals(Map.of(), failuresOf(rules));
    }

    @Test
    void aControllerMayUseTheNovaLibrariesButNotTheRestOfNova() {
        final Map<String, String> failures = failuresOf(evaluate(LeakyControllerArchitecture.class));

        final String violations = failures.get(CONTROLLER_ALLOW_LIST);
        assertNotNull(violations, "the controller reached packages it should not have: " + failures.keySet());
        assertTrue(violations.contains("Settings.name()"), violations);
        assertTrue(violations.contains("Wiring.name()"), violations);
        assertFalse(violations.contains("FixtureError"), violations);
    }

    @Test
    void aLayerWithoutClassesFailsInsteadOfPassing() {
        final Map<String, String> failures = failuresOf(evaluate(PartialServiceArchitecture.class));

        assertEquals(
                Set.of(
                        "repositories_must_not_depend_on_services",
                        "entities_must_not_depend_on_any_other_layer",
                        "dtos_must_not_depend_on_entities"),
                failures.keySet());
        failures.forEach((rule, message) ->
                assertTrue(message.contains(NO_CLASSES_TO_CHECK), rule + " failed for another reason: " + message));
    }

    /** Corre las reglas de un servicio de ejemplo y devuelve el resultado de cada una por su nombre. */
    private static Map<String, TestExecutionResult> evaluate(final Class<?> architecture) {
        final Map<String, TestExecutionResult> rules = new TreeMap<>();
        EngineTestKit.engine("archunit")
                .selectors(selectClass(architecture))
                .execute()
                .testEvents()
                .finished()
                .stream()
                .forEach(event -> rules.put(
                        event.getTestDescriptor().getDisplayName(), event.getRequiredPayload(TestExecutionResult.class)));
        return rules;
    }

    /** Las reglas que no pasaron, con el mensaje con que fallaron. */
    private static Map<String, String> failuresOf(final Map<String, TestExecutionResult> rules) {
        final Map<String, String> failures = new TreeMap<>();
        rules.forEach((rule, result) -> {
            if (result.getStatus() != SUCCESSFUL) {
                failures.put(
                        rule,
                        result.getThrowable().map(Throwable::getMessage).orElse(result.getStatus().name()));
            }
        });
        return failures;
    }
}
