package com.sjzy.jczx.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sjzy.jczx.IntegrationTest;
import com.sjzy.jczx.domain.DeviceStatus;
import com.sjzy.jczx.repository.DeviceStatusRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeviceStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeviceStatusResourceIT {

    private static final String DEFAULT_DEVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Integer DEFAULT_FOUR_G = 1;
    private static final Integer UPDATED_FOUR_G = 2;

    private static final Long DEFAULT_TERM = 1L;
    private static final Long UPDATED_TERM = 2L;

    private static final Integer DEFAULT_BATTERY = 1;
    private static final Integer UPDATED_BATTERY = 2;

    private static final Integer DEFAULT_BAT_MODE = 1;
    private static final Integer UPDATED_BAT_MODE = 2;

    private static final String DEFAULT_CUR_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_CUR_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORK_MODE = 1;
    private static final Integer UPDATED_WORK_MODE = 2;

    private static final String ENTITY_API_URL = "/api/device-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceStatusMockMvc;

    private DeviceStatus deviceStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceStatus createEntity(EntityManager em) {
        DeviceStatus deviceStatus = new DeviceStatus()
            .deviceNo(DEFAULT_DEVICE_NO)
            .code(DEFAULT_CODE)
            .fourG(DEFAULT_FOUR_G)
            .term(DEFAULT_TERM)
            .battery(DEFAULT_BATTERY)
            .batMode(DEFAULT_BAT_MODE)
            .curVersion(DEFAULT_CUR_VERSION)
            .workMode(DEFAULT_WORK_MODE);
        return deviceStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceStatus createUpdatedEntity(EntityManager em) {
        DeviceStatus deviceStatus = new DeviceStatus()
            .deviceNo(UPDATED_DEVICE_NO)
            .code(UPDATED_CODE)
            .fourG(UPDATED_FOUR_G)
            .term(UPDATED_TERM)
            .battery(UPDATED_BATTERY)
            .batMode(UPDATED_BAT_MODE)
            .curVersion(UPDATED_CUR_VERSION)
            .workMode(UPDATED_WORK_MODE);
        return deviceStatus;
    }

    @BeforeEach
    public void initTest() {
        deviceStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createDeviceStatus() throws Exception {
        int databaseSizeBeforeCreate = deviceStatusRepository.findAll().size();
        // Create the DeviceStatus
        restDeviceStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isCreated());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testDeviceStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDeviceStatus.getFourG()).isEqualTo(DEFAULT_FOUR_G);
        assertThat(testDeviceStatus.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testDeviceStatus.getBattery()).isEqualTo(DEFAULT_BATTERY);
        assertThat(testDeviceStatus.getBatMode()).isEqualTo(DEFAULT_BAT_MODE);
        assertThat(testDeviceStatus.getCurVersion()).isEqualTo(DEFAULT_CUR_VERSION);
        assertThat(testDeviceStatus.getWorkMode()).isEqualTo(DEFAULT_WORK_MODE);
    }

    @Test
    @Transactional
    void createDeviceStatusWithExistingId() throws Exception {
        // Create the DeviceStatus with an existing ID
        deviceStatus.setId(1L);

        int databaseSizeBeforeCreate = deviceStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeviceStatuses() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        // Get all the deviceStatusList
        restDeviceStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceNo").value(hasItem(DEFAULT_DEVICE_NO)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fourG").value(hasItem(DEFAULT_FOUR_G)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.intValue())))
            .andExpect(jsonPath("$.[*].battery").value(hasItem(DEFAULT_BATTERY)))
            .andExpect(jsonPath("$.[*].batMode").value(hasItem(DEFAULT_BAT_MODE)))
            .andExpect(jsonPath("$.[*].curVersion").value(hasItem(DEFAULT_CUR_VERSION)))
            .andExpect(jsonPath("$.[*].workMode").value(hasItem(DEFAULT_WORK_MODE)));
    }

    @Test
    @Transactional
    void getDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        // Get the deviceStatus
        restDeviceStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, deviceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceStatus.getId().intValue()))
            .andExpect(jsonPath("$.deviceNo").value(DEFAULT_DEVICE_NO))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.fourG").value(DEFAULT_FOUR_G))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM.intValue()))
            .andExpect(jsonPath("$.battery").value(DEFAULT_BATTERY))
            .andExpect(jsonPath("$.batMode").value(DEFAULT_BAT_MODE))
            .andExpect(jsonPath("$.curVersion").value(DEFAULT_CUR_VERSION))
            .andExpect(jsonPath("$.workMode").value(DEFAULT_WORK_MODE));
    }

    @Test
    @Transactional
    void getNonExistingDeviceStatus() throws Exception {
        // Get the deviceStatus
        restDeviceStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();

        // Update the deviceStatus
        DeviceStatus updatedDeviceStatus = deviceStatusRepository.findById(deviceStatus.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceStatus are not directly saved in db
        em.detach(updatedDeviceStatus);
        updatedDeviceStatus
            .deviceNo(UPDATED_DEVICE_NO)
            .code(UPDATED_CODE)
            .fourG(UPDATED_FOUR_G)
            .term(UPDATED_TERM)
            .battery(UPDATED_BATTERY)
            .batMode(UPDATED_BAT_MODE)
            .curVersion(UPDATED_CUR_VERSION)
            .workMode(UPDATED_WORK_MODE);

        restDeviceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeviceStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeviceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testDeviceStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDeviceStatus.getFourG()).isEqualTo(UPDATED_FOUR_G);
        assertThat(testDeviceStatus.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testDeviceStatus.getBattery()).isEqualTo(UPDATED_BATTERY);
        assertThat(testDeviceStatus.getBatMode()).isEqualTo(UPDATED_BAT_MODE);
        assertThat(testDeviceStatus.getCurVersion()).isEqualTo(UPDATED_CUR_VERSION);
        assertThat(testDeviceStatus.getWorkMode()).isEqualTo(UPDATED_WORK_MODE);
    }

    @Test
    @Transactional
    void putNonExistingDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deviceStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deviceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviceStatusWithPatch() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();

        // Update the deviceStatus using partial update
        DeviceStatus partialUpdatedDeviceStatus = new DeviceStatus();
        partialUpdatedDeviceStatus.setId(deviceStatus.getId());

        partialUpdatedDeviceStatus.fourG(UPDATED_FOUR_G).term(UPDATED_TERM).battery(UPDATED_BATTERY);

        restDeviceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testDeviceStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDeviceStatus.getFourG()).isEqualTo(UPDATED_FOUR_G);
        assertThat(testDeviceStatus.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testDeviceStatus.getBattery()).isEqualTo(UPDATED_BATTERY);
        assertThat(testDeviceStatus.getBatMode()).isEqualTo(DEFAULT_BAT_MODE);
        assertThat(testDeviceStatus.getCurVersion()).isEqualTo(DEFAULT_CUR_VERSION);
        assertThat(testDeviceStatus.getWorkMode()).isEqualTo(DEFAULT_WORK_MODE);
    }

    @Test
    @Transactional
    void fullUpdateDeviceStatusWithPatch() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();

        // Update the deviceStatus using partial update
        DeviceStatus partialUpdatedDeviceStatus = new DeviceStatus();
        partialUpdatedDeviceStatus.setId(deviceStatus.getId());

        partialUpdatedDeviceStatus
            .deviceNo(UPDATED_DEVICE_NO)
            .code(UPDATED_CODE)
            .fourG(UPDATED_FOUR_G)
            .term(UPDATED_TERM)
            .battery(UPDATED_BATTERY)
            .batMode(UPDATED_BAT_MODE)
            .curVersion(UPDATED_CUR_VERSION)
            .workMode(UPDATED_WORK_MODE);

        restDeviceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeviceStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeviceStatus))
            )
            .andExpect(status().isOk());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testDeviceStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDeviceStatus.getFourG()).isEqualTo(UPDATED_FOUR_G);
        assertThat(testDeviceStatus.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testDeviceStatus.getBattery()).isEqualTo(UPDATED_BATTERY);
        assertThat(testDeviceStatus.getBatMode()).isEqualTo(UPDATED_BAT_MODE);
        assertThat(testDeviceStatus.getCurVersion()).isEqualTo(UPDATED_CUR_VERSION);
        assertThat(testDeviceStatus.getWorkMode()).isEqualTo(UPDATED_WORK_MODE);
    }

    @Test
    @Transactional
    void patchNonExistingDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deviceStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deviceStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();
        deviceStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviceStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deviceStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        int databaseSizeBeforeDelete = deviceStatusRepository.findAll().size();

        // Delete the deviceStatus
        restDeviceStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, deviceStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
