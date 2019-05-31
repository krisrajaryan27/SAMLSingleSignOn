package com.talentPool.notifier.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.notifier.TemplateConstants;

public class VelocityManager {
	static {
		// init velocity templates
		try {
			Properties props = System.getProperties();
			props.put("file.resource.loader.path", TemplateConstants.VELOCITY_TEMPLATE_DIR);
			props.put("runtime.log", TemplateConstants.VELOCITY_LOG_PATH);
			Velocity.init(props);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while initializing", e);
		}
	}

	public String handle(String templateFile, HashMap data) {
		String content = null;
		try {
			VelocityContext context = new VelocityContext();
			Set set = data.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object keyData = data.get(key);
				context.put(key, keyData);
			}

			Template template = null;
			try {
				template = Velocity.getTemplate(templateFile);
			} catch (ResourceNotFoundException rnfe) {
				TPLogger.getLogger().error("cannot find template " + templateFile);
			} catch (ParseErrorException pee) {
				TPLogger.getLogger().error("Syntax error in template " + templateFile);
			}
			StringWriter writer = new StringWriter();

			if (template != null)
				template.merge(context, writer);
			content = writer.toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return content;
	}

	public String getContent(String templateFile) {
		String content = "";
		try {
			VelocityContext context = new VelocityContext();
			Template template = Velocity.getTemplate(templateFile);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			content = writer.toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while reading velocity plain content", e);
		}
		return content;
	}
	
	public String handleContent(String content, HashMap data){
		File file = createVmFile(content);
		return handle(file.getName(), data);
	}
	
	// TODO: check why delete throws access denied exception for some cases
	private File createVmFile(String content){
		File file = null;
		try {
			String fileName = TemplateConstants.VELOCITY_TEMPLATE_DIR+"/tempVM.vm";
			file = new File(fileName);
			if(file.exists()){
	//			file.delete();
				PrintWriter writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			}
			FileWriter fstream = new FileWriter(file);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(content);
		    //Close the output stream
		    out.close();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return file;
	}
}
