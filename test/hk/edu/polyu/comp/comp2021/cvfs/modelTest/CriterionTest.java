package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewBinaryCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewNegationCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.NewSimpleCriterionCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria.PrintAllCriteriaCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ListFilesCommand;
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
//        java.lang.System.out.println(outContent);
    }

    @Test
    void testPrintAll() {
        System.run(new NewSimpleCriterionCommand("cr", "name", "contains", "\"3\""));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new PrintAllCriteriaCommand());

        assertEquals("nn: !(name contains \"3\")\r\n" +
                "bb: (name contains \"3\") || (!(name contains \"3\"))\r\n" +
                "IsDocument: IsDocument()\r\n" +
                "cr: name contains \"3\"\r\n", outContent.toString());
    }

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
    void test01() {

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
        System.run(new NewSimpleCriterionCommand("cr", "name", "contains", "\"3\""));
        System.run(new NewNegationCommand("nn", "cr"));
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(true, "nn"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir3 320\r\n" +
                "  dir4 40 (matched)\r\n" +
                "  dir42 40 (matched)\r\n" +
                "  dir44 40 (matched)\r\n" +
                "  dir45 40 (matched)\r\n" +
                "  dir46 40 (matched)\r\n" +
                "  dir47 40 (matched)\r\n" +
                "number of matched files: 6\r\n" +
                "total size of matched files: 240\r\n", outContent.toString());
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
        System.run(new SearchFilesCommand(true, "nn"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 760 (matched)\r\n" +
                "  dir2 560 (matched)\r\n" +
                "    dir3 320\r\n" +
                "      dir4 40 (matched)\r\n" +
                "      dir42 40 (matched)\r\n" +
                "      dir44 40 (matched)\r\n" +
                "      dir45 40 (matched)\r\n" +
                "      dir46 40 (matched)\r\n" +
                "      dir47 40 (matched)\r\n" +
                "  dir22 40 (matched)\r\n" +
                "  dir24 40 (matched)\r\n" +
                "  dir25 40 (matched)\r\n" +
                "dir12 40 (matched)\r\n" +
                "dir14 40 (matched)\r\n" +
                "number of matched files: 13\r\n" +
                "total size of matched files: 840\r\n", outContent.toString());
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
        System.run(new SearchFilesCommand(false, "cr"));
        assertEquals("dir1 240 (matched)\r\n" +
                "number of matched files: 1\r\n" +
                "total size of matched files: 240\r\n", outContent.toString());
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
        System.run(new NewBinaryCriterionCommand("bb", "cr", "||", "nn"));
        System.run(new SearchFilesCommand(false, "bb"));
//        System.run(new PrintAllCriteriaCommand());
        assertEquals("dir1 240 (matched)\r\n" +
                "dir12 40 (matched)\r\n" +
                "dir13 40 (matched)\r\n" +
                "dir14 40 (matched)\r\n" +
                "number of matched files: 4\r\n" +
                "total size of matched files: 360\r\n", outContent.toString());
    }

    @Test
    void test051() {
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
    void testIsDocument() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir13"));
        System.run(new NewDocumentCommand("doc1", "txt", "asf as s w"));
        System.run(new SearchFilesCommand(true, "IsDocument"));
        assertEquals("doc1 txt 60\r\n" +
                "number of matched files: 1\r\n" +
                "total size of matched files: 60\r\n", outContent.toString());
    }
    @Test
    void testUndo() {
        System.run(new NewSimpleCriterionCommand("cr", "size", ">", "80"));
        System.undo();
        assertThrows(IllegalArgumentException.class, () -> System.run(new SearchFilesCommand(true, "cr")));
    }
    @Test
    void test08() {
        String[] outputArr = new String[] {
                "dir12 80 (matched)\r\n" +
                "number of matched files: 1\r\n" +
                "total size of matched files: 80\r\n",
                "dir1 120\r\n" +
                "  dir2 40 (matched)\r\n" +
                "  dir22 40 (matched)\r\n" +
                "dir12 80 (matched)\r\n" +
                "  dir122 40 (matched)\r\n" +
                "number of matched files: 4\r\n" +
                "total size of matched files: 160\r\n",
                "dir1 120\r\n" +
                "  dir2 40 (matched)\r\n" +
                "  dir22 40 (matched)\r\n" +
                "dir12 80\r\n" +
                "  dir122 40 (matched)\r\n" +
                "number of matched files: 3\r\n" +
                "total size of matched files: 120\r\n",
                "dir1 120 (matched)\r\n" +
                "  dir2 40 (matched)\r\n" +
                "  dir22 40 (matched)\r\n" +
                "dir12 80\r\n" +
                "  dir122 40 (matched)\r\n" +
                "number of matched files: 4\r\n" +
                "total size of matched files: 160\r\n",
                "dir1 120 (matched)\r\n" +
                "number of matched files: 1\r\n" +
                "total size of matched files: 120\r\n",
                "dir1 120 (matched)\r\n" +
                "dir12 80 (matched)\r\n" +
                "number of matched files: 2\r\n" +
                "total size of matched files: 200\r\n",
        };
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDirectoryCommand("dir12"));
        System.run(new ChangeDirectoryCommand("dir1"));

        System.run(new NewDirectoryCommand("dir2"));
        System.run(new NewDirectoryCommand("dir22"));
        System.run(new ChangeDirectoryCommand(".."));
        System.run(new ChangeDirectoryCommand("dir12"));
        System.run(new NewDirectoryCommand("dir122"));
        System.run(new ChangeDirectoryCommand(".."));
        int i = 0;
        for (String x : SimpleCriterion.VALUE_OPERATORS) {
            System.run(new NewSimpleCriterionCommand("cr", "size", x, "80"));
            System.run(new SearchFilesCommand(true, "cr"));
            assertEquals(outputArr[i++], outContent.toString());
            outContent.reset();
        }
    }
}