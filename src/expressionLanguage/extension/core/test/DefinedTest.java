package expressionLanguage.extension.core.test;

import java.util.Map;

/**
 * Implementation for the test function 'defined'.
 *
 * <p>
 * Inversion of 'null' test function to provide better compatibility with the
 * original twig version and JTwig.
 *
 */
public class DefinedTest extends NullTest {

    @Override
    public boolean apply(Object input, Map<String, Object> args) {
        return !super.apply(input, args);
    }

}
