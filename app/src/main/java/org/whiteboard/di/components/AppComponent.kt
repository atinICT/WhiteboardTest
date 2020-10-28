package org.whiteboard.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import org.whiteboard.App
import org.whiteboard.di.modules.ActivityInjectorsModule
import org.whiteboard.di.modules.AppModule
import org.whiteboard.di.modules.ServiceInjectorsModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        ServiceInjectorsModule::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)

}

