package ru.touchin.ua.devices

import ru.touchin.common.devices.enums.DevicePlatform
import ua_parser.Parser as UaParser

class DevicePlatformFactory {

    fun fromUserAgent(userAgent: String): DevicePlatform {
        val client = UaParser().parse(userAgent)

        if (client.device.family.matches(HUAWEI_DEVICE_FAMILY_REGEX)) {
            return DevicePlatform.Huawei
        }

        return when (client.os.family) {
            "iOS" -> DevicePlatform.Apple
            "Android" -> DevicePlatform.Android
            else -> DevicePlatform.Web
        }
    }

    private companion object {

        val HUAWEI_DEVICE_FAMILY_REGEX = Regex(
            "ALP-|AMN-|ANA-|ANE-|ANG-|AQM-|ARS-|ART-|ATU-|BAC-|BLA-|BRQ-|CAG-|CAM-|CAN-|CAZ-|CDL-|CDY-|CLT-" +
                "|CRO-|CUN-|DIG-|DRA-|DUA-|DUB-|DVC-|ELE-|ELS-|EML-|EVA-|EVR-|FIG-|FLA-|FRL-|GLK-|HMA-|HW-|HWI-|INE-" +
                "|JAT-|JEF-|JER-|JKM-|JNY-|JSC-|LDN-|LIO-|LON-|LUA-|LYA-|LYO-|MAR-|MED-|MHA-|MLA-|MRD-|MYA-|NCE-|NEO-" +
                "|NOH-|NOP-|OCE-|PAR-|PIC-|POT-|PPA-|PRA-|RNE-|SEA-|SLA-|SNE-|SPN-|STK-|TAH-|TAS-|TET-|TRT-|VCE-|VIE-" +
                "|VKY-|VNS-|VOG-|VTR-|WAS-|WKG-|WLZ-|YAL"
        )

    }

}
