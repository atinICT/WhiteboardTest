package org.whiteboard.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import org.whiteboard.di.modules.data.AppDataManager
import org.whiteboard.di.modules.data.DataManager
import org.whiteboard.di.modules.data.pref.SharedPrefModule
import org.whiteboard.liveData.SingleLiveData
import javax.inject.Singleton

@Module(
    includes = [
        SharedPrefModule::class
    ]
)
open class AppModule {

    @Provides
    @Singleton
    open fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    open fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    open fun provideIntegerSingleLiveData(): SingleLiveData<Int> {
        return SingleLiveData()
    }


}