package hk.edu.polyu.comp.comp2021.cvfs.model.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.files.base.File;

import java.util.Arrays;
import java.util.HashSet;

public class SimpleCriterion extends Criterion {
    public static final HashSet<String> VALUE_OPERATORS = new HashSet<String>(Arrays.asList( ">", "<", ">=", "<=", "==", "!="));
    private String name;
    private String attribute;
    private String operator;
    private String value;

    public SimpleCriterion(String criName, String attrName, String op, String val) {
        if (criName.length() != 2 && !criName.matches("[a-zA-Z]{2}")) throw new IllegalArgumentException("criteria name can only be 2-letter long!");

        switch (attrName) {
            case "name" : {
                if (!op.equals("contains")) throw new IllegalArgumentException("invalid op!");
                if (!val.matches("^[\"]{1}.*[\"]{1}$")) throw new IllegalArgumentException("Invalid value! (not a string enquoted with double quote)");
                break;
            }
            case "type" : {
                if (!op.equals("equals")) throw new IllegalArgumentException("invalid op!");
                if (!val.matches("^[\"]{1}.*[\"]{1}$")) throw new IllegalArgumentException("Invalid value! (not a string enquoted with double quote)");
                break;
            }
            case "size" : {
                if (!VALUE_OPERATORS.contains(op)) throw new IllegalArgumentException("invalid op!");
                if (!val.matches("[0-9]+")) throw new IllegalArgumentException("Invalid value! (Should be an integer value)");
                break;
            }
            default: throw new IllegalArgumentException("Invalid attribute name!");
        }
        this.name = criName;
        this.attribute = attrName;
        this.operator = op;
        this.value = val;
    }

    public boolean check(File file) {
        switch (this.attribute) {
            case "name": {
                return file.getName().contains(this.value.substring(1, this.value.length() - 1));
            }
            case "type": {
                return file.getType().equals(this.value.substring(1, this.value.length() - 1));
            }
            default: {
                switch (this.operator) {
                    case ">": {return file.size() > Integer.valueOf(this.value);}
                    case "<": {return file.size() < Integer.valueOf(this.value);}
                    case ">=": {return file.size() >= Integer.valueOf(this.value);}
                    case "<=": {return file.size() <= Integer.valueOf(this.value);}
                    case "==": {return file.size() == Integer.valueOf(this.value);}
                    default: {return file.size() != Integer.valueOf(this.value);}
                }
            }
//            default: return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return attribute + " " + operator + " " + value;
    }
}
