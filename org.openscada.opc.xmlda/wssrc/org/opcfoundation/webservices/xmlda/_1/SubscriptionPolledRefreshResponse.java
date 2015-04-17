
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
 *         &lt;element name="SubscriptionPolledRefreshResult" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyBase" minOccurs="0"/>
 *         &lt;element name="InvalidServerSubHandles" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RItemList" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}SubscribePolledRefreshReplyItemList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DataBufferOverflow" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subscriptionPolledRefreshResult",
    "invalidServerSubHandles",
    "rItemList",
    "errors"
})
@XmlRootElement(name = "SubscriptionPolledRefreshResponse")
public class SubscriptionPolledRefreshResponse {

    @XmlElement(name = "SubscriptionPolledRefreshResult")
    protected ReplyBase subscriptionPolledRefreshResult;
    @XmlElement(name = "InvalidServerSubHandles")
    protected List<String> invalidServerSubHandles;
    @XmlElement(name = "RItemList")
    protected List<SubscribePolledRefreshReplyItemList> rItemList;
    @XmlElement(name = "Errors")
    protected List<OPCError> errors;
    @XmlAttribute(name = "DataBufferOverflow")
    protected Boolean dataBufferOverflow;

    /**
     * Gets the value of the subscriptionPolledRefreshResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyBase }
     *     
     */
    public ReplyBase getSubscriptionPolledRefreshResult() {
        return subscriptionPolledRefreshResult;
    }

    /**
     * Sets the value of the subscriptionPolledRefreshResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyBase }
     *     
     */
    public void setSubscriptionPolledRefreshResult(ReplyBase value) {
        this.subscriptionPolledRefreshResult = value;
    }

    /**
     * Gets the value of the invalidServerSubHandles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invalidServerSubHandles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvalidServerSubHandles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInvalidServerSubHandles() {
        if (invalidServerSubHandles == null) {
            invalidServerSubHandles = new ArrayList<String>();
        }
        return this.invalidServerSubHandles;
    }

    /**
     * Gets the value of the rItemList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rItemList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRItemList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscribePolledRefreshReplyItemList }
     * 
     * 
     */
    public List<SubscribePolledRefreshReplyItemList> getRItemList() {
        if (rItemList == null) {
            rItemList = new ArrayList<SubscribePolledRefreshReplyItemList>();
        }
        return this.rItemList;
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
     * Gets the value of the dataBufferOverflow property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDataBufferOverflow() {
        if (dataBufferOverflow == null) {
            return false;
        } else {
            return dataBufferOverflow;
        }
    }

    /**
     * Sets the value of the dataBufferOverflow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDataBufferOverflow(Boolean value) {
        this.dataBufferOverflow = value;
    }

}
