package com.y3.presentation.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import timber.log.Timber


inline fun Fragment.runIfViewAlive(action: () -> Unit) {
    if (this.view == null) {
        Timber.d("Fragment is null, skipping.")
        return
    }

    if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        action.invoke()
    } else {
        Timber.d("Fragment is not started, skipping.")
    }
}

fun Fragment.toast(@StringRes message: Int) {
    this.runIfViewAlive {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.toast(message: String) {
    this.runIfViewAlive {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}