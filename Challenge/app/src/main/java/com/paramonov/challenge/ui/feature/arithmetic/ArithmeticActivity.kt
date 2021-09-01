package com.paramonov.challenge.ui.feature.arithmetic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paramonov.challenge.databinding.ActivityArithmeticBinding

class ArithmeticActivity : AppCompatActivity() {
    private var binding: ActivityArithmeticBinding? = null
    private val mBinding get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArithmeticBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}