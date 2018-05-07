package com.intuit.cg.backendtechassessment.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"user_id", "project_id"})
	}) 
public class UserRating implements Serializable {
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
    
    public static enum Type {
		BUYER, SELLER
	}

	@Column(nullable = false, name = "status")
	@Enumerated(EnumType.STRING)
	public Type type;
	
	public static enum Rating {
		BAD, AVERAGE, GOOD, VERY_GOOD, EXCELENT
	}
	
	@Column(nullable = false, name = "rating")
	public Integer rating;

    
    public UserRating() {}

	public UserRating(User user, Project project, Type type, Integer rating) {
		this.user = user;
		this.project = project;
		this.type = type;
		this.rating = rating;
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


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public Integer getRating() {
		return rating;
	}


	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	

}



