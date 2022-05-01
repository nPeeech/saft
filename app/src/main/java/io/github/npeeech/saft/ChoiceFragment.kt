package io.github.npeeech.saft

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import io.github.npeeech.saft.databinding.FragmentChoiceBinding

class ChoiceFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentChoiceBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_choice,
            container,
            false
        )
        binding.sharedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val alarmOffsetDefaultValue = resources.getInteger(R.integer.alarmOffsetDefaultValue)
        val alarmOffsetValue =
            sharedPref?.getInt(getString(R.string.alarmOffsetKey), alarmOffsetDefaultValue)

        val adapter = AlarmListAdapter(viewModel.alarmList)
        binding.alarmList.adapter = adapter

        val spinner: Spinner = binding.alarmOffsetSpinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.offset_time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            alarmOffsetValue?.let { spinner.setSelection(it) }
        }

        return binding.root
    }

}