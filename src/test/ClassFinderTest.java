package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import classfinder.ClassFinder;

import org.junit.Test;


public class ClassFinderTest {

    private int testClassFinder(String classNames, String searchKey) throws IOException {
        return new ClassFinder(new ByteArrayInputStream(classNames.getBytes()))
                .findMatching(searchKey).size();
    }

    @Test
    public void basicTest() throws IOException {
        assertEquals(1, testClassFinder("a.b.FooBar", "FooBar"));
        assertEquals(1, testClassFinder("a.b.FooBar", "FooB"));
        assertEquals(1, testClassFinder("a.b.FooBar", "FBar"));
        assertEquals(1, testClassFinder("a.b.FooBar", "FB"));
        assertEquals(0, testClassFinder("a.b.FooBar", "FooBarC"));
        assertEquals(0, testClassFinder("a.b.FooBar", "FooBor"));
        assertEquals(1, testClassFinder("a.b.FooBar", "Foo"));
        assertEquals(0, testClassFinder("a.b.FooBar", "F00"));
        assertEquals(1, testClassFinder("a.b.FooBar", "F"));
        assertEquals(0, testClassFinder("a.b.FooBar", "f"));

        assertEquals(1, testClassFinder("a.b.FooBarCar", "FooBarC"));
        assertEquals(0, testClassFinder("a.b.FooBarCar", "FooCar"));
        assertEquals(1, testClassFinder("a.b.FooBarCar", "FooBCar"));
    }

    @Test
    public void starTest() throws IOException {
        assertEquals(1, testClassFinder("a.b.FooBarCar", "F*C"));
        assertEquals(1, testClassFinder("a.b.FooBarCar", "F*C*"));
        assertEquals(1, testClassFinder("a.b.FooBarCar", "Foo*C"));
        assertEquals(1, testClassFinder("a.b.FooBarCar", "Foo*Car"));

        assertEquals(1, testClassFinder("a.b.FooBarCarCar", "FooB*Car"));
        assertEquals(1, testClassFinder("a.b.FooBarCarCar", "Foo*Car"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "Foo*Ser"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "F*B"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "F*S"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "F*B*C*S"));
        assertEquals(0, testClassFinder("a.b.FooBarCarService", "F*F*C*S"));
        assertEquals(0, testClassFinder("a.b.FooBarCarService", "F*C*F*S"));
        assertEquals(0, testClassFinder("a.b.FooBarCarService", "F*Caa*F*S"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "FB*CS"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "FooB*CServ"));

        assertEquals(1, testClassFinder("a.b.FooBarCarService", "FooB*"));
        assertEquals(1, testClassFinder("a.b.FooBarCarService", "FooB*CServ*"));

        assertEquals(1, testClassFinder("a.b.FooCubaCarService", "Foo*CServ*"));
        assertEquals(1, testClassFinder("a.b.FooCubaCarService", "*CServ*"));
        assertEquals(1, testClassFinder("a.b.FooCubaCarService", "*CServ"));
        assertEquals(0, testClassFinder("a.b.FooCubaCarService", "*FServ"));
        assertEquals(0, testClassFinder("a.b.FooCubaCarService", "*CServServ"));
        assertEquals(1, testClassFinder("a.b.FooCubaCarService", "*C*Serv"));
    }

    @Test
    public void recursionTest() throws IOException {
        assertEquals(1, testClassFinder("a.b.FooCubaCarService", "Foo*CServ"));
    }

    @Test
    public void spaceTest() throws IOException {
        assertEquals(0, testClassFinder("a.b.FooBar", "Foo "));
        assertEquals(1, testClassFinder("a.b.FooBar", "FooB "));
        assertEquals(1, testClassFinder("a.b.FooBarService", "Foo*Servi "));
    }

    @Test
    public void fileStreamTest() throws IOException {
        InputStream is = new FileInputStream("in.txt");
        ClassFinder classFinder = new ClassFinder(is);
        assertEquals(3, classFinder.findMatching("F*B").size());
        assertEquals(2, classFinder.findMatching("Fo*B").size());
        assertEquals(2, classFinder.findMatching("FoBar").size());
        assertEquals(0, classFinder.findMatching("FoBaz").size());
    }

}
