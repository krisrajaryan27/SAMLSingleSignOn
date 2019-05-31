package com.talentPool.desktop.saml.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.talentPool.desktop.saml.util.Constants;
import com.talentPool.desktop.saml.util.Properties;

public class SPContextListener implements ServletContextListener {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(SPContextListener.class);

	private static String IDP_FOLDER;

	static {
		try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			log.error("Error initializing SAML", e);
		}
	}

	private static BasicParserPool ppool;

	private final static CircleOfTrust circleOfTrust = CircleOfTrust.getInstance();

	public SPContextListener() {
		ppool = new BasicParserPool();
		ppool.setNamespaceAware(true);
		circleOfTrust.setSp(new SP());
	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		IDP_FOLDER= Properties.getString(Constants.IDP_FOLDER);
		log.debug("Properties File: "+IDP_FOLDER);
		setSP();
	}

	public static void setSP() {

		final File idpFolder = new File(IDP_FOLDER);
		if (!idpFolder.isDirectory() || idpFolder.list().length == 0) {
			log.info("No configured IdPs");
		} else {
			for (File idp : idpFolder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(final File dir, final String name) {
					return name.endsWith(".xml");
				}
			})) {
				log.info("Process IdP file descriptor {}", idp.getAbsolutePath());
				try {
					final EntityDescriptor descriptor = unmarshall(new FileInputStream(idp));
					circleOfTrust.addIdP(descriptor.getEntityID(), new IdP(descriptor));
					log.debug("Descriptor Entity id: " + descriptor.getEntityID() + "IDP details: " + idp.getName()
							+ "Descriptor ID: " + descriptor.getID());
				} catch (Exception ignore) {
					log.debug("Error loading IdP {}", idp, ignore);
					log.info("Invalid IdP {}", idp);
				}
			}
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		// do nothing
	}

	private static EntityDescriptor unmarshall(final InputStream is) throws Exception {
		try {
			// Parse metadata file
			final Element metadata = ppool.parse(is).getDocumentElement();
			// Get apropriate unmarshaller
			final Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(metadata);
			// Unmarshall using the document root element, an EntitiesDescriptor
			// in this case
			return EntityDescriptor.class.cast(unmarshaller.unmarshall(metadata));
		} catch (XMLParserException e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}
}
