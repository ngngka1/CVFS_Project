package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiskTest {

    @BeforeEach
    void setUp() {
        System.getInstance();
        System.newDisk(1000);
    }

    @Test
    void testInitialRootDirectory() {
        assertNotNull(System.getWorkingDisk().getRootDirectory());
        assertEquals("", System.getWorkingDisk().getRootDirectory().getName());
    }

    @Test
    void testHandleSizeChange() {
        System.getWorkingDisk().handleSizeChange(100);
        assertEquals(100, System.getWorkingDisk().currentSize);

        System.getWorkingDisk().handleSizeChange(-50);
        assertEquals(50, System.getWorkingDisk().currentSize);
    }

    @Test
    void testHandleSizeChangeExceedsMaxSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            System.getWorkingDisk().handleSizeChange(1001);
        });
    }

    @Test
    void testAddUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file1");
        assertTrue(System.getWorkingDisk().hasFileName("file1"));
    }

    @Test
    void testDeleteUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file2");
        System.getWorkingDisk().deleteUniqueFileName("file2");
        assertFalse(System.getWorkingDisk().hasFileName("file2"));
    }

    @Test
    void testRenameUniqueFileName() {
        System.getWorkingDisk().addUniqueFileName("file3");
        System.getWorkingDisk().renameUniqueFileName("file3", "file4");
        assertFalse(System.getWorkingDisk().hasFileName("file3"));
        assertTrue(System.getWorkingDisk().hasFileName("file4"));
    }

    @Test
    void testHasFileName() {
        System.getWorkingDisk().addUniqueFileName("file5");
        assertTrue(System.getWorkingDisk().hasFileName("file5"));
        assertFalse(System.getWorkingDisk().hasFileName("noThisFile"));
    }
}