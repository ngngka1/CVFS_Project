package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.criteria;

import hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base.Command;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.Criteria;
import hk.edu.polyu.comp.comp2021.cvfs.model.criteria.base.Criterion;

public class PrintAllCriteriaCommand extends Command {
    public PrintAllCriteriaCommand() {}

    @Override
    public void run() {
        Criterion[] criteria = Criteria.getAll();
        for (Criterion criterion : criteria) {
            java.lang.System.out.print(criterion.getName() + ": ");
            java.lang.System.out.print(criterion);
            java.lang.System.out.println();
        }
    }
}
