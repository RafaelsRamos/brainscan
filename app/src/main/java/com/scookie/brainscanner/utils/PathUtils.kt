package com.scookie.brainscanner.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Constraints
import kotlin.math.abs

fun Constraints.drawPathWith(points: List<Offset>): Path {

    return Path().apply {

        val firstPoint = points.first()
        moveTo(firstPoint.x, firstPoint.y)

        for (index in 1 until  points.size){
            standardQuadFromTo(points[index - 1], points[index])
        }

        lineTo(maxWidth.toFloat() + 100F, maxHeight + 100F)
        lineTo(-100f, maxHeight.toFloat() + 100f)
        close()

    }

}

private fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}