package hk.edu.polyu.comp.comp2021.cvfs.model.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;

import java.util.HashMap;

public class Criteria {
    private static final HashMap<String, Criterion> criteria = new HashMap<>();
    static {
        Criterion isDocument = IsDocumentCriterion.instance;
        criteria.put(isDocument.getName(), isDocument);
    }
    public static void add(Criterion x) {
        criteria.put(x.getName(), x);
    }
    public static void pop(Criterion x) {
        criteria.remove(x.getName());
    }
    public static Criterion get(String criName) {return criteria.get(criName);}

    public static Criterion[] getAll() {
        return criteria.values().toArray(new Criterion[0]);
    }


}
