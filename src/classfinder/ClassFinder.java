package classfinder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import classfinder.ClassName;
import classfinder.Matcher;
import classfinder.Pattern;

/**
 * ClassFinder is used to find all class names matching a search pattern
 */
public class ClassFinder {
	
	private static final byte NEW_LINE = 10;
	private static final byte RETURN = 13;
	private List<ClassName> allClasses;

    /**
     * Initializes a newly created ClassFinder object with a
     * list of class names as an input stream.
     * @param classNamesStream stream of class names that are separated by \n or \n\r
     * @throws IOException throws an IOException when an error has occurred with the stream
     */
	public ClassFinder(InputStream classNamesStream) throws IOException {
		allClasses = new ArrayList<ClassName>();
		StringBuffer lineBuffer = new StringBuffer();
		int i;
		char c;
		while ((i = classNamesStream.read()) != -1) {
			c = (char)i;
			if (i == NEW_LINE) {
				addClassNameToClassList(lineBuffer);
			} else if (i != RETURN) {
				lineBuffer.append(c);
			}
		}
		if (i == -1) {
			addClassNameToClassList(lineBuffer);
		}
		classNamesStream.close();
	}

    /**
     * Creates a ClassName object from given StringBuffer and adds it to classList.
     * StringBuffer is emptied.
     * @param lineBuffer class name with package (eg a.b.FooBar)
     */
	private void addClassNameToClassList(StringBuffer lineBuffer) {
		String lineString = lineBuffer.toString();
		lineBuffer.setLength(0);
		int splitPoint = lineString.lastIndexOf(".");
		ClassName className = new ClassName(
				lineString.substring(0, splitPoint),
				lineString.substring(splitPoint + 1));
		allClasses.add(className);
	}

    /**
     * Finds all class names that match the given pattern.
     * @param patternString search keyword
     * @return all matching class names with package names
     */
	public Collection<String> findMatching(String patternString) {
        Pattern pattern = new Pattern(patternString);
		Collection<String> matchingClasses = new ArrayList<String>();
        Matcher matcher = new Matcher();
		for (ClassName className : allClasses) {
			if (matcher.matches(pattern, className)) {
				matchingClasses.add(className.getFullName());
			}
		}
		return matchingClasses;
	}
	
}
