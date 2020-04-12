package in.avc.csvsql.parser;

import in.avc.csvsql.parser.model.StringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryBuilderStateMachineTest {
    private QueryBuilderStateMachine queryBuilder;

    @Test
    public void test_valid_StateTransitionValidations_with_Star() {
        queryBuilder = new QueryBuilderStateMachine();
        queryBuilder.accept(Keyword.SELECT); // Should not throw
        queryBuilder.accept(Keyword.STAR); // Should not throw
        queryBuilder.accept(Keyword.FROM); // Should not throw
    }

    @Test
    public void test_invalid_StateTransitionValidations_with_Star() {
        queryBuilder = new QueryBuilderStateMachine();
        queryBuilder.accept(Keyword.SELECT); // Should not throw
        queryBuilder.accept(Keyword.STAR); // Should not throw
        assertThrows(RuntimeException.class, () -> queryBuilder.accept(Keyword.STAR));
    }

    @Test
    public void test_valid_StateTransitionValidations_with_twoColumsnIncolumnList() {
        queryBuilder = new QueryBuilderStateMachine();
        queryBuilder.accept(Keyword.SELECT); // Should not throw
        queryBuilder.accept(new StringList("Column1", "Column2")); // Should not throw
        queryBuilder.accept(Keyword.FROM); // Should not throw
        queryBuilder.accept(new StringList("File1"));
    }

    @Test
    public void test_valid_StateTransitionValidations_with_oneColumnIncolumnList() {
        queryBuilder = new QueryBuilderStateMachine();
        queryBuilder.accept(Keyword.SELECT); // Should not throw
        queryBuilder.accept(new StringList("Column1")); // Should not throw
        queryBuilder.accept(Keyword.FROM); // Should not throw
        queryBuilder.accept(new StringList("File1"));
    }

    @Test
    public void test_valid_StateTransitionValidations_with_twoFileRowsources() {
        queryBuilder = new QueryBuilderStateMachine();
        queryBuilder.accept(Keyword.SELECT); // Should not throw
        queryBuilder.accept(new StringList("Column1", "Column2")); // Should not throw
        queryBuilder.accept(Keyword.FROM); // Should not throw
        assertThrows(RuntimeException.class, () -> queryBuilder.accept(new StringList("File1", "File2")));
    }
}
