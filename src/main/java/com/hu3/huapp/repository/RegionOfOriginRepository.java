package com.hu3.huapp.repository;

import com.hu3.huapp.domain.RegionOfOrigin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RegionOfOrigin entity.
 */
@SuppressWarnings("unused")
public interface RegionOfOriginRepository extends JpaRepository<RegionOfOrigin,Long> {

}
