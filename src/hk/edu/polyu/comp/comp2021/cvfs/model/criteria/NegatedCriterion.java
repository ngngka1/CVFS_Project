package hk.edu.polyu.comp.comp2021.cvfs.model.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class NegatedCriterion extends Criterion {
    private final Criterion original;
    private String name;

    public NegatedCriterion(String name, Criterion original) {
        this.name = name;
        this.original = original;
    }

    @Override
    public boolean check(File file) {
        return !original.check(file);
    }

    @Override
    public String getName() {return this.name;}

    @Override
    public String toString() {
        return "!(" + original.toString() + ")";
    }
}
