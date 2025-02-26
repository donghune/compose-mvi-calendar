package io.github.donghune.calendar.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> T
) : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initViews()
        setObserves()
        setListeners()
    }

    open fun initViews() {}
    open fun setObserves() {}
    open fun setListeners() {}
}

