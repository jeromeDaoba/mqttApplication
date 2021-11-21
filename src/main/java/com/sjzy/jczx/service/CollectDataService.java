package com.sjzy.jczx.service;

import com.sjzy.jczx.domain.CollectData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CollectData}.
 */
public interface CollectDataService {
    /**
     * Save a collectData.
     *
     * @param collectData the entity to save.
     * @return the persisted entity.
     */
    CollectData save(CollectData collectData);

    /**
     * Partially updates a collectData.
     *
     * @param collectData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollectData> partialUpdate(CollectData collectData);

    /**
     * Get all the collectData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollectData> findAll(Pageable pageable);

    /**
     * Get the "id" collectData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollectData> findOne(Long id);

    /**
     * Delete the "id" collectData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
