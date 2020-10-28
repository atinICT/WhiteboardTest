package org.whiteboard

import io.reactivex.disposables.CompositeDisposable
import org.whiteboard.di.modules.data.DataManager
import org.whiteboard.di.modules.data.scheduler.SchedulersFacade
import org.whiteboard.ui.base.BaseViewModel

class MainActivityViewModel (
    dataManager: DataManager,
    compositeDisposable: CompositeDisposable,
    schedulersFacade: SchedulersFacade
) : BaseViewModel(dataManager, compositeDisposable, schedulersFacade) {
}