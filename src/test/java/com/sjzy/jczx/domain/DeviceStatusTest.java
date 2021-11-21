package com.sjzy.jczx.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjzy.jczx.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviceStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceStatus.class);
        DeviceStatus deviceStatus1 = new DeviceStatus();
        deviceStatus1.setId(1L);
        DeviceStatus deviceStatus2 = new DeviceStatus();
        deviceStatus2.setId(deviceStatus1.getId());
        assertThat(deviceStatus1).isEqualTo(deviceStatus2);
        deviceStatus2.setId(2L);
        assertThat(deviceStatus1).isNotEqualTo(deviceStatus2);
        deviceStatus1.setId(null);
        assertThat(deviceStatus1).isNotEqualTo(deviceStatus2);
    }
}
