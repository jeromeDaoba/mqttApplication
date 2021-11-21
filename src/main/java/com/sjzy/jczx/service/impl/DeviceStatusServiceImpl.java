package com.sjzy.jczx.service.impl;

import com.sjzy.jczx.domain.DeviceStatus;
import com.sjzy.jczx.repository.DeviceStatusRepository;
import com.sjzy.jczx.service.DeviceStatusService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeviceStatus}.
 */
@Service
@Transactional
public class DeviceStatusServiceImpl implements DeviceStatusService {

    private final Logger log = LoggerFactory.getLogger(DeviceStatusServiceImpl.class);

    private final DeviceStatusRepository deviceStatusRepository;

    public DeviceStatusServiceImpl(DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusRepository = deviceStatusRepository;
    }

    @Override
    public DeviceStatus save(DeviceStatus deviceStatus) {
        log.debug("Request to save DeviceStatus : {}", deviceStatus);
        return deviceStatusRepository.save(deviceStatus);
    }

    @Override
    public Optional<DeviceStatus> partialUpdate(DeviceStatus deviceStatus) {
        log.debug("Request to partially update DeviceStatus : {}", deviceStatus);

        return deviceStatusRepository
            .findById(deviceStatus.getId())
            .map(existingDeviceStatus -> {
                if (deviceStatus.getDeviceNo() != null) {
                    existingDeviceStatus.setDeviceNo(deviceStatus.getDeviceNo());
                }
                if (deviceStatus.getCode() != null) {
                    existingDeviceStatus.setCode(deviceStatus.getCode());
                }
                if (deviceStatus.getFourG() != null) {
                    existingDeviceStatus.setFourG(deviceStatus.getFourG());
                }
                if (deviceStatus.getTerm() != null) {
                    existingDeviceStatus.setTerm(deviceStatus.getTerm());
                }
                if (deviceStatus.getBattery() != null) {
                    existingDeviceStatus.setBattery(deviceStatus.getBattery());
                }
                if (deviceStatus.getBatMode() != null) {
                    existingDeviceStatus.setBatMode(deviceStatus.getBatMode());
                }
                if (deviceStatus.getCurVersion() != null) {
                    existingDeviceStatus.setCurVersion(deviceStatus.getCurVersion());
                }
                if (deviceStatus.getWorkMode() != null) {
                    existingDeviceStatus.setWorkMode(deviceStatus.getWorkMode());
                }

                return existingDeviceStatus;
            })
            .map(deviceStatusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceStatus> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceStatuses");
        return deviceStatusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceStatus> findOne(Long id) {
        log.debug("Request to get DeviceStatus : {}", id);
        return deviceStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceStatus : {}", id);
        deviceStatusRepository.deleteById(id);
    }
}
