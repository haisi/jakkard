package li.selman.jakkard;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Foo {

    @Test
    void bar() {
        String[] names1 = new String[] {"Ava", "Emma", "Olivia"};
        String[] names2 = new String[] {"Olivia", "Sophia", "Emma"};
        String[] expected = new String[] {"Ava", "Olivia", "Sophia", "Emma"};

        String[] actual = uniqueNames(names1, names2);
        assertThat(actual).hasSameElementsAs(Arrays.asList(expected.clone()));
    }

    public static String[] uniqueNames(String[] names1, String[] names2) {
        return new String[] {};
    }

}
