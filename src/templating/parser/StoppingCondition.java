package templating.parser;

import templating.token.Token;

/**
 * Implementations of this class are provided by the TokenParsers and handed to
 * the main Parser. The main parser will parse some of the template until the
 * stopping condition evaluates to true; at this point responsibility is
 * transferred back to the TokenParser.
 */
public interface StoppingCondition {

    boolean evaluate(Token data);
}
