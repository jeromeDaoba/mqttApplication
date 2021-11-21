package com.sjzy.jczx.repository;

import com.sjzy.jczx.domain.CollectData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CollectData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectDataRepository extends JpaRepository<CollectData, Long> {}
