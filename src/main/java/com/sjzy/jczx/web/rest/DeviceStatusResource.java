package com.sjzy.jczx.web.rest;

import com.sjzy.jczx.domain.DeviceStatus;
import com.sjzy.jczx.repository.DeviceStatusRepository;
import com.sjzy.jczx.service.DeviceStatusService;
import com.sjzy.jczx.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sjzy.jczx.domain.DeviceStatus}.
 */
@RestController
@RequestMapping("/api")
public class DeviceStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeviceStatusResource.class);

    private static final String ENTITY_NAME = "deviceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceStatusService deviceStatusService;

    private final DeviceStatusRepository deviceStatusRepository;

    public DeviceStatusResource(DeviceStatusService deviceStatusService, DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusService = deviceStatusService;
        this.deviceStatusRepository = deviceStatusRepository;
    }

    /**
     * {@code POST  /device-statuses} : Create a new deviceStatus.
     *
     * @param deviceStatus the deviceStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceStatus, or with status {@code 400 (Bad Request)} if the deviceStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-statuses")
    public ResponseEntity<DeviceStatus> createDeviceStatus(@RequestBody DeviceStatus deviceStatus) throws URISyntaxException {
        log.debug("REST request to save DeviceStatus : {}", deviceStatus);
        if (deviceStatus.getId() != null) {
            throw new BadRequestAlertException("A new deviceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceStatus result = deviceStatusService.save(deviceStatus);
        return ResponseEntity
            .created(new URI("/api/device-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-statuses/:id} : Updates an existing deviceStatus.
     *
     * @param id the id of the deviceStatus to save.
     * @param deviceStatus the deviceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceStatus,
     * or with status {@code 400 (Bad Request)} if the deviceStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-statuses/{id}")
    public ResponseEntity<DeviceStatus> updateDeviceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceStatus deviceStatus
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceStatus : {}, {}", id, deviceStatus);
        if (deviceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceStatus result = deviceStatusService.save(deviceStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-statuses/:id} : Partial updates given fields of an existing deviceStatus, field will ignore if it is null
     *
     * @param id the id of the deviceStatus to save.
     * @param deviceStatus the deviceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceStatus,
     * or with status {@code 400 (Bad Request)} if the deviceStatus is not valid,
     * or with status {@code 404 (Not Found)} if the deviceStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeviceStatus> partialUpdateDeviceStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceStatus deviceStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceStatus partially : {}, {}", id, deviceStatus);
        if (deviceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceStatus> result = deviceStatusService.partialUpdate(deviceStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /device-statuses} : get all the deviceStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceStatuses in body.
     */
    @GetMapping("/device-statuses")
    public ResponseEntity<List<DeviceStatus>> getAllDeviceStatuses(Pageable pageable) {
        log.debug("REST request to get a page of DeviceStatuses");
        Page<DeviceStatus> page = deviceStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-statuses/:id} : get the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-statuses/{id}")
    public ResponseEntity<DeviceStatus> getDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to get DeviceStatus : {}", id);
        Optional<DeviceStatus> deviceStatus = deviceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceStatus);
    }

    /**
     * {@code DELETE  /device-statuses/:id} : delete the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-statuses/{id}")
    public ResponseEntity<Void> deleteDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to delete DeviceStatus : {}", id);
        deviceStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
