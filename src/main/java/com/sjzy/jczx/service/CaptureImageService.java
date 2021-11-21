package com.sjzy.jczx.service;

import com.sjzy.jczx.domain.CaptureImage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CaptureImage}.
 */
public interface CaptureImageService {
    /**
     * Save a captureImage.
     *
     * @param captureImage the entity to save.
     * @return the persisted entity.
     */
    CaptureImage save(CaptureImage captureImage);

    /**
     * Partially updates a captureImage.
     *
     * @param captureImage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CaptureImage> partialUpdate(CaptureImage captureImage);

    /**
     * Get all the captureImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CaptureImage> findAll(Pageable pageable);

    /**
     * Get the "id" captureImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CaptureImage> findOne(Long id);

    /**
     * Delete the "id" captureImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
