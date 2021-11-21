package com.sjzy.jczx.web.rest;

import com.sjzy.jczx.domain.CaptureImage;
import com.sjzy.jczx.repository.CaptureImageRepository;
import com.sjzy.jczx.service.CaptureImageService;
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
 * REST controller for managing {@link com.sjzy.jczx.domain.CaptureImage}.
 */
@RestController
@RequestMapping("/api")
public class CaptureImageResource {

    private final Logger log = LoggerFactory.getLogger(CaptureImageResource.class);

    private static final String ENTITY_NAME = "captureImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaptureImageService captureImageService;

    private final CaptureImageRepository captureImageRepository;

    public CaptureImageResource(CaptureImageService captureImageService, CaptureImageRepository captureImageRepository) {
        this.captureImageService = captureImageService;
        this.captureImageRepository = captureImageRepository;
    }

    /**
     * {@code POST  /capture-images} : Create a new captureImage.
     *
     * @param captureImage the captureImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new captureImage, or with status {@code 400 (Bad Request)} if the captureImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capture-images")
    public ResponseEntity<CaptureImage> createCaptureImage(@RequestBody CaptureImage captureImage) throws URISyntaxException {
        log.debug("REST request to save CaptureImage : {}", captureImage);
        if (captureImage.getId() != null) {
            throw new BadRequestAlertException("A new captureImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaptureImage result = captureImageService.save(captureImage);
        return ResponseEntity
            .created(new URI("/api/capture-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capture-images/:id} : Updates an existing captureImage.
     *
     * @param id the id of the captureImage to save.
     * @param captureImage the captureImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captureImage,
     * or with status {@code 400 (Bad Request)} if the captureImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the captureImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capture-images/{id}")
    public ResponseEntity<CaptureImage> updateCaptureImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaptureImage captureImage
    ) throws URISyntaxException {
        log.debug("REST request to update CaptureImage : {}, {}", id, captureImage);
        if (captureImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captureImage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaptureImage result = captureImageService.save(captureImage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, captureImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /capture-images/:id} : Partial updates given fields of an existing captureImage, field will ignore if it is null
     *
     * @param id the id of the captureImage to save.
     * @param captureImage the captureImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captureImage,
     * or with status {@code 400 (Bad Request)} if the captureImage is not valid,
     * or with status {@code 404 (Not Found)} if the captureImage is not found,
     * or with status {@code 500 (Internal Server Error)} if the captureImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/capture-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaptureImage> partialUpdateCaptureImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaptureImage captureImage
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaptureImage partially : {}, {}", id, captureImage);
        if (captureImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captureImage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!captureImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaptureImage> result = captureImageService.partialUpdate(captureImage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, captureImage.getId().toString())
        );
    }

    /**
     * {@code GET  /capture-images} : get all the captureImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of captureImages in body.
     */
    @GetMapping("/capture-images")
    public ResponseEntity<List<CaptureImage>> getAllCaptureImages(Pageable pageable) {
        log.debug("REST request to get a page of CaptureImages");
        Page<CaptureImage> page = captureImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /capture-images/:id} : get the "id" captureImage.
     *
     * @param id the id of the captureImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the captureImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capture-images/{id}")
    public ResponseEntity<CaptureImage> getCaptureImage(@PathVariable Long id) {
        log.debug("REST request to get CaptureImage : {}", id);
        Optional<CaptureImage> captureImage = captureImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(captureImage);
    }

    /**
     * {@code DELETE  /capture-images/:id} : delete the "id" captureImage.
     *
     * @param id the id of the captureImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capture-images/{id}")
    public ResponseEntity<Void> deleteCaptureImage(@PathVariable Long id) {
        log.debug("REST request to delete CaptureImage : {}", id);
        captureImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
