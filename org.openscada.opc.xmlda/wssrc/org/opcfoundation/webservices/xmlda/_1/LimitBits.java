
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for limitBits.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="limitBits">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="low"/>
 *     &lt;enumeration value="high"/>
 *     &lt;enumeration value="constant"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "limitBits")
@XmlEnum
public enum LimitBits {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("low")
    LOW("low"),
    @XmlEnumValue("high")
    HIGH("high"),
    @XmlEnumValue("constant")
    CONSTANT("constant");
    private final String value;

    LimitBits(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LimitBits fromValue(String v) {
        for (LimitBits c: LimitBits.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
