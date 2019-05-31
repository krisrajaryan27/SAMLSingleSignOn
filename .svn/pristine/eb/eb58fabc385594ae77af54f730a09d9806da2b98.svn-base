package com.talentPool.desktop.saml.context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import org.opensaml.Configuration;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.slf4j.LoggerFactory;

import com.talentPool.desktop.saml.util.Binding;
import com.talentPool.desktop.saml.util.Constants;
import com.talentPool.desktop.saml.util.Properties;

public class SP {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SP.class);

    private final String entityid;

    private final String acs;

    private final String slo;

    private Credential credential;

    private final EntityDescriptor spEntityDescriptor;

    private final boolean wantAssertionsSigned;

    private final boolean authnRequestsSigned;

    SP() {

        this.entityid = Properties.getString(Constants.ENTITYID);
        this.acs = Properties.getString(Constants.CONSUMER);
        this.slo = Properties.getString(Constants.LOGOUT);
        this.wantAssertionsSigned = Properties.getBoolean(Constants.WANT_ASSERTIONS_SIGNED, false);	
		this.authnRequestsSigned = Properties.getBoolean(Constants.AUTHN_REQUESTS_SIGNED, false);

        final XMLObjectBuilderFactory bf = Configuration.getBuilderFactory();

        // Create the EntityDescriptor
        @SuppressWarnings("unchecked")
        final SAMLObjectBuilder<EntityDescriptor> entityDescriptorBuilder = (SAMLObjectBuilder<EntityDescriptor>) bf.
                getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME);

        spEntityDescriptor = entityDescriptorBuilder.buildObject();

        spEntityDescriptor.setEntityID(entityid);

        @SuppressWarnings("unchecked")
        final SAMLObjectBuilder<SPSSODescriptor> spSSODescriptorBuilder = (SAMLObjectBuilder<SPSSODescriptor>) bf.
                getBuilder(SPSSODescriptor.DEFAULT_ELEMENT_NAME);

        final SPSSODescriptor spSSODescriptor = spSSODescriptorBuilder.buildObject();

        spSSODescriptor.setWantAssertionsSigned(wantAssertionsSigned);
        spSSODescriptor.setAuthnRequestsSigned(authnRequestsSigned);
        
        try {
            credential = getCredential();
            @SuppressWarnings("unchecked")
            final SAMLObjectBuilder<KeyDescriptor> keyDescriptorBuilder = (SAMLObjectBuilder<KeyDescriptor>) bf.
                    getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME);

            KeyDescriptor signKeyDescriptor = keyDescriptorBuilder.buildObject();

            signKeyDescriptor.setUse(UsageType.SIGNING);

            try {
                signKeyDescriptor.setKeyInfo(getKeyInfo());
                spSSODescriptor.getKeyDescriptors().add(signKeyDescriptor);
            } catch (org.opensaml.xml.security.SecurityException e) {
                log.error("Error generating credentials", e);
            }

        } catch (Exception e) {
            log.warn("Error retrieving credentials", e);
        }

        @SuppressWarnings("unchecked")
        SAMLObjectBuilder<SingleLogoutService> sloHttpServiceBuilder = (SAMLObjectBuilder<SingleLogoutService>) bf.
                getBuilder(SingleLogoutService.DEFAULT_ELEMENT_NAME);

        String[] singleLogoutServices= {Binding.POST.getBinding(),Binding.REDIRECT.getBinding()};
        for(String singleLogoutService: singleLogoutServices){
        	SingleLogoutService sloHttpService = sloHttpServiceBuilder.buildObject();
            sloHttpService.setBinding(singleLogoutService);
            sloHttpService.setLocation(Properties.getString(Constants.LOGOUT));
            sloHttpService.setResponseLocation(Properties.getString(Constants.LOGOUT));
            spSSODescriptor.getSingleLogoutServices().add(sloHttpService);
        }
        
        @SuppressWarnings("unchecked")
        final SAMLObjectBuilder<NameIDFormat> nameIDFormatBuilder = (SAMLObjectBuilder<NameIDFormat>) bf.getBuilder(
                NameIDFormat.DEFAULT_ELEMENT_NAME);

        String[] formats = {Constants.NAMEID_ENTITY,Constants.NAMEID_EMAIL_ADDRESS,Constants.NAMEID_PERSISTENT,Constants.NAMEID_TRANSIENT,Constants.NAMEID_UNSPECIFIED,Constants.NAMEID_X509_SUBJECT_NAME};
        for (String format : formats) {
            NameIDFormat nameIDFormat = nameIDFormatBuilder.buildObject();
            nameIDFormat.setFormat(format);
            spSSODescriptor.getNameIDFormats().add(nameIDFormat);
        }

        @SuppressWarnings("unchecked")
        final SAMLObjectBuilder<AssertionConsumerService> assertionConsumerServiceBuilder
                = (SAMLObjectBuilder<AssertionConsumerService>) bf.getBuilder(
                        AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        String[] acsBindings= {SAMLConstants.SAML2_ARTIFACT_BINDING_URI,SAMLConstants.SAML2_POST_BINDING_URI};
        for(String acsBinding: acsBindings){
        	AssertionConsumerService assertionConsumerService = assertionConsumerServiceBuilder.buildObject();
            assertionConsumerService.setIndex(0);
            assertionConsumerService.setBinding(acsBinding);
            // Setting address for our AssertionConsumerService
            assertionConsumerService.setLocation(Properties.getString(Constants.CONSUMER));
            spSSODescriptor.getAssertionConsumerServices().add(assertionConsumerService);
        }
        
        spSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);

        spEntityDescriptor.getRoleDescriptors().add(spSSODescriptor);
    }

    public final Credential getCredential() throws Exception {
        if (this.credential == null) {
            final KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            log.debug("Loading {} kestore", keystore.getType());

            try (final InputStream inputStream = SP.class.getResourceAsStream("/Asian.keystore")) {
                keystore.load(inputStream, Properties.getString(Constants.STOREPASS, "changeit").toCharArray());
                log.debug("Loaded");
            }

            final String alias = Properties.getString(Constants.CERT_ALIAS, "asian");

            log.debug("Loading certificate .... {}", alias);

            final Map<String, String> passwordMap = new HashMap<>();
            passwordMap.put(alias, Properties.getString(Constants.KEYPASS, "changeit"));
            
            KeyStore.PrivateKeyEntry pkEntry = null;
            
            try{
            pkEntry=(KeyStore.PrivateKeyEntry) keystore.getEntry(alias, new KeyStore.PasswordProtection("changeit".toCharArray()));
            }catch(Exception e){
            	log.error("Failed to Get Private Entry From the keystore", e);
            }
            PrivateKey pk = pkEntry.getPrivateKey();
            X509Certificate certificate = (X509Certificate) pkEntry.getCertificate();
            BasicX509Credential signingcredential = new BasicX509Credential();
            signingcredential.setEntityCertificate(certificate);
            signingcredential.setPrivateKey(pk);
            
            this.credential=signingcredential;
            final KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(keystore, passwordMap);

            this.credential = resolver.resolveSingle(new CriteriaSet(new EntityIDCriteria(alias)));

            log.debug("Loaded");
        }

        return credential;
    }

    public final EntityDescriptor getSpEntityDescriptor() {
        return spEntityDescriptor;
    }

    public final KeyInfo getKeyInfo() throws Exception {

        final X509KeyInfoGeneratorFactory keyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();

        keyInfoGeneratorFactory.setEmitEntityCertificate(true);
        KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();

        return keyInfoGenerator.generate(getCredential());
    }

    public String getEntityid() {
        return entityid;
    }

    /**
     * Gets Assertion Consumer Service.
     *
     * @return Assertion Consumer Service URL.
     */
    public String getAcs() {
        return acs;
    }

    /**
     * Gets Single Logout Service.
     *
     * @return Single Logout Service URL.
     */
    public String getSlo() {
        return slo;
    }

    public boolean isWantAssertionsSigned() {
        return wantAssertionsSigned;
    }

    public boolean isAuthnRequestsSigned() {
        return authnRequestsSigned;
    }

}
