package hk.edu.polyu.comp.comp2021.cvfs.model.files.base;

// unused for now

interface Storable {
    public void add(File newFile);
    public void delete(String fileName);
    public void rename(String oldFileName, String newFileName);
    public void createDir(String dirName);
    public void createDoc(String docName, String docType, String docContent);
}
