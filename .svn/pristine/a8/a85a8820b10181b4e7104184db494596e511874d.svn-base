package com.talentPool.ie.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.io.IOUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileUploader;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.documents.DocumentConstants;

import eu.medsea.mimeutil.MimeUtil;

/**
 * @author SiddharthK
 * A layer to interact with importServlet while download from mozilla firefox.
 * It's been created so that code of importServlet is not required to be changed, as importServlet also imports docs from
 * IE, bulk import etc.
 */
public class ImportServletHelper extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = (String) request.getParameter("mode");
		String inputAttribute = "";
		response.setHeader("Access-Control-Allow-Origin","*");
		if (mode.equalsIgnoreCase("getFileName")){
			inputAttribute = (String) request.getParameter("appUrl");
			String res = getFileName(inputAttribute);
			response.getOutputStream().print(res);
			//request.setAttribute("html", res);
			//RequestDispatcher rd = request.getRequestDispatcher("/common/htmlFile.jsp");
			//rd.forward(request, response);
		} else if (mode.equalsIgnoreCase("checkForURL")){
			inputAttribute = (String) request.getParameter("appUrl");
			String res = checkForURL(inputAttribute);
			PrintWriter out = response.getWriter();
			out.println(res);
		} else if (mode.equalsIgnoreCase("getBulkImportSessionId")){
			inputAttribute = (String) request.getParameter("appUrl");
			String res = getBulkImportSessionId(inputAttribute);
			PrintWriter out = response.getWriter();
			out.println(res);
		} else if (mode.equalsIgnoreCase("authorize")){
			inputAttribute = (String) request.getParameter("appUrl");
			String res = authorize(inputAttribute);
			PrintWriter out = response.getWriter();
			out.println(res);
		} else {
			PrintWriter out = response.getWriter();
			out.println("no meaningful attribute");
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = (String) request.getParameter("mode");
		response.setHeader("Access-Control-Allow-Origin","*");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = request.getInputStream().read(buffer)) > -1 ) {
		    baos.write(buffer, 0, len);
		}
		baos.flush();
		
		FileUploader fileUploader = new FileUploader();
		if (mode.equals("sendFilesToServer")){
			String fname = (String) request.getParameter("fname");
			String ext = (String) request.getParameter("ext");
			String sessionId = (String) request.getParameter("sessionId");
			String serverMode = (String) request.getParameter("serverMode");
			String emailBodyFile = (String) request.getParameter("emailBodyFile");
			String commentUrl = (String) request.getParameter("commentUrl");
			String appURL = (String) request.getParameter("appURL");
			if(ext.contains("doc")){
				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				Collection<?> mimeTypes = MimeUtil.getMimeTypes(baos.toByteArray());
				String mimeType = MimeUtil.getFirstMimeType(mimeTypes.toString()).toString();
				String subMimeType = MimeUtil.getSubType(mimeTypes.toString());
				TPLogger.getLogger().debug("The Mime type is: " + mimeTypes + ", " + mimeType + ", " + subMimeType);
				MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				if(mimeType.equals("application/zip")){
					ext=".docx";
				}else{
					ext=".doc";
				}
			}
			String fileName = fname +ext;
			String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, "helperDocuments");
			try {
				File f = new File(filePath);
				if (!f.isDirectory()){
					f.mkdir();
				}
				if(fileName.endsWith(".pdf")){
					OutputStream out = new FileOutputStream(new File(Utils.concatFilePath(filePath, fileName)));
					out.write(baos.toByteArray());
					out.close();
				}else{
					fileUploader.uploadFileFromFireFox(new ByteArrayInputStream(baos.toByteArray()), filePath, fileName);
				}
			} catch (FileUploadException e) {
				TPLogger.getLogger().error(e.getMessage(), e);
			}
			String res = sendFilesToServer(appURL, filePath, fname, ext, sessionId, serverMode, emailBodyFile, commentUrl);
			PrintWriter out = response.getWriter();
			out.println(res);
		} else if(mode.equals("saveHelperFiles")){
			String fname = (String) request.getParameter("fname");
			String path = Utils.concatFilePath(DocumentConstants.documentsPath, "helperDocuments");
			String filePath = Utils.concatFilePath(path, fname)+"_files";
			try {
				File f = new File(filePath);
				if (!f.isDirectory()){
					f.mkdir();
				}
				String fileName = (String) request.getParameter("fileName");
				fileUploader.uploadFileFromFireFox(new ByteArrayInputStream(baos.toByteArray()), filePath, fileName);
			} catch (FileUploadException e) {
				TPLogger.getLogger().error(e.getMessage(), e);
			}
		}
	}
	
	public static String getFileName(String appURL) {
		String name = "";
		try {
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.addRequestProperty("newFileName", "1");
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write("");
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (name.equals("")) {
					name = line;
				} else {
					name += line;
				}
			}
			wr.close();
			rd.close();
			
		} catch (Exception e) {
			System.out.println(e);
			//writeToLog("getFileName==" + e.toString());
		}
		return name;
	}
	
	public String checkForURL(String appURL) {
		String result = "true";
		try {
			URL url = new URL(appURL);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write("");
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			wr.close();
			rd.close();
		} catch (Exception e) {
			//writeToLog("checkForURL==" + e.toString());
			result = "false";
		}
		return result;
	}
	
	public static String getBulkImportSessionId(String appURL) {
		String name = "";
		String mode = "getBulkImportSessionId";
		try {
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.addRequestProperty("mode", mode);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write("");
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (name.equals("")) {
					name = line;
				} else {
					name += line;
				}
			}
			wr.close();
			rd.close();
			
		} catch (Exception e) {
			//writeToLog("getFileName==" + e.toString());
		}
		return name;
	}
	
	public static String authorize(String appURL) {
		String authError = "";
		String mode = "authorize";
		try {
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.addRequestProperty("mode", mode);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write("");
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (authError.equals("")) {
					authError = line;
				} else {
					authError += line;
				}
			}
			wr.close();
			rd.close();
			
		} catch (Exception e) {
			//writeToLog("authorize==" + e.toString());
		}
		return authError;
	}
	
	public static String sendFilesToServer(String appURL, String filePath, String fileName, String extension, String sessionId, String mode, String emailBodyFile, String commentUrl) {
		String result = "";
		try {
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			if(".html".equals(extension)) {
				conn.setRequestProperty("Content-Type", "text/html");
			} else if(".doc".equals(extension)) {
				conn.setRequestProperty("Content-Type", "application/msword");				
			} else if(".rtf".equals(extension)) {
				conn.setRequestProperty("Content-Type", "application/rtf");
			}			
			conn.addRequestProperty("fileName", fileName+extension);
			conn.addRequestProperty("sessionId", sessionId);
			conn.addRequestProperty("mode", mode);
			conn.addRequestProperty("emailBodyFile", emailBodyFile);
			conn.addRequestProperty("commentUrl", commentUrl);
			
			if(".doc".equals(extension) || ".rtf".equals(extension)) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				readBinaryFile(filePath+"\\"+fileName+extension, outputStream);			
				
				conn.setRequestProperty("Content-Length", ""+outputStream.size());
				outputStream.writeTo(conn.getOutputStream());
				
				conn.getOutputStream().flush();
				conn.getOutputStream().close();				
			} else {
				IOUtils.write(Files.readAllBytes(Paths.get(filePath+"\\"+fileName+extension)), conn.getOutputStream());
			}
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (result.equals("")) {
					result = line;
				} else {
					result += line;
				}
			}			
			rd.close();
			
			String[] arrayResult = result.split(" ");
			String htmlPath = arrayResult[1];
			String path = htmlPath.substring(0, htmlPath.length()-5);
			sendAllFilesToServer(appURL, filePath,fileName, path);
			
			File file = new File(filePath+"\\"+fileName+extension);
			if(file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			//writeToLog("sendFilesToServer==" + e.toString());
			result = "false";
		}
		return result;
	}
	
	public static String sendAllFilesToServer(String appURL, String filePath, String fileName, String folderPath) {
		String result = "true";
		try {
			File dir = new File(filePath+"\\"+fileName+"_files");			
			String[] children = dir.list();
		    if (children == null) {
		        // Either dir does not exist or is not a directory
		    } else {
		        for (int i=0; i<children.length; i++) {
		            // Get filename of file or directory
		            String filename = children[i];
		            String value = sendFiles(appURL, filePath+"\\"+fileName+"_files", filename, folderPath);
			        if(value.equals(false)){
			        	result = "false";
			        }
			        File file = new File(filePath+"\\"+fileName+"_files"+"\\"+filename);
			        if(file.exists()) {
			        	file.delete();
			        }
		        }
		    }   
		    if(dir.exists()) {
		    	dir.delete();
		    }
		} catch (Exception e) {
			//writeToLog(e.toString());
		}
		return result;
	}

	public static String sendFiles(String appURL, String filePath, String fileName, String folderPath) {
		String result = "";
		try {
			// Send data			
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "text/html");
			conn.addRequestProperty("fileName", fileName);
			conn.addRequestProperty("folderPath", folderPath);
			
			String content = readFile(filePath+"\\"+fileName);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(content);
			wr.flush();
			wr.close();
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (result.equals("")) {
					result = line;
				} else {
					result += line;
				}
			}			
			rd.close();

		} catch (Exception e) {
			//writeToLog("sendFiles=" + e.toString());
			result = "false";
		}
		return result;
	}
	
	private static String readFile(String filePath) {
		StringBuffer contents = new StringBuffer();
		BufferedReader input = null;
		try {
			File file = new File(filePath);			
			if (file.exists()) {
				input = new BufferedReader(new FileReader(file));
				
				String line = null; // not declared within while loop
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append("\n");
				}				
			}
		} catch (Exception ex) {
			//writeToLog("readFile==" + ex.toString());
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				//writeToLog("Exception => " + e.getMessage());
			}
		}
		//writeToLog(filePath + "file not found");
		return contents.toString();
	}
	
	private static void readBinaryFile(String filePath, ByteArrayOutputStream outputStream) {
		InputStream input = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				input = new BufferedInputStream(new FileInputStream(filePath));
				
				byte[] buf = new byte[8 * 1024];
				int bytesRead = 0;
				while ((bytesRead = input.read(buf)) > 0) {				
					outputStream.write(buf, 0, bytesRead);
				}	
				outputStream.close();
			}			
		} catch (Exception ex) {
			//writeToLog("readFile==" + ex.toString());
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				//writeToLog("Exception => " + e.getMessage());
			}
		}		
	}
	
}
