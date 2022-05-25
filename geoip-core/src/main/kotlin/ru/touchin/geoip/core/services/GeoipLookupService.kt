package ru.touchin.geoip.core.services

import ru.touchin.common.territories.enums.TerritoryCode

interface GeoipLookupService {

    fun get(ipAddress: String): TerritoryCode

}
