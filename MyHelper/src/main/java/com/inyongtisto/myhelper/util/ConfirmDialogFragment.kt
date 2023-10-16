package com.inyongtisto.myhelper.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inyongtisto.myhelper.base.BaseDialog
import com.inyongtisto.myhelper.databinding.ViewDialogConfirmBinding
import com.inyongtisto.myhelper.extension.visible
import io.reactivex.functions.Cancellable

class ConfirmDialogFragment(
    private val title: String,
    private val subtitle: String,
    private val actionText: String = "Ok",
    private val actionTextSecondary: String? = null,
    private val cancellable: Boolean = true,
    percentage: Int = 80,
    private val onClose: (() -> Unit)? = null,
    private val onActionSecondary: (() -> Unit)? = null,
    private val onAction: (() -> Unit)? = null
) : BaseDialog(percentage) {

    private var _binding: ViewDialogConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewDialogConfirmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupUI()
        setupClickListeners()
    }

    private fun init() {
        isCancelable = cancellable
    }

    private fun setupUI() {
        binding.apply {
            tvTitle.text = title
            tvSubtitle.text = subtitle
            tvConifrm.text = actionText
            tvActionSecondary.text = actionTextSecondary
            lySecondary.visible(!actionTextSecondary.isNullOrEmpty())
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            btnClose.visible(isCancelable)
            btnClose.setOnClickListener {
                onClose?.invoke()
                dismiss()
            }

            btnConfirm.setOnClickListener {
                onAction?.invoke()
                dismiss()
            }

            btnActionSecondary.setOnClickListener {
                onActionSecondary?.invoke()
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "ProductOptionDialogFragment"
    }
}