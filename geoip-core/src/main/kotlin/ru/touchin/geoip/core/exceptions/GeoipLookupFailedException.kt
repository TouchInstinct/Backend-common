package ru.touchin.geoip.core.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class GeoipLookupFailedException(
    ipAddress: String
) : CommonNotFoundException("Geoip lookup failed for ip address $ipAddress")
