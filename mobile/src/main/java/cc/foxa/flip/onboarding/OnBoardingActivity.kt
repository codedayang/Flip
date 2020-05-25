package cc.foxa.flip.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.jaeger.library.StatusBarUtil
import dagger.android.support.DaggerAppCompatActivity
import cc.foxa.flip.MainActivity
import cc.foxa.flip.R
import cc.foxa.flip.databinding.ActivityOnboardingBinding
import cc.foxa.flip.util.DisplayUtils
import javax.inject.Inject

class OnBoardingActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<OnBoardingViewModel> { factory }

    lateinit var binding: ActivityOnboardingBinding

    private val fade = Fade().apply {
        duration = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DisplayUtils.setCustomDensity(this, application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            StatusBarUtil.setColor(
                this, resources.getColor(R.color.colorSecondary, theme), 0
            )
        }


        setUpEvents()
        binding.importDemoIndicator.visibility = View.INVISIBLE
    }

    private fun setUpEvents() {
        binding.toMainActivity.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP  })
            finish()
        }

        viewModel.importDemoState.observe(this, Observer {
            when (it) {
                ImportDemoState.IMPORTING -> {
                    TransitionManager.beginDelayedTransition(binding.root as ViewGroup, fade)
                    binding.importDemo.visibility = View.INVISIBLE
                    binding.importDemoIndicator.visibility = View.VISIBLE
                    binding.importDemoSuccess.visibility = View.INVISIBLE
                }

                ImportDemoState.IMPORT_SUCCESS -> {
                    TransitionManager.beginDelayedTransition(binding.root as ViewGroup, fade)
                    binding.importDemoSuccess.visibility = View.VISIBLE
                    binding.importDemoIndicator.visibility = View.INVISIBLE
                    binding.importDemoSuccess.visibility = View.VISIBLE
                }
                else -> {
                    TransitionManager.beginDelayedTransition(binding.root as ViewGroup, fade)
                    binding.importDemo.visibility = View.VISIBLE
                    binding.importDemoIndicator.visibility = View.INVISIBLE
                    binding.importDemoSuccess.visibility = View.INVISIBLE

                }
            }
        })


    }
}