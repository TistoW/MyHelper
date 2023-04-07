package com.inyongtisto.helpers.printExample

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.inyongtisto.helpers.R
import com.inyongtisto.helpers.databinding.ItemSelectPrinterBinding
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible

class PrinterAdapter(
    var onItemClicked: ((printer: PrinterDevice) -> Unit)? = null,
    var onTestPrintClicked: ((printer: PrinterDevice) -> Unit)? = null
) : ListAdapter<PrinterDevice, PrinterAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var selectedPrinter: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSelectPrinterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    fun selectedPrinter(uuid: String? = null) {
        selectedPrinter = uuid
    }

    inner class ViewHolder(private val itemBinding: ItemSelectPrinterBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(printer: PrinterDevice) {
            with(itemBinding) {
                val context = root.context
                tvPrinterName.text = printer.printerName
                tvPrinterType.text = printer.address
                btnConnect.apply {
                    if (printer.address == selectedPrinter) {
                        toVisible()
                        setBackgroundColorResource(R.color.green3)
                        text = "Connected"
                        setTextColorResource(R.color.white)
                        strokeWidth = 0
                    } else {
                        toGone()
                        text = "Connect"
                        setIconResource(0)
                        setTextColorResource(R.color.colorPrimary)
                    }
                }
                lyMain.setOnClickListener {
                    onItemClicked?.invoke(printer)
//                    if (printer.uniqueId != selectedPrinter) {
//                        selectedPrinter = printer.uniqueId
//                        notifyItemRangeChanged(0, itemCount)
//                    } else {
//                        root.context.toastSimple(root.context.getString(R.string.device_in_use_please_choose_a_different_device))
//                    }
                }
            }
        }
    }


    fun MaterialButton.setBackgroundColorResource(id: Int) {
        setBackgroundColor(ContextCompat.getColor(context, id))
    }

    fun MaterialButton.setTextColorResource(id: Int) {
        setTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, id)))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PrinterDevice>() {
            override fun areItemsTheSame(oldItem: PrinterDevice, newItem: PrinterDevice): Boolean {
                return oldItem.printerName == newItem.printerName
            }

            override fun areContentsTheSame(oldItem: PrinterDevice, newItem: PrinterDevice): Boolean {
                return oldItem == newItem
            }
        }
    }
}