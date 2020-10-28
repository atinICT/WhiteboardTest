package org.whiteboard.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.jhoobin.amaroidsdk.Amaroid
import org.whiteboard.BuildConfig
import org.whiteboard.R
import timber.log.Timber
import java.util.*


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity() {

    private var disposable: Disposable? = null

    lateinit var binding: T
    var showActionBar = true
    abstract val bindingVariable: Int?

    open var isHandleError = true

    open var isHandleNetworkConnectivity = true

    open var viewModel: V? = null
        set(value) {
            field = value
            bindingVariable()
        }

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun updateShowActionBarValue() {
        showActionBar = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        if (showActionBar) {
            if (Build.VERSION.SDK_INT in 19..20) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
            if (Build.VERSION.SDK_INT >= 19) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
        super.onCreate(savedInstanceState)

        performDataBinding()

    }



    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    fun bindingVariable() {
        bindingVariable?.let {
            binding.setVariable(it, viewModel)
        }
        binding.executePendingBindings()
    }



    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        fragments.takeIf { it.isNotEmpty() }
            ?.let { it[it.size - 1]?.onActivityResult(requestCode, resultCode, data) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragments = supportFragmentManager.fragments
        fragments.takeIf { it.isNotEmpty() }?.let {
            it[it.size - 1].onRequestPermissionsResult(
                requestCode and 0xff,
                permissions,
                grantResults
            )
        }
    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {

        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

}
