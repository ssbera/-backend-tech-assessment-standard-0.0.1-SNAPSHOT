package com.intuit.cg.backendtechassessment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false, length = 150)
    private String name;
    
    @Column(nullable = false)
    private long maxBudget;
    
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date lastBidDate;
     
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column
	@Temporal(TemporalType.TIMESTAMP)
    private Date bidingAccptedDate = null;
    
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = null;
    
    public Project() {}

    
    
	public Project(Long id, String name, long maxBudget, String description, Date lastBidDate) {
		this.id = id;
		this.name = name;
		this.maxBudget = maxBudget;
		this.description = description;
		this.lastBidDate = lastBidDate;
	}



	public Project(String name, long maxBudget, String description, Date lastBidDate) {
		this.name = name;
		this.maxBudget = maxBudget;
		this.description = description;
		this.lastBidDate = lastBidDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(long maxBudget) {
		this.maxBudget = maxBudget;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastBidDate() {
		return lastBidDate;
	}

	public void setLastBidDate(Date lastBidDate) {
		this.lastBidDate = lastBidDate;
	}

	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getBidingAccptedDate() {
		return bidingAccptedDate;
	}

	public void setBidingAccptedDate(Date bidingAccptedDate) {
		this.bidingAccptedDate = bidingAccptedDate;
	}



	public Date getCreatedAt() {
		return createdAt;
	}


	
}



