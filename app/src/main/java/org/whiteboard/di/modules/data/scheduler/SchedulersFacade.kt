package org.whiteboard.di.modules.data.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersFacade @Inject
constructor() {

    /**
     * IO thread pool scheduler
     */
    fun io(): Scheduler {
        return Schedulers.io()
    }

    /**
     * Computation thread pool scheduler
     */
    fun computation(): Scheduler {
        return Schedulers.computation()
    }

    fun single(): Scheduler {
        return Schedulers.single()
    }

    /**
     * Main Thread scheduler
     */
    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}