
package org.opcfoundation.webservices.xmlda._1;

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
 *         &lt;element name="Options" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}RequestOptions" minOccurs="0"/>
 *         &lt;element name="ItemList" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}WriteRequestItemList" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ReturnValuesOnReply" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
    "itemList"
})
@XmlRootElement(name = "Write")
public class Write {

    @XmlElement(name = "Options")
    protected RequestOptions options;
    @XmlElement(name = "ItemList")
    protected WriteRequestItemList itemList;
    @XmlAttribute(name = "ReturnValuesOnReply", required = true)
    protected boolean returnValuesOnReply;

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
     * Gets the value of the itemList property.
     * 
     * @return
     *     possible object is
     *     {@link WriteRequestItemList }
     *     
     */
    public WriteRequestItemList getItemList() {
        return itemList;
    }

    /**
     * Sets the value of the itemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link WriteRequestItemList }
     *     
     */
    public void setItemList(WriteRequestItemList value) {
        this.itemList = value;
    }

    /**
     * Gets the value of the returnValuesOnReply property.
     * 
     */
    public boolean isReturnValuesOnReply() {
        return returnValuesOnReply;
    }

    /**
     * Sets the value of the returnValuesOnReply property.
     * 
     */
    public void setReturnValuesOnReply(boolean value) {
        this.returnValuesOnReply = value;
    }

}
