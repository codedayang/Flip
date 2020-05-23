package ink.ddddd.flip.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeger.library.StatusBarUtil
import dagger.android.support.DaggerAppCompatActivity
import ink.ddddd.flip.MainActivity
import ink.ddddd.flip.R
import ink.ddddd.flip.onboarding.OnBoardingActivity
import ink.ddddd.flip.shared.EventObserver
import javax.inject.Inject

class LauncherActivity : DaggerAppCompatActivity() {
    @Inject lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<LauncherViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.launchDestination.observe(this, EventObserver {
            when (it) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                LaunchDestination.ONBOARDING -> startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            overridePendingTransition(0, R.anim.fade_out_short)
            finish()
        })
    }
}