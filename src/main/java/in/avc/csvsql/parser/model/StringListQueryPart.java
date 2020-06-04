package in.avc.csvsql.parser.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class StringListQueryPart implements QueryPart {
    public static final int MAX_SIZE = 10;
    @Getter
    private int positionBasedMaxSize = MAX_SIZE;
    @Getter
    private List<String> strings;
    @Setter
    private boolean representsARowsource;

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
            throw new RuntimeException("Current string list exceeded maximum allowed for its position");
        }

        // FIXME: !!! HACK ALERT !!!
        // All strings except the last must end with a comma ','.
        // The following line makes this StringList opinionated by stripping ','. This MUST NOT be done by StringList.
        // This must be done by the consumer of this string list.
        strings.add(trim(string.replace(",", "")));
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
        return strings.toArray(new String[strings.size()]);
    }

    @Override
    public boolean isRepresentsARowsource() {
        return representsARowsource;
    }
}
