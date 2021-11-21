package com.sjzy.jczx.web.rest;

import com.sjzy.jczx.domain.Collect;
import com.sjzy.jczx.repository.CollectRepository;
import com.sjzy.jczx.service.CollectService;
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
 * REST controller for managing {@link com.sjzy.jczx.domain.Collect}.
 */
@RestController
@RequestMapping("/api")
public class CollectResource {

    private final Logger log = LoggerFactory.getLogger(CollectResource.class);

    private static final String ENTITY_NAME = "collect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectService collectService;

    private final CollectRepository collectRepository;

    public CollectResource(CollectService collectService, CollectRepository collectRepository) {
        this.collectService = collectService;
        this.collectRepository = collectRepository;
    }

    /**
     * {@code POST  /collects} : Create a new collect.
     *
     * @param collect the collect to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collect, or with status {@code 400 (Bad Request)} if the collect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collects")
    public ResponseEntity<Collect> createCollect(@RequestBody Collect collect) throws URISyntaxException {
        log.debug("REST request to save Collect : {}", collect);
        if (collect.getId() != null) {
            throw new BadRequestAlertException("A new collect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collect result = collectService.save(collect);
        return ResponseEntity
            .created(new URI("/api/collects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collects/:id} : Updates an existing collect.
     *
     * @param id the id of the collect to save.
     * @param collect the collect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collect,
     * or with status {@code 400 (Bad Request)} if the collect is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collects/{id}")
    public ResponseEntity<Collect> updateCollect(@PathVariable(value = "id", required = false) final Long id, @RequestBody Collect collect)
        throws URISyntaxException {
        log.debug("REST request to update Collect : {}, {}", id, collect);
        if (collect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Collect result = collectService.save(collect);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collect.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /collects/:id} : Partial updates given fields of an existing collect, field will ignore if it is null
     *
     * @param id the id of the collect to save.
     * @param collect the collect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collect,
     * or with status {@code 400 (Bad Request)} if the collect is not valid,
     * or with status {@code 404 (Not Found)} if the collect is not found,
     * or with status {@code 500 (Internal Server Error)} if the collect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Collect> partialUpdateCollect(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Collect collect
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collect partially : {}, {}", id, collect);
        if (collect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Collect> result = collectService.partialUpdate(collect);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collect.getId().toString())
        );
    }

    /**
     * {@code GET  /collects} : get all the collects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collects in body.
     */
    @GetMapping("/collects")
    public ResponseEntity<List<Collect>> getAllCollects(Pageable pageable) {
        log.debug("REST request to get a page of Collects");
        Page<Collect> page = collectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collects/:id} : get the "id" collect.
     *
     * @param id the id of the collect to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collect, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collects/{id}")
    public ResponseEntity<Collect> getCollect(@PathVariable Long id) {
        log.debug("REST request to get Collect : {}", id);
        Optional<Collect> collect = collectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collect);
    }

    /**
     * {@code DELETE  /collects/:id} : delete the "id" collect.
     *
     * @param id the id of the collect to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collects/{id}")
    public ResponseEntity<Void> deleteCollect(@PathVariable Long id) {
        log.debug("REST request to delete Collect : {}", id);
        collectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
