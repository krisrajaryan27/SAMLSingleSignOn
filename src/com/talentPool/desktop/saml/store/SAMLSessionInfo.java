package com.talentPool.desktop.saml.store;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SAMLSessionInfo {

    private final String nameId;

    private final Map<String, String> attributes;

    private final Date validTo;

    public SAMLSessionInfo(final String nameId, final Map<String, String> attributes, final Date validTo) {
        this.nameId = nameId;
        this.attributes = attributes;
        this.validTo = validTo;
    }

    public String getNameId() {
        return nameId;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Date getValidTo() {
        return validTo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
