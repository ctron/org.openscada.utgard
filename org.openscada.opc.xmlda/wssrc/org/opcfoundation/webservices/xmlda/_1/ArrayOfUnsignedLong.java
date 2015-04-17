
package org.opcfoundation.webservices.xmlda._1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfUnsignedLong complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUnsignedLong">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unsignedLong" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUnsignedLong", propOrder = {
    "unsignedLong"
})
public class ArrayOfUnsignedLong {

    @XmlSchemaType(name = "unsignedLong")
    protected List<BigInteger> unsignedLong;

    /**
     * Gets the value of the unsignedLong property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unsignedLong property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnsignedLong().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getUnsignedLong() {
        if (unsignedLong == null) {
            unsignedLong = new ArrayList<BigInteger>();
        }
        return this.unsignedLong;
    }

}
