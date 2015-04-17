
package org.opcfoundation.webservices.xmlda._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="ReadResult" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyBase" minOccurs="0"/>
 *         &lt;element name="RItemList" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}ReplyItemList" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}OPCError" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "readResult",
    "rItemList",
    "errors"
})
@XmlRootElement(name = "ReadResponse")
public class ReadResponse {

    @XmlElement(name = "ReadResult")
    protected ReplyBase readResult;
    @XmlElement(name = "RItemList")
    protected ReplyItemList rItemList;
    @XmlElement(name = "Errors")
    protected List<OPCError> errors;

    /**
     * Gets the value of the readResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyBase }
     *     
     */
    public ReplyBase getReadResult() {
        return readResult;
    }

    /**
     * Sets the value of the readResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyBase }
     *     
     */
    public void setReadResult(ReplyBase value) {
        this.readResult = value;
    }

    /**
     * Gets the value of the rItemList property.
     * 
     * @return
     *     possible object is
     *     {@link ReplyItemList }
     *     
     */
    public ReplyItemList getRItemList() {
        return rItemList;
    }

    /**
     * Sets the value of the rItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplyItemList }
     *     
     */
    public void setRItemList(ReplyItemList value) {
        this.rItemList = value;
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

}
