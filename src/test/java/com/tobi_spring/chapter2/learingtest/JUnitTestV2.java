package com.tobi_spring.chapter2.learingtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tobi_spring.chapter2.user.DaoFactory;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = DaoFactory.class)
public class JUnitTestV2 {

    @Autowired
    ApplicationContext context;
    static Set<JUnitTestV2> testObjects = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test
    void test1() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isEqualTo(true);
        contextObject = this.context;
    }

    @Test
    void test2() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    void test3() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;

    }
}
