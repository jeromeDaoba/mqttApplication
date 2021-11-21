package com.sjzy.jczx.service.impl;

import com.sjzy.jczx.domain.CollectData;
import com.sjzy.jczx.repository.CollectDataRepository;
import com.sjzy.jczx.service.CollectDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CollectData}.
 */
@Service
@Transactional
public class CollectDataServiceImpl implements CollectDataService {

    private final Logger log = LoggerFactory.getLogger(CollectDataServiceImpl.class);

    private final CollectDataRepository collectDataRepository;

    public CollectDataServiceImpl(CollectDataRepository collectDataRepository) {
        this.collectDataRepository = collectDataRepository;
    }

    @Override
    public CollectData save(CollectData collectData) {
        log.debug("Request to save CollectData : {}", collectData);
        return collectDataRepository.save(collectData);
    }

    @Override
    public Optional<CollectData> partialUpdate(CollectData collectData) {
        log.debug("Request to partially update CollectData : {}", collectData);

        return collectDataRepository
            .findById(collectData.getId())
            .map(existingCollectData -> {
                if (collectData.getDeviceNo() != null) {
                    existingCollectData.setDeviceNo(collectData.getDeviceNo());
                }
                if (collectData.getTime() != null) {
                    existingCollectData.setTime(collectData.getTime());
                }
                if (collectData.getxVal() != null) {
                    existingCollectData.setxVal(collectData.getxVal());
                }
                if (collectData.getyVal() != null) {
                    existingCollectData.setyVal(collectData.getyVal());
                }
                if (collectData.getAllVals() != null) {
                    existingCollectData.setAllVals(collectData.getAllVals());
                }

                return existingCollectData;
            })
            .map(collectDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollectData> findAll(Pageable pageable) {
        log.debug("Request to get all CollectData");
        return collectDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollectData> findOne(Long id) {
        log.debug("Request to get CollectData : {}", id);
        return collectDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollectData : {}", id);
        collectDataRepository.deleteById(id);
    }
}
