package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.newDisk(1000);
        System.getWorkingDirectory();
    }

    @Test
    void testCreateDirectory() {
        System.getWorkingDirectory().createDir("dir1");
        assertEquals(1, System.getWorkingDirectory().getDirectories().size());
        assertEquals("dir1", System.getWorkingDirectory().getDirectories().get(0).getName());
    }

    @Test
    void testCreateDocument() {
        System.getWorkingDirectory().createDoc("doc1", "txt", "TEXT");
        assertEquals(1, System.getWorkingDirectory().getFiles().size());
        assertEquals("doc1", System.getWorkingDirectory().getFiles().get(0).getName());
        assertEquals("txt", System.getWorkingDirectory().getFiles().get(0).getType());
    }

    @Test
    void testSizeCalculationWithFiles() {
        System.getWorkingDirectory().createDoc("doc2", "java", "CODE");
        System.getWorkingDirectory().createDoc("doc3", "html", "HTML");
        assertEquals(Directory.DEFAULT_SIZE + 40 + 52, System.getWorkingDirectory().size());
    }

    @Test
    void testSizeCalculationWithDirectories() {
        System.getWorkingDirectory().createDir("dir1");
        System.getWorkingDirectory().createDoc("doc4", "css", "STYLE");
        assertEquals(Directory.DEFAULT_SIZE + 40 + 10, System.getWorkingDirectory().size());
    }

    @Test
    void testCreateDocumentWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDirectory().createDoc("doc5", "pdf", "Unsupported");
        });
    }
}