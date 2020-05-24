package cc.foxa.flip.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import cc.foxa.flip.MainActivity
import cc.foxa.flip.R
import cc.foxa.flip.onboarding.OnBoardingActivity
import cc.foxa.flip.shared.EventObserver
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