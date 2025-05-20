package com.example.examen.data

import com.example.examen.R

enum class Status (val title: Int, val icon: Int) {
    WANT_TO_WATCH(R.string.status_want_to_read, R.drawable.ic_status_want_to_read),
    UNFINISHED(R.string.status_reading, R.drawable.ic_status_reading),
    WATCHED(R.string.status_read, R.drawable.ic_status_read)
}