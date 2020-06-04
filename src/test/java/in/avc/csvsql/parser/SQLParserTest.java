package in.avc.csvsql.parser;

import in.avc.csvsql.parser.model.ParseTree;
import org.junit.jupiter.api.Test;

public class SQLParserTest {
    private SQLParser parser;

    @Test
    public void test() {
        SQLParser parser = new SQLParser();

        ParseTree tree = parser.parse("select * from /tmp/mycsv.csv");

        System.out.println(tree.getRoot().getContent());
        System.out.println(tree.getRoot().getChildren().size());

    }
}
