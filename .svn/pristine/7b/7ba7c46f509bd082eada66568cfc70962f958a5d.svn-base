package com.talentPool.desktop.saml;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talentPool.desktop.saml.context.CircleOfTrust;
import com.talentPool.desktop.saml.context.IdP;

/**
 *
 * @author KrishnaV
 */
public class Authentication extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(Authentication.class);

    private final static String IDP_ENTITY_ID = "entityid";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        final String entityid = request.getParameter(IDP_ENTITY_ID);
        log.info("Retrieving IdP '{}'", entityid);

        final IdP idp;
        if (StringUtils.isBlank(entityid)) {
            idp = CircleOfTrust.getInstance().getIdP();
            log.error("IDP URL: "+idp+ " boolean: "+StringUtils.isBlank(entityid));
        } else {
            idp = CircleOfTrust.getInstance().getIdP(entityid);
        }

        if (idp == null) {
            log.error("No IdP in COT found");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            log.info("Send SAML AuthN request to {}", idp.getId());
            log.error(" IDP Error URL: "+idp.getErrorURL()+" Sign Validator: "+idp.getSignatureValidatorChain()+ " request: "+request+" response: "+response);
            new SAMLRequestSender().sendSAMLAuthRequest(
                    request,
                    response,
                    idp);
        } catch (Exception e) {
            log.error("Error creating request sender", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Service Provider Authentication Servlet Initiator";
    }// </editor-fold>

}
