package com.sjzy.jczx.service.impl;

import com.sjzy.jczx.domain.Collect;
import com.sjzy.jczx.repository.CollectRepository;
import com.sjzy.jczx.service.CollectService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Collect}.
 */
@Service
@Transactional
public class CollectServiceImpl implements CollectService {

    private final Logger log = LoggerFactory.getLogger(CollectServiceImpl.class);

    private final CollectRepository collectRepository;

    public CollectServiceImpl(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    @Override
    public Collect save(Collect collect) {
        log.debug("Request to save Collect : {}", collect);
        return collectRepository.save(collect);
    }

    @Override
    public Optional<Collect> partialUpdate(Collect collect) {
        log.debug("Request to partially update Collect : {}", collect);

        return collectRepository
            .findById(collect.getId())
            .map(existingCollect -> {
                if (collect.getOutSideId() != null) {
                    existingCollect.setOutSideId(collect.getOutSideId());
                }
                if (collect.getName() != null) {
                    existingCollect.setName(collect.getName());
                }
                if (collect.getTime() != null) {
                    existingCollect.setTime(collect.getTime());
                }
                if (collect.getData() != null) {
                    existingCollect.setData(collect.getData());
                }
                if (collect.getProjectId() != null) {
                    existingCollect.setProjectId(collect.getProjectId());
                }
                if (collect.getChannelCount() != null) {
                    existingCollect.setChannelCount(collect.getChannelCount());
                }
                if (collect.getPlusInterval() != null) {
                    existingCollect.setPlusInterval(collect.getPlusInterval());
                }
                if (collect.getUploadInterval() != null) {
                    existingCollect.setUploadInterval(collect.getUploadInterval());
                }
                if (collect.getHz() != null) {
                    existingCollect.setHz(collect.getHz());
                }
                if (collect.getDeviceNo() != null) {
                    existingCollect.setDeviceNo(collect.getDeviceNo());
                }

                return existingCollect;
            })
            .map(collectRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Collect> findAll(Pageable pageable) {
        log.debug("Request to get all Collects");
        return collectRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Collect> findOne(Long id) {
        log.debug("Request to get Collect : {}", id);
        return collectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collect : {}", id);
        collectRepository.deleteById(id);
    }
}
