
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OPCQuality complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OPCQuality">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="QualityField" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}qualityBits" default="good" />
 *       &lt;attribute name="LimitField" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}limitBits" default="none" />
 *       &lt;attribute name="VendorField" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OPCQuality")
public class OPCQuality {

    @XmlAttribute(name = "QualityField")
    protected QualityBits qualityField;
    @XmlAttribute(name = "LimitField")
    protected LimitBits limitField;
    @XmlAttribute(name = "VendorField")
    @XmlSchemaType(name = "unsignedByte")
    protected Short vendorField;

    /**
     * Gets the value of the qualityField property.
     * 
     * @return
     *     possible object is
     *     {@link QualityBits }
     *     
     */
    public QualityBits getQualityField() {
        if (qualityField == null) {
            return QualityBits.GOOD;
        } else {
            return qualityField;
        }
    }

    /**
     * Sets the value of the qualityField property.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityBits }
     *     
     */
    public void setQualityField(QualityBits value) {
        this.qualityField = value;
    }

    /**
     * Gets the value of the limitField property.
     * 
     * @return
     *     possible object is
     *     {@link LimitBits }
     *     
     */
    public LimitBits getLimitField() {
        if (limitField == null) {
            return LimitBits.NONE;
        } else {
            return limitField;
        }
    }

    /**
     * Sets the value of the limitField property.
     * 
     * @param value
     *     allowed object is
     *     {@link LimitBits }
     *     
     */
    public void setLimitField(LimitBits value) {
        this.limitField = value;
    }

    /**
     * Gets the value of the vendorField property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getVendorField() {
        if (vendorField == null) {
            return ((short) 0);
        } else {
            return vendorField;
        }
    }

    /**
     * Sets the value of the vendorField property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setVendorField(Short value) {
        this.vendorField = value;
    }

}
