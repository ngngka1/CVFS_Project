package hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base;

import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public abstract class Criterion {
    abstract public boolean check(File file);
    abstract public String getName();
    abstract public String toString();
}
