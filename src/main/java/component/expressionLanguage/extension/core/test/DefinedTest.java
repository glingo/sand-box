package component.expressionLanguage.extension.core.test;

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
    public boolean apply(Object input) {
        return !super.apply(input);
    }

}
