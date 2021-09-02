package com.paramonov.challenge.ui.navigation

import com.github.terrakok.cicerone.Screen
import com.paramonov.challenge.data.repository.model.Category

interface IScreens {
   fun navigateToMainActivity(): Screen
   fun navigateToLoginActivity(): Screen
   fun navigateToSettingsActivity(): Screen
   fun navigateToCategoryFragment(category: Category): Screen
   fun navigateToStatisticsFragment(): Screen
   fun navigateToCategoryListFragment(): Screen
   fun navigateToCollectionFragment(): Screen
   fun navigateToPlannerFragment(): Screen
   fun navigateToSettingsFragment(): Screen
}