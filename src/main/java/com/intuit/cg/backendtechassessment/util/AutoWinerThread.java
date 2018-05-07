package com.intuit.cg.backendtechassessment.util;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;

import com.intuit.cg.backendtechassessment.domain.Project;
import com.intuit.cg.backendtechassessment.domain.User;
import com.intuit.cg.backendtechassessment.domain.UserProjectBidWinner;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.repository.UserProjectBidRepository;
import com.intuit.cg.backendtechassessment.repository.UserProjectBidWinnerRepository;
import com.intuit.cg.backendtechassessment.repository.UserRepository;

/*
 * A thread runs after every 60 seconds and check the buyer with the lowest bid automatically wins the bid when the deadline is reached. A user can bid more than once. 
 * There can be more than 1 bid and there can be multiple bids.
 * If two bids are same and minimum for a project then it checks best user rating among those users.
 * If those users do not have user rating then it choose the winner based on whoever bided first
 * 
 * @see UserProjectBidRepository
 */
public class AutoWinerThread extends Thread {
	
	private UserProjectBidRepository poUserProjectBidRepository = null;
	private ProjectRepository poProjectRepository = null;
	private UserProjectBidWinnerRepository poUserProjectBidWinnerRepository = null;
	private UserRepository poUserRepository = null;
	
	public AutoWinerThread(ApplicationContext oApplicationContext) {
		poUserProjectBidRepository = oApplicationContext.getBean(UserProjectBidRepository.class);
		poProjectRepository = oApplicationContext.getBean(ProjectRepository.class);
		poUserProjectBidWinnerRepository = oApplicationContext.getBean(UserProjectBidWinnerRepository.class);
		poUserRepository = oApplicationContext.getBean(UserRepository.class);
	}
	
	@Override
	public void run() {
		ExecutorService es = Executors.newFixedThreadPool(5);
		while(true) {
			try {
				List<Object[]> oList = poUserProjectBidRepository.findWinner();
				long lastProjectID = 0L;
				for(Object[] oaObj : oList) {
					long projectID = ((Number)oaObj[0]).longValue();
					if(lastProjectID == projectID)
						continue;
					else {
						lastProjectID = projectID;
					}
					es.submit(new Runnable() {
						
						@Override
						public void run() {
							try {
								Optional<Project> oOptionalProject = poProjectRepository.findById(projectID);
								
								if(oOptionalProject.isPresent()) {
									Optional<User> oOptionalUser = poUserRepository.findById(((Number)oaObj[1]).longValue());
									User oUser = oOptionalUser.get();
									
									
									Project oPro = oOptionalProject.get();
									oPro.setActive(false);
									oPro.setBidingAccptedDate(new Date());
									
									poProjectRepository.save(oPro);
									
									UserProjectBidWinner oUserProjectBidWinner = new UserProjectBidWinner(oUser, oPro);
									poUserProjectBidWinnerRepository.save(oUserProjectBidWinner);
								}
							}
							catch(Exception exp) {
								exp.printStackTrace();
							}
							
						}
					});
				}
			}
			catch(Exception exp) {
				exp.printStackTrace();
			}
			
			try { Thread.sleep(60000); } catch(Exception exp) {}
		}
	}

}
