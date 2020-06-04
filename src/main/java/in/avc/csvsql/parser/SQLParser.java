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

                    // If the keywordMatcherFunction evaluated to true, then that means it is a valid reserved
                    // keyword (e.g. SELECT or FROM). If it was the keyword 'FROM', then chances are what preceded this
                    // it was a list of columns (which are not reserved keywords, as opposed to the '*' selector).
                    // We will then infer that the current keyword, FROM, indicates the end of the list of columns.
                    // So, we need to have them captured in the parse tree.
                    if (nonReservedWords.isNonEmpty()) {
                        stateMachine.accept(nonReservedWords);
                        parseTree.addChild(nonReservedWords);
                        nonReservedWords = new StringList();
                    }

                    // Next, process this keyword.
                    stateMachine.accept(keyword);
                    parseTree.addChild(keyword);
                    tokenIsAValidKeyword = true;
                }
            }

            // If this token hasn't matched with any of the reserved keywords, then this is either a column name in the
            // columns to be selected list. Or this is the CSV file name. It depends on the context (what keyword
            // preceded this token).
            if (!tokenIsAValidKeyword) {
                nonReservedWords.addString(token);
            }
        }

        if (nonReservedWords.isNonEmpty()) {
            stateMachine.accept(nonReservedWords);
            parseTree.addChild(nonReservedWords);
            nonReservedWords = new StringList();
        }

        return parseTree;
    }
}
