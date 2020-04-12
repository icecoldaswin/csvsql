package in.avc.csvsql.parser.model;

import lombok.Getter;

public class ParseTree<T> {
    @Getter
    private final ParseTreeNode<T> root;
    @Getter
    private ParseTreeNode<T> lastAdded;

    public ParseTree() {
        root = new ParseTreeNode<>(null);
    }

    public ParseTreeNode<T> addChild(final ParseTreeNode<T> content) {
        lastAdded = content;
        root.addChild(lastAdded);

        return lastAdded;
    }

    public ParseTreeNode<T> addChild(final T content) {
        lastAdded = new ParseTreeNode<>(content);
        root.addChild(lastAdded);

        return lastAdded;
    }
}
