package hk.edu.polyu.comp.comp2021.cvfs.model.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class IsDocumentCriterion extends Criterion {
    private final String name;
    public static final Criterion instance = new IsDocumentCriterion();
    private IsDocumentCriterion() {
        this.name = "IsDocument";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean check(File file) {
        return file instanceof Document;
    }

    @Override
    public String toString() {
        return "IsDocument()";
    }
}
