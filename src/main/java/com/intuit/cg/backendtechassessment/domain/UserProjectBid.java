package com.intuit.cg.backendtechassessment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserProjectBid implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
	@JoinColumn(nullable = false, name="user_id")
	private User user;
    
    @ManyToOne
	@JoinColumn(nullable = false, name="project_id")
	private Project project;
    
    @Column(nullable = false)
    private long bidValue;
    
    @Column(nullable = false, length = 250)
    private String skillSet;
    
    @Column(length = 1000)
    private String comment;
    
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
   	@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = null;
    
    public UserProjectBid() {}

	public UserProjectBid(User user, Project project, long bidValue, String skillSet, String comment) {
		this.user = user;
		this.project = project;
		this.bidValue = bidValue;
		this.skillSet = skillSet;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public long getBidValue() {
		return bidValue;
	}

	public void setBidValue(long bidValue) {
		this.bidValue = bidValue;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}



