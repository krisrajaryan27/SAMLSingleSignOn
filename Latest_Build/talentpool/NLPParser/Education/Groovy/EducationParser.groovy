import gate.creole.SerialAnalyserController;

import gate.ProcessingResource;

import java.util.Collection;

import gate.creole.ontology.ShortDT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import antlr.NameSpace;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Executable;
import gate.FeatureMap;
import gate.creole.annic.apache.lucene.search.ScoreDoc;
import gate.creole.gazetteer.DefaultGazetteer;
import gate.creole.gazetteer.GazetteerList;
import gate.creole.gazetteer.LinearDefinition;
import gate.creole.gazetteer.LinearNode;
import gate.util.InvalidOffsetException;
import gate.util.OffsetComparator;
import gate.annotation.AnnotationSetImpl;

Hashtable<String, String> degreeMap;
Hashtable<String, String> otherGraduationMap;
Hashtable<String, String> instituteMap;
String[] instCommonWords;
boolean debug_tblh;// = false;
boolean debug_tblnh;// = false;
boolean debug_grmr;// = false;
boolean debug_global;// = false;
long startTime = System.nanoTime(); 
def yearComparator;
processDocument();
long endTime = System.nanoTime();
double elapsedTime = (double)((endTime - startTime)/1000000000)
println "Total Elapsed Time:"+elapsedTime;
public void processDocument() {
	yearComparator = [
	    compare:{ann1, ann2 ->
		    int year1 = 0;
			int year2 = 0;
			FeatureMap fm1 = ann1.getFeatures();
			if (fm1 != null && fm1.containsKey("year")) {
				try {
					year1 = Integer.parseInt(fm1.get("year").toString());
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			FeatureMap fm2 = ann2.getFeatures();
			if (fm2 != null && fm2.containsKey("year")) {
				try {
					year2 = Integer.parseInt(fm2.get("year").toString());
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			return year2.compareTo(year1);
			}] as Comparator<Annotation>;
	debug_tblh = false;
	debug_tblnh = false;
	debug_grmr = false;
	debug_global = true;
	
	build_inst_common_words();
	AnnotationSet annAll = doc.getAnnotations();
	build_degree_map();
	build_institute_map();
	remove_emptyspan_td_tr();
	//Extract Section containing education information
	get_edu_sections(doc);
	
	//Parse table with education information which contains header row like Degree/University
	tbl_parse_education(doc);
	tbl_check_edu_token(doc); //Remove the tokens which don'ot have degree
	map_degree_tokens(doc, "EduToken", false); //Map degree into Standarised 
	
	AnnotationSet nameAnns = annAll.get("EduFPToken");
	if(nameAnns == null || nameAnns.isEmpty()){
		tblnh_process_education(doc);
		tbl_check_edu_token(doc);		
		map_degree_tokens(doc, "EduToken", false);
	}
	nameAnns = annAll.get("MappedEduToken");
	if(nameAnns == null || nameAnns.isEmpty()){
		grmr_process_education_sections(doc);
		map_degree_tokens(doc, "EduToken", false);
		nameAnns = annAll.get("MappedEduToken");
		if(nameAnns == null || nameAnns.isEmpty()){
			map_degree_tokens(doc, "EduSGNRToken", true);
		}
	}
	remove_duplicate_degree();
}
public void build_inst_common_words(){
	instCommonWords = new String[28];
	instCommonWords[0] = " university ";
	instCommonWords[1] = " medical ";
	instCommonWords[2] = " sciences ";
	instCommonWords[3] =  " management ";
	instCommonWords[4] = " association ";
	instCommonWords[5] = " univ";
	instCommonWords[6] = " business ";
	instCommonWords[7] = " school ";
	instCommonWords[8] = " marketing ";
	instCommonWords[9] = " institute ";
	instCommonWords[10] = " technology ";
	instCommonWords[11] = " engineering ";
	instCommonWords[12] = " college ";
	instCommonWords[13] = " science ";
	instCommonWords[14] = " medical ";
	instCommonWords[15] = " degree ";
	instCommonWords[16] = " science ";
	instCommonWords[17] = " art " ;
	instCommonWords[18] = " engg";
	instCommonWords[19] = " technological ";
	instCommonWords[20] = " studies ";
	instCommonWords[21] = " agricultural ";
	instCommonWords[22] = " vishwavidyalaya ";
	instCommonWords[23] = " hospital ";
	instCommonWords[24] = " law ";
	instCommonWords[25] = " of ";
	instCommonWords[26] = " and ";
	instCommonWords[27] = " in " ;
}
public String inst_replace_common_words(String inst){
	inst = " " + inst.trim() + " ";
	inst = inst.replaceAll(","," ");
	inst = inst.replaceAll("-", " ");
	for(String str : instCommonWords){
		
		inst = inst.replaceAll(str, " ");
		//println inst;
	}
	return inst.trim();
}
/*
 * HTML parser of GATE introduces some td and tr tags
 * These extra tr, td tags contains a feature called isEmptyAndSpan.
 * These tags are not necessary and creates problems while extraction education info
 * from the table so we will remove these tags
 */
public void remove_emptyspan_td_tr(){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet annTdSet = annAll.get("td");
	for(Annotation ann : annTdSet){
		gate.FeatureMap fm = ann.getFeatures();
		if(fm.containsKey("isEmptyAndSpan")) {
			doc.annotations.remove(ann);
		}
	}
	AnnotationSet annTrSet = annAll.get("tr");
	for(Annotation ann : annTrSet){
		gate.FeatureMap fm = ann.getFeatures();
		if(fm.containsKey("isEmptyAndSpan")) {
			doc.annotations.remove(ann);
		}
	}
}

/*
 * 
 * 
 */
public void remove_duplicate_degree(){
	AnnotationSet annAll = doc.getAnnotations();
	Hashtable<String, Annotation> duplicateDetector = new Hashtable<String, Annotation>();
	AnnotationSet degreeAnns = annAll.get("MappedEduToken");
	/*
	//Remove duplicates with empty branches
	if(degreeAnns != null && degreeAnns.size() > 0){
		for(Annotation ann : degreeAnns){
			gate.FeatureMap fm = ann.getFeatures();
			String tempDegree = fm.get("degree").toLowerCase();
			if(duplicateDetector.containsKey(tempDegree)){
				Annotation prevAnn = duplicateDetector.get(tempDegree);
				gate.FeatureMap prevFM = prevAnn.getFeatures();
				if(fm.containsKey("branch") && !prevFM.containsKey("branch")){
					doc.annotations.remove(prevAnn);
					doc.annotations.add(prevAnn.getStartNode(), prevAnn.getEndNode(), "EduFPToken",prevFM);	
					duplicateDetector.put(tempDegree, ann);
				}
				else if(!fm.containsKey("branch") && prevFM.containsKey("branch")){
					doc.annotations.remove(ann);
					doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "EduFPToken",fm);
				}
			}else{
				duplicateDetector.put(tempDegree, ann);
			}
		}
	}
	*/
	duplicateDetector = new Hashtable<String, Annotation>();
	degreeAnns = annAll.get("MappedEduToken");
	if(degreeAnns != null && degreeAnns.size() > 0){
		for(Annotation ann : degreeAnns){
			gate.FeatureMap fm = ann.getFeatures();
			String tempDegree = fm.get("degree").toString().toLowerCase();
			/*
			if(fm.containsKey("branch")){
				tempDegree = tempDegree + "-"+fm.get("branch").toString().toLowerCase();
			}
			*/
			if(duplicateDetector.containsKey(tempDegree)){
				Annotation prevAnn = duplicateDetector.get(tempDegree);
				gate.FeatureMap prevFM = prevAnn.getFeatures();
				if(fm.size() > prevFM.size()){
					duplicateDetector.put(tempDegree, ann);
					doc.annotations.remove(prevAnn);
					doc.annotations.add(prevAnn.getStartNode(), prevAnn.getEndNode(), "EduFPToken",prevFM);	
				}
				else{
					doc.annotations.remove(ann);
					doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "EduFPToken",fm);
				}
			}else{
				duplicateDetector.put(tempDegree, ann);
			}
		}
	}
}
public void build_degree_map(){
	Executable application = gate.getExecutable();
	
	Collection<ProcessingResource> cl = application.getPRs();
	degreeMap = new Hashtable<String, String>();
	otherGraduationMap = new Hashtable<String, String>();
	for(ProcessingResource pr : cl){
		if(pr.getName().equalsIgnoreCase("Degree Branch Gazetteer")){
			DefaultGazetteer gz = (DefaultGazetteer)pr;
			LinearDefinition ld = gz.getLinearDefinition();
			Map m = ld.getListsByNode();
			int mapsize = m.size();		
			Iterator keyValuePairs1 = m.entrySet().iterator();
			for (int i = 0; i < mapsize; i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs1.next();
				LinearNode key = (LinearNode)entry.getKey();
				GazetteerList value = (GazetteerList)entry.getValue();
				if(key.getMajorType().equalsIgnoreCase("degree") && !key.getMinorType().equals("Other Graduation") && !key.getMinorType().equals("Other Post Graduation")){
					for(int j = 0; j< value.size(); j++){
						String degree = value.get(j).toString();
						if(!degreeMap.containsKey(degree)){
							degreeMap.put(degree, key.getMinorType());
						}
					}
				}
				if(key.getMajorType().equalsIgnoreCase("degree") && (key.getMinorType().equals("Other Graduation") || key.getMinorType().equals("Other Post Graduation"))){
					for(int j = 0; j< value.size(); j++){
						String degree = value.get(j).toString().toLowerCase();
						if(!otherGraduationMap.containsKey(degree)){
							if(key.getMinorType().equals("Other Graduation")) {
								otherGraduationMap.put(degree, "Bachelor");
							}
							else if(key.getMinorType().equals("Other Post Graduation")){
								otherGraduationMap.put(degree, "Masters");
							}
						}
					}
				}
			}
		}
	}
}
public void build_institute_map(){
	Executable application = gate.getExecutable();
	
	Collection<ProcessingResource> cl = application.getPRs();
	instituteMap = new Hashtable<String, String>();
	for(ProcessingResource pr : cl){
		if(pr.getName().equalsIgnoreCase("Institute Gazetteer")){
			DefaultGazetteer gz = (DefaultGazetteer)pr;
			
			LinearDefinition ld = gz.getLinearDefinition();
			Map m = ld.getListsByNode();
			int mapsize = m.size();		
			Iterator keyValuePairs1 = m.entrySet().iterator();
			for (int i = 0; i < mapsize; i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs1.next();
				LinearNode key = (LinearNode)entry.getKey();
				GazetteerList value = (GazetteerList)entry.getValue();
				if(key.getMajorType().equalsIgnoreCase("institute")){
					for(int j = 0; j< value.size(); j++){
						String degree = value.get(j).toString();
						if(!instituteMap.containsKey(degree)){
							instituteMap.put(degree, key.getMinorType());
						}
					}
				}
			}
		}
	}
}
public String get_institute_using_institute_gazetteer(Annotation annEdu){
	//writelog("----------get_institute_using_institute_gazetteer-------------- Start", debug_global);
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet containedAnns = annAll.getContained(annEdu.getStartNode().getOffset(), annEdu.getEndNode().getOffset());
	//writelog("containedAnns Size:"+containedAnns.size(), debug_global);
	gate.FeatureMap fm = gate.Factory.newFeatureMap();
	fm.put("majorType", "institute");
	AnnotationSet lkpInstAnns = containedAnns.get("Lookup",fm);
	//writelog("lkpInstAnns Size:"+lkpInstAnns.size(), debug_global);
	if(lkpInstAnns != null && !lkpInstAnns.isEmpty()){
		Annotation prevAnn = null;
		for(Annotation ann : lkpInstAnns){
			if(prevAnn == null){
				prevAnn = ann;
			}else{
				if((ann.getEndNode().getOffset()-ann.getStartNode().getOffset()) > (prevAnn.getEndNode().getOffset()-prevAnn.getStartNode().getOffset())){
					prevAnn = ann;
				}
			}
		}
		if(prevAnn != null){
			return prevAnn.getFeatures().get("minorType");
		}
	}
	return null;
}
public String get_short_institute(String univ){
	if(univ != null){
		univ = univ.toLowerCase().trim();
		String shortUniv = "";
		for (int j = 0; j < univ.length(); j++) {
			Character c = univ.charAt(j);
			if (Character.isLetter(c)) {
				shortUniv = shortUniv + c.toString();
			}
		}
		return shortUniv;
	}
}
public String get_short_institute_without_common_words(String univ){
	if(univ != null){
		univ = inst_replace_common_words(univ.toLowerCase()).trim();
		//String[] parts = univ.split("[\\s]");
		//println univ;
		String shortUniv = "";
		for (int j = 0; j < univ.length(); j++) {
			Character c = univ.charAt(j);
			if (Character.isLetter(c)) {
				shortUniv = shortUniv + c.toString();
			}
		}
		if(shortUniv.length() > 0){
			return shortUniv;
		}
	}
	return null;
}
public String get_acronym(String univ){
	if(univ != null){
		univ = univ.trim();// inst_replace_common_words(univ.toLowerCase()).trim();
		String acronym = null;
		int index = -1;
		if(univ.indexOf(' ') > 0){
			index = univ.indexOf(' ');
			//acronym = univ.substring(0, univ.indexOf(' '));
		}
		if(univ.indexOf('-') > 0 && univ.indexOf('-') < index){
			index = univ.indexOf('-');	
		}
		if(univ.indexOf(',') > 0 && univ.indexOf(',') < index){
			index = univ.indexOf(',');	
		}
		if(index > 0){
			acronym = univ.substring(0, index);			
		}
		else{
			acronym = univ;
		}
		if(acronym.toUpperCase().equals(acronym)){
			String shortUniv = "";
			for (int j = 0; j < acronym.length(); j++) {
				Character c = acronym.charAt(j);
				if (Character.isLetter(c)) {
					shortUniv = shortUniv + c.toString();
				}
			}
			if(shortUniv.length() > 0){
				return shortUniv.toLowerCase();
			}
		}
	}
	return null;
}
public String institute_lcs(Annotation ann){
	String inst = get_institute_using_institute_gazetteer(ann);
	if(inst != null && !inst.toLowerCase().contains("other")){
		writelog("got inst from lcs:"+ inst, debug_global);
		return inst;
	}
	gate.FeatureMap fm = ann.getFeatures();
	String univ = fm.get("university");
	String college = fm.get("college");
	if(univ != null){
		if(fm.get("rule").toString().equals("table_with_header") && !univ.toLowerCase().contains("univ") && univ.length() < 10){
			univ = univ + " University";
		}
	}
	//doc.getContent().getContent(ann.getStartNode().getOffset(), ann.getEndNode().getOffset())
	String annContent = doc.getContent().getContent(ann.getStartNode().getOffset(), ann.getEndNode().getOffset());
	//println annContent;
	String[] tokens = new String[2];
	tokens[0] = univ;
	tokens[1] = college;
	//tokens[2] = annContent;
	for(int pass = 0; pass < tokens.length; pass++){
		double highScore = 0;
		String highInst = "";
		double highScore1 = 0;
		String shortUniv = get_short_institute(tokens[pass]);
		if(shortUniv != null && shortUniv.length() > 0){
			//writelog("------------- institute_lcs: "+tokens[pass], debug_global);
			//writelog("insmapcount:"+instituteMap.size(), debug_global);
			for(Map.Entry me : instituteMap){ 
				String lkpUniv = me.getKey();
				boolean debug = false;
				
				 if(me.getValue().equals("PGDM")){
				 debug = true;
				 }
				 
				String shortLkpUniv = get_short_institute(lkpUniv);
				String shortLkpUnivAcronym = get_short_institute_without_common_words(lkpUniv);
				String shortUnivAcronym = get_short_institute_without_common_words(tokens[pass]);
				String acronym = get_acronym(lkpUniv);
				writelog("acronym:"+acronym+"; lkpUniv:"+lkpUniv, debug);
				writelog("shortLkpUnivAcronym:"+shortLkpUnivAcronym+"; shortUnivAcronym:"+shortUnivAcronym, debug);
				if(shortLkpUniv != null && shortLkpUniv.length() > 0 && shortUniv != null && shortUniv.length() > 0){
					
					if(acronym != null &&  shortUniv.indexOf(acronym) == -1){
						writelog("Acronym continue",debug);
						continue;
					}
					double acnLcs = 0;
					if(shortLkpUnivAcronym.length() <= 5) {
						if(shortUniv.indexOf(shortLkpUnivAcronym) == -1){
							continue
						}
					}
					/*
					 else{
					 String acronymLcs = longest_sub_sequence(shortUnivAcronym, shortLkpUnivAcronym);
					 acnLcs = (2*acronymLcs.length()) / (shortUnivAcronym.length()+shortLkpUnivAcronym.length());
					 if(acnLcs < 0.90){
					 writelog("acnLcs < 0.85 "+shortUnivAcronym+":"+shortLkpUnivAcronym+":"+acnLcs, debug);
					 continue;
					 }
					 }
					 */
					if(shortUniv.equals(shortLkpUniv)){
						writelog("shortUniv.equals(shortLkpUniv):"+shortUniv+":"+shortLkpUniv, debug);
						return instituteMap.get(lkpUniv);
					}
					String lcs = longest_sub_sequence(shortUniv.toLowerCase(), shortLkpUniv.toLowerCase());
					double score = (2*lcs.length()) / (shortLkpUniv.length()+shortUniv.length());
					//double shortUnivScore = lcs.length() / shortUniv.length();
					writelog(shortUniv+":"+shortLkpUniv+":"+score, debug);
					if(score > 0.85 && score >= highScore && lkpUniv.length() > highInst.length()) {
						
						highInst = lkpUniv;
						highScore = score;
						/*
						 String lss = longest_sub_string(shortUniv.toLowerCase(), shortLkpUniv.toLowerCase());
						 String tempShortUniv = shortUniv.replaceAll(lss, "");
						 String tempShortLkpUniv = shortLkpUniv.replaceAll(lss, "");
						 String lss1 = longest_sub_string(tempShortUniv.toLowerCase(), tempShortLkpUniv.toLowerCase());						
						 writelog(lcs.length() + ":"+ lss.length(), debug_global);
						 double score1  = (2*(lss.length()+lss1.length())) / (shortUniv.length()+shortLkpUniv.length());
						 writelog("------------- 2score:"+score+"; score1:"+score1+"; shortUniv:"+shortUniv+"; shortLkpUniv:"+shortLkpUniv+"; lcs:"+lcs+"; lss:"+lss+"; lss1:"+lss1, debug_global);
						 if(score1 > 0.85 && score1 >= highScore1) {
						 highInst = lkpUniv;
						 highScore = score;
						 highScore1 = score1;
						 }
						 */
					}
				}
			}
			if(highScore > 0.85){
				writelog("******Returning "+highInst, debug_global);
				return instituteMap.get(highInst);
			}
			
		}
	}
	return null;
}
public void map_degree_tokens(Document doc, String tokenToLook, boolean onlyFirst){
	writelog("-------------map_degree_tokens "+tokenToLook+"--------------------------", debug_global);
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet annEduSet = annAll.get(tokenToLook);
	List annEduList = new ArrayList(annEduSet);
	Collections.sort(annEduList, new OffsetComparator());
	
	for(Annotation ann : annEduList){
		writelog("----------------------------------------------------", debug_global);
		writelog(get_annotation_content(ann), debug_global);
		AnnotationSet tempSet = annAll.getContained(ann.getStartNode().getOffset(), ann.getEndNode().getOffset());
		gate.FeatureMap fmDegree = gate.Factory.newFeatureMap();
		fmDegree.put("majorType", "degree");
		
		gate.FeatureMap fmBranch = gate.Factory.newFeatureMap();
		fmBranch.put("majorType", "branch");
		gate.FeatureMap fmNew = gate.Factory.newFeatureMap();
		AnnotationSet degreeSet = tempSet.get("Lookup", fmDegree);
		Annotation degreeAnnLookup = null;
		String rule = ann.getFeatures().get("rule");
		if(degreeSet != null && !degreeSet.isEmpty()){
			List degreeList = new ArrayList(degreeSet);
			Collections.sort(degreeList, new OffsetComparator());
			degreeAnnLookup = get_first_longest_annotation(degreeList);
			writelog("degree "+degreeAnnLookup.getFeatures().get("minorType"), debug_global);
			fmNew.put("degree", degreeAnnLookup.getFeatures().get("minorType"));
		}
		if(!fmNew.containsKey("degree") || fmNew.get("degree").equals("Other Graduation") 
		|| fmNew.get("degree").equals("Other Post Graduation")){
			
			String lcsDegree = get_degree_from_lcs(ann.getFeatures().get("degree"), ann.getFeatures().get("branch"));
			if(lcsDegree != null){
				rule = rule + "_"+"lcsdegree";
				fmNew.put("degree",lcsDegree)
			}
		}
		boolean skipThis = false;
		if(fmNew.containsKey("degree")){
			AnnotationSet branchSet = tempSet.get("Lookup", fmBranch);
			if(branchSet != null && !branchSet.isEmpty()){
				List branchList = new ArrayList(branchSet);
				Collections.sort(branchList, new OffsetComparator());
				Annotation branchAnn = get_first_longest_annotation(branchList);
				fmNew.put("branch", doc.getContent().getContent(branchAnn.getStartNode().getOffset(), branchAnn.getEndNode().getOffset()));	
			}
			else if(fmNew.get("degree").equals("Other Graduation") || fmNew.get("degree").equals("Other Post Graduation")){
				skipThis = true;
			}
			/*
			 if(ann.getFeatures().containsKey("date")){
			 fmNew.put("date", ann.getFeatures().get("date"));
			 }
			 */
			String year = get_year(ann);
			if(year != null){
				fmNew.put("year", year);
			}
			/*
			 if(ann.getFeatures().containsKey("date1")){
			 fmNew.put("date1", ann.getFeatures().get("date1"));
			 }
			 if(ann.getFeatures().containsKey("college")){
			 fmNew.put("college", ann.getFeatures().get("college"));
			 }
			 if(ann.getFeatures().containsKey("university")){
			 fmNew.put("university", ann.getFeatures().get("university"));
			 }
			 */
			/*
			 String inst = get_institute_from_bwp(ann);
			 if(inst != null){
			 fmNew.put("institute", inst);				
			 }
			 */
			long startTime = System.nanoTime(); 
			String inst = institute_lcs(ann);
			long endTime = System.nanoTime();
			double elapsedTime = (double)((endTime - startTime)/1000000000)
			println "LCS Elapsed Time:"+elapsedTime;
			
			if(inst != null){
				fmNew.put("institute", inst);				
			}
			/*
			 if(ann.getFeatures().containsKey("percent")){
			 fmNew.put("percent", ann.getFeatures().get("percent"));
			 }
			 */
			if(ann.getFeatures().containsKey("rule")){
				fmNew.put("rule", rule);
			}
			/*
			 if(ann.getFeatures().containsKey("sgnr")){
			 fmNew.put("sgnr", ann.getFeatures().get("sgnr"));
			 }
			 */
			doc.annotations.remove(ann);
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "EduFPToken",fmNew);
			if(fmNew.size() > 2 && !skipThis){
				doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "MappedEduToken",fmNew);
				if(onlyFirst){
					break;
				}
			}
		}
		else{
			doc.annotations.remove(ann);
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "EduFPToken",ann.getFeatures());			
		}
	}
}
public String get_year(Annotation ann){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet tempSet = annAll.getContained(ann.getStartNode().getOffset(), ann.getEndNode().getOffset());
	gate.FeatureMap fmInst = gate.Factory.newFeatureMap();
	fmInst.put("kind", "year");
	AnnotationSet annInsts = tempSet.get("TempEduToken", fmInst);
	if(annInsts != null && !annInsts.isEmpty()){
		List annEduList = new ArrayList(annInsts);
		Collections.sort(annEduList, yearComparator);
		return annEduList.get(0).getFeatures().get("year");
	}
	/*
	 Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
	 if(annInsts != null && !annInsts.isEmpty()){
	 for(Annotation iAnn : annInsts){
	 String minorType = iAnn.getFeatures().get("minorType");
	 if(minorType != null){
	 if(ht.containsKey(minorType)){
	 ht.put(minorType, ht.get(minorType)+1);
	 }else{
	 ht.put(minorType, 1);
	 }
	 }
	 }
	 int currentCount = 0;
	 String currentString = "";
	 int mapsize = ht.size();		
	 Iterator keyValuePairs1 = ht.entrySet().iterator();
	 for (int i = 0; i < mapsize; i++) {
	 Map.Entry entry = (Map.Entry) keyValuePairs1.next();
	 String key = (String)entry.getKey();
	 int value = (Integer)entry.getValue();
	 if(value > currentCount){
	 currentCount = value;
	 currentString = key;
	 }
	 }
	 if(currentCount > 1){
	 return currentString;
	 }
	 }
	 */
	return null;
}
public String get_degree_from_lcs(String degree, String branch){
	if(branch == null)branch = "";
	if(degree == null)degree = "";
	if(otherGraduationMap.containsKey(degree.toLowerCase())){
		degree = otherGraduationMap.get(degree.toLowerCase());
	}
	for(Map.Entry me : degreeMap){ 
		String degreeKey = me.getKey();
		degreeKey = degreeKey.replaceAll(" [Oo][Ff] "," ");
		degreeKey = degreeKey.replaceAll(" [Ii][Nn] "," ");
		//degreeKey = degreeKey.toLowerCase();
		String shortDegreeKey = "";
		for (int j = 0; j < degreeKey.length(); j++) {
			Character c = degreeKey.charAt(j);
			if (Character.isLetter(c)) {
				shortDegreeKey = shortDegreeKey + c.toString();
			}
		}
		String tempDegree = degree;
		if(shortDegreeKey.length() >= 10 && !degree.contains(".") && !degree.equals(degree.toUpperCase())){
			tempDegree = degree + branch;
		}
		tempDegree = tempDegree.replaceAll(" [Oo][Ff] "," ");
		tempDegree = tempDegree.replaceAll(" [Ii][Nn] "," ");
		//tempDegree = tempDegree.toLowerCase();
		String shortDegree = "";
		for (int j = 0; j < tempDegree.length(); j++) {
			Character c = tempDegree.charAt(j);
			if (Character.isLetter(c)) {
				shortDegree = shortDegree + c.toString();
			}
		}
		String lcs = longest_sub_sequence(shortDegree.toLowerCase(), shortDegreeKey.toLowerCase());
		if(lcs != null && shortDegree.length() > 0 && shortDegreeKey.length() > 0){
			double score = 0;
			if(shortDegreeKey.length() < 10 || shortDegree.length() < 10){
				score = 2*lcs.length() / (shortDegree.length() + shortDegreeKey.length());
			}
			else{
				int shortLength = (shortDegree.length() < shortDegreeKey.length())?shortDegree.length():shortDegreeKey.length();
				score = lcs.length() / shortLength;
			}
			if(score > 0.95){
				writelog("------------- got match:", debug_global);
				return me.getValue();
			}
		}
	}
	return null;
}
//If PG Diploma and Diploma are mentioned get PG Diploma
public Annotation get_first_longest_annotation(List annotationList){
	Annotation prevAnn = null;
	for(Annotation ann : annotationList){
		if(prevAnn == null){
			prevAnn = ann;
		}else{
			if(ann.getStartNode().getOffset() > prevAnn.getStartNode().getOffset()){
				break;
			}
			else{
				if((ann.getEndNode().getOffset()-ann.getStartNode().getOffset()) > (prevAnn.getEndNode().getOffset()-prevAnn.getStartNode().getOffset())){
					prevAnn = ann;
				}
			}
		}
	}
	return prevAnn;
}
public void tbl_check_edu_token(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet annEduSet = annAll.get("EduToken");
	for(Annotation ann : annEduSet){
		gate.FeatureMap fm = ann.getFeatures();
		if(!fm.containsKey("degree")){
			if(fm.containsKey("branch")){
				fm.put("degree", fm.get("branch"));
				fm.remove("branch");
			}
			else{
				doc.annotations.remove(ann);
			}
		}
		if(fm.size() < 3){
			doc.annotations.remove(ann);			
		}
	}
	
	
}
public void tblnh_process_education(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	List annAllList = new ArrayList(annAll);
	Collections.sort(annAllList, new OffsetComparator());
	Annotation ann = annAllList.get(annAllList.size()-1);	
	Long docEndOffSet = ann.getEndNode().getOffset();
	AnnotationSet secSet = annAll.get("EduSection");
	if(secSet != null && !secSet.isEmpty()){
		List secList = new ArrayList(secSet);
		Collections.sort(secList, new OffsetComparator());
		for(Annotation secAnn : secList){
			AnnotationSet tempAnnSet = annAll.getContained(secAnn.getStartNode().getOffset(), secAnn.getEndNode().getOffset());
			if(tempAnnSet == null || tempAnnSet.isEmpty()){
				return;
			}
			AnnotationSet tblSet = tempAnnSet.get("table")
			if(tblSet != null && !tblSet.isEmpty()){
				List tblList = new ArrayList(tblSet);
				Collections.sort(tblList, new OffsetComparator());
				for(Annotation tblAnn : tblList){
					Hashtable colMappings = tblnh_get_column_mappings(doc, tblAnn.getStartNode().getOffset(), tblAnn.getEndNode().getOffset());
					tblnh_parse_tbl(doc, colMappings, tblAnn.getStartNode().getOffset(), tblAnn.getEndNode().getOffset());
					//Get col mappings
					//if col mappings > 1 and col mappings got degree
					//make edu tokens
				}
			}
			else{
				Hashtable colMappings = tblnh_get_column_mappings(doc, secAnn.getStartNode().getOffset(), secAnn.getEndNode().getOffset());				
				tblnh_parse_tbl(doc, colMappings, secAnn.getStartNode().getOffset(), secAnn.getEndNode().getOffset());
			}
		}
	}
}
public Hashtable tblnh_get_column_mappings(Document doc, long start, long end){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet tempAnnSet = annAll.getContained(start, end);
	if(tempAnnSet == null || tempAnnSet.isEmpty()){
		return;
	}
	writelog("tblnh_get_column_mappings1", debug_tblnh);
	AnnotationSet trSet = tempAnnSet.get("tr");
	if(trSet == null || trSet.isEmpty()){
		return;
	}
	writelog("trSet size:"+trSet.size(), debug_tblnh);
	List trList = new ArrayList(trSet);
	Collections.sort(trList, new OffsetComparator());
	Hashtable collAnnSets = new Hashtable();
	int totalSize = 0;
	for (Annotation trAnn : trList) {
		AnnotationSet tempTdAnnSet = annAll.getContained(trAnn.getStartNode().getOffset(), trAnn.getEndNode().getOffset());
		if(tempTdAnnSet == null || tempTdAnnSet.isEmpty()){
			continue;
		}
		AnnotationSet tdSet = tempTdAnnSet.get("td");
		if(tdSet == null || tdSet.isEmpty()){
			continue;
		}
		writelog("tdSet size:"+tdSet.size(), debug_tblnh);
		List tdList = new ArrayList(tdSet);
		Collections.sort(tdList, new OffsetComparator());
		int i = 0;		
		for(Annotation tdAnn : tdList){
			AnnotationSet tempSet = annAll.getContained(tdAnn.getStartNode().getOffset(), tdAnn.getEndNode().getOffset());
			if(tempSet != null && !tempSet.isEmpty()){
				if(collAnnSets.containsKey(i)){
					AnnotationSetImpl prevAnnSet = (AnnotationSetImpl)collAnnSets.get(i);
					prevAnnSet.addAll(tempSet);
				}else{
					AnnotationSetImpl asi = new AnnotationSetImpl(tempSet);
					collAnnSets.put(i, asi);
				}
			}
			i++;
		}
		totalSize = i;
	}
	Hashtable colMappings = new Hashtable();
	if(totalSize > 2){
		gate.FeatureMap fmDegree = gate.Factory.newFeatureMap();
		fmDegree.put("kind","degree");
		gate.FeatureMap fmUniversity = gate.Factory.newFeatureMap();
		fmUniversity.put("kind","university");
		gate.FeatureMap fmCollege = gate.Factory.newFeatureMap();
		fmCollege.put("kind","college");
		gate.FeatureMap fmYear = gate.Factory.newFeatureMap();
		fmYear.put("majorType","year");
		
		for(int i = 0; i< totalSize; i++){
			AnnotationSetImpl annSet = (AnnotationSetImpl)collAnnSets.get(i);
			AnnotationSet degreeAnnSet = annSet.get("TempEduToken",fmDegree);
			if(degreeAnnSet != null && degreeAnnSet.size() > 0){
				colMappings.put(i, "degree");
				continue;
			}
			AnnotationSet collegeAnnSet = annSet.get("TempEduToken",fmCollege);
			if(collegeAnnSet != null && collegeAnnSet.size() > 0){
				colMappings.put(i, "college");
				continue;
			}
			AnnotationSet univAnnSet = annSet.get("TempEduToken",fmUniversity);
			if(univAnnSet != null && univAnnSet.size() > 0){
				colMappings.put(i, "university");
				continue;
			}
			AnnotationSet percentAnnSet = annSet.get("Percent");
			if(percentAnnSet != null && percentAnnSet.size() > 0){
				colMappings.put(i, "percent");
				continue;
			}
			AnnotationSet dateAnnSet = annSet.get("Lookup", fmYear);
			if(dateAnnSet != null && dateAnnSet.size() > 0){
				colMappings.put(i, "date");
				continue;
			}
			AnnotationSet acnAnnSet = annSet.get("AcronymToken");
			if(acnAnnSet != null && acnAnnSet.size() > 0){
				colMappings.put(i, "degree");
				continue;
			}
		}
	}
	int mapsize = colMappings.size();		
	Iterator keyValuePairs1 = colMappings.entrySet().iterator();
	for (int i = 0; i < mapsize; i++) {
		Map.Entry entry = (Map.Entry) keyValuePairs1.next();
		Object key = entry.getKey();
		Object value = entry.getValue();
		writelog(key.toString()+":"+value.toString(), debug_tblnh);
	}
	return colMappings;
	
}
public void tblnh_parse_tbl(Document doc, Hashtable colMappings, long start, long end){
	writelog("tblnh_parse_tbl", debug_tblnh);
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet tempAnnSet = annAll.getContained(start, end);
	if(tempAnnSet == null || tempAnnSet.isEmpty()){
		return;
	}
	writelog("tblnh_parse_tbl 1", debug_tblnh);
	AnnotationSet trSet = tempAnnSet.get("tr");
	if(trSet == null || trSet.isEmpty()){
		return;
	}
	writelog("trSet size:"+trSet.size(), debug_tblnh);
	List trList = new ArrayList(trSet);
	Collections.sort(trList, new OffsetComparator());
	Hashtable collAnnSets = new Hashtable();
	int totalSize = 0;
	for (Annotation trAnn : trList) {
		AnnotationSet tempTdAnnSet = annAll.getContained(trAnn.getStartNode().getOffset(), trAnn.getEndNode().getOffset());
		if(tempTdAnnSet == null || tempTdAnnSet.isEmpty()){
			continue;
		}
		AnnotationSet tdSet = tempTdAnnSet.get("td");
		if(tdSet == null || tdSet.isEmpty()){
			continue;
		}
		writelog("tdSet size:"+tdSet.size(), debug_tblnh);
		List tdList = new ArrayList(tdSet);
		Collections.sort(tdList, new OffsetComparator());
		int i = 0;
		gate.FeatureMap eduFeature = gate.Factory.newFeatureMap();
		for(Annotation tdAnn : tdList){
			Object col = colMappings.get(i);
			if(col != null){
				long tdStart = tdAnn.getStartNode().getOffset();
				long tdEnd = tdAnn.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(tdStart, tdEnd);
				Object prevVal = eduFeature.get(col);
				if(prevVal == null){
					eduFeature.put(col, annotation);
				}
				else{
					eduFeature.put(col, annotation);
				}
			}
			i++;
		}
		eduFeature.put("rule", "table_without_header");
		if(eduFeature.containsKey("degree") && eduFeature.size() > 1){
			doc.annotations.add(trAnn.getStartNode(), trAnn.getEndNode(), "EduToken",eduFeature);
		}		
	}
}
public void tbl_parse_education(Document doc){
	writelog("tbl_parse_education", debug_tblh);
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("majorType","edu_prefix");
	
	ArrayList<Annotation> altrAnns = tbl_get_education_labels_rows(doc);
	ArrayList<Annotation> processedTables = new ArrayList<Annotation>();
	for(Annotation trEduLblAnn : altrAnns){
		
		if(trEduLblAnn == null){
			return;
		}
		Annotation tblEduLblAnn = tbl_get_containing_table(doc, trEduLblAnn);
		if(!tbl_already_processed(processedTables, tblEduLblAnn)){
			processedTables.add(tblEduLblAnn);
			
			if(tblEduLblAnn == null){
				return null;
			}
			Hashtable colMappings = tbl_get_col_mappings(doc, trEduLblAnn);
			int mapsize = colMappings.size();		
			Iterator keyValuePairs1 = colMappings.entrySet().iterator();
			for (int i = 0; i < mapsize; i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs1.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				writelog(key.toString()+":"+value.toString(), debug_tblh);
				
			}
			
			tbl_parse_tbl(doc, colMappings, trEduLblAnn, tblEduLblAnn);
		}
	}
}
public boolean tbl_already_processed(ArrayList<Annotation> processedTables, Annotation tblEduLblAnn){
	for(Annotation processedTable : processedTables){
		if(processedTable.equals(tblEduLblAnn)){
			return true;
		}
	}
	return false;
}
public void tbl_parse_tbl(Document doc, Hashtable colMappings, Annotation trEduLblAnn, Annotation tblEduLblAnn){
	writelog("tbl_parse_tbl", debug_tblh);
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("majorType","edu_prefix");
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet containedAnns = annAll.getContained(trEduLblAnn.getStartNode().getOffset()+1, tblEduLblAnn.getEndNode().getOffset());
	AnnotationSet trSet = containedAnns.get("tr");
	List trList = new ArrayList(trSet);
	Collections.sort(trList, new OffsetComparator());
	Object[][] arr = new Object[trList.size()][colMappings.size()+1];
	int i = 0;
	for (Annotation trAnn : trList) {
		writelog("-----------tr---------------", debug_tblh);
		writelog(get_annotation_content(trAnn), debug_tblh);
		AnnotationSet tempAnnSet = annAll.getContained(trAnn.getStartNode().getOffset(), trAnn.getEndNode().getOffset());
		AnnotationSet tdSet = tempAnnSet.get("td");	
		List tdList = new ArrayList(tdSet);
		Collections.sort(tdList, new OffsetComparator());
		int j = 0;
		boolean notRowSpanColumnFound = false;
		int maxRowSpan = 0;
		for (Annotation tdAnn : tdList) {
			writelog("-----------td---------------:"+notRowSpanColumnFound, debug_tblh);
			for(int tempI = j; tempI < colMappings.size(); tempI++ ){
				if(arr[i][j] != null){
					j++;
				}
				else{
					break;
				}
			}
			if(j >= colMappings.size()){
				continue;
			}
			long start = tdAnn.getStartNode().getOffset();
			long end = tdAnn.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			writelog(annotation, debug_tblh);
			if(!tdAnn.getFeatures().containsKey("rowspan")){
				arr[i][j] = annotation;
				notRowSpanColumnFound = true;
				writelog(i+":"+j+":"+annotation, debug_tblh);
				j++;
			}
			else{
				writelog("got row span", debug_tblh);
				int rowSpan = Integer.parseInt(tdAnn.getFeatures().get("rowspan"));
				maxRowSpan = rowSpan;
				for(int k = i; k< (i+rowSpan); k++){
					writelog(k+":"+j+":"+annotation, debug_tblh);
					arr[k][j] = annotation;
				}
				j++;
			}
		}
		if(!notRowSpanColumnFound){
			writelog("no RowSpan Column Found", debug_tblh);
			for(int k = i+1; k< (i+1+maxRowSpan); k++){
				for(int l = 0; l<colMappings.size() ; l++){
					arr[k][l] = null;
				}
			}
		}
		writelog(i+"#"+j, debug_tblh);
		arr[i][colMappings.size()] = trAnn;
		i++;
	}
	writelog("tesT 7", debug_tblh);
	for(int m = 0; m< trList.size(); m++){
		gate.FeatureMap eduFeature = gate.Factory.newFeatureMap();
		writelog(m+"+"+colMappings.size(), debug_tblh);
		Annotation ann = arr[m][colMappings.size()];
		String t = "";
		boolean skip = false;
		for(int n = 0; n< colMappings.size(); n++){
			String col = colMappings.get(n).toString()
			if(arr[m][n] == null){
				skip = true;
				writelog(m+":"+n+":"+"skipped", debug_tblh);
			}
			t = t+"##"+arr[m][n];
			if(!col.equalsIgnoreCase("invalid")){
				Object prevVal = eduFeature.get(col);
				if(prevVal == null){
					eduFeature.put(col, arr[m][n]);
				}
				else{
					eduFeature.put(col, prevVal.toString()+"-"+arr[m][n]);
				}				
			}
		}
		writelog(t, debug_tblh);
		eduFeature.put("rule", "table_with_header");
		if(!skip){
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "EduToken",eduFeature);
		}
	}
}
public Annotation tbl_get_containing_table(Document doc, Annotation trAnn){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet tableSet = annAll.get("table");
	if(tableSet != null &&  !tableSet.isEmpty()){
		List annList = new ArrayList(tableSet);
		Collections.sort(annList, new OffsetComparator());
		
		for (Annotation ann : annList) {
			AnnotationSet tempAnnSet = annAll.getContained(ann.getStartNode().getOffset()+1, ann.getEndNode().getOffset()-1);
			AnnotationSet containedTableSet = tempAnnSet.get("table");
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("majorType","edu_prefix");
			if(containedTableSet == null || containedTableSet.isEmpty()){
				if(trAnn.withinSpanOf(ann)){
					return ann;
				}
			}
		}
	}
	return null;	
}
public ArrayList<Annotation> tbl_get_education_labels_rows(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	ArrayList<Annotation> myArr = new ArrayList<Annotation>();
	AnnotationSet trSet = annAll.get("tr");
	if(trSet != null &&  !trSet.isEmpty()){
		List annList = new ArrayList(trSet);
		Collections.sort(annList, new OffsetComparator());
		
		for (Annotation ann : annList) {
			AnnotationSet tempTrSet = annAll.getContained(ann.getStartNode().getOffset()+1, ann.getEndNode().getOffset()-1);
			AnnotationSet containedTrSet = tempTrSet.get("tr");
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("majorType","edu_prefix");
			if(containedTrSet == null || containedTrSet.isEmpty()){
				AnnotationSet containedSet = annAll.getContained(ann.getStartNode().getOffset(), ann.getEndNode().getOffset());
				AnnotationSet eduSet = containedSet.get("Lookup",features);
				if(eduSet != null && eduSet.size() > 1) {
					//Table row contains more than 1 Education labels
					//Now check if each of these labels are contained in separated columns
					AnnotationSet tdSet = containedSet.get("td");
					int count =  0;
					for (Annotation tdAnn : tdSet) {
						if((tdAnn.getEndNode().getOffset()-tdAnn.getStartNode().getOffset()) < 50){
							AnnotationSet tdContainedSet = annAll.getContained(tdAnn.getStartNode().getOffset(), tdAnn.getEndNode().getOffset());
							AnnotationSet tdEduSet = tdContainedSet.get("Lookup",features);
							if(tdEduSet != null && tdEduSet.size() > 0){
								count = count + 1;
							}
						}
					}
					if(count > 1){
						myArr.add(ann);
						//return ann;
					}
				}
			}
			
		}
	}
	return myArr;
}
public Annotation tbl_get_next_tr_annotation(Document doc, Annotation trAnn){
	AnnotationSet annAll = doc.getAnnotations();
	List annAllList = new ArrayList(annAll);
	Collections.sort(annAllList, new OffsetComparator());
	Annotation ann = annAllList.get(annAllList.size()-1);
	writelog("tbl_get_col_mappings1", debug_tblh);
	Long docEndOffSet = ann.getEndNode().getOffset();
	AnnotationSet nextAnnTokenSet = annAll.getContained(trAnn.getStartNode().getOffset()+1, docEndOffSet);
	AnnotationSet nextAnnTrSet = nextAnnTokenSet.get("tr");
	List nextTRList = new ArrayList(nextAnnTrSet);
	Collections.sort(nextTRList, new OffsetComparator());
	Annotation nextTrAnn = nextTRList.get(0);	
	return nextTrAnn;	
}
public Hashtable tbl_get_col_mappings(Document doc, Annotation trAnn){
	writelog("tbl_get_col_mappings", debug_tblh);
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("majorType","edu_prefix");
	AnnotationSet annAll = doc.getAnnotations();
	Annotation nextTrAnn = tbl_get_next_tr_annotation(doc, trAnn);
	AnnotationSet nextAnnTokenSet = annAll.getContained(nextTrAnn.getStartNode().getOffset(), nextTrAnn.getEndNode().getOffset());
	AnnotationSet nextAnnTdSet = nextAnnTokenSet.get("td");
	writelog("tbl_get_col_mappings2", debug_tblh);
	AnnotationSet tempAnnSet = annAll.getContained(trAnn.getStartNode().getOffset(), trAnn.getEndNode().getOffset());
	AnnotationSet containedTdSet = tempAnnSet.get("td");
	boolean skipColSpan = false;
	writelog("tbl_get_col_mappings3", debug_tblh);
	if(containedTdSet.size() == nextAnnTdSet.size()){
		skipColSpan = true;
	}
	List annList = new ArrayList(containedTdSet);
	Collections.sort(annList, new OffsetComparator());
	Hashtable colMapping = new Hashtable();
	int i = 0;
	for (Annotation tdAnn : annList) {
		if(!tdAnn.getFeatures().containsKey("rowspan")){
			AnnotationSet tdContainedSet = annAll.getContained(tdAnn.getStartNode().getOffset(), tdAnn.getEndNode().getOffset());
			Annotation temp = tdContainedSet.iterator().next();
			long start = temp.getStartNode().getOffset();
			long end = temp.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);		
			AnnotationSet eduSet = tdContainedSet.get("Lookup",features);
			if(eduSet != null && !eduSet.isEmpty()){
				Annotation eduAnn = eduSet.iterator().next();
				if(skipColSpan || !tdAnn.getFeatures().containsKey("colspan")){
					colMapping.put(i, eduAnn.getFeatures().get("minorType"));
					i++;
				}
				else{
					int colSpan = Integer.parseInt(tdAnn.getFeatures().get("colspan"));
					for(int j = 0; j< colSpan; j++){
						colMapping.put(i, eduAnn.getFeatures().get("minorType"));
						i++;
					}
				}
			}
			else{
				colMapping.put(i, "invalid");			
				i++;
			}
		}
	}
	return colMapping;
}
public void get_edu_sections(Document doc){
	gate.FeatureMap featuresAll = gate.Factory.newFeatureMap();
	featuresAll.put("kind","all");
	gate.FeatureMap featuresEdu = gate.Factory.newFeatureMap();
	featuresEdu.put("kind","education");
	
	AnnotationSet annAll = doc.getAnnotations();
	List annAllList = new ArrayList(annAll);
	Collections.sort(annAllList, new OffsetComparator());
	Annotation ann = annAllList.get(annAllList.size()-1);
	
	Long docEndOffSet = ann.getEndNode().getOffset();
	writelog("End Off set:"+docEndOffSet, debug_global);
	AnnotationSet eduSet = annAll.get("SectionToken", featuresEdu);
	
	
	List eduList = new ArrayList(eduSet);
	Collections.sort(eduList, new OffsetComparator());
	
	
	ArrayList<Annotation> myArr = new ArrayList<Annotation>();
	for(Annotation eduAnn : eduList){
		writelog("test:"+eduAnn.getEndNode().getOffset(), debug_global);
		AnnotationSet containedAnns = annAll.getContained(eduAnn.getEndNode().getOffset(), docEndOffSet);
		AnnotationSet allSet = containedAnns.get("SectionToken", featuresAll);
		if(allSet != null && !allSet.isEmpty()){
			List allList = new ArrayList(allSet);
			Collections.sort(allList, new OffsetComparator());
			//S
			long properEnd = 0;
			for(Annotation sectionAnn : allList){
				AnnotationSet betweenAnns = annAll.getContained(eduAnn.getEndNode().getOffset(), sectionAnn.getStartNode().getOffset());
				AnnotationSet betweenCustomSentences = betweenAnns.get("CustomSentence");
				if(betweenCustomSentences != null && !betweenCustomSentences.isEmpty()){
					properEnd = sectionAnn.getStartNode().getOffset();
					break;
				}
			}
			if(properEnd == 0){
				doc.annotations.add(eduAnn.getEndNode().getOffset(), docEndOffSet, "EduSection",featuresAll);
			}
			else{
				doc.annotations.add(eduAnn.getEndNode().getOffset(), properEnd, "EduSection",featuresAll);
				
			}
		}
		else{
			doc.annotations.add(eduAnn.getEndNode().getOffset(), docEndOffSet, "EduSection",featuresAll);			
		}
		
	}
}
public void grmr_process_education_sections(Document doc){
	writelog("grmr_process_education_sections", debug_grmr);
	AnnotationSet annAll = doc.getAnnotations();
	List annAllList = new ArrayList(annAll);
	Collections.sort(annAllList, new OffsetComparator());
	Annotation ann = annAllList.get(annAllList.size()-1);	
	Long docEndOffSet = ann.getEndNode().getOffset();
	writelog("1", debug_grmr);	
	AnnotationSet secSet = annAll.get("EduSection");
	if(secSet != null && !secSet.isEmpty()){
		List secList = new ArrayList(secSet);
		Collections.sort(secList, new OffsetComparator());
		for(Annotation secAnn : secList){
			long start = secAnn.getStartNode().getOffset();
			long end = secAnn.getEndNode().getOffset();
			writelog("2", debug_grmr);
			boolean onlyOneSentence = (docEndOffSet == end)?true:false;
			grmr_process_edu_section(doc, start, end, false);
			writelog("3", debug_grmr);
		}
	}
	AnnotationSet eduTokenSet = annAll.get("EduToken");
	if(eduTokenSet == null || eduTokenSet.isEmpty()) {
		writelog("4", debug_grmr);
		grmr_process_edu_section(doc, 0, docEndOffSet, true);
		writelog("5", debug_grmr);
	}
	writelog("6", debug_grmr);
}
public void grmr_process_edu_section(Document doc, long start, long end, boolean onlyOneSentence){
	writelog("A", debug_grmr);
	ArrayList<Annotation> degreeSentences = grmr_get_degree_sentences(doc, start, end);
	writelog("B", debug_grmr);
	grmr_process_degree_sentences(doc, degreeSentences, start, end, onlyOneSentence);
	writelog("C", debug_grmr);
}
public ArrayList<Annotation> grmr_get_degree_sentences(Document doc, long start, long end){
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("kind","degree");
	
	AnnotationSet annAll = doc.getAnnotations().getContained(start, end);
	AnnotationSet senSet = annAll.get("CustomSentence");
	List senList = new ArrayList(senSet);
	Collections.sort(senList, new OffsetComparator());
	ArrayList<Annotation> myArr = new ArrayList<Annotation>();
	for(Annotation senAnn : senList){
		
		if((senAnn.getEndNode().getOffset() - senAnn.getStartNode().getOffset())< 350 && !grmr_is_already_processed_for_header(senAnn)){
			
			AnnotationSet containedAnns = annAll.getContained(senAnn.getStartNode().getOffset(), senAnn.getEndNode().getOffset());
			AnnotationSet eduSet = containedAnns.get("TempEduToken", features);
			if(!eduSet.isEmpty()){
				myArr.add(senAnn);
			}			
		}
	}
	return myArr;
}
public boolean grmr_is_already_processed_for_header(Annotation senAnn){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet eduFPSet = annAll.get("EduFPToken");
	for(Annotation ann : eduFPSet){
		if(senAnn.getStartNode().getOffset() >= ann.getStartNode().getOffset() && senAnn.getStartNode().getOffset() <= ann.getEndNode().getOffset()){
			return true;
		}
	}
	return false;
}
public void grmr_process_degree_sentences(Document doc, ArrayList<Annotation> degreeSentences, long start, long end, boolean onlyOneSentence){
	AnnotationSet annAll = doc.getAnnotations().getContained(start, end);
	AnnotationSet senSet = annAll.get("CustomSentence");
	if(senSet == null || senSet.isEmpty()){
		return;
	}
	List senList = new ArrayList(senSet);
	Collections.sort(senList, new OffsetComparator());
	Annotation ann = (Annotation)senList.get(senList.size()-1);
	if(degreeSentences != null &&  !degreeSentences.isEmpty()){
		for(Annotation senAnn : degreeSentences){
			gate.FeatureMap finalFeatures = gate.Factory.newFeatureMap();			
			grmr_fill_edu_tokens(doc, senAnn, finalFeatures, true);
			
			if(!onlyOneSentence){
				int numberOfSentences = 8;
				//Look backward
				AnnotationSet backSet = annAll.getContained(start, senAnn.getEndNode().getOffset()-1);
				AnnotationSet backSenSet = backSet.get("CustomSentence");
				List backSenList = new ArrayList(backSenSet);
				Collections.sort(backSenList, new OffsetComparator());
				Collections.reverse(backSenList);
				List back3Sentences
				if(backSenList.size() >= numberOfSentences){
					back3Sentences = backSenList.subList(0,numberOfSentences);
				}
				else{
					back3Sentences = backSenList.subList(0, backSenList.size());				
				}
				for(Annotation backAnn : back3Sentences){
					if(backAnn.getFeatures().containsKey("processed")){
						break;
					}
					boolean breakLoop = false;
					breakLoop = grmr_fill_edu_tokens(doc, backAnn, finalFeatures, false);
					if(breakLoop){
						break;
					}
				}
				//Look forward
				
				AnnotationSet forwardSet = annAll.getContained(senAnn.getEndNode().getOffset()+1, end);
				AnnotationSet forwardSenSet = forwardSet.get("CustomSentence");
				List forwardSenList = new ArrayList(forwardSenSet);
				Collections.sort(forwardSenList, new OffsetComparator());
				List forward3Sentences
				if(forwardSenList.size() >= numberOfSentences){
					forward3Sentences = forwardSenList.subList(0,numberOfSentences);
				}
				else{
					forward3Sentences = forwardSenList.subList(0, forwardSenList.size());				
				}
				for(Annotation forwardAnn : forward3Sentences){
					if(forwardAnn.getFeatures().containsKey("processed")){
						break;
					}
					boolean breakLoop = false;
					breakLoop = grmr_fill_edu_tokens(doc, forwardAnn, finalFeatures, false);
					if(breakLoop){
						break;
					}
				}
			}
			//grmr_do_lcs(finalFeatures);
			if(finalFeatures.size() > 1){
				int percent = grmr_signal_to_noise_ratio(doc, finalFeatures);
				long annStart = Long.parseLong(finalFeatures.get("start").toString());
				long annEnd = Long.parseLong(finalFeatures.get("end").toString());
				finalFeatures.remove("start");
				finalFeatures.remove("end");
				finalFeatures.put("rule", "grmr");
				finalFeatures.put("sgnr",percent);
				if(percent > 20){
					doc.annotations.add(annStart, annEnd, "EduToken",finalFeatures);
				}else{
					doc.annotations.add(annStart, annEnd, "EduSGNRToken",finalFeatures);
				}
			}
		}		
	}
}
public int grmr_signal_to_noise_ratio(Document doc, gate.FeatureMap finalFeatures){
	long start = Long.parseLong(finalFeatures.get("start").toString());
	long end = Long.parseLong(finalFeatures.get("end").toString());
	int mapsize = finalFeatures.size();		
	Iterator keyValuePairs1 = finalFeatures.entrySet().iterator();
	int nonNoiseLength = 0;
	for (int i = 0; i < mapsize; i++) {
		Map.Entry entry = (Map.Entry) keyValuePairs1.next();
		String key = entry.getKey().toString();
		String value = entry.getValue().toString();
		if(!key.equalsIgnoreCase("date") && !key.equalsIgnoreCase("date1") && !key.equalsIgnoreCase("percent")){
			nonNoiseLength += value.length();
		}
	}
	if(finalFeatures.containsKey("date") && finalFeatures.containsKey("percent")){
		nonNoiseLength += finalFeatures.get("date").toString().length();
		nonNoiseLength += finalFeatures.get("percent").toString().length();
		if(finalFeatures.containsKey("date1")){
			nonNoiseLength += finalFeatures.get("date1").toString().length();			
		}
	}
	//Get white space length
	int whiteSpaceLength = 0;
	AnnotationSet annAll = doc.getAnnotations();
	gate.FeatureMap fm = gate.Factory.newFeatureMap();
	fm.put("kind", "space");
	AnnotationSet containedAnns = annAll.getContained(start, end);
	AnnotationSet spaceSet = containedAnns.get("Token", fm);
	writelog(containedAnns.size()+":"+spaceSet.size(), debug_grmr);
	if(spaceSet != null && !spaceSet.isEmpty()){
		for(Annotation spaceAnn : spaceSet){
			long sStart = spaceAnn.getStartNode().getOffset();
			long sEnd = spaceAnn.getEndNode().getOffset();
			whiteSpaceLength += sEnd - sStart;
		}
	}
	int totalLength = (end - start) - whiteSpaceLength;
	int percent = 100;
	if(totalLength > 0){
		percent = (nonNoiseLength * 100)/totalLength;
	}
	finalFeatures.put("snr",percent);
	return percent;
}
public void grmr_do_lcs(gate.FeatureMap finalFeatures){
	grmr_do_lcs_heck(finalFeatures, "degree", "branch");
	grmr_do_lcs_heck(finalFeatures, "college", "degree");
	grmr_do_lcs_heck(finalFeatures, "university", "degree");
	grmr_do_lcs_heck(finalFeatures, "college", "branch");
	grmr_do_lcs_heck(finalFeatures, "university", "branch");			
	grmr_do_lcs_heck(finalFeatures, "college", "university");
}
public void grmr_do_lcs_heck(gate.FeatureMap finalFeatures, String token1, String token2){
	if(finalFeatures.containsKey(token1) && finalFeatures.containsKey(token2)){
		String degree = finalFeatures.get(token1).toString().toUpperCase();
		String branch = finalFeatures.get(token2).toString().toUpperCase();
		String[] token1Words = degree.split("[\\s,\\.]");
		String[] token2Words = branch.split("[\\s,\\.]");
		String shortDegree = "";
		if(token1Words.size() == 1){
			shortDegree = degree;
		}else{
			for(String s : token1Words){
				if(s.length() > 0){
					Character c = s.charAt(0);			
					shortDegree = shortDegree + c.toString();
				}
			}			
		}
		writelog(degree+":"+shortDegree, debug_grmr);
		String shortBranch = "";
		if(token2Words.size() == 1){
			shortBranch = branch;
		}else{
			
			for(String s : token2Words){
				if(s.length() > 0){
					Character c = s.charAt(0);			
					shortBranch = shortBranch + c.toString();
				}
			}
		}
		writelog("branch:"+branch+"; shortBranch:"+shortBranch, debug_grmr);
		writelog("degree:"+degree+"; shortDegree:"+shortDegree, debug_grmr);
		if(shortDegree == shortBranch){
			writelog("both tokens are same", debug_grmr);
			finalFeatures.remove(token2);
		}
		if(!shortDegree.contains(shortBranch) && !shortBranch.contains(shortDegree)){
			String longestSubSequence = longest_sub_sequence(shortDegree, shortBranch);
			writelog(longestSubSequence+":"+shortDegree+":"+shortBranch, debug_grmr);
			if(longestSubSequence.length() == shortDegree.length() || longestSubSequence.length() == shortBranch.length()){
				writelog(longestSubSequence+":"+shortDegree+":"+shortBranch, debug_grmr);
				finalFeatures.remove(token2);
			}
		}
	}
}
public boolean grmr_fill_edu_tokens(Document doc, Annotation senAnn, gate.FeatureMap finalFeatures, boolean firstSentence){
	boolean breakLoop = false;
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet containedAnns = annAll.getContained(senAnn.getStartNode().getOffset(), senAnn.getEndNode().getOffset());
	
	//Fill Edu Tokens
	gate.FeatureMap tempFeatures = gate.Factory.newFeatureMap();
	AnnotationSet eduSet = containedAnns.get("TempEduToken");
	List eduList = new ArrayList(eduSet);
	Collections.sort(eduList, new OffsetComparator());
	for(Annotation eduAnn : eduList){
		gate.FeatureMap initialFeatures = eduAnn.getFeatures();
		String token = initialFeatures.get("kind").toString();
		long start = eduAnn.getStartNode().getOffset();
		long end = eduAnn.getEndNode().getOffset();
		String annotation = doc.getContent().getContent(start, end);
		if(!finalFeatures.containsKey(token)){
			grmr_add_feature(tempFeatures,token,annotation);
		}
		else if(!token.equals("branch") && !firstSentence){
			breakLoop = true;
		}
	}
	
	//Fill Date Tokens
	AnnotationSet dateSet = containedAnns.get("Date");
	if(dateSet != null && !dateSet.isEmpty()){
		List dateList = new ArrayList(dateSet);
		Collections.sort(dateList, new OffsetComparator());
		int i = 0;
		for(Annotation dateAnn : dateList){
			long start = dateAnn.getStartNode().getOffset();
			long end = dateAnn.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			if(i == 0){
				if(!finalFeatures.containsKey("date")){
					//tempFeatures.put("date", annotation);
					grmr_add_feature(tempFeatures,"date",annotation);
				}
				else if(!firstSentence){
					breakLoop = true;
				}
			}
			else if(i == 1){
				//tempFeatures.put("date1", annotation);
				grmr_add_feature(tempFeatures,"date1",annotation);
			}
			else{
				break;
			}
			i++;
		}
	}
	
	//Fill Percent Tokens
	AnnotationSet percentSet = containedAnns.get("Percent");
	if(percentSet != null && !percentSet.isEmpty()){
		List percentList = new ArrayList(percentSet);
		Collections.sort(percentList, new OffsetComparator());
		Annotation percentAnn = (Annotation)percentList.get(0);
		long start = percentAnn.getStartNode().getOffset();
		long end = percentAnn.getEndNode().getOffset();
		String annotation = doc.getContent().getContent(start, end);
		if(!finalFeatures.containsKey("percent")){
			//tempFeatures.put("percent", annotation);
			grmr_add_feature(tempFeatures,"percent",annotation);
		}
		else if(!firstSentence){
			breakLoop = true;
		}
	}
	
	if(!breakLoop){
		if(firstSentence){
			finalFeatures.put("start", senAnn.getStartNode().getOffset());	
			finalFeatures.put("end", senAnn.getEndNode().getOffset());	
		}
		else{
			if(tempFeatures.size() > 0){
				long start = Long.parseLong(finalFeatures.get("start").toString());
				long end = Long.parseLong(finalFeatures.get("end").toString());
				if(senAnn.getStartNode().getOffset() < start){
					if((end - senAnn.getStartNode().getOffset()) > 500){
						return true;
					}
					finalFeatures.put("start", senAnn.getStartNode().getOffset());				
				}
				if(senAnn.getEndNode().getOffset() > end){
					if((senAnn.getEndNode().getOffset() - start) > 500){
						return true;
					}
					finalFeatures.put("end", senAnn.getEndNode().getOffset());				
				}
			}
		}
		// Copy temp features to final features
		int mapsize = tempFeatures.size();		
		Iterator keyValuePairs1 = tempFeatures.entrySet().iterator();
		for (int i = 0; i < mapsize; i++)
		{
			Map.Entry entry = (Map.Entry) keyValuePairs1.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			finalFeatures.put(key, value);
		}
		
		//
		doc.annotations.remove(senAnn);
		gate.FeatureMap sentenceFeatures = senAnn.getFeatures();
		sentenceFeatures.put("processed", "true");
		doc.annotations.add(senAnn.getStartNode(), senAnn.getEndNode(), "CustomSentence",sentenceFeatures);
		
		return false;
	}
	else{
		return true;
	}
}
public void grmr_add_feature(gate.FeatureMap featureMap, String key, String value){
	writelog("grmr_add_feature:"+key+"--"+value, debug_grmr);				
	int mapsize = featureMap.size();		
	Iterator keyValuePairs1 = featureMap.entrySet().iterator();
	boolean ignore = false;
	boolean removed = false;
	for (int i = 0; i < mapsize; i++) {
		Map.Entry entry = (Map.Entry) keyValuePairs1.next();
		Object oldKey = entry.getKey().toString();
		Object oldValue = entry.getValue().toString();
		if(oldValue.startsWith(value)){
			ignore = true;
			writelog("Ignoring:"+key+"--"+value+"   for:"+oldKey+"---"+oldValue, debug_grmr);			
		}
		else{
			if(value.startsWith(oldValue)){
				writelog("Removing:"+oldKey+"---"+oldValue+"   for:"+key+"--"+value, debug_grmr);
				featureMap.remove(oldKey);
				removed = true;
			}
		}
	}
	if(!ignore && (removed || !featureMap.containsKey(key))){
		featureMap.put(key, value);
	}
}
public String longest_sub_sequence(String X, String Y) {
	int i,j;
	//String X = "ABCABCBA";
	//String Y = "BACBBA";
	/* initialize the n x m matrix B and C for dynamic programming 
	 * B[i][j] stores the directions, C[i][j] stores the length of LCS of
	 * X[0..i-1] and Y[0..j-1]
	 */ 
	int n = X.length();
	int m = Y.length();
	int[][] C = new int[n+1][m+1];
	int[][] B = new int[n+1][m+1];
	
	/* C[i][0] = 0 for 0<=i<=n */
	for (i = 0; i <= n; i++) {
		C[i][0] = 0;
	}
	
	/* C[0][j] = 0 for  0<=j<=m */
	for (j = 0; j <= m; j++) {
		C[0][j] = 0;
	}
	
	/* dynamic programming */
	for (i = 1; i <= n; i++) {
		for (j = 1; j <= m; j++) {
			if (X.charAt(i-1) == Y.charAt(j-1)) {
				C[i][j]=C[i-1][j-1]+1;
				B[i][j]=1;  /* diagonal */
			}
			else if (C[i-1][j]>=C[i][j-1]) {
				C[i][j]=C[i-1][j];
				B[i][j] = 2;  /* down */
			}
			else {
				C[i][j]=C[i][j-1];     
				B[i][j]=3;   /* forword */
			}
		}
	}
	/* Backtracking */
	String lcs = new String();
	i=n;
	j=m;
	while (i!=0 && j!=0) {
		if (B[i][j] ==1) {   /* diagonal */
			lcs =X.charAt(i-1).toString() + lcs;
			i = i - 1;
			j = j - 1;
		}
		if (B[i][j] == 2) {  /* up */
			i = i - 1;
		}
		if (B[i][j] == 3) {  /* backword */
			j = j - 1;
		}
	}
	
	return lcs;
}
/**
 * This method is extracted from
 * http://en.wikibooks.org/wiki/Algorithm_Implementation
 * /Strings/Longest_common_substring#C.23 The java implementation was giving
 * only length of longest substring to also get the longest substring we also used C#
 * implementation
 * 
 * @param str_
 * @param toCompare_
 * @return
 */
public static String longest_sub_string(String str_, String toCompare_) {
	if (str_.isEmpty() || toCompare_.isEmpty())
		return "";
	
	int[][] compareTable = new int[str_.length()][toCompare_.length()];
	int maxLen = 0;
	int lastSubsBegin = 0;
	StringBuilder sequenceBuilder = new StringBuilder();
	
	for (int m = 0; m < str_.length(); m++) {
		for (int n = 0; n < toCompare_.length(); n++) {
			if (str_.charAt(m) != toCompare_.charAt(n)) {
				compareTable[m][n] = 0;
			} else {
				if ((m == 0) || (n == 0)) {
					compareTable[m][n] = 1;
				} else {
					compareTable[m][n] = compareTable[m - 1][n - 1] + 1;
				}
			}
			if (compareTable[m][n] > maxLen) {
				maxLen = compareTable[m][n];
				int thisSubsBegin = m - compareTable[m][n] + 1;
				if (lastSubsBegin == thisSubsBegin) {
					// if the current LCS is the same as the last time this
					// block ran
					sequenceBuilder.append(str_.charAt(m));
				} else {
					// this block resets the string builder if a different
					// LCS is found
					lastSubsBegin = thisSubsBegin;
					sequenceBuilder.delete(0, sequenceBuilder.length());// clear
					// it
					sequenceBuilder.append(str_.substring(lastSubsBegin,
							m + 1));
				}
			}
			/*
			 * maxLen = (compareTable[m][n] > maxLen) ? compareTable[m][n] :
			 * maxLen;
			 */
		}
	}
	return sequenceBuilder.toString();
}
public String get_annotation_content(long start, long end){
	return doc.getContent().getContent(start, end);
}

public String get_annotation_content(Annotation ann){
	return doc.getContent().getContent(ann.getStartNode().getOffset(), ann.getEndNode().getOffset());
}

public void writelog(String message, boolean debug){
	if(debug){
		//System.out.println(message);
	}
}