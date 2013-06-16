package classfinder;

/**
 * Matcher matches an instance of Pattern and ClassName
 */
public class Matcher {

    /**
     * Matcher matches an instance of Pattern and ClassName
     * @param pattern Pattern object to be matched
     * @param className ClassName object to be matched
     */
    public boolean matches(Pattern pattern, ClassName className) {
        return matches(pattern, className, false);
    }

    /**
     * Matcher matches an instance of Pattern and ClassName
     * @param pattern Pattern object to be matched
     * @param className ClassName object to be matched
     * @param isStarMode true if the previous pattern part was a star
     */
    private boolean matches(Pattern pattern, ClassName className, boolean isStarMode) {
        boolean bestMatch = false;
        if (className.isOutOfBounds()) {
            if (pattern.isLast() && pattern.matches(" ")) {
                return true;
            } else {
                return bestMatch;
            }
        }
        if (pattern.isLast()) {
            if (pattern.matches(className.getCurrent())) {
                return true;
            } else if (!isStarMode) {
                return bestMatch;
            }
        }
        if (pattern.matches(className.getCurrent())) {
            bestMatch = bestMatch || matches(pattern.next(), className.next(), false);
        }
        if (isStarMode) {
            if (className.isLast()) {
                return bestMatch;
            } else {
                bestMatch = bestMatch || matches(pattern, className.next(), true);
            }
        }
        if (pattern.matches("*")) {
            bestMatch = bestMatch || matches(pattern.next(), className, true);
        }
        return bestMatch;
    }

}
