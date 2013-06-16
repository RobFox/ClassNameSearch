package classfinder;

import util.SplitDecider;
import util.Util;

import java.util.List;

public class ClassName {

    private final List<String> classNameParts;
    private final String prefix;
    private int index;

    public ClassName(String prefix, String className) {
        classNameParts = Util.split(className, new SplitDecider() {
            @Override
            public boolean split(char c) {
                return Character.isUpperCase(c);
            }
        });
        this.prefix = prefix;
    }

    private ClassName(List<String> classNameParts, String prefix, int index) {
        this.classNameParts = classNameParts;
        this.prefix = prefix;
        this.index = index;
    }

    public boolean isLast() {
        return index == classNameParts.size() - 1;
    }

    public ClassName next() {
        return new ClassName(classNameParts, prefix, index + 1);
    }

    public String getCurrent() {
        return classNameParts.size() >= index + 1 ? classNameParts.get(index) : null;
    }

    public String getFullName() {
        StringBuffer buffer = new StringBuffer(prefix + ".");
        for (int i = 0; i < classNameParts.size(); i++) {
            buffer.append(classNameParts.get(i));
        }
        return buffer.toString();
    }

    public boolean isOutOfBounds() {
        return classNameParts.size() < index + 1;
    }
}
