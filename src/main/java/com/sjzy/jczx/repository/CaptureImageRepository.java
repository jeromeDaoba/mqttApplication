package com.sjzy.jczx.repository;

import com.sjzy.jczx.domain.CaptureImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaptureImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptureImageRepository extends JpaRepository<CaptureImage, Long> {}
