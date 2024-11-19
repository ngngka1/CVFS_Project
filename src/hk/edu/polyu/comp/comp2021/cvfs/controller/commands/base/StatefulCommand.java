package hk.edu.polyu.comp.comp2021.cvfs.controller.commands.base;

public abstract class StatefulCommand extends Command {
    public abstract void undo();
    public abstract void redo();
}
