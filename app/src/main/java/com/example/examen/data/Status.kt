package com.example.examen.data

import com.example.examen.R

enum class Status (val title: Int, val icon: Int) {
    WANT_TO_WATCH(R.string.status_want_to_watch, R.drawable.want_to_watch_ic),
    UNFINISHED(R.string.status_unfinished, R.drawable.unfinished_ic),
    WATCHED(R.string.status_watched, R.drawable.watched_ic)
}