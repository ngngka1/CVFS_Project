package hk.edu.polyu.comp.comp2021.cvfs.model;

public abstract class File {
    private String name;
    abstract public int size();
    abstract public String getType();

    protected File(String name) {
        if (name == null) {
            throw new IllegalArgumentException("hk.edu.polyu.comp.comp2021.cvfs.model.File name cannot be empty!");
        }
        Disk workingDisk = System.getWorkingDisk();
        if (!(name.isEmpty() && !workingDisk.hasFileName(""))) {
            if (workingDisk.hasFileName(name)) {
                throw new IllegalArgumentException("hk.edu.polyu.comp.comp2021.cvfs.model.File with the same name already exists!");
            }
            if (!(name.matches("[A-Za-z0-9]+") && name.length() <= 10)) {
                throw new IllegalArgumentException("Invalid file name!");
            }
        }
        workingDisk.addUniqueFileName(name);
        setName(name);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
