package com.hu3.huapp.repository;

import com.hu3.huapp.domain.TravelPackages;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelPackages entity.
 */
@SuppressWarnings("unused")
public interface TravelPackagesRepository extends JpaRepository<TravelPackages,Long> {

}
