package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.exception.SystemTerminatedException;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SystemTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.newDisk(1000);
    }

    @Test
    void testNewDisk() {
        assertNotNull(System.getWorkingDisk());
        assertEquals(1000, System.getWorkingDisk().maxSize);
    }

    @Test
    void testNewDirectory() {
        System.newDirectory("dir1");
        Directory workingDir = System.getWorkingDirectory();
        assertEquals(1, workingDir.getDirectories().size());
        assertEquals("dir1", workingDir.getDirectories().get(0).getName());
    }

    @Test
    void testNewDocument() {
        System.newDirectory("dir1");
        System.newDocument("doc1", "txt", "TEXT");
        Document doc = (Document) System.getWorkingDirectory().getFiles().get(0);
        assertEquals("doc1", doc.getName());
        assertEquals("txt", doc.getType());
    }

    @Test
    void testDeleteFile() {
        System.newDirectory("dir1");
        System.newDocument("doc1", "txt", "TEXT");
        System.deleteFile("doc1");
        assertThrows(IllegalArgumentException.class, () -> System.getWorkingDirectory().delete("doc1"));
    }

    @Test
    void testRenameFile() {
        System.newDirectory("dir1");
        System.newDocument("doc1", "txt", "TEXT");
        System.renameFile("doc1", "doc2");
        assertThrows(IllegalArgumentException.class, () -> System.getWorkingDirectory().delete("doc1"));
        assertNotNull(System.getWorkingDirectory().getFiles().stream().filter(f -> f.getName().equals("doc2")).findFirst().orElse(null));
    }

    @Test
    void testChangeDirectory() {
        System.newDirectory("dir1");
        System.changeDirectory("dir1");
        assertEquals("dir1", System.getWorkingDirectory().getName());
    }

    @Test
    void testTraverseDirectoriesRecursive() {
        System.newDirectory("dir1");
        System.changeDirectory("dir1");
        System.newDirectory("dir2");
        Directory dir2 = System.traverseDirectoriesRecursive("dir2", System.getWorkingDirectory());
        assertNotNull(dir2);
        assertEquals("dir2", dir2.getName());
    }

    @Test
    void testTerminate() {
        assertThrows(SystemTerminatedException.class, () -> {
            System.terminate();
        });
        assertFalse(System.isRunning());
    }
}