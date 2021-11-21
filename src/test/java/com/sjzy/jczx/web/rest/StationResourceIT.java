package com.sjzy.jczx.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sjzy.jczx.IntegrationTest;
import com.sjzy.jczx.domain.Station;
import com.sjzy.jczx.repository.StationRepository;
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
 * Integration tests for the {@link StationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StationResourceIT {

    private static final String DEFAULT_DEVICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPTURE_TIME = 1L;
    private static final Long UPDATED_CAPTURE_TIME = 2L;

    private static final String DEFAULT_BOTTOM_RIGHT = "AAAAAAAAAA";
    private static final String UPDATED_BOTTOM_RIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_C_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_C_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_D_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_D_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ERROR_CODE = 1;
    private static final Integer UPDATED_ERROR_CODE = 2;

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final String DEFAULT_OBJ_PARA_X = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_PARA_X = "BBBBBBBBBB";

    private static final String DEFAULT_OBJ_PARA_Y = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_PARA_Y = "BBBBBBBBBB";

    private static final String DEFAULT_OBJ_POS_X = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_POS_X = "BBBBBBBBBB";

    private static final String DEFAULT_OBJ_POS_Y = "AAAAAAAAAA";
    private static final String UPDATED_OBJ_POS_Y = "BBBBBBBBBB";

    private static final String DEFAULT_TOP_LEFT = "AAAAAAAAAA";
    private static final String UPDATED_TOP_LEFT = "BBBBBBBBBB";

    private static final Integer DEFAULT_W_PARAM = 1;
    private static final Integer UPDATED_W_PARAM = 2;

    private static final String ENTITY_API_URL = "/api/stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStationMockMvc;

    private Station station;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createEntity(EntityManager em) {
        Station station = new Station()
            .deviceNo(DEFAULT_DEVICE_NO)
            .captureTime(DEFAULT_CAPTURE_TIME)
            .bottomRight(DEFAULT_BOTTOM_RIGHT)
            .cValue(DEFAULT_C_VALUE)
            .dValue(DEFAULT_D_VALUE)
            .errorCode(DEFAULT_ERROR_CODE)
            .index(DEFAULT_INDEX)
            .objParaX(DEFAULT_OBJ_PARA_X)
            .objParaY(DEFAULT_OBJ_PARA_Y)
            .objPosX(DEFAULT_OBJ_POS_X)
            .objPosY(DEFAULT_OBJ_POS_Y)
            .topLeft(DEFAULT_TOP_LEFT)
            .wParam(DEFAULT_W_PARAM);
        return station;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createUpdatedEntity(EntityManager em) {
        Station station = new Station()
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .bottomRight(UPDATED_BOTTOM_RIGHT)
            .cValue(UPDATED_C_VALUE)
            .dValue(UPDATED_D_VALUE)
            .errorCode(UPDATED_ERROR_CODE)
            .index(UPDATED_INDEX)
            .objParaX(UPDATED_OBJ_PARA_X)
            .objParaY(UPDATED_OBJ_PARA_Y)
            .objPosX(UPDATED_OBJ_POS_X)
            .objPosY(UPDATED_OBJ_POS_Y)
            .topLeft(UPDATED_TOP_LEFT)
            .wParam(UPDATED_W_PARAM);
        return station;
    }

    @BeforeEach
    public void initTest() {
        station = createEntity(em);
    }

    @Test
    @Transactional
    void createStation() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().size();
        // Create the Station
        restStationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(station)))
            .andExpect(status().isCreated());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate + 1);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getDeviceNo()).isEqualTo(DEFAULT_DEVICE_NO);
        assertThat(testStation.getCaptureTime()).isEqualTo(DEFAULT_CAPTURE_TIME);
        assertThat(testStation.getBottomRight()).isEqualTo(DEFAULT_BOTTOM_RIGHT);
        assertThat(testStation.getcValue()).isEqualTo(DEFAULT_C_VALUE);
        assertThat(testStation.getdValue()).isEqualTo(DEFAULT_D_VALUE);
        assertThat(testStation.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testStation.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testStation.getObjParaX()).isEqualTo(DEFAULT_OBJ_PARA_X);
        assertThat(testStation.getObjParaY()).isEqualTo(DEFAULT_OBJ_PARA_Y);
        assertThat(testStation.getObjPosX()).isEqualTo(DEFAULT_OBJ_POS_X);
        assertThat(testStation.getObjPosY()).isEqualTo(DEFAULT_OBJ_POS_Y);
        assertThat(testStation.getTopLeft()).isEqualTo(DEFAULT_TOP_LEFT);
        assertThat(testStation.getwParam()).isEqualTo(DEFAULT_W_PARAM);
    }

    @Test
    @Transactional
    void createStationWithExistingId() throws Exception {
        // Create the Station with an existing ID
        station.setId(1L);

        int databaseSizeBeforeCreate = stationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(station)))
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStations() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList
        restStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(station.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceNo").value(hasItem(DEFAULT_DEVICE_NO)))
            .andExpect(jsonPath("$.[*].captureTime").value(hasItem(DEFAULT_CAPTURE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].bottomRight").value(hasItem(DEFAULT_BOTTOM_RIGHT)))
            .andExpect(jsonPath("$.[*].cValue").value(hasItem(DEFAULT_C_VALUE)))
            .andExpect(jsonPath("$.[*].dValue").value(hasItem(DEFAULT_D_VALUE)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].objParaX").value(hasItem(DEFAULT_OBJ_PARA_X)))
            .andExpect(jsonPath("$.[*].objParaY").value(hasItem(DEFAULT_OBJ_PARA_Y)))
            .andExpect(jsonPath("$.[*].objPosX").value(hasItem(DEFAULT_OBJ_POS_X)))
            .andExpect(jsonPath("$.[*].objPosY").value(hasItem(DEFAULT_OBJ_POS_Y)))
            .andExpect(jsonPath("$.[*].topLeft").value(hasItem(DEFAULT_TOP_LEFT)))
            .andExpect(jsonPath("$.[*].wParam").value(hasItem(DEFAULT_W_PARAM)));
    }

    @Test
    @Transactional
    void getStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get the station
        restStationMockMvc
            .perform(get(ENTITY_API_URL_ID, station.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(station.getId().intValue()))
            .andExpect(jsonPath("$.deviceNo").value(DEFAULT_DEVICE_NO))
            .andExpect(jsonPath("$.captureTime").value(DEFAULT_CAPTURE_TIME.intValue()))
            .andExpect(jsonPath("$.bottomRight").value(DEFAULT_BOTTOM_RIGHT))
            .andExpect(jsonPath("$.cValue").value(DEFAULT_C_VALUE))
            .andExpect(jsonPath("$.dValue").value(DEFAULT_D_VALUE))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.objParaX").value(DEFAULT_OBJ_PARA_X))
            .andExpect(jsonPath("$.objParaY").value(DEFAULT_OBJ_PARA_Y))
            .andExpect(jsonPath("$.objPosX").value(DEFAULT_OBJ_POS_X))
            .andExpect(jsonPath("$.objPosY").value(DEFAULT_OBJ_POS_Y))
            .andExpect(jsonPath("$.topLeft").value(DEFAULT_TOP_LEFT))
            .andExpect(jsonPath("$.wParam").value(DEFAULT_W_PARAM));
    }

    @Test
    @Transactional
    void getNonExistingStation() throws Exception {
        // Get the station
        restStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station
        Station updatedStation = stationRepository.findById(station.getId()).get();
        // Disconnect from session so that the updates on updatedStation are not directly saved in db
        em.detach(updatedStation);
        updatedStation
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .bottomRight(UPDATED_BOTTOM_RIGHT)
            .cValue(UPDATED_C_VALUE)
            .dValue(UPDATED_D_VALUE)
            .errorCode(UPDATED_ERROR_CODE)
            .index(UPDATED_INDEX)
            .objParaX(UPDATED_OBJ_PARA_X)
            .objParaY(UPDATED_OBJ_PARA_Y)
            .objPosX(UPDATED_OBJ_POS_X)
            .objPosY(UPDATED_OBJ_POS_Y)
            .topLeft(UPDATED_TOP_LEFT)
            .wParam(UPDATED_W_PARAM);

        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testStation.getCaptureTime()).isEqualTo(UPDATED_CAPTURE_TIME);
        assertThat(testStation.getBottomRight()).isEqualTo(UPDATED_BOTTOM_RIGHT);
        assertThat(testStation.getcValue()).isEqualTo(UPDATED_C_VALUE);
        assertThat(testStation.getdValue()).isEqualTo(UPDATED_D_VALUE);
        assertThat(testStation.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testStation.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testStation.getObjParaX()).isEqualTo(UPDATED_OBJ_PARA_X);
        assertThat(testStation.getObjParaY()).isEqualTo(UPDATED_OBJ_PARA_Y);
        assertThat(testStation.getObjPosX()).isEqualTo(UPDATED_OBJ_POS_X);
        assertThat(testStation.getObjPosY()).isEqualTo(UPDATED_OBJ_POS_Y);
        assertThat(testStation.getTopLeft()).isEqualTo(UPDATED_TOP_LEFT);
        assertThat(testStation.getwParam()).isEqualTo(UPDATED_W_PARAM);
    }

    @Test
    @Transactional
    void putNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, station.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(station))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(station))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(station)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .bottomRight(UPDATED_BOTTOM_RIGHT)
            .cValue(UPDATED_C_VALUE)
            .errorCode(UPDATED_ERROR_CODE)
            .objParaY(UPDATED_OBJ_PARA_Y)
            .wParam(UPDATED_W_PARAM);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testStation.getCaptureTime()).isEqualTo(UPDATED_CAPTURE_TIME);
        assertThat(testStation.getBottomRight()).isEqualTo(UPDATED_BOTTOM_RIGHT);
        assertThat(testStation.getcValue()).isEqualTo(UPDATED_C_VALUE);
        assertThat(testStation.getdValue()).isEqualTo(DEFAULT_D_VALUE);
        assertThat(testStation.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testStation.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testStation.getObjParaX()).isEqualTo(DEFAULT_OBJ_PARA_X);
        assertThat(testStation.getObjParaY()).isEqualTo(UPDATED_OBJ_PARA_Y);
        assertThat(testStation.getObjPosX()).isEqualTo(DEFAULT_OBJ_POS_X);
        assertThat(testStation.getObjPosY()).isEqualTo(DEFAULT_OBJ_POS_Y);
        assertThat(testStation.getTopLeft()).isEqualTo(DEFAULT_TOP_LEFT);
        assertThat(testStation.getwParam()).isEqualTo(UPDATED_W_PARAM);
    }

    @Test
    @Transactional
    void fullUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .deviceNo(UPDATED_DEVICE_NO)
            .captureTime(UPDATED_CAPTURE_TIME)
            .bottomRight(UPDATED_BOTTOM_RIGHT)
            .cValue(UPDATED_C_VALUE)
            .dValue(UPDATED_D_VALUE)
            .errorCode(UPDATED_ERROR_CODE)
            .index(UPDATED_INDEX)
            .objParaX(UPDATED_OBJ_PARA_X)
            .objParaY(UPDATED_OBJ_PARA_Y)
            .objPosX(UPDATED_OBJ_POS_X)
            .objPosY(UPDATED_OBJ_POS_Y)
            .topLeft(UPDATED_TOP_LEFT)
            .wParam(UPDATED_W_PARAM);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getDeviceNo()).isEqualTo(UPDATED_DEVICE_NO);
        assertThat(testStation.getCaptureTime()).isEqualTo(UPDATED_CAPTURE_TIME);
        assertThat(testStation.getBottomRight()).isEqualTo(UPDATED_BOTTOM_RIGHT);
        assertThat(testStation.getcValue()).isEqualTo(UPDATED_C_VALUE);
        assertThat(testStation.getdValue()).isEqualTo(UPDATED_D_VALUE);
        assertThat(testStation.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testStation.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testStation.getObjParaX()).isEqualTo(UPDATED_OBJ_PARA_X);
        assertThat(testStation.getObjParaY()).isEqualTo(UPDATED_OBJ_PARA_Y);
        assertThat(testStation.getObjPosX()).isEqualTo(UPDATED_OBJ_POS_X);
        assertThat(testStation.getObjPosY()).isEqualTo(UPDATED_OBJ_POS_Y);
        assertThat(testStation.getTopLeft()).isEqualTo(UPDATED_TOP_LEFT);
        assertThat(testStation.getwParam()).isEqualTo(UPDATED_W_PARAM);
    }

    @Test
    @Transactional
    void patchNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, station.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(station))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(station))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(station)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeDelete = stationRepository.findAll().size();

        // Delete the station
        restStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, station.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
