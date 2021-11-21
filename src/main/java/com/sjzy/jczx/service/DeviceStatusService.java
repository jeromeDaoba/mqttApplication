package com.sjzy.jczx.service;

import com.sjzy.jczx.domain.DeviceStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DeviceStatus}.
 */
public interface DeviceStatusService {
    /**
     * Save a deviceStatus.
     *
     * @param deviceStatus the entity to save.
     * @return the persisted entity.
     */
    DeviceStatus save(DeviceStatus deviceStatus);

    /**
     * Partially updates a deviceStatus.
     *
     * @param deviceStatus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceStatus> partialUpdate(DeviceStatus deviceStatus);

    /**
     * Get all the deviceStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceStatus> findAll(Pageable pageable);

    /**
     * Get the "id" deviceStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceStatus> findOne(Long id);

    /**
     * Delete the "id" deviceStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
