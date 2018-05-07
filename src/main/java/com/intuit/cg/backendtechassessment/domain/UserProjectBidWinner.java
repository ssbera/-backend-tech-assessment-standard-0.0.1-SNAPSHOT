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
public class UserProjectBidWinner implements Serializable {
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
    
    @Column(updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
   	@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = null;
    
    public UserProjectBidWinner() {}

	public UserProjectBidWinner(User user, Project project) {
		this.user = user;
		this.project = project;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	
}



