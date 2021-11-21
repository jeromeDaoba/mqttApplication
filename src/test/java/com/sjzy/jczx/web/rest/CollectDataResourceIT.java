package com.sjzy.jczx.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sjzy.jczx.IntegrationTest;
import com.sjzy.jczx.domain.CollectData;
import com.sjzy.jczx.repository.CollectDataRepository;
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
 * Integration tests for the {@link CollectDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollectDataResourceIT {

    private static final String DEFAULT_DEVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME = 1L;
    private static final Long UPDATED_TIME = 2L;

    private static final String DEFAULT_X_VAL = "AAAAAAAAAA";
    private static final String UPDATED_X_VAL = "BBBBBBBBBB";

    private static final String DEFAULT_Y_VAL = "AAAAAAAAAA";
    private static final String UPDATED_Y_VAL = "BBBBBBBBBB";

    private static final String DEFAULT_ALL_VALS = "AAAAAAAAAA";
    private static final String UPDATED_ALL_VALS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collect-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollectDataRepository collectDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectDataMockMvc;

    private CollectData collectData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectData createEntity(EntityManager em) {
        CollectData collectData = new CollectData()
            .deviceNo(DEFAULT_DEVICE_NO)
            .time(DEFAULT_TIME)
            .xVal(DEFAULT_X_VAL)
            .yVal(DEFAULT_Y_VAL)
            .allVals(DEFAULT_ALL_VALS);
        return collectData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectData createUpdatedEntity(EntityManager em) {
        CollectData collectData = new CollectData()
            .deviceNo(UPDATED_DEVICE_NO)
            .time(UPDATED_TIME)
            .xVal(UPDATED_X_VAL)
            .yVal(UPDATED_Y_VAL)
            .allVals(UPDATED_ALL_VALS);
        return collectData;
    }

    @BeforeEach
    public void initTest() {
        collectData = createEntity(em);
    }

    @Test
    @Transactional
    void createCollectData() throws Exception {
        int databaseSizeBeforeCreate = collectDataRepository.findAll().size();
        // Create the CollectData
        restCollectDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collectData)))
            .andExpect(status().isCreated());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeCreate + 1);
        CollectData testCollectData = collectDataList.get(collectDataList.size() - 1);
        assertThat(testCollectData.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testCollectData.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCollectData.getxVal()).isEqualTo(DEFAULT_X_VAL);
        assertThat(testCollectData.getyVal()).isEqualTo(DEFAULT_Y_VAL);
        assertThat(testCollectData.getAllVals()).isEqualTo(DEFAULT_ALL_VALS);
    }

    @Test
    @Transactional
    void createCollectDataWithExistingId() throws Exception {
        // Create the CollectData with an existing ID
        collectData.setId(1L);

        int databaseSizeBeforeCreate = collectDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collectData)))
            .andExpect(status().isBadRequest());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCollectData() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        // Get all the collectDataList
        restCollectDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectData.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceNo").value(hasItem(DEFAULT_DEVICE_NO)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].xVal").value(hasItem(DEFAULT_X_VAL)))
            .andExpect(jsonPath("$.[*].yVal").value(hasItem(DEFAULT_Y_VAL)))
            .andExpect(jsonPath("$.[*].allVals").value(hasItem(DEFAULT_ALL_VALS)));
    }

    @Test
    @Transactional
    void getCollectData() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        // Get the collectData
        restCollectDataMockMvc
            .perform(get(ENTITY_API_URL_ID, collectData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectData.getId().intValue()))
            .andExpect(jsonPath("$.deviceNo").value(DEFAULT_DEVICE_NO))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()))
            .andExpect(jsonPath("$.xVal").value(DEFAULT_X_VAL))
            .andExpect(jsonPath("$.yVal").value(DEFAULT_Y_VAL))
            .andExpect(jsonPath("$.allVals").value(DEFAULT_ALL_VALS));
    }

    @Test
    @Transactional
    void getNonExistingCollectData() throws Exception {
        // Get the collectData
        restCollectDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollectData() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();

        // Update the collectData
        CollectData updatedCollectData = collectDataRepository.findById(collectData.getId()).get();
        // Disconnect from session so that the updates on updatedCollectData are not directly saved in db
        em.detach(updatedCollectData);
        updatedCollectData.deviceNo(UPDATED_DEVICE_NO).time(UPDATED_TIME).xVal(UPDATED_X_VAL).yVal(UPDATED_Y_VAL).allVals(UPDATED_ALL_VALS);

        restCollectDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollectData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollectData))
            )
            .andExpect(status().isOk());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
        CollectData testCollectData = collectDataList.get(collectDataList.size() - 1);
        assertThat(testCollectData.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testCollectData.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCollectData.getxVal()).isEqualTo(UPDATED_X_VAL);
        assertThat(testCollectData.getyVal()).isEqualTo(UPDATED_Y_VAL);
        assertThat(testCollectData.getAllVals()).isEqualTo(UPDATED_ALL_VALS);
    }

    @Test
    @Transactional
    void putNonExistingCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collectData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collectData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collectData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collectData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollectDataWithPatch() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();

        // Update the collectData using partial update
        CollectData partialUpdatedCollectData = new CollectData();
        partialUpdatedCollectData.setId(collectData.getId());

        partialUpdatedCollectData.xVal(UPDATED_X_VAL);

        restCollectDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollectData))
            )
            .andExpect(status().isOk());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
        CollectData testCollectData = collectDataList.get(collectDataList.size() - 1);
        assertThat(testCollectData.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testCollectData.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCollectData.getxVal()).isEqualTo(UPDATED_X_VAL);
        assertThat(testCollectData.getyVal()).isEqualTo(DEFAULT_Y_VAL);
        assertThat(testCollectData.getAllVals()).isEqualTo(DEFAULT_ALL_VALS);
    }

    @Test
    @Transactional
    void fullUpdateCollectDataWithPatch() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();

        // Update the collectData using partial update
        CollectData partialUpdatedCollectData = new CollectData();
        partialUpdatedCollectData.setId(collectData.getId());

        partialUpdatedCollectData
            .deviceNo(UPDATED_DEVICE_NO)
            .time(UPDATED_TIME)
            .xVal(UPDATED_X_VAL)
            .yVal(UPDATED_Y_VAL)
            .allVals(UPDATED_ALL_VALS);

        restCollectDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollectData))
            )
            .andExpect(status().isOk());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
        CollectData testCollectData = collectDataList.get(collectDataList.size() - 1);
        assertThat(testCollectData.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testCollectData.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCollectData.getxVal()).isEqualTo(UPDATED_X_VAL);
        assertThat(testCollectData.getyVal()).isEqualTo(UPDATED_Y_VAL);
        assertThat(testCollectData.getAllVals()).isEqualTo(UPDATED_ALL_VALS);
    }

    @Test
    @Transactional
    void patchNonExistingCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collectData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collectData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collectData))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollectData() throws Exception {
        int databaseSizeBeforeUpdate = collectDataRepository.findAll().size();
        collectData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectDataMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collectData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollectData in the database
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollectData() throws Exception {
        // Initialize the database
        collectDataRepository.saveAndFlush(collectData);

        int databaseSizeBeforeDelete = collectDataRepository.findAll().size();

        // Delete the collectData
        restCollectDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, collectData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollectData> collectDataList = collectDataRepository.findAll();
        assertThat(collectDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
