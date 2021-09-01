package com.paramonov.challenge.ui.feature.main

import androidx.navigation.NavController

interface NavigationView {

    interface Item {
        fun navigateToStatistics()
        fun navigateToCollection()
        fun navigateToCategoryList()
        fun navigateToPlanner()
        fun navigateToSettings()
    }

    interface ControllerProvider {
        fun getNavController(): NavController?
    }
}