package com.example.mygames

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable
