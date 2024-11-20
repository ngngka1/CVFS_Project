package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class DiskTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.run(new NewDiskCommand(1000));
    }

    @Test
    void testSetRootDirectory() {
        assertEquals("", System.getWorkingDirectory().getName());
    }

    @Test
    void testHandleSizeChangeIncrease() {
        assertEquals(0, System.getWorkingDisk().currentSize);
        System.getWorkingDisk().handleSizeChange(200);
        assertEquals(200, System.getWorkingDisk().currentSize);
    }

    @Test
    void testHandleSizeChangeDecrease() {
        System.getWorkingDisk().handleSizeChange(300);
        assertEquals(300, System.getWorkingDisk().currentSize);
        System.getWorkingDisk().handleSizeChange(-100);
        assertEquals(200, System.getWorkingDisk().currentSize);
    }

    @Test
    void testHandleSizeChangeExceedMaxSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDisk().handleSizeChange(1001);
        });
    }

    @Test
    void testAddUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        assertTrue(System.getWorkingDisk().hasFileName("file1"));
        assertFalse(System.getWorkingDisk().hasFileName("file2"));
    }

    @Test
    void testDeleteUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        System.getWorkingDisk().deleteUniqueFileName("file1");
        assertFalse(System.getWorkingDisk().hasFileName("file1"));
    }

    @Test
    void testRenameUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        System.getWorkingDisk().renameUniqueFileName("file1", "file2");
        assertFalse(System.getWorkingDisk().hasFileName("file1"));
        assertTrue(System.getWorkingDisk().hasFileName("file2"));
    }

    @Test
    void testSaveDisk() throws IOException {
        System.run(new NewDocumentCommand("doc1", "txt", "Content of doc1"));
        System.run(new NewDocumentCommand("doc2", "txt", "Content of doc2"));

        // Save the System.getWorkingDisk() to a temporary directory
        String tempDir = java.lang.System.getProperty("java.io.tmpdir") + "/System.getWorkingDisk()Test";
        System.getWorkingDisk().save(tempDir);

        // Verify that the files have been created
        File savedFile1 = new File(tempDir + "/doc1.txt");
        File savedFile2 = new File(tempDir + "/doc2.txt");
        assertTrue(savedFile1.exists());
        assertTrue(savedFile2.exists());

        // Clean up created files
        Files.deleteIfExists(savedFile1.toPath());
        Files.deleteIfExists(savedFile2.toPath());
        Files.deleteIfExists(new File(tempDir).toPath());
    }

    @Test
    void testSaveDiskFolderCreationFailure() {
        assertThrows(RuntimeException.class, () -> {
            System.getWorkingDisk().save("/invalid/path/to/save");
        });
    }

    @Test
    void testGetRootDirectoryDefault() {
        System.run(new NewDiskCommand(1000));
        assertNotNull(System.getWorkingDirectory());
    }
}