package com.talentPool.reports.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class CSVReportUtil {

	public static String generateCSVReport(List<String> headers,
			List<String> fieldNames, List<?> reportData) {
		StringBuffer report = new StringBuffer("");		

		if (headers != null && headers.size() > 0) {
			for (String header : headers) {
				report.append("\"" + header + "\"");
				if (headers.indexOf(header) != headers.size() - 1) {
					report.append(",");
				} else {
					report.append("\n");
				}
			}
		}
		if (reportData != null && reportData.size() > 0) {
			Class<?> recordClass = reportData.get(0).getClass();
			for (Object recordObject : reportData) {

				for (String fieldName : fieldNames) {
					PropertyDescriptor propertyDescriptor = BeanUtils
							.getPropertyDescriptor(recordClass, fieldName);
					Method method = propertyDescriptor.getReadMethod();
					String columnValue = "\"\"";
					try {
						String beanColumnValue = (String) method.invoke(
								recordObject, new Object[] {});
						if (beanColumnValue != null && !beanColumnValue.trim().equals(""))
							columnValue = "\"" + beanColumnValue + "\"";
					} catch (Exception e) {
					}
					report.append(columnValue);
					if (fieldNames.indexOf(fieldName) != fieldNames.size() - 1) {
						report.append(",");
					} else {
						report.append("\n");
					}
				}
			}
		}
		return report.toString();
	}
}
