package com.paramonov.challenge.ui.navigation

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.*
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.ui.feature.category.CategoryFragment
import com.paramonov.challenge.ui.feature.category_list.CategoryListFragment
import com.paramonov.challenge.ui.feature.collection.CollectionFragment
import com.paramonov.challenge.ui.feature.login.LoginActivity
import com.paramonov.challenge.ui.feature.main.MainActivity
import com.paramonov.challenge.ui.feature.planner.PlannerFragment
import com.paramonov.challenge.ui.feature.settings.SettingsFragment
import com.paramonov.challenge.ui.feature.view_pager_container_statistics_fragment.ViewPagerContainerStatisticsFragment

class AndroidScreens : IScreens {

    override fun navigateToCategoryFragment(category: Category): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
                return CategoryFragment.newInstance(category)
            }
        })
    }

    override fun navigateToStatisticsFragment(): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
              return ViewPagerContainerStatisticsFragment.newInstance()
            }
        })
    }

    override fun navigateToCategoryListFragment(): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
                return CategoryListFragment.newInstance()
            }
        })
    }

    override fun navigateToCollectionFragment(): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
                return CollectionFragment.newInstance()
            }
        })
    }

    override fun navigateToPlannerFragment(): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
                return PlannerFragment.newInstance()
            }
        })
    }

    override fun navigateToSettingsFragment(): Screen {
        return FragmentScreen(null, object : Creator<FragmentFactory, Fragment> {
            override fun create(argument: FragmentFactory): Fragment {
                return SettingsFragment.newInstance()
            }
        })
    }

    override fun navigateToMainActivity(): Screen {
        return ActivityScreen(null, object : Creator<Context, Intent> {
            override fun create(context: Context): Intent {
                return Intent(context, MainActivity::class.java)
            }
        })
    }

    override fun navigateToLoginActivity(): Screen {
        return ActivityScreen(null, object : Creator<Context, Intent> {
            override fun create(context: Context): Intent {
                return Intent(context, LoginActivity::class.java)
            }
        })
    }

    override fun navigateToSettingsActivity(): Screen {
        return ActivityScreen(null, object : Creator<Context, Intent> {
            override fun create(context: Context): Intent {
                return Intent(Settings.ACTION_SETTINGS)
            }
        })
    }
}