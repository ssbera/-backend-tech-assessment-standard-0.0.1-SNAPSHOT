package com.intuit.cg.backendtechassessment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.intuit.cg.backendtechassessment.domain.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
	@Query("SELECT p FROM Project p WHERE (p.active = true) and (p.lastBidDate < CURRENT_TIME()) ")
	Iterable<Project> getAllActiveProjectWhoseBidingDateEnded();
}
