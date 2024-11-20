package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.navigation.ChangeDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000));
    }

    @Test
    void testCreateDir() {
        System.getWorkingDirectory().createDir("dir1");
        assertEquals(1, System.getWorkingDirectory().getDirectories().size());
        assertEquals("dir1", System.getWorkingDirectory().getDirectories().get(0).getName());
    }

    @Test
    void testCreateDoc() {
        System.getWorkingDirectory().createDoc("doc1", "txt", "TEXT");
        assertEquals(1, System.getWorkingDirectory().getFiles().size());
        Document doc = (Document) System.getWorkingDirectory().getFiles().get(0);
        assertEquals("doc1", doc.getName());
    }

    @Test
    void testSize() {
        System.getWorkingDirectory().createDoc("doc1", "txt", "TEXT");
        assertEquals(40 + 40 + 2 * "TEXT".length(), System.getWorkingDirectory().size());
    }

    @Test
    void testToString() {
        System.getWorkingDirectory().createDoc("doc1", "txt", "TEXT");
        assertEquals("doc1 " + "txt " + (40 + 2 * "TEXT".length()), System.getWorkingDirectory().getFiles().get(0).toString());
    }

    @Test
    void testGetType(){
        assertEquals("directory", System.getWorkingDirectory().getType());
    }

    @Test
    void testChangeDirectory(){
        assertThrows(IllegalArgumentException.class, () -> System.run(new ChangeDirectoryCommand("")));
    }
}