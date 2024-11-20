package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @BeforeEach
    void setUp(){
        System.getInstance();
        System.run(new NewDiskCommand(1000));
        System.getWorkingDirectory();
    }

    @Test
    void testCreateDocumentWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> new Document("doc2", "pdf"));
    }

    @Test
    void testSize() {
        Document doc = new Document("doc3", "java", "CODE");
        assertEquals(Document.DEFAULT_SIZE + "Code".length() * 2, doc.size());
    }

    @Test
    void testToDisplayString() {
        Document doc = new Document("doc4", "html", "HTML");
        assertEquals("doc4 html " + (Document.DEFAULT_SIZE + "HTML".length() * 2), doc.toDisplayString());
    }

    @Test
    void testSaveDocumentWithIOException() {
        Document doc = new Document("doc5", "txt", "TEXT");
        assertThrows(RuntimeException.class, () -> doc.save("/invalid/path/testDoc.txt"));
    }
}