package in.avc.csvsql.parser.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ParseTreeNode<T> {
    private T content;
    private List<ParseTreeNode<T>> children;

    public ParseTreeNode(final T content) {
        this.content = content;
        children = new LinkedList<>();
    }

    public void addChild(final ParseTreeNode<T> childNode) {
        children.add(childNode);
    }
}
