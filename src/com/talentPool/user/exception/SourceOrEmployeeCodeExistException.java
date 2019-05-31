/**
 * 
 */
package com.talentPool.user.exception;

/**
 * @author ajeet
 *
 */
public class SourceOrEmployeeCodeExistException extends Exception{

	public SourceOrEmployeeCodeExistException(){
		
	}
	
	public SourceOrEmployeeCodeExistException(String msg){
		super(msg);
	}
}
