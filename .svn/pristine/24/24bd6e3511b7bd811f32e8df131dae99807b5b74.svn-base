/*
 * JRCollectionSource.java
 *
 * Created on December 5, 2005, 12:47 PM
 */

package com.talentPool.reports.dataobject;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Collection;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author abhijit
 */
public class JRCollectionSource implements JRDataSource {

    private List data = null;
    private int index = -1;

    /**
     * Creates a new instance of JRCollectionSource
     */
    public JRCollectionSource(Collection _data) {
        data = (List) _data;
    }

    public boolean next() throws JRException {
        index++;
        return (index < data.size());
    }

    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;
        String fieldName = field.getName();
        Object objDO = (Object) data.get(index);

        if (objDO instanceof com.talentPool.common.db.SimpleDataObject) {
            SimpleDataObject sDO = (SimpleDataObject)objDO;
        	value = sDO.getAttribute(fieldName);
        } 
        
        return value;
    }


}
