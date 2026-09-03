package com.example.aura.ui.navigation

import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey

sealed interface Route : NavKey {
    @Serializable
    data object Timeline : Route

    @Serializable
    data object Composer : Route
}
