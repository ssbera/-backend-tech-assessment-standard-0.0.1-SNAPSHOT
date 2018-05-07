package com.intuit.cg.backendtechassessment.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.intuit.cg.backendtechassessment.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	Optional<User> findByUserid(String userid);

}
