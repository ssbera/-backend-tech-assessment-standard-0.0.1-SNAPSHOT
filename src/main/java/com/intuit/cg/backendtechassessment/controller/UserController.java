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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings;
import com.intuit.cg.backendtechassessment.domain.User;
import com.intuit.cg.backendtechassessment.repository.UserRepository;
import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/*
 * This is responsible to create, edit, find, delete user (buyer / seller)
 * @author ssbera
 */
@RestController
@RequestMapping(value = RequestMappings.USERS_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends DefaultRestController {
	
	@Autowired
	private UserRepository poUserRepository = null;
	
	@ApiOperation(value="", notes = "Get all users")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "Enter page no for pagination", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "size", value = "Enter page size for pagination", required = false, dataType = "string", paramType = "query")
	  })
	@GetMapping
	public ResponseEntity<ServiceResponseVO> findUsers(@RequestParam(value = "page", required=false, defaultValue = "0") final Integer page,
			@RequestParam(value = "size", required=false, defaultValue = "12") final Integer size) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Pageable oPageable = PageRequest.of(page, size);
			Iterator<User> oIte = poUserRepository.findAll(oPageable).iterator();
			List<User> list = new ArrayList<>();
			oIte.forEachRemaining(list::add);
			if(list.size() == 0) {
				oServiceResponseVO.setSummaryMsg("No user found");
				return notFoundResponse(oServiceResponseVO);
			}
			oServiceResponseVO.set("list", list);
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	@ApiOperation(value="", notes = "Get User deatils")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter User id", required = true, dataType = "string", paramType = "path")
	  })
	@GetMapping(path= "/{id}")
	public ResponseEntity<ServiceResponseVO> findUser(@PathVariable(value = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Optional<User> oUser = poUserRepository.findById(id);
			
			if(!oUser.isPresent()) {
				oServiceResponseVO.setSummaryMsg("No User found");
				return notFoundResponse(oServiceResponseVO);
			}
				
			
			oServiceResponseVO.set("result", oUser.get());
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	
	@ApiOperation(value="", notes = "Create new User")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userid", value = "Enter user id", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "name", value = "Enter user name", required = true, dataType = "string", paramType = "query")
	  })
	@PostMapping
	public ResponseEntity<ServiceResponseVO> createUser(@RequestParam(value = "userid", required=true) final String userid,
			@RequestParam(value = "name", required=true) final String name) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		User oUser = new User(userid, name);
		try {
			poUserRepository.save(oUser);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	
	@ApiOperation(value="", notes = "Update user name")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter User id", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "name", value = "Enter user name", required = true, dataType = "string", paramType = "query")
	  })
	@PutMapping(value = "/{id}/name")
	public ResponseEntity<ServiceResponseVO> updateUser(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(value = "name", required=true) final String name) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<User> oOptionalUser = poUserRepository.findById(id);
		
		if(!oOptionalUser.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No User found");
			return notFoundResponse(oServiceResponseVO);
		}
		User oUser = oOptionalUser.get();
		try {
			oUser.setName(name);
			poUserRepository.save(oUser);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	@ApiOperation(value="", notes = "Delete a User")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter User id", required = true, dataType = "string", paramType = "path")
	  })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ServiceResponseVO> deleteUser(@PathVariable(name = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));

		try {
			poUserRepository.deleteById(id);
			return okResponse(oServiceResponseVO);
		}
		catch(EmptyResultDataAccessException exp1) {
			oServiceResponseVO.setSummaryMsg("No User found to delete");
			return notFoundResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}

}
