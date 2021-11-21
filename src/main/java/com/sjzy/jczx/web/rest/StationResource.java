package com.sjzy.jczx.web.rest;

import com.sjzy.jczx.domain.Station;
import com.sjzy.jczx.repository.StationRepository;
import com.sjzy.jczx.service.StationService;
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
 * REST controller for managing {@link com.sjzy.jczx.domain.Station}.
 */
@RestController
@RequestMapping("/api")
public class StationResource {

    private final Logger log = LoggerFactory.getLogger(StationResource.class);

    private static final String ENTITY_NAME = "station";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StationService stationService;

    private final StationRepository stationRepository;

    public StationResource(StationService stationService, StationRepository stationRepository) {
        this.stationService = stationService;
        this.stationRepository = stationRepository;
    }

    /**
     * {@code POST  /stations} : Create a new station.
     *
     * @param station the station to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new station, or with status {@code 400 (Bad Request)} if the station has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stations")
    public ResponseEntity<Station> createStation(@RequestBody Station station) throws URISyntaxException {
        log.debug("REST request to save Station : {}", station);
        if (station.getId() != null) {
            throw new BadRequestAlertException("A new station cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Station result = stationService.save(station);
        return ResponseEntity
            .created(new URI("/api/stations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stations/:id} : Updates an existing station.
     *
     * @param id the id of the station to save.
     * @param station the station to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated station,
     * or with status {@code 400 (Bad Request)} if the station is not valid,
     * or with status {@code 500 (Internal Server Error)} if the station couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stations/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable(value = "id", required = false) final Long id, @RequestBody Station station)
        throws URISyntaxException {
        log.debug("REST request to update Station : {}, {}", id, station);
        if (station.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, station.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Station result = stationService.save(station);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, station.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stations/:id} : Partial updates given fields of an existing station, field will ignore if it is null
     *
     * @param id the id of the station to save.
     * @param station the station to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated station,
     * or with status {@code 400 (Bad Request)} if the station is not valid,
     * or with status {@code 404 (Not Found)} if the station is not found,
     * or with status {@code 500 (Internal Server Error)} if the station couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/stations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Station> partialUpdateStation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Station station
    ) throws URISyntaxException {
        log.debug("REST request to partial update Station partially : {}, {}", id, station);
        if (station.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, station.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Station> result = stationService.partialUpdate(station);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, station.getId().toString())
        );
    }

    /**
     * {@code GET  /stations} : get all the stations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stations in body.
     */
    @GetMapping("/stations")
    public ResponseEntity<List<Station>> getAllStations(Pageable pageable) {
        log.debug("REST request to get a page of Stations");
        Page<Station> page = stationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stations/:id} : get the "id" station.
     *
     * @param id the id of the station to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the station, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stations/{id}")
    public ResponseEntity<Station> getStation(@PathVariable Long id) {
        log.debug("REST request to get Station : {}", id);
        Optional<Station> station = stationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(station);
    }

    /**
     * {@code DELETE  /stations/:id} : delete the "id" station.
     *
     * @param id the id of the station to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stations/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        log.debug("REST request to delete Station : {}", id);
        stationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
