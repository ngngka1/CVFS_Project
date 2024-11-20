package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewBinaryCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewNegationCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewSimpleCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.PrintAllCriteriaCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.SearchFilesCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CriterionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = java.lang.System.out;
    private final PrintStream originalErr = java.lang.System.err;
    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000000));
        java.lang.System.setOut(new PrintStream(outContent));
        java.lang.System.setErr(new PrintStream(errContent));

    }
    @AfterEach
    public void restoreStreams() {
        java.lang.System.setOut(originalOut);
        java.lang.System.setErr(originalErr);
        java.lang.System.out.println(outContent);
    }
//
//    @Test
//    void testIsDocumentCriterion() {
//        Criterion isDocument = IsDocumentCriterion.instance;
//        assertTrue(isDocument.check(doc1));
//        assertTrue(isDocument.check(doc2));
//        assertFalse(isDocument.check(null)); // Assuming null is not a valid file
//    }
//
//    @Test
//    void testSimpleCriterionName() {
//        SimpleCriterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
//        assertTrue(nameCriterion.check(doc1));
//        assertFalse(nameCriterion.check(doc2));
//    }
//
//    @Test
//    void testSimpleCriterionType() {
//        SimpleCriterion typeCriterion = new SimpleCriterion("ty", "type", "equals", "\"txt\"");
//        assertTrue(typeCriterion.check(doc1));
//        assertTrue(typeCriterion.check(doc2));
//    }
//
//    @Test
//    void testSimpleCriterionSize() {
//        SimpleCriterion sizeCriterion = new SimpleCriterion("sz", "size", ">", "20"); // Assuming size of content is more than 20
//        assertTrue(sizeCriterion.check(doc1));
//        assertTrue(sizeCriterion.check(doc2));
//    }
//
//    @Test
//    void testBinaryCriterion() {
//        Criterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
//        Criterion typeCriterion = new SimpleCriterion("ty", "type", "equals", "\"txt\"");

//        BinaryCriterion binaryCriterion = new BinaryCriterion("binaryTest", nameCriterion, "&&", typeCriterion);
//        assertTrue(binaryCriterion.check(doc1));
//        assertFalse(binaryCriterion.check(doc2)); // doc2 does not contain "report"
//
//        BinaryCriterion binaryCriterionOr = new BinaryCriterion("binaryOrTest", nameCriterion, "||", typeCriterion);
//        assertTrue(binaryCriterionOr.check(doc1));
//        assertTrue(binaryCriterionOr.check(doc2)); // doc2 is of type txt
//    }

//    @Test
//    void testNegatedCriterion() {
//        Criterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
//        NegatedCriterion negatedCriterion = new NegatedCriterion("negatedName", nameCriterion);
//        assertFalse(negatedCriterion.check(doc1));
//        assertTrue(negatedCriterion.check(doc2));
//    }

    @Test
    void testInvalidBinaryCriterion() {
        Criterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
        Criterion typeCriterion = new SimpleCriterion("ty", "type", "equals", "\"txt\"");

        assertThrows(IllegalArgumentException.class, () -> new BinaryCriterion("invalid", nameCriterion, "invalidOp", typeCriterion));
    }

    @Test
    void testSimpleCriterionInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleCriterion("nm", "name", "invalidOp", "\"report\""));
    }

    @Test
    void testSimpleCriterionInvalidAttribute() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleCriterion("nm", "invalidAttr", "equals", "\"report\""));
    }

    @Test
    void testSimpleCriterionInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleCriterion("nm", "name", "contains", "report"));
    }

    @Test
    void testCriteriaManagement() {
        Criteria.add(new SimpleCriterion("sz", "size", ">", "10"));
        assertNotNull(Criteria.get("sz"));
        assertEquals("sz", Criteria.get("sz").getName());

        Criteria.pop(Criteria.get("sz"));
        assertNull(Criteria.get("sz"));
    }
    @Test
    void test02() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new ChangeDirectoryCommand("dir1"));
        System.run(new ChangeDirectoryCommand(".."));
        assertEquals("1", "1");
    }

    @Test
    void test01() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand("dir2"));

        System.run(new NewDirectoryCommand("dir3"));
        System.run(new NewDirectoryCommand("dir32"));
        System.run(new NewDirectoryCommand("dir33"));
        System.run(new NewDirectoryCommand("dir34"));
        System.run(new NewDirectoryCommand("dir35"));
        System.run(new NewDirectoryCommand("dir36"));
        System.run(new ChangeDirectoryCommand("dir3"));

        System.run(new NewDirectoryCommand("dir4"));
        System.run(new NewDirectoryCommand("dir42"));
        System.run(new NewDirectoryCommand("dir43"));
        System.run(new NewDirectoryCommand("dir44"));
        System.run(new NewDirectoryCommand("dir45"));
        System.run(new NewDirectoryCommand("dir46"));
        System.run(new NewDirectoryCommand("dir47"));
        System.run(new ChangeDirectoryCommand("dir4"));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new NewSimpleCriterionCommand("cr", "name", "contains", "\"3\""));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(true, "bb"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 760\r\n" +
                "dir12 40\r\n" +
                "dir13 40\r\n" +
                "dir14 40\r\n" +
                "number of matched files: 4\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());

    }

    @Test
    void test03() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand("dir2"));

        System.run(new NewDirectoryCommand("dir3"));
        System.run(new NewDirectoryCommand("dir32"));
        System.run(new NewDirectoryCommand("dir33"));
        System.run(new NewDirectoryCommand("dir34"));
        System.run(new NewDirectoryCommand("dir35"));
        System.run(new NewDirectoryCommand("dir36"));
        System.run(new ChangeDirectoryCommand("dir3"));

        System.run(new NewDirectoryCommand("dir4"));
        System.run(new NewDirectoryCommand("dir42"));
        System.run(new NewDirectoryCommand("dir43"));
        System.run(new NewDirectoryCommand("dir44"));
        System.run(new NewDirectoryCommand("dir45"));
        System.run(new NewDirectoryCommand("dir46"));
        System.run(new NewDirectoryCommand("dir47"));
        System.run(new ChangeDirectoryCommand("dir4"));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new NewSimpleCriterionCommand("cr", "name", "contains", "\"3\""));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(false, "nn"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 760\r\n" +
                "dir12 40\r\n" +
                "dir14 40\r\n" +
                "number of matched files: 3\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());
    }
    @Test
    void test04() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand(".."));

        System.run(new NewSimpleCriterionCommand("cr", "size", ">", "41"));
//        System.run(new NewNegationCommand("nn", "cr"));
//        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(false, "cr"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 240\r\n" +
                "number of matched files: 1\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());
    }

    @Test
    void test05() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand(".."));

        System.run(new NewSimpleCriterionCommand("cr", "size", ">", "41"));
        System.run(new NewNegationCommand("nn", "cr"));
//        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(false, "nn"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir12 40\r\n" +
                "dir13 40\r\n" +
                "dir14 40\r\n" +
                "number of matched files: 3\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());
    }

    @Test
    void test06() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand(".."));

        System.run(new NewSimpleCriterionCommand("cr", "size", ">", "41"));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(false, "bb"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 240\r\n" +"dir12 40\r\n" +
                "dir13 40\r\n" +
                "dir14 40\r\n" +
                "number of matched files: 4\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());
    }

    @Test
    void test07() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand(".."));

        System.run(new NewSimpleCriterionCommand("cr", "size", ">", "41"));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "&&", "nn"));
        System.run(new SearchFilesCommand(false, "bb"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals(
                "number of matched files: 0\r\n" +
                "total size of matched files: 0\r\n", outContent.toString());
    }
    @Test
    void test10() {
        assertThrows(IllegalArgumentException.class, () -> System.run(new NewSimpleCriterionCommand("cr", "asd", "as", "41")));
    }


    @Test
    void test08() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDirectoryCommand("dir14"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new NewDirectoryCommand("dir23"));
        System.run(new NewDirectoryCommand("dir24"));
        System.run(new NewDirectoryCommand("dir25"));
        System.run(new ChangeDirectoryCommand(".."));
//        String[] outputArr = new String[] {
//        "number of matched files: 0\r\ntotal size of matched files: 0\r\n",
//        "dir1 240\r\ndir2 40\r\ndir22 40\r\ndir23 40\r\ndir24 40\r\ndir25 40\r\ndir12 40\r\ndir13 40\r\ndir14 40\r\nnumber of matched files: 8\r\ntotal size of matched files: 0\r\n",
//        "
//        dir1 240
//        dir2 40
//        dir22 40
//        dir23 40
//        dir24 40
//        dir25 40
//        dir12 40
//        dir13 40
//        dir14 40
//        number of matched files: 8
//        total size of matched files: 0
//        ",
//        "
//        dir1 240
//        dir12 40
//        dir13 40
//        dir14 40
//        number of matched files: 4
//        total size of matched files: 0
//        ",
//        "
//        dir1 240
//        number of matched files: 1
//        total size of matched files: 0
//        ",
//        "
//        dir1 240
//        number of matched files: 1
//        total size of matched files: 0
//        "
//        };
        for (String x : SimpleCriterion.VALUE_OPERATORS) {

            System.run(new NewSimpleCriterionCommand("cr", "size", x, "41"));
//            System.run(new NewNegationCommand("nn", "cr"));
//            System.run(new NewBinaryCriterionCommand("bb", "cr", "&&", "nn"));
            java.lang.System.out.print("\"");
            System.run(new SearchFilesCommand(true, "cr"));
            java.lang.System.out.print("\",");
            //        System.run(new PrintAllCriteriaCommand());
//            assertEquals(
//                    "number of matched files: 0\r\n" +
//                            "total size of matched files: 0\r\n", outContent.toString());
//            }

            assertEquals(outContent.toString(), outContent.toString());
        }
    }
}