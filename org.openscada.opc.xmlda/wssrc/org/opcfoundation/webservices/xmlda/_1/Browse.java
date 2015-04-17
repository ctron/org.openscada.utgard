
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


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
 *         &lt;element name="PropertyNames" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="LocaleID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ClientRequestHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ItemPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ContinuationPoint" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MaxElementsReturned" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="BrowseFilter" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}browseFilter" default="all" />
 *       &lt;attribute name="ElementNameFilter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="VendorFilter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ReturnAllProperties" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="ReturnPropertyValues" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="ReturnErrorText" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "propertyNames"
})
@XmlRootElement(name = "Browse")
public class Browse {

    @XmlElement(name = "PropertyNames")
    protected List<QName> propertyNames;
    @XmlAttribute(name = "LocaleID")
    protected String localeID;
    @XmlAttribute(name = "ClientRequestHandle")
    protected String clientRequestHandle;
    @XmlAttribute(name = "ItemPath")
    protected String itemPath;
    @XmlAttribute(name = "ItemName")
    protected String itemName;
    @XmlAttribute(name = "ContinuationPoint")
    protected String continuationPoint;
    @XmlAttribute(name = "MaxElementsReturned")
    protected Integer maxElementsReturned;
    @XmlAttribute(name = "BrowseFilter")
    protected BrowseFilter browseFilter;
    @XmlAttribute(name = "ElementNameFilter")
    protected String elementNameFilter;
    @XmlAttribute(name = "VendorFilter")
    protected String vendorFilter;
    @XmlAttribute(name = "ReturnAllProperties")
    protected Boolean returnAllProperties;
    @XmlAttribute(name = "ReturnPropertyValues")
    protected Boolean returnPropertyValues;
    @XmlAttribute(name = "ReturnErrorText")
    protected Boolean returnErrorText;

    /**
     * Gets the value of the propertyNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QName }
     * 
     * 
     */
    public List<QName> getPropertyNames() {
        if (propertyNames == null) {
            propertyNames = new ArrayList<QName>();
        }
        return this.propertyNames;
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
     * Gets the value of the continuationPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinuationPoint() {
        return continuationPoint;
    }

    /**
     * Sets the value of the continuationPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinuationPoint(String value) {
        this.continuationPoint = value;
    }

    /**
     * Gets the value of the maxElementsReturned property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMaxElementsReturned() {
        if (maxElementsReturned == null) {
            return  0;
        } else {
            return maxElementsReturned;
        }
    }

    /**
     * Sets the value of the maxElementsReturned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxElementsReturned(Integer value) {
        this.maxElementsReturned = value;
    }

    /**
     * Gets the value of the browseFilter property.
     * 
     * @return
     *     possible object is
     *     {@link BrowseFilter }
     *     
     */
    public BrowseFilter getBrowseFilter() {
        if (browseFilter == null) {
            return BrowseFilter.ALL;
        } else {
            return browseFilter;
        }
    }

    /**
     * Sets the value of the browseFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrowseFilter }
     *     
     */
    public void setBrowseFilter(BrowseFilter value) {
        this.browseFilter = value;
    }

    /**
     * Gets the value of the elementNameFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElementNameFilter() {
        return elementNameFilter;
    }

    /**
     * Sets the value of the elementNameFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElementNameFilter(String value) {
        this.elementNameFilter = value;
    }

    /**
     * Gets the value of the vendorFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorFilter() {
        return vendorFilter;
    }

    /**
     * Sets the value of the vendorFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorFilter(String value) {
        this.vendorFilter = value;
    }

    /**
     * Gets the value of the returnAllProperties property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnAllProperties() {
        if (returnAllProperties == null) {
            return false;
        } else {
            return returnAllProperties;
        }
    }

    /**
     * Sets the value of the returnAllProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnAllProperties(Boolean value) {
        this.returnAllProperties = value;
    }

    /**
     * Gets the value of the returnPropertyValues property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReturnPropertyValues() {
        if (returnPropertyValues == null) {
            return false;
        } else {
            return returnPropertyValues;
        }
    }

    /**
     * Sets the value of the returnPropertyValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnPropertyValues(Boolean value) {
        this.returnPropertyValues = value;
    }

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
            return false;
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

}
