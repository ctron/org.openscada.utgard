
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BrowseResult" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyBase" minOccurs="0"/>
 *         &lt;element name="Elements" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}BrowseElement" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ContinuationPoint" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MoreElements" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "browseResult",
    "elements",
    "errors"
})
@XmlRootElement(name = "BrowseResponse")
public class BrowseResponse {

    @XmlElement(name = "BrowseResult")
    protected ReplyBase browseResult;
    @XmlElement(name = "Elements")
    protected List<BrowseElement> elements;
    @XmlElement(name = "Errors")
    protected List<OPCError> errors;
    @XmlAttribute(name = "ContinuationPoint")
    protected String continuationPoint;
    @XmlAttribute(name = "MoreElements")
    protected Boolean moreElements;

    /**
     * Gets the value of the browseResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyBase }
     *     
     */
    public ReplyBase getBrowseResult() {
        return browseResult;
    }

    /**
     * Sets the value of the browseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyBase }
     *     
     */
    public void setBrowseResult(ReplyBase value) {
        this.browseResult = value;
    }

    /**
     * Gets the value of the elements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BrowseElement }
     * 
     * 
     */
    public List<BrowseElement> getElements() {
        if (elements == null) {
            elements = new ArrayList<BrowseElement>();
        }
        return this.elements;
    }

    /**
     * Gets the value of the errors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OPCError }
     * 
     * 
     */
    public List<OPCError> getErrors() {
        if (errors == null) {
            errors = new ArrayList<OPCError>();
        }
        return this.errors;
    }

    /**
     * Gets the value of the continuationPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinuationPoint() {
        return continuationPoint;
    }

    /**
     * Sets the value of the continuationPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinuationPoint(String value) {
        this.continuationPoint = value;
    }

    /**
     * Gets the value of the moreElements property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isMoreElements() {
        if (moreElements == null) {
            return false;
        } else {
            return moreElements;
        }
    }

    /**
     * Sets the value of the moreElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMoreElements(Boolean value) {
        this.moreElements = value;
    }

}
