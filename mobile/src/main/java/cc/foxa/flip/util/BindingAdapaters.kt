package cc.foxa.flip.util

import android.view.View
import androidx.databinding.BindingAdapter
import cc.foxa.flip.util.anim.MEDIUM_COLLAPSE_DURATION
import cc.foxa.flip.util.anim.MEDIUM_EXPAND_DURATION
import cc.foxa.flip.widget.DoubleSideCardView

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