package component.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PathUtils {

    private static final Pattern separatorRegex = Pattern.compile("\\\\|/");

    /**
     * Resolves the given {@code relativePath} based on the given
     * {@code anchorPath}.
     *
     * @param relativePath      the relative path which should be resolved.
     * @param anchorPath        the anchor path based on which the relative path should be
     *                          resolved on.
     * @param expectedSeparator The character expected to be used as a separator; dictated by the Loader.
     * @return the resolved path or {@code null} when the path could not be
     * resolved.
     */
    public static String resolveRelativePath(String relativePath, String anchorPath, char expectedSeparator) {
        if (relativePath == null || relativePath.isEmpty()) {
            return null;
        }

        // ensure both paths use the same separator character
        relativePath = sanitize(relativePath, expectedSeparator);
        anchorPath = sanitize(anchorPath, expectedSeparator);

        if (relativePath.startsWith(".." + expectedSeparator) || relativePath.startsWith("." + expectedSeparator)) {
            return resolvePathInner(relativePath, anchorPath, expectedSeparator);
        }

        return null;
    }

    private static String sanitize(String path, char expectedSeparator){
        return separatorRegex.matcher(path).replaceAll(Matcher.quoteReplacement(String.valueOf(expectedSeparator)));
    }

    private static String resolvePathInner(String relativePath, String anchorPath, char separator) {
        StringBuilder resultingPath = new StringBuilder();

        resolvePathSegments(determineAnchorPathSegments(anchorPath, separator),
                splitBySeparator(relativePath, separator)).stream().forEach((segment) -> {
                    resultingPath.append(segment).append(separator);
        });

        // remove the erroneous separator added at the end
        return resultingPath.substring(0, resultingPath.length() - 1);
    }

    private static Collection<String> determineAnchorPathSegments(String anchorPath, char separator) {
        if (anchorPath == null || anchorPath.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayDeque<String> anchorPathSegments = new ArrayDeque<>(splitBySeparator(anchorPath, separator));
        if (anchorPath.charAt(anchorPath.length() - 1) != separator) {
            anchorPathSegments.pollLast();
        }
        return anchorPathSegments;
    }

    private static Collection<String> resolvePathSegments(Collection<String> anchorSegments,
            Collection<String> relativeSegments) {
        ArrayDeque<String> result = new ArrayDeque<>(anchorSegments);
        relativeSegments.stream().forEach((String segment) -> {
            switch (segment) {
                case ".": // do nothing
                    break;
                case "..":
                    result.pollLast();
                    break;
                default:
                    result.add(segment);
                    break;
            }
        });

        return result;
    }

    private static List<String> splitBySeparator(String path, char separator) {
        return Arrays.asList(path.split(Pattern.quote(String.valueOf(separator))));
    }

    private PathUtils() {
        throw new IllegalAccessError();
    }

}
