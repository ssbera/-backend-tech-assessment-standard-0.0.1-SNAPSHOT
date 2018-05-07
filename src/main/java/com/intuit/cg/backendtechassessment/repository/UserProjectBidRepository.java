package com.intuit.cg.backendtechassessment.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.intuit.cg.backendtechassessment.domain.UserProjectBid;

public interface UserProjectBidRepository extends PagingAndSortingRepository<UserProjectBid, Long> {
	@Query(value = "SELECT p.PROJECT_ID, p.user_id, p.bidValue, p.created_at, AVG(ur.rating) as rating " + 
			"	FROM " + 
			"	(SELECT x.PROJECT_ID, y.user_id, x.bidValue, y.created_at " + 
			"				FROM\n" + 
			"				(SELECT DISTINCT upb.PROJECT_ID, MAX(upb.BID_VALUE) as bidValue " + 
			"				FROM  PROJECT p, USER_PROJECT_BID upb " + 
			"				WHERE (upb.PROJECT_ID = p.id)  AND (p.active = TRUE) " + 
			"			       GROUP BY upb.PROJECT_ID) as x, USER_PROJECT_BID y " + 
			"				WHERE (y.PROJECT_ID = x.PROJECT_ID) AND (y.BID_VALUE = x.bidValue) " + 
			"	ORDER BY PROJECT_ID, created_at desc) as p LEFT JOIN USER_RATING ur ON (p.USER_ID = ur.USER_ID) " + 
			"	GROUP BY  p.PROJECT_ID, p.user_id, p.created_at " + 
			"	ORDER BY PROJECT_ID, created_at desc, rating asc", nativeQuery = true)
	public List<Object[]> findWinner();
}
