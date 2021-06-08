@file:Suppress("unused")

package ru.touchin.common.geo.components

import org.locationtech.spatial4j.context.SpatialContext
import org.locationtech.spatial4j.distance.DistanceUtils
import org.locationtech.spatial4j.shape.Point
import org.springframework.stereotype.Component
import ru.touchin.common.geo.GeoCalculator
import ru.touchin.common.geo.dto.Boundary
import ru.touchin.common.geo.dto.Location
import ru.touchin.common.measure.MeasureUtils.toKilometers
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units.METRE
import javax.measure.MetricPrefix
import javax.measure.Quantity
import javax.measure.quantity.Length

@Component
class Spatial4JGeoCalculatorImpl : GeoCalculator {

    override fun getBoundary(location: Location, radius: Quantity<Length>): Boundary {
        val circle = SpatialContext.GEO.shapeFactory.circle(
            location.lon.toDouble(), // x
            location.lat.toDouble(), // y
            radius.toKilometers() * DistanceUtils.KM_TO_DEG // distance in degree
        )

        val boundary = circle.boundingBox

        return Boundary(
            minLat = boundary.minY.toBigDecimal(),
            minLon = boundary.minX.toBigDecimal(),
            maxLat = boundary.maxY.toBigDecimal(),
            maxLon = boundary.maxX.toBigDecimal()
        )
    }

    override fun getDistance(locationA: Location, locationB: Location): Quantity<Length> {
        val distanceKm = SpatialContext.GEO.calcDistance(
            locationA.toPoint(),
            locationB.toPoint()
        ) * DistanceUtils.DEG_TO_KM

        return Quantities.getQuantity(distanceKm, MetricPrefix.KILO(METRE))
    }

    private fun Location.toPoint(): Point {
        return SpatialContext.GEO.shapeFactory.pointLatLon(
            lat.toDouble(),
            lon.toDouble()
        )
    }

}
