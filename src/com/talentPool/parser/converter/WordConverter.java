package com.talentPool.parser.converter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;

public class WordConverter {
	
	public static boolean convert(String srcPath, String destPath) {
		boolean result = true;
		String ext=FileHandlerUtils.checkMsWordFileExtnsion(srcPath);
		File f = new File(srcPath);
		if(!f.exists()){
			result = false;
		} else {
			String lowersrcpath = srcPath.toLowerCase();
			if(ext.equals(".docx")){
				result = convertDocx(srcPath, destPath);
			}
			else if(ext.equals(".doc")) {
				result = convertDoc(srcPath, destPath);
			} else {
				result = false;
			}		
		}
		return result;
	}
	
	public static boolean convert(InputStream in,String srcPath, String destPath) {
		boolean result = true;
		File f = new File(srcPath);
		String lowersrcpath = srcPath.toLowerCase();
		if(lowersrcpath.endsWith(".docx")) {
			result = convertDocx(in,srcPath, destPath);
		} else if(lowersrcpath.endsWith(".doc")) {
			result = convertDoc(in, srcPath, destPath);
		} else {
			result = false;
		}	
		return result;
	}
	
	private static boolean convertDocx(InputStream in ,String srcPath, String destPath) {
		boolean result = true;
		File f = new File(srcPath);
		try {
			ByteArrayInputStream bs=new ByteArrayInputStream(IOUtils.toByteArray(in));
			XWPFDocument doc = new XWPFDocument(bs);
			
			OutputStream out = new FileOutputStream(new File(destPath));
			XHTMLConverter.getInstance().convert(doc, out, null);
		} catch (Exception e2) {
			result = false;
			TPLogger.getLogger().error("error converting docx",e2);
		} catch (NoClassDefFoundError e ){
			TPLogger.getLogger().error("NoClassDefFoundError in converting docx",e);
		}
		return result;
	}

	
	private static boolean convertDocx(String srcPath, String destPath) {
		boolean result = true;
		File f = new File(srcPath);
		try {
			InputStream in = new FileInputStream(f);
			XWPFDocument doc = new XWPFDocument(in);
			
			OutputStream out = new FileOutputStream(new File(destPath));
			XHTMLConverter.getInstance().convert(doc, out, null);
		} catch (Exception e2) {
			result = false;
			TPLogger.getLogger().error("error converting docx",e2);
		}catch (NoClassDefFoundError e ){
			TPLogger.getLogger().error("NoClassDefFoundError in converting docx",e);
		}
		return result;
	}

	static Document process( File docFile ) throws Exception {
		final HWPFDocumentCore wordDocument = WordToHtmlUtils.loadDoc( docFile );
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
		DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.newDocument() );
		wordToHtmlConverter.processDocument( wordDocument );
		return wordToHtmlConverter.getDocument();
	}

	private static boolean convertDoc(InputStream in ,String srcPath, String destPath) {
		boolean result = true;
			try	{
//				Document doc = process(new File(srcPath));
				ByteArrayInputStream bs=new ByteArrayInputStream(IOUtils.toByteArray(in));
				HWPFDocumentCore wordDocument = WordToHtmlUtils.loadDoc(bs);
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
						DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.newDocument() );
						wordToHtmlConverter.processDocument( wordDocument );
						Document doc =wordToHtmlConverter.getDocument();
				FileWriter out = new FileWriter(destPath);
				DOMSource domSource = new DOMSource(doc);
				StreamResult streamResult = new StreamResult(out);
			
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				// TODO : set encoding
				serializer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
				serializer.setOutputProperty( OutputKeys.INDENT, "yes" );
				serializer.setOutputProperty( OutputKeys.METHOD, "html" );
				serializer.transform( domSource, streamResult );
				out.close();
			} catch (Exception e) {
				result = false;
				TPLogger.getLogger().error("error converting doc",e);
			}
		return result;
	}
	
	private static boolean convertDoc(String srcPath, String destPath) {
		boolean result = true;
//		HWPFDocumentCore wordDocument;
//		File f = new File(srcPath);
		ClassLoader classloader = org.apache.poi.poifs.filesystem.POIFSFileSystem.class
				.getClassLoader();
		URL res = classloader
				.getResource("org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
		String path = res.getPath();
		System.out.println("Core POI came from " + path);
		
//		try {			
			try	{
				Document doc = process(new File(srcPath));			
				FileWriter out = new FileWriter(destPath);
				DOMSource domSource = new DOMSource(doc);
				StreamResult streamResult = new StreamResult(out);
			
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				// TODO : set encoding
				serializer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
				serializer.setOutputProperty( OutputKeys.INDENT, "yes" );
				serializer.setOutputProperty( OutputKeys.METHOD, "html" );
				serializer.transform( domSource, streamResult );
				out.close();
			} catch (Exception e) {
				result = false;
				TPLogger.getLogger().error("error converting docx",e);
			}
			
//			wordDocument = WordToHtmlUtils.loadDoc(new FileInputStream(f));
//			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
//					DocumentBuilderFactory.newInstance().newDocumentBuilder()
//							.newDocument());
//			wordToHtmlConverter.processDocument(wordDocument);
//			Document htmlDocument = wordToHtmlConverter.getDocument();
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			DOMSource domSource = new DOMSource(htmlDocument);
//			StreamResult streamResult = new StreamResult(out);
//
//			TransformerFactory tf = TransformerFactory.newInstance();
//			Transformer serializer;
//			try {
//				serializer = tf.newTransformer();
//				serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//				serializer.setOutputProperty(OutputKeys.METHOD, "html");
//				serializer.transform(domSource, streamResult);
//				out.close();
//
//			} catch (TransformerConfigurationException e) {
//				result = false;
//				e.printStackTrace();
//			} catch (TransformerException e) {
//				result = false;
//				e.printStackTrace();
//			}
//			
//			String output = new String(out.toByteArray());
//			System.out.println(output);
//			FileOutputStream fos = new FileOutputStream(new File(destPath));
//
//			try {
//				BufferedWriter out1 = new BufferedWriter(
//						new OutputStreamWriter(fos, "UTF-8"));
//				out1.write(output);
//				out1.close();
//			} catch (IOException e) {
//				result = false;
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e1) {
//			result = false;
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			result = false;
//			e1.printStackTrace();
//		} catch (ParserConfigurationException e1) {
//			result = false;
//			e1.printStackTrace();
//		}
		return result;
	}

	public static void main(String[] args) {
		String srcPath = "C:\\Users\\AmitK2\\Desktop\\ATT2490583320653184627.doc";
		String destPath = "C:\\Users\\AmitK2\\Desktop\\ATT2490583320653184627.html";
		
		convertDocx(srcPath, destPath);
	}
}
