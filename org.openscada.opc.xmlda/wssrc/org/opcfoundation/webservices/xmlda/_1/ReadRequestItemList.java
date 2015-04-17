
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ReadRequestItemList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadRequestItemList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Items" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReadRequestItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ItemPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ReqType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="MaxAge" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadRequestItemList", propOrder = {
    "items"
})
public class ReadRequestItemList {

    @XmlElement(name = "Items")
    protected List<ReadRequestItem> items;
    @XmlAttribute(name = "ItemPath")
    protected String itemPath;
    @XmlAttribute(name = "ReqType")
    protected QName reqType;
    @XmlAttribute(name = "MaxAge")
    protected Integer maxAge;

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReadRequestItem }
     * 
     * 
     */
    public List<ReadRequestItem> getItems() {
        if (items == null) {
            items = new ArrayList<ReadRequestItem>();
        }
        return this.items;
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
