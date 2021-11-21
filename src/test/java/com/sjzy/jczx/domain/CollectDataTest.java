package com.sjzy.jczx.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjzy.jczx.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollectDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectData.class);
        CollectData collectData1 = new CollectData();
        collectData1.setId(1L);
        CollectData collectData2 = new CollectData();
        collectData2.setId(collectData1.getId());
        assertThat(collectData1).isEqualTo(collectData2);
        collectData2.setId(2L);
        assertThat(collectData1).isNotEqualTo(collectData2);
        collectData1.setId(null);
        assertThat(collectData1).isNotEqualTo(collectData2);
    }
}
