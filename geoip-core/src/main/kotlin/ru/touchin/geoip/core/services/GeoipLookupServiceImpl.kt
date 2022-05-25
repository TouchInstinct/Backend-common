package ru.touchin.geoip.core.services

import ru.touchin.geoip.core.repositories.GeoipLookupRepository
import ru.touchin.geoip.core.repositories.findByIpAddress
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.common.territories.enums.TerritoryCode

@Service
class GeoipLookupServiceImpl(
    private val territoryCodeByInetAddressRepository: GeoipLookupRepository,
) : GeoipLookupService {

    @Transactional(readOnly = true)
    override fun get(ipAddress: String): TerritoryCode {
        return territoryCodeByInetAddressRepository.findByIpAddress(ipAddress)
            .territoryCode
    }

}
