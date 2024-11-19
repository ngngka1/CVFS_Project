package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.files;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;

public class NewDocumentCommand extends NewFileCommand {

    public NewDocumentCommand(String docName, String docType, String docContent) {
        setCreatedFile(new Document(docName, docType, docContent));
    }

    public NewDocumentCommand(String docName, String docType) {
        this(docName, docType, "");
    }

}
