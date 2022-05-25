package ru.touchin.geoip.core.repositories

import ru.touchin.geoip.core.exceptions.GeoipLookupFailedException
import ru.touchin.geoip.core.models.GeoipLookupEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.touchin.geoip.core.configurations.GeoipCoreDatabaseConfiguration.Companion.SCHEMA

interface GeoipLookupRepository : CrudRepository<GeoipLookupEntity, Long> {

    @Query(
        """
            SELECT "location"."geoname_id" AS "geoname_id",
                   "location"."country_iso_code" AS "territory_code"
            FROM "$SCHEMA"."geoip2_network" "network"
            JOIN "$SCHEMA"."geoip2_location" "location"
              ON "network"."geoname_id" = "location"."geoname_id"
             AND "location"."locale_code" = 'en'
            WHERE "network"."network" >> CAST(:ipAddress AS inet)
        """,
        nativeQuery = true,
    )
    fun findByIpAddressOrNull(ipAddress: String): GeoipLookupEntity?

}

fun GeoipLookupRepository.findByIpAddress(ipAddress: String): GeoipLookupEntity {
    return findByIpAddressOrNull(ipAddress)
        ?: throw GeoipLookupFailedException(ipAddress)
}
