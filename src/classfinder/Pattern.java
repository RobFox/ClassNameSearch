package classfinder;

import util.SplitDecider;
import util.Util;

import java.util.List;

/**
 * Pattern for searching class names
 */
public class Pattern {

    /**
     * List of pattern parts
     * (eg 'FooB*Serv ' -> 'Foo', 'B', '*', 'Serv', ' ')
     */
    private List<String> patternParts;
    /**
     * Number of pattern parts that aren't '*' or ' '
     */
    private int charactersLenght;
    /**
     * Index of the current pattern part
     */
    private int index;

    /**
     * Initializes a newly created Pattern object.
     * @param pattern pattern as a String
     */
    public Pattern(String pattern) {
        patternParts = Util.split(pattern, new SplitDecider() {
            @Override
            public boolean split(char c) {
                return (Character.isUpperCase(c) || c == '*' || c == ' ');
            }
        });
        removeStarsFromEnd(patternParts);
        for (int i = 0; i < patternParts.size(); i++) {
            String patternPart = patternParts.get(i);
            if (!(patternPart.equals("*") || patternPart.equals(" "))) {
                charactersLenght++;
            }
        }
    }

    private void removeStarsFromEnd(List<String> patternParts) {
        for (int i = patternParts.size() - 1; i >= 0; i--) {
            if (patternParts.get(i).equals("*")) {
                patternParts.remove(i);
            } else {
                break;
            }
        }
    }

    private Pattern(int charactersLenght, int index, List<String> patternParts) {
        this.charactersLenght = charactersLenght;
        this.index = index;
        this.patternParts = patternParts;
    }

    /**
     * Return a Pattern object with a new pattern part.
     * Using next to get the next pattern part makes Pattern immutable
     * @return new instance of Pattern with the next pattern part
     */
    public Pattern next() {
        return new Pattern(charactersLenght, index + 1, patternParts);
    }

    /**
     * @return true if the current pattern part the last one
     */
    public boolean isLast() {
        return patternParts.size() == index + 1;
    }

    /**
     * @return the current pattern part, use next() to get the next one
     */
    private String getCurrent() {
        return patternParts.get(index);
    }

    /**
     * Checks if the current pattern part matches the given class name
     * @param classNamePart current class name part
     * @return true if matches
     */
    public boolean matches (String classNamePart) {
        String patternPart = getCurrent();
        if (classNamePart.length() < patternPart.length()) {
            return false;
        } else {
            return (classNamePart.substring(0, patternPart.length()).equals(patternPart));
        }
    }

    @Override
    public String toString() {
        return getCurrent();
    }
}
