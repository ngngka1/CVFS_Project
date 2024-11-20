package hk.edu.polyu.comp.comp2021.cvfs.modelTest;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.disk.NewDiskCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDirectoryCommand;
import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files.NewDocumentCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.System;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @BeforeEach
    void setUp(){
        System.getInstance();
        System.run(new NewDiskCommand(1000));
        System.getWorkingDirectory();
    }


    @Test
    void testFileCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Document(null, "txt"));
    }

    @Test
    void testFileCreationWithExistingName() {
        System.run(new NewDirectoryCommand("dir1"));
        System.run(new NewDocumentCommand("file1", "txt", "TEXT"));
        assertThrows(IllegalArgumentException.class, () -> System.run(new NewDocumentCommand("file1", "txt", "TEXT")));
    }

    @Test
    void testFileCreationWithInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> System.run(new NewDocumentCommand("invalid$name", "txt", "TEXT")));
    }
}