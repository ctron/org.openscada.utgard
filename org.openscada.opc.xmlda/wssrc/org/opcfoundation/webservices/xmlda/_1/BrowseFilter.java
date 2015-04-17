
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for browseFilter.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="browseFilter">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="all"/>
 *     &lt;enumeration value="branch"/>
 *     &lt;enumeration value="item"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "browseFilter")
@XmlEnum
public enum BrowseFilter {

    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("branch")
    BRANCH("branch"),
    @XmlEnumValue("item")
    ITEM("item");
    private final String value;

    BrowseFilter(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BrowseFilter fromValue(String v) {
        for (BrowseFilter c: BrowseFilter.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
