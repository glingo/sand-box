package component.expressionLanguage.scope;

import java.util.HashMap;
import java.util.Map;

/**
 * A scope is a map of variables. A "local" scope ensures that
 * the search for a particular variable will end at this scope
 * whether or not it was found.
 *
 */
public class Scope {

    /**
     * A "local" scope ensures that
     * the search for a particular variable will end at this scope
     * whether or not it was found.
     */
    private final boolean isLocal;

    /**
     * The map of variables known at this scope
     */
    private final Map<String, Object> backingMap;

    /**
     * Constructor
     *
     * @param backingMap The backing map of variables
     * @param isLocal    Whether this scope is local or not
     */
    public Scope(Map<String, Object> backingMap, boolean isLocal) {
        this.backingMap = backingMap == null ? new HashMap<>() : backingMap;
        this.isLocal = isLocal;
    }

    /**
     * Creates a shallow copy of the Scope.
     * <p>
     * This is used for the parallel tag  because every new thread should have a
     * "snapshot" of the scopes, i.e. one thread should not affect rendering output of another.
     * <p>
     * It will construct a new collection but
     * it will contain references to all of the original variables therefore it is not
     * a deep copy. This is why it is import for the user to use thread-safe variables
     * when using the parallel tag.
     *
     * @return A copy of the scope
     */
    public Scope shallowCopy() {

        Map<String, Object> backingMapCopy = new HashMap<>();
        backingMapCopy.putAll(backingMap);

        return new Scope(backingMapCopy, isLocal);
    }

    /**
     * Returns whether or not this scope is "local".
     *
     * @return boolean stating whether this scope is local or not.
     */
    public boolean isLocal() {
        return isLocal;
    }

    /**
     * Adds a variable to this scope
     *
     * @param key   The name of the variable
     * @param value The value of the variable
     */
    public void put(String key, Object value) {
        backingMap.put(key, value);
    }

    /**
     * Retrieves the variable at this scope
     *
     * @param key The name of the variable
     * @return The value of the variable
     */
    public Object get(String key) {
        return backingMap.get(key);
    }

    /**
     * Checks if this scope contains a variable of a certain name.
     *
     * @param key The name of the variable
     * @return boolean stating whether or not the backing map of this scope contains that variable
     */
    public boolean containsKey(String key) {
        return backingMap.containsKey(key);
    }

}
