package io.github.donghune.calendar.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.donghune.calendar.databinding.EmotionSelectDialogBinding
import io.github.donghune.calendar.presentation.main.EmotionType

class EmotionSelectDialog(
    private val onSelect: (EmotionType) -> Unit
) : BottomSheetDialogFragment() {

    private var binding: EmotionSelectDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EmotionSelectDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding?.imageSad?.setOnClickListener {
            onSelect(EmotionType.SAD)
            dismiss()
        }
        binding?.imageHappy?.setOnClickListener {
            onSelect(EmotionType.HAPPY)
            dismiss()
        }
        binding?.imageAngry?.setOnClickListener {
            onSelect(EmotionType.ANGRY)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
