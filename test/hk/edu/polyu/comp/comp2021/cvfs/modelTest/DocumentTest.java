package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void testDocumentCreationWithValidType() {
        Document doc = new Document("doc1", "txt");
        assertEquals("txt", doc.getType());
        assertEquals("doc1", doc.getName());
        assertEquals(Document.DEFAULT_SIZE, doc.size());
    }

    @Test
    void testDocumentCreationWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Document("doc2", "pdf");
        });
    }

    @Test
    void testDocumentSizeCalculation() {
        Document doc = new Document("doc3", "java", "Hello, World!");
        assertEquals(Document.DEFAULT_SIZE + 26, doc.size());
    }

    @Test
    void testDocumentCreationWithContent() {
        Document doc = new Document("doc5", "css", "body { }");
        assertEquals("css", doc.getType());
        assertEquals("doc5", doc.getName());
        assertEquals(Document.DEFAULT_SIZE + 10, doc.size());
    }
}