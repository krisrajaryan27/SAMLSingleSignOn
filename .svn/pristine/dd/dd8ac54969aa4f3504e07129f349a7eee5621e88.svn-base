/**
 * 
 */
package com.talentPool.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import com.talentPool.common.utils.Utils;
import com.talentPool.user.UserConstants;

/**
 * @author Ajeet
 * 
 */
public class createDemoData {

	public static String[] positionIds = null;
	public static String[] recruiterIds = null;
	public static String[] managerIds = null;
	public static HashMap<Integer, String> managerMap = new HashMap<Integer, String>();;
	public static HashMap<Integer, String> recruiterMap = new HashMap<Integer, String>();;
	public static Random generator = new Random();

	public static File folder = new File("C:\\TalentSuite\\SVN\\Branches\\v_3_9_0\\documents\\resume");
	
	public static int manager = 100;
	public static int recruiter = 20;
	public static int position = 500;
	public static int start = 0 ;
	public static int count = 10;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();
		/*
		 * Run DB script before running this class
		 */
		try {
			
			/*
			 * Set applicant start count and end count
			 */
			
			//create users
			createUsers();
			
			//create Positions
			createPositions();

			
			
			/*
			 //initialize only if position is already created and applicant creation stopped in between
			 // comment user and position creation too 
			positionIds = new String[position];
			for (int p = 1; p <= position; p++) {
				positionIds[p-1] = ""+p;
			}
			*/
			
			long endtime = System.currentTimeMillis();
			System.out.println("Total time taken " + (endtime - starttime) + " ms for positions"+position);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void createUsers() {
		String[] args;
		// create Manager
		args = new String[2];
		managerIds = new String[manager];
		for (int m = 0; m < manager; m++) {
			args[0] = "manager" + m;
			args[1] = "" + UserConstants.ROLE_HR_MANAGER;
			managerIds[m] = createRandomUsers.createUsers(args);
		}
		int q = 0;
		int m = -1;
		for (int p = 0; p < position; p++) {
			if(q%(position/manager)==0 || m==-1){
				q=0;
				m++;
			}
			managerMap.put(p, managerIds[m]);
			q++;
		}
		
		
		// create Recruiter			
		recruiterIds = new String[recruiter];
		for (int r = 0; r < recruiter; r++) {
			args[0] = "recruiter" + r;
			args[1] = "" + UserConstants.ROLE_RECRUITER;
			recruiterIds[r] = createRandomUsers.createUsers(args);
		}
					
		q = 0;
		int r = -1;
		for (int p = 0; p < position; p++) {
			if(q%(position/recruiter)==0 || r==-1){
				q=0;
				r++;
			}
			recruiterMap.put(p, recruiterIds[r]);
			q++;
		}
	}
	
	public static void createPositions() {
		// create random position
		positionIds = new String[position];
		for (int p = 0; p < position; p++) {

			long starttime = System.currentTimeMillis();
			String assignTo = recruiterMap.get(p) + "," + managerMap.get(p);
			if (Utils.isBlankOrNull(assignTo)) {
				assignTo = "1";
			}
			System.out.println("assignToUsers=" + assignTo);
			String positionId = createRandomPositions.createPosition(
					assignTo, "Position" + p);
			positionIds[p] = positionId;
			start=p*count;
			int finish =start+count;
			//System.out.println("start="+start);
			//System.out.println("finish="+finish);
			createRandomApplicant applicants = new createRandomApplicant(positionId,start,finish);
			applicants.createApplicants();
			long endtime = System.currentTimeMillis();
			System.out.println("Time taken " + (endtime - starttime) + " ms for position"+p);
		}
	}

}
