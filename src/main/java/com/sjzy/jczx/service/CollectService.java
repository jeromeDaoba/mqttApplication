package com.sjzy.jczx.service;

import com.sjzy.jczx.domain.Collect;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Collect}.
 */
public interface CollectService {
    /**
     * Save a collect.
     *
     * @param collect the entity to save.
     * @return the persisted entity.
     */
    Collect save(Collect collect);

    /**
     * Partially updates a collect.
     *
     * @param collect the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Collect> partialUpdate(Collect collect);

    /**
     * Get all the collects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Collect> findAll(Pageable pageable);

    /**
     * Get the "id" collect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Collect> findOne(Long id);

    /**
     * Delete the "id" collect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
