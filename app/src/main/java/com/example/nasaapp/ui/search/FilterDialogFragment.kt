package com.example.nasaapp.ui.search

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog

class FilterDialogFragment : DialogFragment() {

    private var listener: ((String) -> Unit)? = null

    fun setOnFilterSelectedListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val types = arrayOf("Image", "Video", "Audio")
        return AlertDialog.Builder(requireContext())
            .setTitle("Select Data Type")
            .setItems(types) { _, which ->
                val selectedType = types[which]
                listener?.invoke(selectedType)
            }
            .create()
    }
}