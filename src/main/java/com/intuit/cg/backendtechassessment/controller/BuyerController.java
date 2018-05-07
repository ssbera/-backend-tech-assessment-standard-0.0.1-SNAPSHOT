package com.intuit.cg.backendtechassessment.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.domain.UserRating;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.repository.UserRatingRepository;
import com.intuit.cg.backendtechassessment.repository.UserRepository;
import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/*
 * Buyer endpoint
 * @author ssbera
 */
@RestController
@RequestMapping(value = RequestMappings.BUYERS_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyerController extends AbstractUserController {
	
	@Autowired
	private UserRatingRepository poUserRatingRepository = null;
	
	@Autowired
	private UserRepository poUserRepository = null;
	
	@Autowired
	private ProjectRepository poProjectRepository = null;
	
	@PostConstruct
	public void init() {
		init(poUserRepository, poUserRatingRepository, poProjectRepository);
	}
	
	
	
	@ApiOperation(value="", notes = "Insert seller rating")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "Enter userid", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "projectId", value = "Enter project id", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "rating", value = "Enter rating", required = true, dataType = "string", paramType = "query")
	  })
	@PostMapping(path = "/userrating/{userid}")
	public ResponseEntity<ServiceResponseVO> insertUserRating(@PathVariable(value = "userid", required=true) final String userid,
			@RequestParam(value = "projectId", required=true) final Long projectId,
			@RequestParam(value = "rating", required=true) final UserRating.Rating rating) {
		return insertUserRating(userid, projectId, UserRating.Type.SELLER, rating);
		
	}
	


}
