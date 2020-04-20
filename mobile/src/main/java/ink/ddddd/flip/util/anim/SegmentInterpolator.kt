package ink.ddddd.flip.util.anim

import android.animation.TimeInterpolator

/**
 * Takes a [base] interpolator and extracts out a segment from it as a new [TimeInterpolator].
 *
 * This is useful for sequential animations where each of the child animations should be
 * interpolated so that they match with another animation when combined.
 */
class SegmentInterpolator(
    val base: TimeInterpolator,
    val start: Float = 0f,
    val end: Float = 1f
) : TimeInterpolator {

    private val offset = base.getInterpolation(start)
    private val xRatio = (end - start) / 1f
    private val yRatio = (base.getInterpolation(end) - offset) / 1f

    override fun getInterpolation(input: Float): Float {
        return (base.getInterpolation(start + (input * xRatio)) - offset) / yRatio
    }
}
