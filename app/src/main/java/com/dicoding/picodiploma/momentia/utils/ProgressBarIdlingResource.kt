package com.dicoding.picodiploma.momentia.utils

import android.view.View
import androidx.test.espresso.IdlingResource

class ProgressBarIdlingResource(private val progressBar: View) : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = ProgressBarIdlingResource::class.java.name

    override fun isIdleNow(): Boolean {
        val idle = progressBar.visibility == View.GONE
        if (idle && callback != null) {
            callback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}