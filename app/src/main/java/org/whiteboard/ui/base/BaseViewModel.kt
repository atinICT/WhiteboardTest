package org.whiteboard.ui.base

import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.whiteboard.R
import org.whiteboard.di.modules.data.DataManager
import org.whiteboard.di.modules.data.pref.PreferencesManager
import org.whiteboard.di.modules.data.scheduler.SchedulersFacade
import retrofit2.adapter.rxjava2.Result
import timber.log.Timber

open class BaseViewModel(
    val mDataManager: DataManager,
    val mCompositeDisposable: CompositeDisposable,
    val mSchedulersFacade: SchedulersFacade
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var isCallingApi = false



    fun addDisposable(disposable: Disposable) {
        this.mCompositeDisposable.add(disposable)
    }

    fun getSharedPreferences(): PreferencesManager {
        return mDataManager.preferencesManager
    }




    override fun onCleared() {
        this.mCompositeDisposable.dispose()
        this.mCompositeDisposable.clear()
        super.onCleared()
    }
}
