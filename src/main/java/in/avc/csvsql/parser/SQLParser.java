package in.avc.csvsql.parser;

import in.avc.csvsql.parser.model.ParseTree;
import in.avc.csvsql.parser.model.StringList;

public class SQLParser {
    private ParseTree parseTree;
    private static final String SPACE=" ";

    private final QueryBuilderStateMachine stateMachine;

    public SQLParser() {
        parseTree = new ParseTree();
        stateMachine = new QueryBuilderStateMachine();
    }

    public ParseTree parse(final String sql) {
        String[] tokens = sql.split(SPACE);
        StringList nonReservedWords = new StringList();

        for (String token: tokens) {
            boolean tokenIsAValidKeyword = false;

            for (Keyword keyword: Keyword.values()) {
                if (keyword.getKeywordMatcherFunction().apply(token)) {
                    if (nonReservedWords.isNonEmpty()) {
                        stateMachine.accept(nonReservedWords);
                        parseTree.addChild(nonReservedWords);
                        nonReservedWords = new StringList();
                    }

                    stateMachine.accept(keyword);
                    parseTree.addChild(keyword);
                    tokenIsAValidKeyword = true;
                }
            }

            if (!tokenIsAValidKeyword) {
                nonReservedWords.addString(token);
            }
        }

        return parseTree;
    }
}
