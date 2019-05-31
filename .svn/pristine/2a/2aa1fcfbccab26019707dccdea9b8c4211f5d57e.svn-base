package com.talentPool.parser.converter.test;

import junit.framework.TestCase;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLToXHTML extends TestCase {
	private static final String xmlFileName = "C:\\Documents and Settings\\pallavi\\Desktop\\xml\\Resume.xml";
	private static final String xslFileName = "C:\\Documents and Settings\\pallavi\\Desktop\\xml\\Resume.xsl";
	//C:\Documents and Settings\pallavi\Desktop\xml
	public void testXMLToXHTML() throws Exception {
		File xmlFile = new File(xmlFileName);
        File xsltFile = new File(xslFileName);

        // JAXP reads data using the Source interface
        Source xmlSource = new StreamSource(xmlFile);
        Source xsltSource = new StreamSource(xsltFile);

        // the factory pattern supports different XSLT processors
        TransformerFactory transFact =
                TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);

        trans.transform(xmlSource, new StreamResult(System.out));
	}
}
