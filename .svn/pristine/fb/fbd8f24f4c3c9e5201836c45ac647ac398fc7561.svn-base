/**
 * 
 */
package com.talentPool.common.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 *
 */
public class DBConstants {
    public static int ID = 0;
    public static int INT = 1;
    public static int STRING = 2;
    public static int DATE = 3;
    public static int DATETIME = 4;
    
    public static HashMap<String, String> columnKeyMap = new HashMap<String, String>();
    private static HashMap<String,String> customReportColumnKeyMap = new HashMap<String,String>();
    
    static {
    	columnKeyMap.put("current_ctc", "K6C04dAePkZRU7ykC8q10nf78e53n3EU");
    	columnKeyMap.put("expected_ctc", "pLiFp356a9Oqc6omx4jN388w12W78enL");
    	columnKeyMap.put("offered_basic", "P74xYI0lyQ5To6p6tchsqxHQazQ8rge5");
    	columnKeyMap.put("offered_ctc", "6NdeBQ5wosiI0hgA9Vw74t4Y7BgSoj6P");
    	columnKeyMap.put("date_of_birth", "YK4r3P1P3TAQMj44K286gttA15gq2sZv");
    	columnKeyMap.put("passport_number", "W2U8xIWP2yEDp8ZZ6mXl85DmdSz6SE0n");
    	columnKeyMap.put("current_basic", "17CzPoA9Kg0b440T4KMAsQ6F8T85s1H7");
    	columnKeyMap.put("applicant_text_resume", "Fb4uqVyeN4r4OB25RBp2hlbXNwapQL42");
    	columnKeyMap.put("attribute_value", "5bdgG7hX801I36R6zCxMalqCDcsW2M1w");
    	// More keys :
    	// C5Bt0xG89Qp0f9cTinNhPy1O3d1bu54a
    	// JA9iNhY5bCqhA3XavdJC8J30jjfKj6zN
    	// 7M2V0paMO5505CCG37HGr5427RiA2hsX
    	// 7D1Q54r0QV7kyr2s58END76Hec4YQE87
    	// tv7N2p99a9M8k9wLVoOi45vF4h9xb436
    	// apF4V8E6Z49z1vcJm79f949577AKyho2
    	// q2nxMz5796robOEW2d2dBjYk00YXdRwC
    	// ot43YX7qGHefr7K70pe7oZwWghITkP2s
    	// nK3v70L602k437CnR40rZ1NCOv2tCWrW
    	    	
    }
    
    /**
     * called for setting the map //lazy init
     */
    private static void setCustomReportColumnKeyMap(){
     	StringBuilder str = new  StringBuilder();
    	for(String s : columnKeyMap.keySet()){
    		String tmp = "'"+s+"'";
    		if(!Utils.isBlankOrNull(str.toString())){
    			tmp = "," +tmp;
    		}
    		str.append(tmp);
    	}
    	
    	String[] dynParams = new String[1];
    	dynParams[0] = str.toString();
    	ArrayList<SimpleDataObject> result = null;
    	DBPreparedQuery dq = new DBPreparedQuery("dGetCustomReportColumnKeyMap",dynParams);
    	try {
			result = dq.getResult();
		} catch (Exception e) {
    		TPLogger.getLogger().error("Error fetching custom report column key map",e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
    	for(SimpleDataObject sdo: result){
    		customReportColumnKeyMap.put(sdo.getId("column_property"),sdo.getId("column_dbname"));
    	}
    	
    }
    
    public static HashMap<String,String> getCustomReportColumnKeyMap(){
    	if(customReportColumnKeyMap.isEmpty()){
    		setCustomReportColumnKeyMap();
    	}
    	return customReportColumnKeyMap;
    }
}
