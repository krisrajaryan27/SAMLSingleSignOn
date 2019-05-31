package com.talentPool.customReports.dataobject;

import static com.talentPool.customReports.djhelper.constants.DJHelperConstants.ORDER_BY_FIELD;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import com.talentPool.common.Logger.TPLogger;
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
public class TPMapCollectionDataSource extends JRMapCollectionDataSource {
	
	private Iterator<Map<String,?>> iterator;
	private Map<String,?> currentRecord;

	/**
	 * @param rs
	 */
	public TPMapCollectionDataSource(Collection<Map<String,?>> col) {
		super(col);
		if (getData() != null)
		{
			iterator = getData().iterator();
		}
	}
	
	/**
	 *
	 */
	public boolean next()
	{
		boolean hasNext = false;
		
		if (iterator != null)
		{
			hasNext = iterator.hasNext();
			
			if (hasNext)
			{
				currentRecord = iterator.next();
			}
		}
		
		return hasNext;
	}

	/**
	 * Gets the field value for the current position.
	 * 
	 * @return an object containing the field value. The object type must be the field object type.
	 */
	public Object getFieldValue(JRField field)  {
		String fieldClassName 	= field.getValueClassName();
		@SuppressWarnings("rawtypes")
		Class fieldClass 		= field.getValueClass();
		Object dataValObj 		= null;
		try {
			if(DJComparatorWrapper.class.isAssignableFrom(fieldClass)){
				if(StepLevelComparator.class.getName().equals(fieldClassName)){
					dataValObj = new StepLevelComparator((String)currentRecord.get(field.getName()), (Integer)currentRecord.get(field.getName()+ORDER_BY_FIELD));
				}else if(StepComparator.class.getName().equals(fieldClassName)){
					dataValObj = new StepComparator((String)currentRecord.get(field.getName()), (Integer)currentRecord.get(field.getName()+ORDER_BY_FIELD));
				}else if(JasperMonthWrapper.class.getName().equals(fieldClassName)){
					dataValObj = new JasperMonthWrapper((String)currentRecord.get(field.getName()), Integer.parseInt((String)currentRecord.get(field.getName()+ORDER_BY_FIELD)));
				}else if(JasperDateWrapper.class.getName().equals(fieldClassName)){
					dataValObj = new JasperDateWrapper((Date)currentRecord.get(field.getName()));
				}else if(JasperWeekWrapper.class.getName().equals(fieldClassName)){
					dataValObj = new JasperWeekWrapper((String)currentRecord.get(field.getName()), Integer.parseInt((String)currentRecord.get(field.getName()+ORDER_BY_FIELD)));
				}
			} else {
				dataValObj =  currentRecord.get(field.getName());			
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while processing data for field: "+field.getName(), e);
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
	
}
