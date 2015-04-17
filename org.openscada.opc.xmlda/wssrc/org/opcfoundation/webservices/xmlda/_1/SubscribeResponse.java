
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
 *         &lt;element name="SubscribeResult" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyBase" minOccurs="0"/>
 *         &lt;element name="RItemList" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}SubscribeReplyItemList" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ServerSubHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subscribeResult",
    "rItemList",
    "errors"
})
@XmlRootElement(name = "SubscribeResponse")
public class SubscribeResponse {

    @XmlElement(name = "SubscribeResult")
    protected ReplyBase subscribeResult;
    @XmlElement(name = "RItemList")
    protected SubscribeReplyItemList rItemList;
    @XmlElement(name = "Errors")
    protected List<OPCError> errors;
    @XmlAttribute(name = "ServerSubHandle")
    protected String serverSubHandle;

    /**
     * Gets the value of the subscribeResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyBase }
     *     
     */
    public ReplyBase getSubscribeResult() {
        return subscribeResult;
    }

    /**
     * Sets the value of the subscribeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyBase }
     *     
     */
    public void setSubscribeResult(ReplyBase value) {
        this.subscribeResult = value;
    }

    /**
     * Gets the value of the rItemList property.
     * 
     * @return
     *     possible object is
     *     {@link SubscribeReplyItemList }
     *     
     */
    public SubscribeReplyItemList getRItemList() {
        return rItemList;
    }

    /**
     * Sets the value of the rItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscribeReplyItemList }
     *     
     */
    public void setRItemList(SubscribeReplyItemList value) {
        this.rItemList = value;
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
     * Gets the value of the serverSubHandle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerSubHandle() {
        return serverSubHandle;
    }

    /**
     * Sets the value of the serverSubHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerSubHandle(String value) {
        this.serverSubHandle = value;
    }

}
