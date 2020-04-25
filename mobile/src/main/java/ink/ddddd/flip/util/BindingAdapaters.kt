package ink.ddddd.flip.util

import android.animation.ObjectAnimator
import android.view.View
import androidx.databinding.BindingAdapter
import ink.ddddd.flip.util.anim.MEDIUM_COLLAPSE_DURATION
import ink.ddddd.flip.util.anim.MEDIUM_EXPAND_DURATION
import ink.ddddd.flip.widget.DoubleSideCardView

@BindingAdapter("visibleWhen")
fun visibleWhen(view: View, condition: Boolean) {
    if (condition) view.visibility = View.VISIBLE
    else view.visibility = View.INVISIBLE
}

@BindingAdapter("showFront")
fun showFront(doubleSideCardView: DoubleSideCardView, condition: Boolean) {
    if (condition) doubleSideCardView.setState(DoubleSideCardView.STATE_FRONT, true)
    else doubleSideCardView.setState(DoubleSideCardView.STATE_BACK, true)
}