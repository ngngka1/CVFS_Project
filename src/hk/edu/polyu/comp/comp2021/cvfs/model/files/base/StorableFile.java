    package hk.edu.polyu.comp.comp2021.cvfs.model.files.base;

    import hk.edu.polyu.comp.comp2021.cvfs.model.System;
    import hk.edu.polyu.comp.comp2021.cvfs.model.disk.Disk;
    import hk.edu.polyu.comp.comp2021.cvfs.model.files.Directory;

    import java.util.ArrayList;
    import java.util.List;

    public abstract class StorableFile extends File implements Storable {
        private final List<File> files = new ArrayList<>();
        protected StorableFile(String name) {
            super(name);
        }

        @Override
        public void add(File newFile) {
            Disk workingDisk = hk.edu.polyu.comp.comp2021.cvfs.model.System.getWorkingDisk();
            workingDisk.handleSizeChange(newFile.size());
            files.add(newFile);
            workingDisk.addUniqueFileName(newFile.getName());

        }

        public List<File> getFiles() {
            return files;
        }

        public List<Directory> getDirectories() {
            List<Directory> directories = new ArrayList<>();
            for (File file : files) {
                if (file instanceof Directory) {
                    directories.add((Directory) file);
                }
            }
            return directories;
        }

        @Override
        public void delete(String fileName) {
            List<File> files = getFiles();
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (file.getName().equals(fileName)) {
                    Disk workingDisk = hk.edu.polyu.comp.comp2021.cvfs.model.System.getWorkingDisk();
                    workingDisk.handleSizeChange(- file.size());
                    workingDisk.deleteUniqueFileName(fileName);
                    files.remove(i);
                    return;
                }
            }
            throw new IllegalArgumentException("Target file not found!");
        }

        @Override
        public void rename(String oldFileName, String newFileName) {
            Disk workingDisk = System.getWorkingDisk();

            List<File> files = getFiles();
            for (File file : files) {
                if (file.getName().equals(oldFileName)) {
                    if (workingDisk.hasFileName(newFileName)) {
                        throw new IllegalArgumentException("File with the same name already exists!");
                    }
                    file.setName(newFileName);
                    workingDisk.renameUniqueFileName(oldFileName, newFileName);
                    return;
                }
            }
            throw new IllegalArgumentException("Target file not found!");
        }
    }
