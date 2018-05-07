package com.intuit.cg.backendtechassessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.intuit.cg.backendtechassessment.util.ServiceResponseVO;



/*
 * Default rest controller
 * @author ssbera
 */
public abstract class DefaultRestController {
	
	public ResponseEntity<ServiceResponseVO> okResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.OK);
	}
	
	public ResponseEntity<ServiceResponseVO> badResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<ServiceResponseVO> forbiddenResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.FORBIDDEN);
	}
	   
	public ResponseEntity<ServiceResponseVO> notFoundResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<ServiceResponseVO> unauhrizedResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.UNAUTHORIZED);
	}
	
	public ResponseEntity<ServiceResponseVO> serviceUnavailableResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	public ResponseEntity<ServiceResponseVO> methodNotAllowedResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	public ResponseEntity<ServiceResponseVO> requestTimeoutResponse(ServiceResponseVO oServiceResponseVO) {
		return new ResponseEntity<ServiceResponseVO>(oServiceResponseVO, HttpStatus.REQUEST_TIMEOUT);
	}

}
