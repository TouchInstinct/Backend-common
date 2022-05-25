package ru.touchin.geoip.core.models

import ru.touchin.common.territories.enums.TerritoryCode
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
class GeoipLookupEntity {

    @Id
    var geonameId: Long? = null

    @Enumerated(EnumType.STRING)
    lateinit var territoryCode: TerritoryCode

}
