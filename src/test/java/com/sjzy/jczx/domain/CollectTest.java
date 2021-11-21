package com.sjzy.jczx.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjzy.jczx.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collect.class);
        Collect collect1 = new Collect();
        collect1.setId(1L);
        Collect collect2 = new Collect();
        collect2.setId(collect1.getId());
        assertThat(collect1).isEqualTo(collect2);
        collect2.setId(2L);
        assertThat(collect1).isNotEqualTo(collect2);
        collect1.setId(null);
        assertThat(collect1).isNotEqualTo(collect2);
    }
}
