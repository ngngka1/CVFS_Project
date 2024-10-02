class Document {
    String name;
    String type;
    String content;
    // remember to add a stack for undo/redo action
    public Document(String name, String type) {
        if (name != null && name.matches("[A-Za-z0-9]+") && name.length() <= 10) { // fyi: https://www.w3schools.com/python/python_regex.asp
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid document name!");
        }
        this.type = type;
        this.content = "";
    }

    public Document(String name, String type, String content) {
        this(name, type);
        this.content = content;
    }

    public int size() {
        return System.defaultDocumentSize + content.length() * 2;
    }

}
