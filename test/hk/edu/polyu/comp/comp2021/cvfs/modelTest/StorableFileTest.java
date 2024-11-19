package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorableFileTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.newDisk(1000);
        System.getWorkingDirectory();
    }

    @Test
    void testAddFile() {
        Document doc = new Document("doc1", "txt");
        System.getWorkingDirectory().add(doc);
        assertTrue(System.getWorkingDirectory().getFiles().contains(doc));
        assertEquals(doc.size(), System.getWorkingDirectory().size() - Directory.DEFAULT_SIZE);
    }

    @Test
    void testDeleteFile() {
        Document doc = new Document("doc2", "txt");
        System.getWorkingDirectory().add(doc);
        System.getWorkingDirectory().delete("doc2");
        assertFalse(System.getWorkingDirectory().getFiles().contains(doc));
    }

    @Test
    void testDeleteFileNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDirectory().delete("nonExistentFile");
        });
    }

    @Test
    void testRenameFile() {
        Document doc = new Document("doc3", "txt");
        System.getWorkingDirectory().add(doc);
        System.getWorkingDirectory().rename("doc3", "docRenamed");
        assertFalse(System.getWorkingDirectory().getFiles().stream().anyMatch(f -> f.getName().equals("doc3")));
        assertTrue(System.getWorkingDirectory().getFiles().stream().anyMatch(f -> f.getName().equals("docRenamed")));
    }

    @Test
    void testRenameFileAlreadyExists() {
        Document doc1 = new Document("doc4", "txt");
        Document doc2 = new Document("doc5", "txt");
        java.lang.System.out.println(doc1.getName());
        java.lang.System.out.println(doc2.getName());
        assertThrows(IllegalArgumentException.class, () -> System.getWorkingDirectory().rename("doc4", "doc5"));
    }

    @Test
    void testRenameFileNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDirectory().rename("nonExistentFile", "newName");
        });
    }

    @Test
    void testGetDirectories() {
        Directory subDir = new Directory("subDir");
        System.getWorkingDirectory().add(subDir);
        assertTrue(System.getWorkingDirectory().getDirectories().contains(subDir));
    }
}