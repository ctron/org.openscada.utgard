
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for interfaceVersion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="interfaceVersion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XML_DA_Version_1_0"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "interfaceVersion")
@XmlEnum
public enum InterfaceVersion {

    @XmlEnumValue("XML_DA_Version_1_0")
    XML_DA_VERSION_1_0("XML_DA_Version_1_0");
    private final String value;

    InterfaceVersion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InterfaceVersion fromValue(String v) {
        for (InterfaceVersion c: InterfaceVersion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
