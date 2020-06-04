package in.avc.csvsql.parser;

import in.avc.csvsql.parser.model.ParseTree;
import in.avc.csvsql.parser.model.ParseTreeNode;
import in.avc.csvsql.parser.model.QueryPart;
import in.avc.csvsql.parser.model.StringList;
import in.avc.csvsql.parser.model.StringListQueryPart;

import java.util.Optional;
import java.util.function.Function;

import static in.avc.csvsql.parser.Keyword.FROM;
import static in.avc.csvsql.parser.Keyword.SELECT;
import static in.avc.csvsql.parser.Keyword.STAR;

public class QueryBuilderStateMachine {
    private static final ParseTree MODEL_PARSE_TREE = new ParseTree<Function<Object, Boolean>>();
    static {
        ParseTreeNode selectNode = new ParseTreeNode(givenQueryPartEquals(SELECT));

        ParseTreeNode starNode = new ParseTreeNode(givenQueryPartEquals(STAR));
        ParseTreeNode selectColumnListNode =  new ParseTreeNode(stringListValidator(StringListQueryPart.MAX_SIZE));

        ParseTreeNode fromNode = new ParseTreeNode(givenQueryPartEquals(FROM));

        ParseTreeNode fileRowsourceNode = new ParseTreeNode(fileRowsourceFlagIndicatorFunctionWithLengthCheck(1));

        MODEL_PARSE_TREE.addChild(selectNode);

        selectNode.addChild(starNode);
        selectNode.addChild(selectColumnListNode);

        starNode.addChild(fromNode);
        selectColumnListNode.addChild(fromNode);

        fromNode.addChild(fileRowsourceNode);
    }

    private ParseTreeNode<Function<Object, Boolean>> current;

    public QueryBuilderStateMachine() {
        current = MODEL_PARSE_TREE.getRoot();
    }

    public void accept(final QueryPart givenQueryPart) {
        ParseTreeNode validNextNode = current.getChildren().stream()
                .filter(node -> node.getContent().apply(givenQueryPart)).findAny()
                .orElseThrow(() -> new RuntimeException(String.format("Query part %s of type '%s' was not " +
                            "expected at this position", givenQueryPart, givenQueryPart.getClass().getSimpleName())));

        current = validNextNode;
    }

    private static Function<Object, Boolean> givenQueryPartEquals(final QueryPart mustBeEqualTo) {
        return (givenQueryPart) -> givenQueryPart == mustBeEqualTo;
    }

    private static Function<Object, Boolean> stringListValidator(final int allowedLength) {
        return (givenStringList) -> ((StringList)givenStringList).currentSize() <= allowedLength;
    }

    private static Function<QueryPart, Boolean> fileRowsourceFlagIndicatorFunctionWithLengthCheck(final int allowedLength) {
        return
                (givenQueryPart) -> {
                    boolean isLengthUnderLimit = stringListValidator(allowedLength).apply(givenQueryPart);
                    if (isLengthUnderLimit) {
                        ((StringListQueryPart)givenQueryPart).setRepresentsARowsource(true);
                    }

                    return isLengthUnderLimit;
                };
    }
}
