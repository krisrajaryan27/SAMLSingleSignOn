package com.talentPool.customReports.dataobject;

import static com.talentPool.customReports.djhelper.constants.DJHelperConstants.ORDER_BY_FIELD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRResultSetDataSource;

import com.talentPool.customReports.djhelper.wrappers.DJComparatorWrapper;
import com.talentPool.customReports.djhelper.wrappers.JasperDateWrapper;
import com.talentPool.customReports.djhelper.wrappers.JasperMonthWrapper;
import com.talentPool.customReports.djhelper.wrappers.JasperWeekWrapper;
import com.talentPool.customReports.djhelper.wrappers.StepComparator;
import com.talentPool.customReports.djhelper.wrappers.StepLevelComparator;

/**
 * @author PraveenK
 * @since  Nov 26, 2011
 */
public class TPResultSetDataSource extends JRResultSetDataSource {
	
	private ResultSet rs = null;

	/**
	 * @param rs
	 */
	public TPResultSetDataSource(ResultSet rs) {
		super(rs);
		this.rs=rs;
	}
	

	/**
	 * Gets the field value for the current position.
	 * 
	 * @return an object containing the field value. The object type must be the field object type.
	 */
	public Object getFieldValue(JRField field) throws JRException {
		String fieldClassName 	= field.getValueClassName();
		@SuppressWarnings("rawtypes")
		Class fieldClass 		= field.getValueClass();
		Object dataValObj 		= null;
		String name=field.getName();
		try {
			
			
					if(DJComparatorWrapper.class.isAssignableFrom(fieldClass)){
						if(StepLevelComparator.class.getName().equals(fieldClassName)){
							dataValObj = new StepLevelComparator(rs.getString(field.getName()), rs.getInt(field.getName()+ORDER_BY_FIELD));
						}else if(StepComparator.class.getName().equals(fieldClassName)){
							dataValObj = new StepComparator(rs.getString(field.getName()), rs.getInt(field.getName()+ORDER_BY_FIELD));
						}else if(JasperMonthWrapper.class.getName().equals(fieldClassName)){
							dataValObj = new JasperMonthWrapper(rs.getString(field.getName()), rs.getInt(field.getName()+ORDER_BY_FIELD));
						}else if(JasperDateWrapper.class.getName().equals(fieldClassName)){
							dataValObj = new JasperDateWrapper(rs.getDate(field.getName()));
						}else if(JasperWeekWrapper.class.getName().equals(fieldClassName)){
							dataValObj = new JasperWeekWrapper(rs.getString(field.getName()), rs.getInt(field.getName()+ORDER_BY_FIELD));
						}
					} else {
						if(name.equals("P26")){
							String loc=getLocations(super.getFieldValue(field));
							dataValObj=loc;
						}
						else{
						dataValObj =  super.getFieldValue(field);	
						}
					}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dataValObj==null){
			dataValObj = getDefaultValue(dataValObj, fieldClassName);
		}
		return dataValObj;
	}
	
	public Object getDefaultValue(Object dataValObj,String fieldClass){
		if(Long.class.getName().equals(fieldClass)){
			return 0L;
		}else {
			return dataValObj;			
		}
	}
	
	public void closeResultSet() throws SQLException {
		rs.close();
	}
	
	public String getLocations(Object obj) {
		String loc=null;
		if(obj!=null){
			String str=obj.toString();
			String[] locs=str.split(",");
			List<String> list=new ArrayList<String>();
			for(String s:locs){
				list.add(s);
			}
			Set<String> set=new HashSet<String>();
			set.addAll(list);
			list.clear();
			list.addAll(set);
			StringBuilder sbd=new StringBuilder();
			int listSize=0;
			for(int i=0;i<list.size();i++){
				sbd.append(list.get(i));
				if(i<list.size()-2){
				sbd.append(",");
				}
			}
			/*for(String s:list){
				sbd.append(s);
				sbd.append(",");
			}*/
			loc=sbd.toString();
			
		}
		return loc;
	}
	
}
