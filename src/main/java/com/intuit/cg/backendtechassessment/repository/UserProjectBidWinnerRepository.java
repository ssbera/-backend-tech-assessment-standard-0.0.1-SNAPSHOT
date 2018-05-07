package com.intuit.cg.backendtechassessment.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.intuit.cg.backendtechassessment.domain.UserProjectBidWinner;

public interface UserProjectBidWinnerRepository extends PagingAndSortingRepository<UserProjectBidWinner, Long> {
	
}
