/**
 * 
 */
package com.talentPool.parser.converter;

import java.io.File;
import java.io.FileInputStream;

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;


/**
 * @author pallavi
 *
 */
public class PDFToTextConverter extends AbstractConverter {
	
	private String parsedText;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;
	/* (non-Javadoc)
	 * @see com.talentPool.parser.converter.AbstractConverter#convert(java.lang.String)
	 */
	@Override
	public String convert(String filePath) {		
		FileInputStream is = null;
        try {
        	File f = new File(filePath);
        	if(!f.isFile()) {
        		return "";
        	}
        	is = new FileInputStream(f);        	
        	pdDoc = PDDocument.load(is);
        	if(pdDoc.isEncrypted() && !Utils.isBlankOrNull(pdDoc.getOwnerPasswordForEncryption())){
        		pdDoc.decrypt(pdDoc.getOwnerPasswordForEncryption());
        	}
        	pdfStripper = new PDFTextStripper();
        	cosDoc = pdDoc.getDocument();
        	parsedText = pdfStripper.getText(cosDoc);
        } catch (Exception e) {
            TPLogger.getLogger().error(GlobalConstants.ERROR, e);        
            parsedText = "";
        } finally {
        	try {
        		if (cosDoc != null) {
	    			cosDoc.close();
	    		}
	            if (pdDoc != null) {
	            	pdDoc.close();
	            }
        		if(is != null) {
        			is.close();
        		}	    		
        	} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
        }
        return parsedText;
	}

}
