package com.yyy.noteapp.extensions

import android.view.View

/** Set the View visibility to INVISIBLE and eventually animate view alpha till 0% */
fun View.beVisible(animate: Boolean = false) {
    visibility = View.VISIBLE
}

/** Set the View visibility to GONE and eventually animate view alpha till 0% */
fun View.beGone(animate: Boolean = false) {
    visibility = View.GONE
}