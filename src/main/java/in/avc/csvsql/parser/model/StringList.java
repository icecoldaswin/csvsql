package in.avc.csvsql.parser.model;

public final class StringList extends StringListQueryPart {
    public StringList(final String... strings) {
        super();
        for (String string: strings) {
            this.addString(string);
        }
    }
    private StringList() {
        super();
    }
}
