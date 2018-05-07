package com.intuit.cg.backendtechassessment.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.domain.Project;
import com.intuit.cg.backendtechassessment.domain.User;
import com.intuit.cg.backendtechassessment.domain.UserProjectBid;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.repository.UserProjectBidRepository;
import com.intuit.cg.backendtechassessment.repository.UserRepository;
import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/*
 * This is responsible to create, edit, find, delete UserProjectBid
 * @author ssbera
 */
@RestController
@RequestMapping(value = RequestMappings.BIDS_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProjectBidController extends DefaultRestController {
	
	@Autowired
	private UserProjectBidRepository poUserProjectBidRepository = null;
	
	@Autowired
	private UserRepository poUserRepository = null;
	
	@Autowired
	private ProjectRepository poProjectRepository = null;
	
	
	
	@ApiOperation(value="", notes = "Get all project bids")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Enter page no for pagination", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "size", value = "Enter page size for pagination", required = false, dataType = "string", paramType = "query")
	  })
	@GetMapping
	public ResponseEntity<ServiceResponseVO> findBids(@RequestParam(value = "page", required=false, defaultValue = "0") final Integer page,
			@RequestParam(value = "size", required=false, defaultValue = "12") final Integer size) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Pageable oPageable = PageRequest.of(page, size);
			Iterator<UserProjectBid> oIte = poUserProjectBidRepository.findAll(oPageable).iterator();
			List<UserProjectBid> list = new ArrayList<>();
			oIte.forEachRemaining(list::add);
			if(list.size() == 0) {
				oServiceResponseVO.setSummaryMsg("No user project bid found");
				return notFoundResponse(oServiceResponseVO);
			}
			oServiceResponseVO.set("list", list);
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	@ApiOperation(value="", notes = "Get project bid deatils")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project bid id", required = true, dataType = "string", paramType = "path")
	  })
	@GetMapping(path= "/{id}")
	public ResponseEntity<ServiceResponseVO> findUserProjectBid(@PathVariable(value = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Optional<UserProjectBid> oUserProjectBid = poUserProjectBidRepository.findById(id);
			
			if(!oUserProjectBid.isPresent()) {
				oServiceResponseVO.setSummaryMsg("No UserProjectBid found");
				return notFoundResponse(oServiceResponseVO);
			}
				
			
			oServiceResponseVO.set("result", oUserProjectBid.get());
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	@ApiOperation(value="", notes = "Create new project bid")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "Enter userid", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "projectId", value = "Enter project id", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "bidValue", value = "Enter bid value", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "skillSet", value = "Enter skill set", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "comment", value = "Enter comment", required = true, dataType = "string", paramType = "body")
	  })
	@PostMapping
	public ResponseEntity<ServiceResponseVO> createUserProjectBid(@RequestParam(value = "userid", required=true) final String userid,
			@RequestParam(value = "projectId", required=true) final Long projectId,
			@RequestParam(value = "bidValue", required=true) final Long bidValue,
			@RequestParam(value = "skillSet", required=true) final String skillSet,
			@RequestBody(required=true) final String comment) {
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
		
		Project oPro = oProject.get();
		
		if(System.currentTimeMillis() > oPro.getLastBidDate().getTime()) {
			oServiceResponseVO.setSummaryMsg("Project is not taking bid any more");
			return badResponse(oServiceResponseVO);
		}
		
		UserProjectBid oUserProjectBid = new UserProjectBid(oUser.get(), oProject.get(), bidValue, skillSet, comment);
		try {
			poUserProjectBidRepository.save(oUserProjectBid);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	

	
	
	
	@ApiOperation(value="", notes = "Update project bid value of the bid")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project bid id", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "bidValue", value = "Enter bid value", required = true, dataType = "string", paramType = "query"),
	  })
	@PutMapping(value = "/{id}/bidValue")
	public ResponseEntity<ServiceResponseVO> updateUserProjectBidValue(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(value = "bidValue", required=true) final long bidValue) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<UserProjectBid> oOptionalUserProjectBid = poUserProjectBidRepository.findById(id);
		
		if(!oOptionalUserProjectBid.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project bid found");
			return notFoundResponse(oServiceResponseVO);
		}
		UserProjectBid oUserProjectBid = oOptionalUserProjectBid.get();
		try {
			oUserProjectBid.setBidValue(bidValue);
			poUserProjectBidRepository.save(oUserProjectBid);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	@ApiOperation(value="", notes = "Update comment of the bid")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project bid id", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "comment", value = "Enter comment", required = true, dataType = "string", paramType = "query"),
	  })
	@PutMapping(value = "/{id}/comment")
	public ResponseEntity<ServiceResponseVO> updateUserProjectBidComment(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(value = "comment", required=true) final String comment) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<UserProjectBid> oOptionalUserProjectBid = poUserProjectBidRepository.findById(id);
		
		if(!oOptionalUserProjectBid.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project bid found");
			return notFoundResponse(oServiceResponseVO);
		}
		UserProjectBid oUserProjectBid = oOptionalUserProjectBid.get();
		try {
			oUserProjectBid.setComment(comment);
			poUserProjectBidRepository.save(oUserProjectBid);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	
	
	
	@ApiOperation(value="", notes = "Delete user project bid")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter user project bid id", required = true, dataType = "string", paramType = "path")
	  })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ServiceResponseVO> deleteUserProjectBid(@PathVariable(name = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));

		try {
			poUserProjectBidRepository.deleteById(id);
			return okResponse(oServiceResponseVO);
		}
		catch(EmptyResultDataAccessException exp1) {
			oServiceResponseVO.setSummaryMsg("No UserProjectBid found to delete");
			return notFoundResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}

}
