package com.intuit.cg.backendtechassessment.controller;

import java.util.Optional;

import org.apache.log4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import com.intuit.cg.backendtechassessment.domain.Project;
import com.intuit.cg.backendtechassessment.domain.User;
import com.intuit.cg.backendtechassessment.domain.UserRating;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.repository.UserRatingRepository;
import com.intuit.cg.backendtechassessment.repository.UserRepository;
import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;

/*
 * This class is responsible to create, edit, find, delete user rating
 * @author ssbera
 */
public abstract class AbstractUserController extends DefaultRestController {
	
	private UserRatingRepository poUserRatingRepository = null;
	
	private UserRepository poUserRepository = null;
	
	private ProjectRepository poProjectRepository = null;
	
	protected void init(UserRepository oUserRepository, UserRatingRepository oUserRatingRepository, 
			ProjectRepository oProjectRepository) {
		this.poUserRepository = oUserRepository;
		this.poUserRatingRepository = oUserRatingRepository;
		this.poProjectRepository = oProjectRepository;
	}
	
	protected ResponseEntity<ServiceResponseVO> insertUserRating(
			String userid,
			final Long projectId,
			final UserRating.Type type,
			final UserRating.Rating rating) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<User> oUser = poUserRepository.findByUserid(userid);
		
		if(!oUser.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No User found");
			return notFoundResponse(oServiceResponseVO);
		}
		
		Optional<Project> oProject = poProjectRepository.findById(projectId);
		
		if(!oProject.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project found");
			return notFoundResponse(oServiceResponseVO);
		}
		
		int r = 0;
		if(rating.equals(UserRating.Rating.BAD))
			r = 2;
		else if(rating.equals(UserRating.Rating.AVERAGE))
			r = 4;
		else if(rating.equals(UserRating.Rating.GOOD))
			r = 6;
		else if(rating.equals(UserRating.Rating.VERY_GOOD))
			r = 8;
		else if(rating.equals(UserRating.Rating.EXCELENT))
			r = 10;
			
		
		UserRating oUserRating = new UserRating(oUser.get(), oProject.get(), type, r);
		try {
			poUserRatingRepository.save(oUserRating);
			return okResponse(oServiceResponseVO);
		}
		catch(DataIntegrityViolationException exp1) {
			oServiceResponseVO.setSummaryMsg("Can not insert user rating more than one time for same project");
			return badResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	


}
