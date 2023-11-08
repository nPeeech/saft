package io.github.npeeech.saft

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.github.npeeech.saft.databinding.FragmentChoiceBinding

class ChoiceFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentChoiceBinding
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_choice,
            container,
            false
        )
        binding.sharedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.richTextView.movementMethod = LinkMovementMethod.getInstance()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val alarmOffsetDefaultValue = resources.getInteger(R.integer.alarmOffsetDefaultValue)
        viewModel.setDeltaTimeList(
            resources.getIntArray(R.array.offset_time_integer_array).toList()
        )
        viewModel.deltaTimePosition.value =
            sharedPref?.getInt(getString(R.string.alarmOffsetKey), alarmOffsetDefaultValue)

        val spinner: Spinner = binding.alarmOffsetSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.offset_time_string_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            viewModel.deltaTimePosition.value?.let { spinner.setSelection(it) }
            spinner.onItemSelectedListener = this
        }

        viewModel.deltaTimePosition.observe(viewLifecycleOwner) { position ->
            if (sharedPref != null) {
                with(sharedPref.edit()) {
                    putInt(getString(R.string.alarmOffsetKey), position)
                    apply()
                }
            }
        }

        viewModel.setAlarmPosition.observe(viewLifecycleOwner) { position ->
            if (position != -1) {
                val alarmTime = viewModel.calcAlarm(position)
                val hour = alarmTime.hour
                val minutes = alarmTime.minute
                val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                    putExtra(AlarmClock.EXTRA_HOUR, hour)
                    putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                    putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                }
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    Toast.makeText(
                        activity,
                        "${resources.getString(R.string.set_alarm_complete_message)} $alarmTime",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        activity,
                        R.string.set_alarm_failed_message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewModel.onSetAlarmPositionComplete()
            }
        }
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.deltaTimePosition.value = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}