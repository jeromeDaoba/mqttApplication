package com.sjzy.jczx.repository;

import com.sjzy.jczx.domain.DeviceStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeviceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {}
