
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ReadRequestItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadRequestItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ItemPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ReqType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ClientItemHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MaxAge" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadRequestItem")
public class ReadRequestItem {

    @XmlAttribute(name = "ItemPath")
    protected String itemPath;
    @XmlAttribute(name = "ReqType")
    protected QName reqType;
    @XmlAttribute(name = "ItemName")
    protected String itemName;
    @XmlAttribute(name = "ClientItemHandle")
    protected String clientItemHandle;
    @XmlAttribute(name = "MaxAge")
    protected Integer maxAge;

    /**
     * Gets the value of the itemPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPath() {
        return itemPath;
    }

    /**
     * Sets the value of the itemPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPath(String value) {
        this.itemPath = value;
    }

    /**
     * Gets the value of the reqType property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getReqType() {
        return reqType;
    }

    /**
     * Sets the value of the reqType property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setReqType(QName value) {
        this.reqType = value;
    }

    /**
     * Gets the value of the itemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    /**
     * Gets the value of the clientItemHandle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientItemHandle() {
        return clientItemHandle;
    }

    /**
     * Sets the value of the clientItemHandle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientItemHandle(String value) {
        this.clientItemHandle = value;
    }

    /**
     * Gets the value of the maxAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the value of the maxAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

}
