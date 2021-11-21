package com.sjzy.jczx.service.impl;

import com.sjzy.jczx.domain.CaptureImage;
import com.sjzy.jczx.repository.CaptureImageRepository;
import com.sjzy.jczx.service.CaptureImageService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaptureImage}.
 */
@Service
@Transactional
public class CaptureImageServiceImpl implements CaptureImageService {

    private final Logger log = LoggerFactory.getLogger(CaptureImageServiceImpl.class);

    private final CaptureImageRepository captureImageRepository;

    public CaptureImageServiceImpl(CaptureImageRepository captureImageRepository) {
        this.captureImageRepository = captureImageRepository;
    }

    @Override
    public CaptureImage save(CaptureImage captureImage) {
        log.debug("Request to save CaptureImage : {}", captureImage);
        return captureImageRepository.save(captureImage);
    }

    @Override
    public Optional<CaptureImage> partialUpdate(CaptureImage captureImage) {
        log.debug("Request to partially update CaptureImage : {}", captureImage);

        return captureImageRepository
            .findById(captureImage.getId())
            .map(existingCaptureImage -> {
                if (captureImage.getDeviceNo() != null) {
                    existingCaptureImage.setDeviceNo(captureImage.getDeviceNo());
                }
                if (captureImage.getCaptureTime() != null) {
                    existingCaptureImage.setCaptureTime(captureImage.getCaptureTime());
                }
                if (captureImage.getFormat() != null) {
                    existingCaptureImage.setFormat(captureImage.getFormat());
                }
                if (captureImage.gethParam() != null) {
                    existingCaptureImage.sethParam(captureImage.gethParam());
                }
                if (captureImage.getImage() != null) {
                    existingCaptureImage.setImage(captureImage.getImage());
                }
                if (captureImage.getMsgId() != null) {
                    existingCaptureImage.setMsgId(captureImage.getMsgId());
                }

                return existingCaptureImage;
            })
            .map(captureImageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CaptureImage> findAll(Pageable pageable) {
        log.debug("Request to get all CaptureImages");
        return captureImageRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CaptureImage> findOne(Long id) {
        log.debug("Request to get CaptureImage : {}", id);
        return captureImageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CaptureImage : {}", id);
        captureImageRepository.deleteById(id);
    }
}
