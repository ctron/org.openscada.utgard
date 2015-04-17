
package org.opcfoundation.webservices.xmlda._1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for qualityBits.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="qualityBits">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="bad"/>
 *     &lt;enumeration value="badConfigurationError"/>
 *     &lt;enumeration value="badNotConnected"/>
 *     &lt;enumeration value="badDeviceFailure"/>
 *     &lt;enumeration value="badSensorFailure"/>
 *     &lt;enumeration value="badLastKnownValue"/>
 *     &lt;enumeration value="badCommFailure"/>
 *     &lt;enumeration value="badOutOfService"/>
 *     &lt;enumeration value="badWaitingForInitialData"/>
 *     &lt;enumeration value="uncertain"/>
 *     &lt;enumeration value="uncertainLastUsableValue"/>
 *     &lt;enumeration value="uncertainSensorNotAccurate"/>
 *     &lt;enumeration value="uncertainEUExceeded"/>
 *     &lt;enumeration value="uncertainSubNormal"/>
 *     &lt;enumeration value="good"/>
 *     &lt;enumeration value="goodLocalOverride"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "qualityBits")
@XmlEnum
public enum QualityBits {

    @XmlEnumValue("bad")
    BAD("bad"),
    @XmlEnumValue("badConfigurationError")
    BAD_CONFIGURATION_ERROR("badConfigurationError"),
    @XmlEnumValue("badNotConnected")
    BAD_NOT_CONNECTED("badNotConnected"),
    @XmlEnumValue("badDeviceFailure")
    BAD_DEVICE_FAILURE("badDeviceFailure"),
    @XmlEnumValue("badSensorFailure")
    BAD_SENSOR_FAILURE("badSensorFailure"),
    @XmlEnumValue("badLastKnownValue")
    BAD_LAST_KNOWN_VALUE("badLastKnownValue"),
    @XmlEnumValue("badCommFailure")
    BAD_COMM_FAILURE("badCommFailure"),
    @XmlEnumValue("badOutOfService")
    BAD_OUT_OF_SERVICE("badOutOfService"),
    @XmlEnumValue("badWaitingForInitialData")
    BAD_WAITING_FOR_INITIAL_DATA("badWaitingForInitialData"),
    @XmlEnumValue("uncertain")
    UNCERTAIN("uncertain"),
    @XmlEnumValue("uncertainLastUsableValue")
    UNCERTAIN_LAST_USABLE_VALUE("uncertainLastUsableValue"),
    @XmlEnumValue("uncertainSensorNotAccurate")
    UNCERTAIN_SENSOR_NOT_ACCURATE("uncertainSensorNotAccurate"),
    @XmlEnumValue("uncertainEUExceeded")
    UNCERTAIN_EU_EXCEEDED("uncertainEUExceeded"),
    @XmlEnumValue("uncertainSubNormal")
    UNCERTAIN_SUB_NORMAL("uncertainSubNormal"),
    @XmlEnumValue("good")
    GOOD("good"),
    @XmlEnumValue("goodLocalOverride")
    GOOD_LOCAL_OVERRIDE("goodLocalOverride");
    private final String value;

    QualityBits(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QualityBits fromValue(String v) {
        for (QualityBits c: QualityBits.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
