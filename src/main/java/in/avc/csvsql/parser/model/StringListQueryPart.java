package in.avc.csvsql.parser.model;

import in.avc.csvsql.parser.Keyword;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class StringListQueryPart implements QueryPart {
    public static final int MAX_SIZE = 10;
    @Getter
    private int positionBasedMaxSize = MAX_SIZE;
    @Getter
    private List<String> strings;

    public StringListQueryPart(final int positionBasedMaxSize) {
        this();
        if (positionBasedMaxSize > MAX_SIZE) {
            throw new RuntimeException(String.format("Cannot be larger than %d", MAX_SIZE));
        }
        this.positionBasedMaxSize = positionBasedMaxSize;
    }
    public StringListQueryPart() {
        this.strings = new LinkedList<>();
    }

    public void addString(final String string) {
        if (strings.size() > positionBasedMaxSize) {
            throw new RuntimeException("Not allowed");
        }
        strings.add(trim(string));
    }

    private String trim(final String string) {
        return string.trim();
    }

    public boolean isNonEmpty() {
        return !strings.isEmpty();
    }

    public int currentSize() { return strings.size(); }

    public void tokenizeAsStringList(final String commaSeparatedWords) {
        for (String string: commaSeparatedWords.split(",")) {
            strings.add(trim(string));
        }
    }

    public String[] getAsArray() {
        return (String[])strings.toArray();
    }

    @Override
    public boolean isRowsource() {
        return false;
    }
}
