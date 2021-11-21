package com.sjzy.jczx.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sjzy.jczx.IntegrationTest;
import com.sjzy.jczx.domain.CaptureImage;
import com.sjzy.jczx.repository.CaptureImageRepository;
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
 * Integration tests for the {@link CaptureImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaptureImageResourceIT {

    private static final String DEFAULT_DEVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPTURE_TIME = 1L;
    private static final Long UPDATED_CAPTURE_TIME = 2L;

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_H_PARAM = 1;
    private static final Integer UPDATED_H_PARAM = 2;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MSG_ID = 1;
    private static final Integer UPDATED_MSG_ID = 2;

    private static final String ENTITY_API_URL = "/api/capture-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaptureImageRepository captureImageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaptureImageMockMvc;

    private CaptureImage captureImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaptureImage createEntity(EntityManager em) {
        CaptureImage captureImage = new CaptureImage()
            .deviceNo(DEFAULT_DEVICE_NO)
            .captureTime(DEFAULT_CAPTURE_TIME)
            .format(DEFAULT_FORMAT)
            .hParam(DEFAULT_H_PARAM)
            .image(DEFAULT_IMAGE)
            .msgId(DEFAULT_MSG_ID);
        return captureImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaptureImage createUpdatedEntity(EntityManager em) {
        CaptureImage captureImage = new CaptureImage()
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .format(UPDATED_FORMAT)
            .hParam(UPDATED_H_PARAM)
            .image(UPDATED_IMAGE)
            .msgId(UPDATED_MSG_ID);
        return captureImage;
    }

    @BeforeEach
    public void initTest() {
        captureImage = createEntity(em);
    }

    @Test
    @Transactional
    void createCaptureImage() throws Exception {
        int databaseSizeBeforeCreate = captureImageRepository.findAll().size();
        // Create the CaptureImage
        restCaptureImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captureImage)))
            .andExpect(status().isCreated());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeCreate + 1);
        CaptureImage testCaptureImage = captureImageList.get(captureImageList.size() - 1);
        assertThat(testCaptureImage.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testCaptureImage.getCaptureTime()).isEqualTo(DEFAULT_CAPTURE_TIME);
        assertThat(testCaptureImage.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testCaptureImage.gethParam()).isEqualTo(DEFAULT_H_PARAM);
        assertThat(testCaptureImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCaptureImage.getMsgId()).isEqualTo(DEFAULT_MSG_ID);
    }

    @Test
    @Transactional
    void createCaptureImageWithExistingId() throws Exception {
        // Create the CaptureImage with an existing ID
        captureImage.setId(1L);

        int databaseSizeBeforeCreate = captureImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaptureImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captureImage)))
            .andExpect(status().isBadRequest());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaptureImages() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        // Get all the captureImageList
        restCaptureImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(captureImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceNo").value(hasItem(DEFAULT_DEVICE_NO)))
            .andExpect(jsonPath("$.[*].captureTime").value(hasItem(DEFAULT_CAPTURE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT)))
            .andExpect(jsonPath("$.[*].hParam").value(hasItem(DEFAULT_H_PARAM)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].msgId").value(hasItem(DEFAULT_MSG_ID)));
    }

    @Test
    @Transactional
    void getCaptureImage() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        // Get the captureImage
        restCaptureImageMockMvc
            .perform(get(ENTITY_API_URL_ID, captureImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(captureImage.getId().intValue()))
            .andExpect(jsonPath("$.deviceNo").value(DEFAULT_DEVICE_NO))
            .andExpect(jsonPath("$.captureTime").value(DEFAULT_CAPTURE_TIME.intValue()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT))
            .andExpect(jsonPath("$.hParam").value(DEFAULT_H_PARAM))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.msgId").value(DEFAULT_MSG_ID));
    }

    @Test
    @Transactional
    void getNonExistingCaptureImage() throws Exception {
        // Get the captureImage
        restCaptureImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaptureImage() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();

        // Update the captureImage
        CaptureImage updatedCaptureImage = captureImageRepository.findById(captureImage.getId()).get();
        // Disconnect from session so that the updates on updatedCaptureImage are not directly saved in db
        em.detach(updatedCaptureImage);
        updatedCaptureImage
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .format(UPDATED_FORMAT)
            .hParam(UPDATED_H_PARAM)
            .image(UPDATED_IMAGE)
            .msgId(UPDATED_MSG_ID);

        restCaptureImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaptureImage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaptureImage))
            )
            .andExpect(status().isOk());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
        CaptureImage testCaptureImage = captureImageList.get(captureImageList.size() - 1);
        assertThat(testCaptureImage.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testCaptureImage.getCaptureTime()).isEqualTo(UPDATED_CAPTURE_TIME);
        assertThat(testCaptureImage.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testCaptureImage.gethParam()).isEqualTo(UPDATED_H_PARAM);
        assertThat(testCaptureImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCaptureImage.getMsgId()).isEqualTo(UPDATED_MSG_ID);
    }

    @Test
    @Transactional
    void putNonExistingCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, captureImage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captureImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captureImage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaptureImageWithPatch() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();

        // Update the captureImage using partial update
        CaptureImage partialUpdatedCaptureImage = new CaptureImage();
        partialUpdatedCaptureImage.setId(captureImage.getId());

        partialUpdatedCaptureImage.format(UPDATED_FORMAT).msgId(UPDATED_MSG_ID);

        restCaptureImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaptureImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaptureImage))
            )
            .andExpect(status().isOk());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
        CaptureImage testCaptureImage = captureImageList.get(captureImageList.size() - 1);
        assertThat(testCaptureImage.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testCaptureImage.getCaptureTime()).isEqualTo(DEFAULT_CAPTURE_TIME);
        assertThat(testCaptureImage.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testCaptureImage.gethParam()).isEqualTo(DEFAULT_H_PARAM);
        assertThat(testCaptureImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCaptureImage.getMsgId()).isEqualTo(UPDATED_MSG_ID);
    }

    @Test
    @Transactional
    void fullUpdateCaptureImageWithPatch() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();

        // Update the captureImage using partial update
        CaptureImage partialUpdatedCaptureImage = new CaptureImage();
        partialUpdatedCaptureImage.setId(captureImage.getId());

        partialUpdatedCaptureImage
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .format(UPDATED_FORMAT)
            .hParam(UPDATED_H_PARAM)
            .image(UPDATED_IMAGE)
            .msgId(UPDATED_MSG_ID);

        restCaptureImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaptureImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaptureImage))
            )
            .andExpect(status().isOk());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
        CaptureImage testCaptureImage = captureImageList.get(captureImageList.size() - 1);
        assertThat(testCaptureImage.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testCaptureImage.getCaptureTime()).isEqualTo(UPDATED_CAPTURE_TIME);
        assertThat(testCaptureImage.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testCaptureImage.gethParam()).isEqualTo(UPDATED_H_PARAM);
        assertThat(testCaptureImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCaptureImage.getMsgId()).isEqualTo(UPDATED_MSG_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, captureImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captureImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captureImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaptureImage() throws Exception {
        int databaseSizeBeforeUpdate = captureImageRepository.findAll().size();
        captureImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaptureImageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(captureImage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaptureImage in the database
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaptureImage() throws Exception {
        // Initialize the database
        captureImageRepository.saveAndFlush(captureImage);

        int databaseSizeBeforeDelete = captureImageRepository.findAll().size();

        // Delete the captureImage
        restCaptureImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, captureImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaptureImage> captureImageList = captureImageRepository.findAll();
        assertThat(captureImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
