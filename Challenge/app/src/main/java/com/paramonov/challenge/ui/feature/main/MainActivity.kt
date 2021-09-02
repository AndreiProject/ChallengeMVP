package com.paramonov.challenge.ui.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.*
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.ActivityMainBinding
import com.paramonov.challenge.domain.authorization.*
import com.paramonov.challenge.ui.feature.login.LoginActivity
import com.paramonov.challenge.ui.feature.main.MainPresenterContract.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.java.KoinJavaComponent.inject

class MainActivity : MvpAppCompatActivity(), View, OnNavigationItemSelectedListener,
    NavigationView.ControllerProvider  {

    private var navController: NavController? = null
    private var binding: ActivityMainBinding? = null
    private val mBinding get() = binding!!

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

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToSettings() {
        val navigationItem = getNavigationItemFragment()
        navigationItem?.navigateToSettings()
    }

    override fun getNavController() = navController

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navigationItem = getNavigationItemFragment()

        when (item.itemId) {
            R.id.nav_statistics -> {
                mBinding.toolbar.setTitle(R.string.nav_statistics)
                navigationItem?.navigateToStatistics()
            }
            R.id.nav_collection -> {
                mBinding.toolbar.setTitle(R.string.nav_collection)
                navigationItem?.navigateToCollection()
            }
            R.id.nav_category_list -> {
                mBinding.toolbar.setTitle(R.string.nav_category_list)
                navigationItem?.navigateToCategoryList()
            }
            R.id.nav_planner -> {
                mBinding.toolbar.setTitle(R.string.nav_planner)
                navigationItem?.navigateToPlanner()
            }
            R.id.nav_arithmetic -> {
            }
            else -> throw RuntimeException("Item not found")
        }

        val drawer = mBinding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getNavigationItemFragment(): NavigationView.Item? {
        supportFragmentManager.fragments.forEach {
            val fragment = it.childFragmentManager.fragments.first()
            return fragment as? NavigationView.Item
        }
        return null
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