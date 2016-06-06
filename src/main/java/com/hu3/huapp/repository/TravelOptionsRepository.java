package com.hu3.huapp.repository;

import com.hu3.huapp.domain.TravelOptions;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelOptions entity.
 */
@SuppressWarnings("unused")
public interface TravelOptionsRepository extends JpaRepository<TravelOptions,Long> {

}
