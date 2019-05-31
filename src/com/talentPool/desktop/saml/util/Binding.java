package com.talentPool.desktop.saml.util;

public enum Binding {

    POST("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"),
    REDIRECT("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"),
    SOAP("urn:oasis:names:tc:SAML:2.0:bindings:SOAP");

    private final String binding;

    private Binding(final String value) {
        this.binding = value;
    }

    public String getBinding() {
        return binding;
    }

}
