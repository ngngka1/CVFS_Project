package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.Criteria;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.NegatedCriterion;

public class NewNegationCommand extends NewCriterionCommand {
    public NewNegationCommand(String criName1, String criName2) {
        Criterion orig = Criteria.get(criName2);
        if (orig == null) throw new IllegalArgumentException("Target criteria not found");
        setCriterion(new NegatedCriterion(criName1, orig));
    }
}
