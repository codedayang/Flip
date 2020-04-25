package ink.ddddd.flip.widget

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter constructor(
    private val views: List<View>,
    private val titles: List<String>
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun isViewFromObject(view: View, o: Any) = view === o

    override fun getCount() = views.size

}