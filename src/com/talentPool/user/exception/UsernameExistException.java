/**
 * 
 */
package com.talentPool.user.exception;

/**
 * @author Shantanu
 *
 */
public class UsernameExistException extends Exception{
	public UsernameExistException(){
		
	}
	public UsernameExistException(String msg){
		super(msg);
	}

}
