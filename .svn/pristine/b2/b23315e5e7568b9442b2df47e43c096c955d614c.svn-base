
package com.talentPool.dynamicReports.data;

import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;


/**
 * @author praveen
 */
public class DynamicReportsDataSource implements JRDataSource {

    private List data = null;
    private int index = -1;

    /**
     * Creates a new instance of DynamicReportsDataSource
     */
    public DynamicReportsDataSource(Collection _data) {
        data = (List) _data;
    }
    
    public boolean next() throws JRException {
        index++;
        return (index < data.size());
    }

    public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		Object objDO = (Object) data.get(index);
		if (objDO instanceof com.talentPool.common.db.SimpleDataObject) {
			value = getSimpleDOAttributes(field, (SimpleDataObject) objDO);
		}
		return value;
	}

	private Object getSimpleDOAttributes(JRField field, SimpleDataObject simpleDO) {
		String fieldName	= field.getName();
		Object dataValObj 	= simpleDO.getAttribute(fieldName);
		String fieldClass 	= field.getValueClassName();
		Object value 		= null;
		if(dataValObj != null){
			if (dataValObj instanceof java.sql.Timestamp || dataValObj instanceof java.sql.Date) {
				value = DateUtils.getSystemDateFormat(simpleDO.getDate(fieldName));
			} else if (dataValObj instanceof java.awt.Image) {
				value = dataValObj;
			} else if (dataValObj instanceof sun.awt.image.ToolkitImage) {
				value = dataValObj;
			} else if (dataValObj instanceof net.sf.jasperreports.engine.JasperReport) {
				value = dataValObj;
			} else if (dataValObj instanceof net.sf.jasperreports.engine.JRDataSource) {
				value = dataValObj;
			} else {
				value = dataValObj;
			}
		}else {
			value = getDefaultValue(dataValObj,fieldClass);
		}
		return value;
	}
	
	private Object getDefaultValue(Object dataValObj,String fieldClass){
		Object value = null;
		if(Long.class.getName().equals(fieldClass)){
			value = 0L;
		}else if(Integer.class.getName().equals(fieldClass)){
			value = 0;
		}
		return value; 
	}
}
