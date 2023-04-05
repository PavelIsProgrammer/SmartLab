package com.petrs.smartlab

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.petrs.smartlab.databinding.FragmentDateTimeBottomSheetDialogBinding
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class DateTimeBottomSheetDialogParams(
    val onConfirm: (String) -> Unit
): Parcelable

class FragmentDateTimeBottomSheetDialog(

) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDateTimeBottomSheetDialogBinding
    private val selectableTime = listOf(
        "10:00", "13:00", "14:00", "15:00", "16:00", "18:00", "19:00", "20:00"
    )
    private val dayOfThis = listOf("Сегодня", "Завтра", "Послезавтра")
    private val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    private val daysAdapter by lazy {
        ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
    }
    private var selectedTime = ""

    private val params by lazy {
        checkNotNull(requireArguments().getParcelable<DateTimeBottomSheetDialogParams>(PARAMS))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateTimeBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChips()
        initSpinner()

        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            btnConfirm.setOnClickListener {
                params.onConfirm("${binding.spinnerDate.selectedItem as String} $selectedTime")
                dismiss()
            }
        }
    }

    private fun initSpinner() {
        binding.apply {
            val curMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
            val curDay = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
            val lastDayInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
            val adapterStrings = arrayListOf<String>()
            for ((k, i) in (curDay.toInt()..curDay.toInt() + 7).withIndex()) {
                if (k < 3) {
                    if (i <= lastDayInMonth) {
                        adapterStrings.add("${dayOfThis[k]}, $i ${months[curMonth.toInt() - 1]}")
                    } else {
                        adapterStrings.add("${dayOfThis[k]}, ${i - lastDayInMonth} ${months[curMonth.toInt()]}")
                    }
                } else {
                    if (i <= lastDayInMonth) {
                        adapterStrings.add("$i ${months[curMonth.toInt() - 1]}")
                    } else {
                        adapterStrings.add("${i - lastDayInMonth} ${months[curMonth.toInt()]}")
                    }
                }
            }
            daysAdapter.addAll(adapterStrings)

            spinnerDate.adapter = daysAdapter

            spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 > 0) reloadChips() else initChips()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun initChips() {
        binding.apply {
            val curTime = SimpleDateFormat("HH:00", Locale.getDefault()).format(Date())
            chipGroupTime.removeAllViews()
            selectableTime.subList(selectableTime.indexOf(selectableTime.find { it == curTime }) + 1, selectableTime.lastIndex).forEachIndexed { index, s ->                 val chip = layoutInflater.inflate(R.layout.chip_time, null, false) as Chip
                chip.apply {
                    text = s
                    setOnCheckedChangeListener { _, b -> if (b) selectedTime = s }
                    if (index == 0) isChecked = true
                }
                chipGroupTime.addView(chip)
            }
        }
    }

    private fun reloadChips() {
        binding.apply {
            chipGroupTime.removeAllViews()
            selectableTime.forEachIndexed { index, s ->
                val chip = layoutInflater.inflate(R.layout.chip_time, null, false) as Chip
                chip.apply {
                    text = s
                    setOnCheckedChangeListener { _, b -> if (b) selectedTime = s }
                    if (index == 0) isChecked = true
                }
                chipGroupTime.addView(chip)
            }
        }
    }

    companion object {
        const val PARAMS = "PARAMS"
        val TAG = "${FragmentDateTimeBottomSheetDialog::class.simpleName}_TAG"

        fun getInstance(params: DateTimeBottomSheetDialogParams) = FragmentDateTimeBottomSheetDialog().apply {
            arguments = bundleOf(PARAMS to params)
        }
    }
}