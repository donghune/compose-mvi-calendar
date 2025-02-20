package io.github.donghune.calendar.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    protected var binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObserves()
        setListeners()
    }

    open fun initViews() {}
    open fun setObserves() {}
    open fun setListeners() {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}