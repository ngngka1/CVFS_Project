package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @BeforeEach
    void setUp(){
        System.getInstance();
        System.newDisk(1000);
        System.getWorkingDirectory();
    }


    @Test
    void testFileCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Document(null, "txt"));
    }

    @Test
    void testFileCreationWithExistingName() {
        System.newDirectory("dir1");
        System.newDocument("file1", "txt", "TEXT");
        assertThrows(IllegalArgumentException.class, () -> System.newDocument("file1", "txt", "TEXT"));
    }

    @Test
    void testFileCreationWithInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> System.newDocument("invalid$name", "txt", "TEXT"));
    }
}