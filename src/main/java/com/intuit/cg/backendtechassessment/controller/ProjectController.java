package com.intuit.cg.backendtechassessment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/*
 * This is responsible to create, edit, find, delete project
 * @author ssbera
 */
@RestController
@RequestMapping(value = RequestMappings.PROJECTS_V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController extends DefaultRestController {
	
	@Autowired
	private ProjectRepository poProjectRepository = null;
	
	@ApiOperation(value="", notes = "Get all projects")
	@GetMapping
	public ResponseEntity<ServiceResponseVO> findProjects() {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Iterator<Project> oIte = poProjectRepository.findAll().iterator();
			List<Project> list = new ArrayList<>();
			oIte.forEachRemaining(list::add);
			if(list.size() == 0) {
				oServiceResponseVO.setSummaryMsg("No project found");
				return notFoundResponse(oServiceResponseVO);
			}
			oServiceResponseVO.set("list", list);
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	@ApiOperation(value="", notes = "Get project deatils")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project id", required = true, dataType = "string", paramType = "path")
	  })
	@GetMapping(path= "/{id}")
	public ResponseEntity<ServiceResponseVO> findProject(@PathVariable(value = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		try {
			Optional<Project> oProject = poProjectRepository.findById(id);
			
			if(!oProject.isPresent()) {
				oServiceResponseVO.setSummaryMsg("No project found");
				return notFoundResponse(oServiceResponseVO);
			}
				
			
			oServiceResponseVO.set("result", oProject.get());
			
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	@ApiOperation(value="", notes = "Create new project")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "Enter project name", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "description", value = "Describe the project", required = true, dataType = "string", paramType = "body"),
		@ApiImplicitParam(name = "maxBudget", value = "Enter max budget of the project", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "lastBidDate", value = "Last bid date", required = true, dataType = "string", paramType = "query")
	  })
	@PostMapping
	public ResponseEntity<ServiceResponseVO> createProject(@RequestParam(value = "name", required=true) final String name,
			@RequestBody(required=true) final String description,
			@RequestParam(value = "maxBudget", required=true) final Long maxBudget,
			@RequestParam(value = "lastBidDate", required=true) final Long lastBidDate) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		Project oProject = new Project(name, maxBudget, description, new Date(lastBidDate));
		try {
			poProjectRepository.save(oProject);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	@ApiOperation(value="", notes = "Update project")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project id", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "name", value = "Enter project name", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "description", value = "Describe the project", required = true, dataType = "string", paramType = "body"),
		@ApiImplicitParam(name = "maxBudget", value = "Enter max budget of the project", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "lastBidDate", value = "Last bid date", required = true, dataType = "string", paramType = "query")
	  })
	@PutMapping(path = "/{id}")
	public ResponseEntity<ServiceResponseVO> updateProject(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(name = "name", required=true) final String name,
			@RequestParam(name = "description", required=true) final String description,
			@RequestParam(name = "maxBudget", required=true) final Long maxBudget,
			@RequestParam(name = "lastBidDate", required=true) final Long lastBidDate) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<Project> oOptionalProject = poProjectRepository.findById(id);
		
		if(!oOptionalProject.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project found");
			return notFoundResponse(oServiceResponseVO);
		}
		Project oProject = oOptionalProject.get();
		try {
			oProject.setName(name);
			oProject.setDescription(description);
			oProject.setMaxBudget(maxBudget);
			oProject.setLastBidDate(new Date(lastBidDate));
			poProjectRepository.save(oProject);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	@ApiOperation(value="", notes = "Update last bid date of the project")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project id", required = true, dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "lastBidDate", value = "Last bid date", required = true, dataType = "string", paramType = "query")
	  })
	@PutMapping(value = "/{id}/lastBidDate")
	public ResponseEntity<ServiceResponseVO> updateProject(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(value = "lastBidDate", required=true) final long lastBidDate) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<Project> oOptionalProject = poProjectRepository.findById(id);
		
		if(!oOptionalProject.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project found");
			return notFoundResponse(oServiceResponseVO);
		}
		Project oProject = oOptionalProject.get();
		try {
			oProject.setLastBidDate(new Date(lastBidDate));
			poProjectRepository.save(oProject);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	
	@ApiOperation(value="", notes = "Update active status of the project")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project id", required = true, dataType = "string", paramType = "path")
	  })
	@PutMapping(value = "/{id}/active")
	public ResponseEntity<ServiceResponseVO> updateProject(@PathVariable(name = "id", required=true) final Long id,
			@RequestParam(value = "active", required=true) final Boolean active) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));
		
		Optional<Project> oOptionalProject = poProjectRepository.findById(id);
		
		if(!oOptionalProject.isPresent()) {
			oServiceResponseVO.setSummaryMsg("No project found");
			return notFoundResponse(oServiceResponseVO);
		}
		Project oProject = oOptionalProject.get();
		try {
			oProject.setActive(active);
			poProjectRepository.save(oProject);
			return okResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}
	
	
	
	
	
	@ApiOperation(value="", notes = "Delete a project")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "Enter project id", required = true, dataType = "string", paramType = "path")
	  })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ServiceResponseVO> deleteProject(@PathVariable(name = "id", required=true) final Long id) {
		ServiceResponseVO oServiceResponseVO = new ServiceResponseVO((String) MDC.get("requestUUID"));

		try {
			poProjectRepository.deleteById(id);
			return okResponse(oServiceResponseVO);
		}
		catch(EmptyResultDataAccessException exp1) {
			oServiceResponseVO.setSummaryMsg("No project found to delete");
			return notFoundResponse(oServiceResponseVO);
		}
		catch(Exception exp) {
			return badResponse(oServiceResponseVO);
		}
		
	}

}
