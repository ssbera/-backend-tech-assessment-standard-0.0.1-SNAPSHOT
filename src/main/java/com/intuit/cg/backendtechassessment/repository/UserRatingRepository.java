package com.intuit.cg.backendtechassessment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.intuit.cg.backendtechassessment.domain.UserRating;

public interface UserRatingRepository extends PagingAndSortingRepository<UserRating, Long> {
	
	@Query("SELECT AVG(ur.rating) FROM UserRating ur, User u WHERE (ur.user.id = u.id) and (u.userid = :userid) ")
    Integer getAverageUserRating(String userid);

}
