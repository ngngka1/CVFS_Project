package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
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
        System.run(new NewDiskCommand(1000));
        System.getWorkingDirectory();
    }

    @Test
    void testAddFile() {
        Document doc = new Document("doc1", "txt");
        System.getWorkingDirectory().add(doc);
        assertTrue(System.getWorkingDirectory().getFiles().contains(doc));
        assertEquals(doc.size(), System.getWorkingDirectory().size() - Directory.getDefaultSize());
    }

    @Test
    void testAddFileWithSameName() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.run(new NewDocumentCommand("doc1", "txt", "TEXT"));
            System.run(new NewDocumentCommand("doc1", "txt", "TEXT"));
        });
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
        System.getWorkingDirectory().add(doc1);
        System.getWorkingDirectory().add(doc2);
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
        Directory subDir = new Directory("dir1");
        System.getWorkingDirectory().add(subDir);
        assertTrue(System.getWorkingDirectory().getDirectories().contains(subDir));
    }

    @Test
    void testGetDirectoriesWhenNoneExist() {
        assertTrue(System.getWorkingDirectory().getDirectories().isEmpty());
    }

    @Test
    void testMultipleAdditionsAndDeletions() {
        Document doc1 = new Document("doc6", "txt");
        Document doc2 = new Document("doc7", "txt");
        System.getWorkingDirectory().add(doc1);
        System.getWorkingDirectory().add(doc2);

        assertTrue(System.getWorkingDirectory().getFiles().contains(doc1));
        assertTrue(System.getWorkingDirectory().getFiles().contains(doc2));

        System.getWorkingDirectory().delete("doc6");
        assertFalse(System.getWorkingDirectory().getFiles().contains(doc1));
        assertTrue(System.getWorkingDirectory().getFiles().contains(doc2));
    }

    @Test
    void testAddingDirectory() {
        Directory dir = new Directory("dir2");
        System.getWorkingDirectory().add(dir);
        assertTrue(System.getWorkingDirectory().getDirectories().contains(dir));
    }
}