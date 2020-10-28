package org.whiteboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdaggerAnnotations.BindModule
import org.whiteboard.databinding.ActivityMainBinding
import org.whiteboard.di.InjectionViewModelProvider
import org.whiteboard.ui.base.BaseActivity
import javax.inject.Inject

@BindModule
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    @Inject
    lateinit var mViewModelFactoryActivity: InjectionViewModelProvider<MainActivityViewModel>

    override val bindingVariable: Int? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = mViewModelFactoryActivity.get(this, MainActivityViewModel::class)
        binding.btnEVent.setOnClickListener {
            viewModel?.mDataManager?.eventManager?.setEvent("lplan_best_plan_c")
        }
    }
}