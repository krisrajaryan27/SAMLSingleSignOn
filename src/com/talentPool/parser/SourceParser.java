/**
 * 
 */
package com.talentPool.parser;

import java.util.ArrayList;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author shivprasad
 * 
 */
public class SourceParser {
	public String getParsedSourceFromMaster(String content) {
		String src = "";
		try {
			ArrayList sources = CommonUtils.getSourceNames();
			content = ParserUtils.getTokenizedString(content);
			src = ParserUtils.getMatchFromList(content, sources);
			if(Utils.isBlankOrNull(src)){
				src = ParserUtils.findIndexOfShorter(content, sources);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return src;
	}

	public String getParsedSourceIdFromMaster(String content) {
		String srcId = null;
		try {
			String src = getParsedSourceFromMaster(content);
			ArrayList sourceIds = CommonUtils.getSourceIds();
			ArrayList sourceNames = CommonUtils.getSourceNames();
			srcId = ParserUtils.getIdForName(src, sourceNames, sourceIds);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return srcId;
	}

	public static void main(String[] args) {
//		SourceParser p = new SourceParser();
//		String content = "resume from naukri";
		
	}
}
