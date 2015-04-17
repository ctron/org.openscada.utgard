
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="GetPropertiesResult" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyBase" minOccurs="0"/>
 *         &lt;element name="PropertyLists" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}PropertyReplyList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getPropertiesResult",
    "propertyLists",
    "errors"
})
@XmlRootElement(name = "GetPropertiesResponse")
public class GetPropertiesResponse {

    @XmlElement(name = "GetPropertiesResult")
    protected ReplyBase getPropertiesResult;
    @XmlElement(name = "PropertyLists")
    protected List<PropertyReplyList> propertyLists;
    @XmlElement(name = "Errors")
    protected List<OPCError> errors;

    /**
     * Gets the value of the getPropertiesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyBase }
     *     
     */
    public ReplyBase getGetPropertiesResult() {
        return getPropertiesResult;
    }

    /**
     * Sets the value of the getPropertiesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyBase }
     *     
     */
    public void setGetPropertiesResult(ReplyBase value) {
        this.getPropertiesResult = value;
    }

    /**
     * Gets the value of the propertyLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyReplyList }
     * 
     * 
     */
    public List<PropertyReplyList> getPropertyLists() {
        if (propertyLists == null) {
            propertyLists = new ArrayList<PropertyReplyList>();
        }
        return this.propertyLists;
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

}
