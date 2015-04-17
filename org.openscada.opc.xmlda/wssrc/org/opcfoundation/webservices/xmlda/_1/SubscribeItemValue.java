
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscribeItemValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscribeItemValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemValue" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ItemValue" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RevisedSamplingRate" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeItemValue", propOrder = {
    "itemValue"
})
public class SubscribeItemValue {

    @XmlElement(name = "ItemValue")
    protected ItemValue itemValue;
    @XmlAttribute(name = "RevisedSamplingRate")
    protected Integer revisedSamplingRate;

    /**
     * Gets the value of the itemValue property.
     * 
     * @return
     *     possible object is
     *     {@link ItemValue }
     *     
     */
    public ItemValue getItemValue() {
        return itemValue;
    }

    /**
     * Sets the value of the itemValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemValue }
     *     
     */
    public void setItemValue(ItemValue value) {
        this.itemValue = value;
    }

    /**
     * Gets the value of the revisedSamplingRate property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRevisedSamplingRate() {
        return revisedSamplingRate;
    }

    /**
     * Sets the value of the revisedSamplingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRevisedSamplingRate(Integer value) {
        this.revisedSamplingRate = value;
    }

}
