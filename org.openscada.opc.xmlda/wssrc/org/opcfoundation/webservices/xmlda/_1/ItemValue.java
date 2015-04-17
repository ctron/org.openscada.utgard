
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ItemValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DiagnosticInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="State" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCQuality" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ValueTypeQualifier" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="ItemPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ClientItemHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ResultID" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemValue", propOrder = {
    "diagnosticInfo",
    "value",
    "quality"
})
public class ItemValue {

    @XmlElement(name = "DiagnosticInfo")
    protected String diagnosticInfo;
    @XmlElement(name = "Value")
    protected Object value;
    @XmlElement(name = "State")
    protected OPCQuality quality;
    @XmlAttribute(name = "ValueTypeQualifier")
    protected QName valueTypeQualifier;
    @XmlAttribute(name = "ItemPath")
    protected String itemPath;
    @XmlAttribute(name = "ItemName")
    protected String itemName;
    @XmlAttribute(name = "ClientItemHandle")
    protected String clientItemHandle;
    @XmlAttribute(name = "Timestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "ResultID")
    protected QName resultID;

    /**
     * Gets the value of the diagnosticInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosticInfo() {
        return diagnosticInfo;
    }

    /**
     * Sets the value of the diagnosticInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosticInfo(String value) {
        this.diagnosticInfo = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the value of the quality property.
     * 
     * @return
     *     possible object is
     *     {@link OPCQuality }
     *     
     */
    public OPCQuality getQuality() {
        return quality;
    }

    /**
     * Sets the value of the quality property.
     * 
     * @param value
     *     allowed object is
     *     {@link OPCQuality }
     *     
     */
    public void setQuality(OPCQuality value) {
        this.quality = value;
    }

    /**
     * Gets the value of the valueTypeQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getValueTypeQualifier() {
        return valueTypeQualifier;
    }

    /**
     * Sets the value of the valueTypeQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setValueTypeQualifier(QName value) {
        this.valueTypeQualifier = value;
    }

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
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the resultID property.
     * 
     * @return
     *     possible object is
     *     {@link QName }
     *     
     */
    public QName getResultID() {
        return resultID;
    }

    /**
     * Sets the value of the resultID property.
     * 
     * @param value
     *     allowed object is
     *     {@link QName }
     *     
     */
    public void setResultID(QName value) {
        this.resultID = value;
    }

}
