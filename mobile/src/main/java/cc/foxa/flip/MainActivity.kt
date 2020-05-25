package cc.foxa.flip

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.jaeger.library.StatusBarUtil
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO:屏幕适配
//        DisplayUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            StatusBarUtil.setColor(
                this, resources.getColor(R.color.colorSecondary, theme), 0)
        }

    }
}