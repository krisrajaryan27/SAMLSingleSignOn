package com.talentPool.positions.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;

public class MyHttpURLConnectionFactory implements HttpURLConnectionFactory {

	@Override
	public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		URL url1 = new URL(TPApplicationProperties.getProperty("naukri.url"));
		System.setProperty("java.net.useSystemProxies","true");
        List<Proxy> l;
        URLConnection conn = null;
		try {
			l = ProxySelector.getDefault().select(
			            new URI("http://www.google.com/"));
	        for (Iterator<Proxy> iter = l.iterator(); iter.hasNext(); ) {
	            Proxy proxy = (Proxy) iter.next();
	            System.out.println("proxy hostname : " + proxy.type());
	
	            InetSocketAddress addr = (InetSocketAddress)
	                proxy.address();
	
	            if(addr == null) {
	
	                TPLogger.getLogger().info("No Proxy");
	    			conn = url1.openConnection();
	
	            } else {
	
	            	TPLogger.getLogger().info("proxy hostname : " +addr.getHostName());
	
	                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(addr.getHostName(), addr.getPort())); 
	    			conn = url1.openConnection(proxy);
	    			break;
	
	            }
	        }
	        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("144.0.1.235", 80)); 
			//conn = url1.openConnection(proxy);
		} catch (Exception e) {
			TPLogger.getLogger().error("error getting http Url connection", e);
		}
		return (HttpURLConnection) conn;
	}

}
