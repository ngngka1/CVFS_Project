package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.SimpleCriterion;

public class NewSimpleCriterionCommand extends NewCriterionCommand {
    public NewSimpleCriterionCommand(String criName, String attrName, String op, String val) {
        setCriterion(new SimpleCriterion(criName, attrName, op, val));
    }
}
