package component.expressionLanguage.extension.core.filter;

import component.expressionLanguage.filter.Filter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SliceFilter implements Filter {

    private final List<String> argumentNames = new ArrayList<>();

    public SliceFilter() {
        argumentNames.add("fromIndex");
        argumentNames.add("toIndex");
    }

//    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {

        if (input == null) {
            return null;
        }

        // argument parsing
        Object argFrom = args.get("fromIndex");

        if (argFrom == null) { // defaults to 0
            argFrom = (long) 0;
        } else if (!(argFrom instanceof Number)) {
            String msg = String.format("Argument fromIndex must be a number. Actual type: %s", (argFrom == null ? "null" : argFrom.getClass().getName()));
            throw new IllegalArgumentException(msg);
        }
        int from = ((Number) argFrom).intValue();
        if (from < 0) {
            throw new IllegalArgumentException("fromIndex must be greater than 0");
        }

        Object argTo = args.get("toIndex");

        if (argTo == null) {
            // defaults to input length
            // argTo == null;
        } else if (!(argTo instanceof Number)) {
            String msg = String.format("Argument fromIndex must be a number. Actual type: %s", (argFrom == null ? "null" : argFrom.getClass().getName()));
            throw new IllegalArgumentException(msg);
        }

        int length;
        if (input instanceof List) {
            length = ((List<?>) input).size();
        } else if (input.getClass().isArray()) {
            length = Array.getLength(input);
        } else if (input instanceof String) {
            length = ((String) input).length();
        } else {
            throw new IllegalArgumentException("Slice filter can only be applied to String, List and array inputs. Actual type was: "
                            + input.getClass().getName());
        }
        int to;

        if (argTo != null) {
            to = ((Number) argTo).intValue();
            if (to > length)
                throw new IllegalArgumentException("toIndex must be smaller than input size: " + length);
            else if (from >= to)
                throw new IllegalArgumentException("toIndex must be greater than fromIndex");
        } else {
            to = length;
        }

        // slice input
        if (input instanceof List) {
            List<?> value = (List<?>) input;
            // FIXME maybe sublist() is not the best option due to its
            // implementation?
            return value.subList(from, to);
        } else if (input.getClass().isArray()) {
            return sliceArray(input, from, to);
        } else {
            String value = (String) input;
            return value.substring(from, to);
        }
    }

    private static Object sliceArray(Object input, int from, int to) {
        if (input instanceof Object[]) {
            return Arrays.copyOfRange((Object[]) input, from, to);
        } else if (input instanceof boolean[]) {
            return Arrays.copyOfRange((boolean[]) input, from, to);
        } else if (input instanceof byte[]) {
            return Arrays.copyOfRange((byte[]) input, from, to);
        } else if (input instanceof char[]) {
            return Arrays.copyOfRange((char[]) input, from, to);
        } else if (input instanceof double[]) {
            return Arrays.copyOfRange((double[]) input, from, to);
        } else if (input instanceof float[]) {
            return Arrays.copyOfRange((float[]) input, from, to);
        } else if (input instanceof int[]) {
            return Arrays.copyOfRange((int[]) input, from, to);
        } else if (input instanceof long[]) {
            return Arrays.copyOfRange((long[]) input, from, to);
        } else {
            return Arrays.copyOfRange((short[]) input, from, to);
        }
    }

}
