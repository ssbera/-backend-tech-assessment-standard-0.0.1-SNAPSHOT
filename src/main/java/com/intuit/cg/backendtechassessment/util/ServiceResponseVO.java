
package com.intuit.cg.backendtechassessment.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Each request to the micro service returns ServiceResponseVO response.
 * author ssbera
 */


public class ServiceResponseVO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Application specific response code
//	private Integer reqRespCode = null;
	
	// Summary message
	private String summaryMsg = null;
	
	// Detail message
	private String detailMsg = null;
	
	private String requestUUID = null;
		
	// request result as response
	private Map<String, Object> responseMap = new HashMap<String, Object>();
	
	public ServiceResponseVO() {}
	
	public ServiceResponseVO(String requestUUID) {
		this.requestUUID = requestUUID;
	}
	
	public ServiceResponseVO(Integer appCode) {
	//	reqRespCode = appCode;
	}
	
	public ServiceResponseVO(Integer appCode, String requestUUID) {
	//	reqRespCode = appCode;
		this.requestUUID = requestUUID;
	}
	
	public void set(String skey, Object oVal) {
		responseMap.put(skey, oVal);
	}
	
	public void appendToList(String skey, Object oVal) {
		List<Object> oList = (List) responseMap.get(skey);
		if(oList == null) {
			oList = new ArrayList<Object>();
			responseMap.put(skey, oList);
		}
		oList.add(oVal);
	}
	
	
	
	public void appendToList(String skey, List<Object> oValList) {
		List<Object> oList = (List) responseMap.get(skey);
		if(oList == null) {
			oList = new ArrayList<Object>();
			responseMap.put(skey, oList);
		}
		oList.addAll(oValList);
	}
	
	
	public void appendToMap(String sMapName, String sKey, Object oVal) {
		Map<String, Object> oMap = (Map) responseMap.get(sMapName);
		if(oMap == null) {
			oMap = new HashMap<String, Object>();
			responseMap.put(sMapName, oMap);
		}
		oMap.put(sKey, oVal);
	}
	
	
	public void appendToMap(String sMapName, String skey, Map<String,Object> oValMap) {
		Map<String, Object> oMap = (Map) responseMap.get(sMapName);
		if(oMap == null) {
			oMap = new HashMap<String, Object>();
			responseMap.put(sMapName, oMap);
		}
		oMap.putAll(oValMap);
	}
	

//	public int getReqRespCode() {
//		return reqRespCode;
//	}
//
//	public void setReqRespCode(Integer reqRespCode) {
//		this.reqRespCode = reqRespCode;
//	}

	public String getSummaryMsg() {
		return summaryMsg;
	}

	public void setSummaryMsg(String summaryMsg) {
		this.summaryMsg = summaryMsg;
	}

	public String getDetailMsg() {
		return detailMsg;
	}

	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}

	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public String getrequestUUID() {
		return requestUUID;
	}

	public void setrequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}
	
	
}
