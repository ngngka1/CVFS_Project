package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.StatefulCommand;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.Criteria;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;

public class NewCriterionCommand extends StatefulCommand {
    private Criterion criterion;
    protected void setCriterion(Criterion x) {criterion = x;}

    @Override
    public void run() {
        Criteria.add(criterion);
    }

    @Override
    public void undo() {
        Criteria.pop(criterion);
    }

    @Override
    public void redo() {
        run();
    }
}
