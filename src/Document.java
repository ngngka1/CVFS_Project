public class Document {
    String name;
    String type;
    String content;
    // remember to add a stack for undo/redo action
    public Document(String name, String type) {
        this.name = name;
        this.type = type;
        this.content = "";
    }

    public Document(String name, String type, String content) {
        this(name, type);
        this.content = content;
    }

}
