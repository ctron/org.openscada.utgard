
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Options" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}RequestOptions" minOccurs="0"/>
 *         &lt;element name="ServerSubHandles" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="HoldTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="WaitTime" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="ReturnAllItems" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "options",
    "serverSubHandles"
})
@XmlRootElement(name = "SubscriptionPolledRefresh")
public class SubscriptionPolledRefresh {

    @XmlElement(name = "Options")
    protected RequestOptions options;
    @XmlElement(name = "ServerSubHandles")
    protected List<String> serverSubHandles;
    @XmlAttribute(name = "HoldTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar holdTime;
    @XmlAttribute(name = "WaitTime")
    protected Integer waitTime;
    @XmlAttribute(name = "ReturnAllItems")
    protected Boolean returnAllItems;

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link RequestOptions }
     *     
     */
    public RequestOptions getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestOptions }
     *     
     */
    public void setOptions(RequestOptions value) {
        this.options = value;
    }

    /**
     * Gets the value of the serverSubHandles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serverSubHandles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServerSubHandles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getServerSubHandles() {
        if (serverSubHandles == null) {
            serverSubHandles = new ArrayList<String>();
        }
        return this.serverSubHandles;
    }

    /**
     * Gets the value of the holdTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoldTime() {
        return holdTime;
    }

    /**
     * Sets the value of the holdTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoldTime(XMLGregorianCalendar value) {
        this.holdTime = value;
    }

    /**
     * Gets the value of the waitTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getWaitTime() {
        if (waitTime == null) {
            return  0;
        } else {
            return waitTime;
        }
    }

    /**
     * Sets the value of the waitTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWaitTime(Integer value) {
        this.waitTime = value;
    }

    /**
     * Gets the value of the returnAllItems property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnAllItems() {
        if (returnAllItems == null) {
            return false;
        } else {
            return returnAllItems;
        }
    }

    /**
     * Sets the value of the returnAllItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnAllItems(Boolean value) {
        this.returnAllItems = value;
    }

}
