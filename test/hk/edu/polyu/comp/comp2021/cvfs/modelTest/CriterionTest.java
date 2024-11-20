package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriterionTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000));
    }

    @Test
    void testIsDocumentCriterion() {
        Criterion isDocument = IsDocumentCriterion.instance;
        assertTrue(isDocument.check(doc1));
        assertTrue(isDocument.check(doc2));
        assertFalse(isDocument.check(null)); // Assuming null is not a valid file
    }

    @Test
    void testSimpleCriterionName() {
        SimpleCriterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
        assertTrue(nameCriterion.check(doc1));
        assertFalse(nameCriterion.check(doc2));
    }

    @Test
    void testSimpleCriterionType() {
        SimpleCriterion typeCriterion = new SimpleCriterion("ty", "type", "equals", "\"txt\"");
        assertTrue(typeCriterion.check(doc1));
        assertTrue(typeCriterion.check(doc2));
    }

    @Test
    void testSimpleCriterionSize() {
        SimpleCriterion sizeCriterion = new SimpleCriterion("sz", "size", ">", "20"); // Assuming size of content is more than 20
        assertTrue(sizeCriterion.check(doc1));
        assertTrue(sizeCriterion.check(doc2));
    }

    @Test
    void testBinaryCriterion() {
        Criterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
        Criterion typeCriterion = new SimpleCriterion("ty", "type", "equals", "\"txt\"");

        BinaryCriterion binaryCriterion = new BinaryCriterion("binaryTest", nameCriterion, "&&", typeCriterion);
        assertTrue(binaryCriterion.check(doc1));
        assertFalse(binaryCriterion.check(doc2)); // doc2 does not contain "report"

        BinaryCriterion binaryCriterionOr = new BinaryCriterion("binaryOrTest", nameCriterion, "||", typeCriterion);
        assertTrue(binaryCriterionOr.check(doc1));
        assertTrue(binaryCriterionOr.check(doc2)); // doc2 is of type txt
    }

    @Test
    void testNegatedCriterion() {
        Criterion nameCriterion = new SimpleCriterion("nm", "name", "contains", "\"report\"");
        NegatedCriterion negatedCriterion = new NegatedCriterion("negatedName", nameCriterion);
        assertFalse(negatedCriterion.check(doc1));
        assertTrue(negatedCriterion.check(doc2));
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
}