package ru.touchin.common.geo

import ru.touchin.common.geo.dto.Boundary
import ru.touchin.common.geo.dto.Location
import javax.measure.Quantity
import javax.measure.quantity.Length

interface GeoHelper {

    fun getBoundary(location: Location, radius: Quantity<Length>): Boundary
    fun getDistance(locationA: Location, locationB: Location): Quantity<Length>

}
