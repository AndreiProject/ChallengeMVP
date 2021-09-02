package com.paramonov.challenge.ui.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.*
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ActivityMainBinding
import com.paramonov.challenge.domain.authorization.*
import com.paramonov.challenge.ui.feature.main.MainPresenterContract.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class MainActivity : MvpAppCompatActivity(), View, OnNavigationItemSelectedListener {

    private var navController: NavController? = null
    private var binding: ActivityMainBinding? = null
    private val mBinding get() = binding!!

    private val navigatorHolder: NavigatorHolder by inject(NavigatorHolder::class.java)
    private val navigator = AppNavigator(this, R.id.container)

    private val useCase: AuthorizationUseCaseContract by inject(AuthorizationUseCase::class.java)
    private val presenter: Presenter by moxyPresenter {
        MainPresenter(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.toolbar.setTitle(R.string.nav_category_list)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun init() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        mBinding.navView.setNavigationItemSelectedListener(this)
        initBarDrawerToggle()
    }

    private fun initBarDrawerToggle() {
        val drawer = mBinding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            mBinding.toolbar,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_statistics -> {
                mBinding.toolbar.setTitle(R.string.nav_statistics)
                presenter.navigateToStatistics()
            }
            R.id.nav_collection -> {
                mBinding.toolbar.setTitle(R.string.nav_collection)
                presenter.navigateToCollection()
            }
            R.id.nav_category_list -> {
                mBinding.toolbar.setTitle(R.string.nav_category_list)
                presenter.navigateToCategoryList()
            }
            R.id.nav_planner -> {
                mBinding.toolbar.setTitle(R.string.nav_planner)
                presenter.navigateToPlanner()
            }
            R.id.nav_arithmetic -> {
            }
            else -> throw RuntimeException("Item not found")
        }
        val drawer = mBinding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_log_out -> {
                presenter.logOut()
                true
            }
            R.id.action_settings -> {
                mBinding.toolbar.setTitle(R.string.nav_settings)
                presenter.navigateToSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val driver = mBinding.drawerLayout
        if (driver.isDrawerOpen(GravityCompat.START)) {
            driver.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}