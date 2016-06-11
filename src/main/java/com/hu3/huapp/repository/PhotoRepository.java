package com.hu3.huapp.repository;

import com.hu3.huapp.domain.Photo;
import com.hu3.huapp.web.rest.dto.PhotoDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
public interface PhotoRepository extends JpaRepository<Photo,Long> {
	
	@Query("select p from Photo p where p.packages.id = :travelPackageId")
	List<Photo> findAllTravelPackageId(@Param("travelPackageId") Long travelPackageId);
}
