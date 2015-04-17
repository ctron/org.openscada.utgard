
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReplyBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReplyBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="RcvTime" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ReplyTime" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ClientRequestHandle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RevisedLocaleID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ServerState" use="required" type="{http://opcfoundation.org/webservices/XMLDA/1.0/}serverState" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReplyBase")
public class ReplyBase {

    @XmlAttribute(name = "RcvTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rcvTime;
    @XmlAttribute(name = "ReplyTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar replyTime;
    @XmlAttribute(name = "ClientRequestHandle")
    protected String clientRequestHandle;
    @XmlAttribute(name = "RevisedLocaleID")
    protected String revisedLocaleID;
    @XmlAttribute(name = "ServerState", required = true)
    protected ServerState serverState;

    /**
     * Gets the value of the rcvTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRcvTime() {
        return rcvTime;
    }

    /**
     * Sets the value of the rcvTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRcvTime(XMLGregorianCalendar value) {
        this.rcvTime = value;
    }

    /**
     * Gets the value of the replyTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReplyTime() {
        return replyTime;
    }

    /**
     * Sets the value of the replyTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReplyTime(XMLGregorianCalendar value) {
        this.replyTime = value;
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
     * Gets the value of the revisedLocaleID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevisedLocaleID() {
        return revisedLocaleID;
    }

    /**
     * Sets the value of the revisedLocaleID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevisedLocaleID(String value) {
        this.revisedLocaleID = value;
    }

    /**
     * Gets the value of the serverState property.
     * 
     * @return
     *     possible object is
     *     {@link ServerState }
     *     
     */
    public ServerState getServerState() {
        return serverState;
    }

    /**
     * Sets the value of the serverState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerState }
     *     
     */
    public void setServerState(ServerState value) {
        this.serverState = value;
    }

}
