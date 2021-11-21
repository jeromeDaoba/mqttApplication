package com.sjzy.jczx.repository;

import com.sjzy.jczx.domain.Collect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Collect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {}
