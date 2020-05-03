package ink.ddddd.flip.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import ink.ddddd.flip.R
import ink.ddddd.flip.util.anim.MEDIUM_COLLAPSE_DURATION
import ink.ddddd.flip.util.anim.MEDIUM_EXPAND_DURATION
import ink.ddddd.flip.util.anim.fadeThrough

class DoubleSideCardView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attr, defStyleAttr) {

    private lateinit var front: View
    private lateinit var back: View

    private var state: Int = 0
    private val transition = fadeThrough()


    override fun onFinishInflate() {
        super.onFinishInflate()
        front = findViewById(R.id.front)
        back = findViewById(R.id.back)
    }
    @Suppress("DEPRECATION")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //max height for perform scene
        val maxHeight = display.height / 3 * 2
        var h: Int = heightMeasureSpec
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            h = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, h)
    }

    fun setState(@State state: Int, animated: Boolean) {
        if (animated) {
            if (state == STATE_FRONT) {
                transition.duration = MEDIUM_EXPAND_DURATION
                TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
                front.visibility = View.VISIBLE
                back.visibility = View.GONE
            } else {
                transition.duration = MEDIUM_COLLAPSE_DURATION
                TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
                front.visibility = View.GONE
                back.visibility = View.VISIBLE
            }
        } else {
            if (state == STATE_FRONT) {
                front.visibility = View.VISIBLE
                back.visibility = View.GONE
            } else {
                front.visibility = View.GONE
                back.visibility = View.VISIBLE
            }
        }
        this.state = state

    }

    fun getState() = state


    companion object {
        const val STATE_FRONT = 0
        const val STATE_BACK = 1
    }

    @IntDef(STATE_FRONT, STATE_BACK)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class State
}