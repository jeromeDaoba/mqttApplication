package com.sjzy.jczx.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sjzy.jczx.IntegrationTest;
import com.sjzy.jczx.domain.Collect;
import com.sjzy.jczx.repository.CollectRepository;
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
 * Integration tests for the {@link CollectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollectResourceIT {

    private static final String DEFAULT_OUT_SIDE_ID = "AAAAAAAAAA";
    private static final String UPDATED_OUT_SIDE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME = 1L;
    private static final Long UPDATED_TIME = 2L;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHANNEL_COUNT = 1;
    private static final Integer UPDATED_CHANNEL_COUNT = 2;

    private static final Integer DEFAULT_PLUS_INTERVAL = 1;
    private static final Integer UPDATED_PLUS_INTERVAL = 2;

    private static final Integer DEFAULT_UPLOAD_INTERVAL = 1;
    private static final Integer UPDATED_UPLOAD_INTERVAL = 2;

    private static final Integer DEFAULT_HZ = 1;
    private static final Integer UPDATED_HZ = 2;

    private static final String DEFAULT_DEVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectMockMvc;

    private Collect collect;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collect createEntity(EntityManager em) {
        Collect collect = new Collect()
            .outSideId(DEFAULT_OUT_SIDE_ID)
            .name(DEFAULT_NAME)
            .time(DEFAULT_TIME)
            .data(DEFAULT_DATA)
            .projectId(DEFAULT_PROJECT_ID)
            .channelCount(DEFAULT_CHANNEL_COUNT)
            .plusInterval(DEFAULT_PLUS_INTERVAL)
            .uploadInterval(DEFAULT_UPLOAD_INTERVAL)
            .hz(DEFAULT_HZ)
            .deviceNo(DEFAULT_DEVICE_NO);
        return collect;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collect createUpdatedEntity(EntityManager em) {
        Collect collect = new Collect()
            .outSideId(UPDATED_OUT_SIDE_ID)
            .name(UPDATED_NAME)
            .time(UPDATED_TIME)
            .data(UPDATED_DATA)
            .projectId(UPDATED_PROJECT_ID)
            .channelCount(UPDATED_CHANNEL_COUNT)
            .plusInterval(UPDATED_PLUS_INTERVAL)
            .uploadInterval(UPDATED_UPLOAD_INTERVAL)
            .hz(UPDATED_HZ)
            .deviceNo(UPDATED_DEVICE_NO);
        return collect;
    }

    @BeforeEach
    public void initTest() {
        collect = createEntity(em);
    }

    @Test
    @Transactional
    void createCollect() throws Exception {
        int databaseSizeBeforeCreate = collectRepository.findAll().size();
        // Create the Collect
        restCollectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isCreated());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate + 1);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getOutSideId()).isEqualTo(DEFAULT_OUT_SIDE_ID);
        assertThat(testCollect.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollect.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCollect.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCollect.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testCollect.getChannelCount()).isEqualTo(DEFAULT_CHANNEL_COUNT);
        assertThat(testCollect.getPlusInterval()).isEqualTo(DEFAULT_PLUS_INTERVAL);
        assertThat(testCollect.getUploadInterval()).isEqualTo(DEFAULT_UPLOAD_INTERVAL);
        assertThat(testCollect.getHz()).isEqualTo(DEFAULT_HZ);
        assertThat(testCollect.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
    }

    @Test
    @Transactional
    void createCollectWithExistingId() throws Exception {
        // Create the Collect with an existing ID
        collect.setId(1L);

        int databaseSizeBeforeCreate = collectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCollects() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get all the collectList
        restCollectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collect.getId().intValue())))
            .andExpect(jsonPath("$.[*].outSideId").value(hasItem(DEFAULT_OUT_SIDE_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID)))
            .andExpect(jsonPath("$.[*].channelCount").value(hasItem(DEFAULT_CHANNEL_COUNT)))
            .andExpect(jsonPath("$.[*].plusInterval").value(hasItem(DEFAULT_PLUS_INTERVAL)))
            .andExpect(jsonPath("$.[*].uploadInterval").value(hasItem(DEFAULT_UPLOAD_INTERVAL)))
            .andExpect(jsonPath("$.[*].hz").value(hasItem(DEFAULT_HZ)))
            .andExpect(jsonPath("$.[*].deviceNo").value(hasItem(DEFAULT_DEVICE_NO)));
    }

    @Test
    @Transactional
    void getCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get the collect
        restCollectMockMvc
            .perform(get(ENTITY_API_URL_ID, collect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collect.getId().intValue()))
            .andExpect(jsonPath("$.outSideId").value(DEFAULT_OUT_SIDE_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID))
            .andExpect(jsonPath("$.channelCount").value(DEFAULT_CHANNEL_COUNT))
            .andExpect(jsonPath("$.plusInterval").value(DEFAULT_PLUS_INTERVAL))
            .andExpect(jsonPath("$.uploadInterval").value(DEFAULT_UPLOAD_INTERVAL))
            .andExpect(jsonPath("$.hz").value(DEFAULT_HZ))
            .andExpect(jsonPath("$.deviceNo").value(DEFAULT_DEVICE_NO));
    }

    @Test
    @Transactional
    void getNonExistingCollect() throws Exception {
        // Get the collect
        restCollectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Update the collect
        Collect updatedCollect = collectRepository.findById(collect.getId()).get();
        // Disconnect from session so that the updates on updatedCollect are not directly saved in db
        em.detach(updatedCollect);
        updatedCollect
            .outSideId(UPDATED_OUT_SIDE_ID)
            .name(UPDATED_NAME)
            .time(UPDATED_TIME)
            .data(UPDATED_DATA)
            .projectId(UPDATED_PROJECT_ID)
            .channelCount(UPDATED_CHANNEL_COUNT)
            .plusInterval(UPDATED_PLUS_INTERVAL)
            .uploadInterval(UPDATED_UPLOAD_INTERVAL)
            .hz(UPDATED_HZ)
            .deviceNo(UPDATED_DEVICE_NO);

        restCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCollect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCollect))
            )
            .andExpect(status().isOk());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getOutSideId()).isEqualTo(UPDATED_OUT_SIDE_ID);
        assertThat(testCollect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollect.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCollect.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCollect.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testCollect.getChannelCount()).isEqualTo(UPDATED_CHANNEL_COUNT);
        assertThat(testCollect.getPlusInterval()).isEqualTo(UPDATED_PLUS_INTERVAL);
        assertThat(testCollect.getUploadInterval()).isEqualTo(UPDATED_UPLOAD_INTERVAL);
        assertThat(testCollect.getHz()).isEqualTo(UPDATED_HZ);
        assertThat(testCollect.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
    }

    @Test
    @Transactional
    void putNonExistingCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollectWithPatch() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Update the collect using partial update
        Collect partialUpdatedCollect = new Collect();
        partialUpdatedCollect.setId(collect.getId());

        partialUpdatedCollect
            .time(UPDATED_TIME)
            .plusInterval(UPDATED_PLUS_INTERVAL)
            .uploadInterval(UPDATED_UPLOAD_INTERVAL)
            .hz(UPDATED_HZ)
            .deviceNo(UPDATED_DEVICE_NO);

        restCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollect))
            )
            .andExpect(status().isOk());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getOutSideId()).isEqualTo(DEFAULT_OUT_SIDE_ID);
        assertThat(testCollect.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollect.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCollect.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCollect.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testCollect.getChannelCount()).isEqualTo(DEFAULT_CHANNEL_COUNT);
        assertThat(testCollect.getPlusInterval()).isEqualTo(UPDATED_PLUS_INTERVAL);
        assertThat(testCollect.getUploadInterval()).isEqualTo(UPDATED_UPLOAD_INTERVAL);
        assertThat(testCollect.getHz()).isEqualTo(UPDATED_HZ);
        assertThat(testCollect.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
    }

    @Test
    @Transactional
    void fullUpdateCollectWithPatch() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Update the collect using partial update
        Collect partialUpdatedCollect = new Collect();
        partialUpdatedCollect.setId(collect.getId());

        partialUpdatedCollect
            .outSideId(UPDATED_OUT_SIDE_ID)
            .name(UPDATED_NAME)
            .time(UPDATED_TIME)
            .data(UPDATED_DATA)
            .projectId(UPDATED_PROJECT_ID)
            .channelCount(UPDATED_CHANNEL_COUNT)
            .plusInterval(UPDATED_PLUS_INTERVAL)
            .uploadInterval(UPDATED_UPLOAD_INTERVAL)
            .hz(UPDATED_HZ)
            .deviceNo(UPDATED_DEVICE_NO);

        restCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollect))
            )
            .andExpect(status().isOk());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getOutSideId()).isEqualTo(UPDATED_OUT_SIDE_ID);
        assertThat(testCollect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollect.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCollect.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCollect.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testCollect.getChannelCount()).isEqualTo(UPDATED_CHANNEL_COUNT);
        assertThat(testCollect.getPlusInterval()).isEqualTo(UPDATED_PLUS_INTERVAL);
        assertThat(testCollect.getUploadInterval()).isEqualTo(UPDATED_UPLOAD_INTERVAL);
        assertThat(testCollect.getHz()).isEqualTo(UPDATED_HZ);
        assertThat(testCollect.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
    }

    @Test
    @Transactional
    void patchNonExistingCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();
        collect.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        int databaseSizeBeforeDelete = collectRepository.findAll().size();

        // Delete the collect
        restCollectMockMvc
            .perform(delete(ENTITY_API_URL_ID, collect.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
