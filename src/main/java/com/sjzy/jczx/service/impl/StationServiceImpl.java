package com.sjzy.jczx.service.impl;

import com.sjzy.jczx.domain.Station;
import com.sjzy.jczx.repository.StationRepository;
import com.sjzy.jczx.service.StationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Station}.
 */
@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station save(Station station) {
        log.debug("Request to save Station : {}", station);
        return stationRepository.save(station);
    }

    @Override
    public Optional<Station> partialUpdate(Station station) {
        log.debug("Request to partially update Station : {}", station);

        return stationRepository
            .findById(station.getId())
            .map(existingStation -> {
                if (station.getDeviceNo() != null) {
                    existingStation.setDeviceNo(station.getDeviceNo());
                }
                if (station.getCaptureTime() != null) {
                    existingStation.setCaptureTime(station.getCaptureTime());
                }
                if (station.getBottomRight() != null) {
                    existingStation.setBottomRight(station.getBottomRight());
                }
                if (station.getcValue() != null) {
                    existingStation.setcValue(station.getcValue());
                }
                if (station.getdValue() != null) {
                    existingStation.setdValue(station.getdValue());
                }
                if (station.getErrorCode() != null) {
                    existingStation.setErrorCode(station.getErrorCode());
                }
                if (station.getIndex() != null) {
                    existingStation.setIndex(station.getIndex());
                }
                if (station.getObjParaX() != null) {
                    existingStation.setObjParaX(station.getObjParaX());
                }
                if (station.getObjParaY() != null) {
                    existingStation.setObjParaY(station.getObjParaY());
                }
                if (station.getObjPosX() != null) {
                    existingStation.setObjPosX(station.getObjPosX());
                }
                if (station.getObjPosY() != null) {
                    existingStation.setObjPosY(station.getObjPosY());
                }
                if (station.getTopLeft() != null) {
                    existingStation.setTopLeft(station.getTopLeft());
                }
                if (station.getwParam() != null) {
                    existingStation.setwParam(station.getwParam());
                }

                return existingStation;
            })
            .map(stationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Station> findAll(Pageable pageable) {
        log.debug("Request to get all Stations");
        return stationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Station> findOne(Long id) {
        log.debug("Request to get Station : {}", id);
        return stationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Station : {}", id);
        stationRepository.deleteById(id);
    }
}
