/**
 * 
 */
package com.talentPool.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;


/**
 * @author Sachinm
 *
 */
public class RequestMethodFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		// Overriding the 'Allow' header of response to avoid false positive
		// response about TRACE to OPTIONS request
		String referrer = req.getHeader("referer");
		TPLogger.getLogger().debug("Referrer while filtering the URL : "+referrer);
		if (!Utils.isBlankOrNull(referrer) && !referrer.startsWith(req.getScheme()+"://"+req.getServerName())
				&& !referrer.startsWith("https://resdex.naukri.com") && !referrer.startsWith("https://tsipl-adsync.talentica-all.com/adfs") 
				&& !referrer.startsWith("https://idp.ssocircle.com/sso/SSORedirect/metaAlias/publicidp?")
				&& !referrer.startsWith("https://login.microsoftonline.com") && !referrer.startsWith("https://apazsts.asianpaints.com/adfs")
				&& !referrer.startsWith("https://apps.thetalentpool.co.in/TalentPool/")){
			TPLogger.getLogger().error("Invalid referer");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid referer");
			return;
		}
		
		if(req.getMethod().equals("OPTIONS")) {
			StringBuffer allow = new StringBuffer();
			// There is a doGet method
			allow.append("GET, HEAD");
			// There is a doPost
			allow.append(", POST");
			// There is a doPut
			//allow.append(", PUT");
			// There is a doDelete
			//allow.append(", DELETE");
			// Trace - assume disabled unless we can prove otherwise
			/*if (req instanceof RequestFacade
					&& ((RequestFacade) req).getAllowTrace()) {
				allow.append(", TRACE");
			}*/
			// Always allow options
			allow.append(", OPTIONS");

			
			resp.setHeader("Allow", allow.toString());
			return;
		}
		if(!(req.getMethod().equals("GET") || req.getMethod().equals("POST"))) {
			TPLogger.getLogger().error("Only post or get is allowed");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Only post or get is allowed");
			return;
		}
		if(req.getMethod().equals("TRACE")) {
			TPLogger.getLogger().error("TRACE method not allowed");
			return;
		}
		chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
