package com.example.weather_fragments.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weather_fragments.R

class WeatherDialogFragment : DialogFragment(){
    var onPositiveClickListener: DialogInterface.OnClickListener? = null
    var onNegativeClickListener: DialogInterface.OnClickListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Выберете вариант отображения погоды")
            .setPositiveButton("Кратко") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, WeatherShortFragment())
                    .commitAllowingStateLoss()
                onPositiveClickListener?.onClick(dialog, which)
                dismiss()
            }
            .setNegativeButton("Подробно") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, WeatherFullFragment())
                    .commitAllowingStateLoss()
                onNegativeClickListener?.onClick(dialog, which)
                dismiss()
            }
            .create()
            .apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
    }
}