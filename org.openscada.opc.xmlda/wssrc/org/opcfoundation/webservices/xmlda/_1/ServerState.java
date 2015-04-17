
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serverState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="serverState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="running"/>
 *     &lt;enumeration value="failed"/>
 *     &lt;enumeration value="noConfig"/>
 *     &lt;enumeration value="suspended"/>
 *     &lt;enumeration value="test"/>
 *     &lt;enumeration value="commFault"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "serverState")
@XmlEnum
public enum ServerState {

    @XmlEnumValue("running")
    RUNNING("running"),
    @XmlEnumValue("failed")
    FAILED("failed"),
    @XmlEnumValue("noConfig")
    NO_CONFIG("noConfig"),
    @XmlEnumValue("suspended")
    SUSPENDED("suspended"),
    @XmlEnumValue("test")
    TEST("test"),
    @XmlEnumValue("commFault")
    COMM_FAULT("commFault");
    private final String value;

    ServerState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServerState fromValue(String v) {
        for (ServerState c: ServerState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
