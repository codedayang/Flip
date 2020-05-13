package ink.ddddd.flip

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import dagger.android.support.DaggerAppCompatActivity
import ink.ddddd.flip.util.DisplayUtils

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO:屏幕适配
//        DisplayUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        StatusBarUtil.setColor(
            this, resources.getColor(R.color.colorSecondary, theme), 0)
    }
}