package hk.edu.polyu.comp.comp2021.cvfs.model.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

public class BinaryCriterion extends Criterion {
    private String name;
    private String logicOp;
    private final Criterion criterion1;
    private final Criterion criterion2;

    public BinaryCriterion(String name, Criterion criterion1, String logicOp, Criterion criterion2) {
        this.name = name;
        this.criterion1 = criterion1;
        if (!logicOp.equals("||") && !logicOp.equals("&&")) throw new IllegalArgumentException("Invalid logical operator");
        this.logicOp = logicOp;
        this.criterion2 = criterion2;
    }

    @Override
    public boolean check(File file) {
        boolean left = criterion1.check(file);
        boolean right = criterion2.check(file);
        switch (this.logicOp) {
            case "||": {
                return left || right;
            }
            case "&&": {
                return left && right;
            }
        }
        return false;
    }

    @Override
    public String getName() {return this.name;}

    @Override
    public String toString() {
        return "(" + criterion1.toString() + ") " + logicOp + " (" + criterion2.toString() + ")";
    }
}
