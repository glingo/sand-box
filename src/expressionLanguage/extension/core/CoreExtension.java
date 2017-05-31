package expressionLanguage.extension.core;

import expressionLanguage.extension.Extension;
import expressionLanguage.extension.core.expression.*;
import expressionLanguage.extension.core.filter.*;
import expressionLanguage.extension.core.token.*;
import expressionLanguage.filter.Filter;
import expressionLanguage.function.Function;
import expressionLanguage.operator.Associativity;
import expressionLanguage.operator.BinaryOperator;
import expressionLanguage.operator.UnaryOperator;
import expressionLanguage.test.Test;
import expressionLanguage.token.parser.TokenParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoreExtension implements Extension {

    @Override
    public List<TokenParser> getTokenParsers() {
        ArrayList<TokenParser> parsers = new ArrayList<>();
        parsers.add(new BlockTokenParser());
        parsers.add(new ExtendsTokenParser());
        parsers.add(new FilterTokenParser());
        parsers.add(new FlushTokenParser());
        parsers.add(new ForTokenParser());
        parsers.add(new IfTokenParser());
        parsers.add(new ImportTokenParser());
        parsers.add(new IncludeTokenParser());
        parsers.add(new MacroTokenParser());
        parsers.add(new ParallelTokenParser());
        parsers.add(new SetTokenParser());
        return parsers;
    }

    @Override
    public List<UnaryOperator> getUnaryOperators() {
        ArrayList<UnaryOperator> operators = new ArrayList<>();
        operators.add(new UnaryOperator("not", 5, UnaryNotExpression.class));
        operators.add(new UnaryOperator("+", 500, UnaryPlusExpression.class));
        operators.add(new UnaryOperator("-", 500, UnaryMinusExpression.class));
        return operators;
    }

    @Override
    public List<BinaryOperator> getBinaryOperators() {
        ArrayList<BinaryOperator> operators = new ArrayList<>();
        operators.add(new BinaryOperator("or", 10, OrExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("and", 15, AndExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("is", 20, PositiveTestExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("is not", 20, NegativeTestExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("contains", 20, ContainsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("==", 30, EqualsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("equals", 30, EqualsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("!=", 30, NotEqualsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator(">", 30, GreaterThanExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("<", 30, LessThanExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator(">=", 30, GreaterThanEqualsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("<=", 30, LessThanEqualsExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("+", 40, AddExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("-", 40, SubtractExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("*", 60, MultiplyExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("/", 60, DivideExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("%", 60, ModulusExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("|", 100, FilterExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("~", 110, ConcatenateExpression.class, Associativity.LEFT));
        operators.add(new BinaryOperator("..", 120, RangeExpression.class, Associativity.LEFT));

        return operators;
    }

    @Override
    public Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new HashMap<>();
        filters.put("abbreviate", new AbbreviateFilter());
        filters.put("abs", new AbsFilter());
        filters.put("capitalize", new CapitalizeFilter());
        filters.put("date", new DateFilter());
        filters.put("default", new DefaultFilter());
        filters.put("first", new FirstFilter());
        filters.put("join", new JoinFilter());
        filters.put("last", new LastFilter());
        filters.put("lower", new LowerFilter());
        filters.put("numberformat", new NumberFormatFilter());
        filters.put("slice", new SliceFilter());
        filters.put("sort", new SortFilter());
        filters.put("rsort", new RsortFilter());
        filters.put("title", new TitleFilter());
        filters.put("trim", new TrimFilter());
        filters.put("upper", new UpperFilter());
        filters.put("urlencode", new UrlEncoderFilter());
        filters.put("length", new LengthFilter());
        filters.put(ReplaceFilter.FILTER_NAME, new ReplaceFilter());
        filters.put(MergeFilter.FILTER_NAME, new MergeFilter());
        return filters;
    }

    @Override
    public Map<String, Test> getTests() {
        Map<String, Test> tests = new HashMap<>();
        tests.put("empty", new EmptyTest());
        tests.put("even", new EvenTest());
        tests.put("iterable", new IterableTest());
        tests.put("map", new MapTest());
        tests.put("null", new NullTest());
        tests.put("odd", new OddTest());
        tests.put("defined", new DefinedTest());
        return tests;
    }

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functions = new HashMap<>();

        /*
         * For efficiency purposes, some core functions are individually parsed
         * by our expression parser and compiled in their own unique way. This
         * includes the block and parent functions.
         */

        functions.put("max", new MaxFunction());
        functions.put("min", new MinFunction());
        functions.put(RangeFunction.FUNCTION_NAME, new RangeFunction());
        return functions;
    }

//    @Override
//    public List<NodeVisitorFactory> getNodeVisitors() {
//        List<NodeVisitorFactory> visitors = new ArrayList<>();
//        visitors.add(new MacroAndBlockRegistrantNodeVisitorFactory());
//        return visitors;
//    }

}
