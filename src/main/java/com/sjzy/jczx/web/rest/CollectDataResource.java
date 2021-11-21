package com.sjzy.jczx.web.rest;

import com.sjzy.jczx.domain.CollectData;
import com.sjzy.jczx.repository.CollectDataRepository;
import com.sjzy.jczx.service.CollectDataService;
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
 * REST controller for managing {@link com.sjzy.jczx.domain.CollectData}.
 */
@RestController
@RequestMapping("/api")
public class CollectDataResource {

    private final Logger log = LoggerFactory.getLogger(CollectDataResource.class);

    private static final String ENTITY_NAME = "collectData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectDataService collectDataService;

    private final CollectDataRepository collectDataRepository;

    public CollectDataResource(CollectDataService collectDataService, CollectDataRepository collectDataRepository) {
        this.collectDataService = collectDataService;
        this.collectDataRepository = collectDataRepository;
    }

    /**
     * {@code POST  /collect-data} : Create a new collectData.
     *
     * @param collectData the collectData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectData, or with status {@code 400 (Bad Request)} if the collectData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collect-data")
    public ResponseEntity<CollectData> createCollectData(@RequestBody CollectData collectData) throws URISyntaxException {
        log.debug("REST request to save CollectData : {}", collectData);
        if (collectData.getId() != null) {
            throw new BadRequestAlertException("A new collectData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectData result = collectDataService.save(collectData);
        return ResponseEntity
            .created(new URI("/api/collect-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collect-data/:id} : Updates an existing collectData.
     *
     * @param id the id of the collectData to save.
     * @param collectData the collectData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectData,
     * or with status {@code 400 (Bad Request)} if the collectData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collect-data/{id}")
    public ResponseEntity<CollectData> updateCollectData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CollectData collectData
    ) throws URISyntaxException {
        log.debug("REST request to update CollectData : {}, {}", id, collectData);
        if (collectData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollectData result = collectDataService.save(collectData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /collect-data/:id} : Partial updates given fields of an existing collectData, field will ignore if it is null
     *
     * @param id the id of the collectData to save.
     * @param collectData the collectData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectData,
     * or with status {@code 400 (Bad Request)} if the collectData is not valid,
     * or with status {@code 404 (Not Found)} if the collectData is not found,
     * or with status {@code 500 (Internal Server Error)} if the collectData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collect-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CollectData> partialUpdateCollectData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CollectData collectData
    ) throws URISyntaxException {
        log.debug("REST request to partial update CollectData partially : {}, {}", id, collectData);
        if (collectData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollectData> result = collectDataService.partialUpdate(collectData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectData.getId().toString())
        );
    }

    /**
     * {@code GET  /collect-data} : get all the collectData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectData in body.
     */
    @GetMapping("/collect-data")
    public ResponseEntity<List<CollectData>> getAllCollectData(Pageable pageable) {
        log.debug("REST request to get a page of CollectData");
        Page<CollectData> page = collectDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collect-data/:id} : get the "id" collectData.
     *
     * @param id the id of the collectData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collect-data/{id}")
    public ResponseEntity<CollectData> getCollectData(@PathVariable Long id) {
        log.debug("REST request to get CollectData : {}", id);
        Optional<CollectData> collectData = collectDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collectData);
    }

    /**
     * {@code DELETE  /collect-data/:id} : delete the "id" collectData.
     *
     * @param id the id of the collectData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collect-data/{id}")
    public ResponseEntity<Void> deleteCollectData(@PathVariable Long id) {
        log.debug("REST request to delete CollectData : {}", id);
        collectDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
