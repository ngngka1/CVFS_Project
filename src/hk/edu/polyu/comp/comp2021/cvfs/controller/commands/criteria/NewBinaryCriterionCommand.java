package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.BinaryCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.Criteria;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;

public class NewBinaryCriterionCommand extends NewCriterionCommand {
    public NewBinaryCriterionCommand(String criName1, String criName3, String logicOp, String criName4) {
        Criterion criterion1 = Criteria.get(criName3);
        Criterion criterion2 = Criteria.get(criName4);
        if (criterion1 == null || criterion2 == null) throw new IllegalArgumentException("Target criteria not found");
        setCriterion(new BinaryCriterion(criName1, criterion1, logicOp, criterion2));
    }
}
