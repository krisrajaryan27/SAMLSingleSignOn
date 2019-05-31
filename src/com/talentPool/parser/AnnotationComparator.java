package com.talentPool.parser;
import java.util.Comparator;

import gate.Annotation;
import gate.FeatureMap;

public class AnnotationComparator implements Comparator<Annotation> {

	public int compare(Annotation ann1, Annotation ann2) {
		Integer priority1 = 0;
		Integer priority2 = 0;
		FeatureMap fm1 = ann1.getFeatures();
		if (fm1 != null && fm1.containsKey("priority")) {
			try {
				priority1 = Integer.parseInt(fm1.get("priority").toString());
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}
		}
		FeatureMap fm2 = ann2.getFeatures();
		if (fm2 != null && fm2.containsKey("priority")) {
			try {
				priority2 = Integer.parseInt(fm2.get("priority").toString());
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}
		}
		return priority2.compareTo(priority1);
	}
}
