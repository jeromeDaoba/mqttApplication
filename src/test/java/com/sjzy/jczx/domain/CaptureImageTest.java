package com.sjzy.jczx.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjzy.jczx.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaptureImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaptureImage.class);
        CaptureImage captureImage1 = new CaptureImage();
        captureImage1.setId(1L);
        CaptureImage captureImage2 = new CaptureImage();
        captureImage2.setId(captureImage1.getId());
        assertThat(captureImage1).isEqualTo(captureImage2);
        captureImage2.setId(2L);
        assertThat(captureImage1).isNotEqualTo(captureImage2);
        captureImage1.setId(null);
        assertThat(captureImage1).isNotEqualTo(captureImage2);
    }
}
