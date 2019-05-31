//
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.05 at 04:29:13 PM IST 
//


package com.talentPool.positions.dataobject;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobPositionRequirements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JobPositionRequirements"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element ref="{}JobExperience"/&gt;
 *         &lt;element ref="{}JobQualifications"/&gt;
 *         &lt;element name="SummaryText" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JobPositionRequirements", propOrder = {

})
public class JobPositionRequirements
    implements Serializable
{

    @XmlElement(name = "JobExperience", required = true)
    protected JobExperience jobExperience;
    @XmlElement(name = "JobQualifications", required = true)
    protected JobQualifications jobQualifications;
    @XmlElement(name = "SummaryText", required = true)
    protected String summaryText;

    /**
     * Gets the value of the jobExperience property.
     * 
     * @return
     *     possible object is
     *     {@link JobExperience }
     *     
     */
    public JobExperience getJobExperience() {
        return jobExperience;
    }

    /**
     * Sets the value of the jobExperience property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobExperience }
     *     
     */
    public void setJobExperience(JobExperience value) {
        this.jobExperience = value;
    }

    /**
     * Gets the value of the jobQualifications property.
     * 
     * @return
     *     possible object is
     *     {@link JobQualifications }
     *     
     */
    public JobQualifications getJobQualifications() {
        return jobQualifications;
    }

    /**
     * Sets the value of the jobQualifications property.
     * 
     * @param value
     *     allowed object is
     *     {@link JobQualifications }
     *     
     */
    public void setJobQualifications(JobQualifications value) {
        this.jobQualifications = value;
    }

    /**
     * Gets the value of the summaryText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryText() {
        return summaryText;
    }

    /**
     * Sets the value of the summaryText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryText(String value) {
        this.summaryText = value;
    }

}
