package in.avc.csvsql.parser;

import org.junit.jupiter.api.Test;

import static org.fest.assertions.Assertions.assertThat;

public class KeywordTests {
    @Test
    public void testKeywordMatches() {
        assertThat(Keyword.STAR.getKeywordMatcherFunction().apply("*")).isTrue();
        assertThat(Keyword.SELECT.getKeywordMatcherFunction().apply("select")).isTrue();
        assertThat(Keyword.SELECT.getKeywordMatcherFunction().apply("SELECT")).isTrue();
        assertThat(Keyword.SELECT.getKeywordMatcherFunction().apply("SElEcT")).isTrue();
        assertThat(Keyword.FROM.getKeywordMatcherFunction().apply("from")).isTrue();
        assertThat(Keyword.WHERE.getKeywordMatcherFunction().apply("where")).isTrue();

        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply(">")).isTrue();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("<")).isTrue();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("=")).isTrue();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("!=")).isTrue();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("<=")).isTrue();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply(">=")).isTrue();

        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("><")).isFalse();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("=!")).isFalse();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("==")).isFalse();
        assertThat(Keyword.OPERATOR.getKeywordMatcherFunction().apply("==")).isFalse();
    }
}
