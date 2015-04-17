
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ReturnErrorText" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="ReturnDiagnosticInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="ReturnItemTime" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="ReturnItemPath" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="ReturnItemName" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="RequestDeadline" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ClientRequestHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LocaleID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestOptions")
public class RequestOptions {

    @XmlAttribute(name = "ReturnErrorText")
    protected Boolean returnErrorText;
    @XmlAttribute(name = "ReturnDiagnosticInfo")
    protected Boolean returnDiagnosticInfo;
    @XmlAttribute(name = "ReturnItemTime")
    protected Boolean returnItemTime;
    @XmlAttribute(name = "ReturnItemPath")
    protected Boolean returnItemPath;
    @XmlAttribute(name = "ReturnItemName")
    protected Boolean returnItemName;
    @XmlAttribute(name = "RequestDeadline")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestDeadline;
    @XmlAttribute(name = "ClientRequestHandle")
    protected String clientRequestHandle;
    @XmlAttribute(name = "LocaleID")
    protected String localeID;

    /**
     * Gets the value of the returnErrorText property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnErrorText() {
        if (returnErrorText == null) {
            return true;
        } else {
            return returnErrorText;
        }
    }

    /**
     * Sets the value of the returnErrorText property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnErrorText(Boolean value) {
        this.returnErrorText = value;
    }

    /**
     * Gets the value of the returnDiagnosticInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnDiagnosticInfo() {
        if (returnDiagnosticInfo == null) {
            return false;
        } else {
            return returnDiagnosticInfo;
        }
    }

    /**
     * Sets the value of the returnDiagnosticInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnDiagnosticInfo(Boolean value) {
        this.returnDiagnosticInfo = value;
    }

    /**
     * Gets the value of the returnItemTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnItemTime() {
        if (returnItemTime == null) {
            return false;
        } else {
            return returnItemTime;
        }
    }

    /**
     * Sets the value of the returnItemTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnItemTime(Boolean value) {
        this.returnItemTime = value;
    }

    /**
     * Gets the value of the returnItemPath property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnItemPath() {
        if (returnItemPath == null) {
            return false;
        } else {
            return returnItemPath;
        }
    }

    /**
     * Sets the value of the returnItemPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnItemPath(Boolean value) {
        this.returnItemPath = value;
    }

    /**
     * Gets the value of the returnItemName property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnItemName() {
        if (returnItemName == null) {
            return false;
        } else {
            return returnItemName;
        }
    }

    /**
     * Sets the value of the returnItemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnItemName(Boolean value) {
        this.returnItemName = value;
    }

    /**
     * Gets the value of the requestDeadline property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestDeadline() {
        return requestDeadline;
    }

    /**
     * Sets the value of the requestDeadline property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestDeadline(XMLGregorianCalendar value) {
        this.requestDeadline = value;
    }

    /**
     * Gets the value of the clientRequestHandle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientRequestHandle() {
        return clientRequestHandle;
    }

    /**
     * Sets the value of the clientRequestHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientRequestHandle(String value) {
        this.clientRequestHandle = value;
    }

    /**
     * Gets the value of the localeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocaleID() {
        return localeID;
    }

    /**
     * Sets the value of the localeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocaleID(String value) {
        this.localeID = value;
    }

}
