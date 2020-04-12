package in.avc.csvsql.parser;

import in.avc.csvsql.parser.model.QueryPart;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public enum Keyword implements QueryPart {
    SELECT((string) -> "select".equalsIgnoreCase(string)),
    STAR((string) -> "*".equalsIgnoreCase(string)),
    FROM((string) -> "from".equalsIgnoreCase(string)),
    WHERE((string) -> "where".equalsIgnoreCase(string)),
    OPERATOR((string) -> ObjectUtils.defaultIfNull(string, StringUtils.EMPTY).matches("[><=]|(!=)|(>=)|(<=)"));

    @Getter
    private Function<String, Boolean> keywordMatcherFunction;

    Keyword(final Function<String, Boolean> keywordMatcherFunction) {
        this.keywordMatcherFunction = keywordMatcherFunction;
    }

    @Override
    public boolean isRowsource() {
        return false;
    }
}
