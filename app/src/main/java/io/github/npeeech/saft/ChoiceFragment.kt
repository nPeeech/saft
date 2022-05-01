package io.github.npeeech.saft

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import io.github.npeeech.saft.databinding.FragmentChoiceBinding

class ChoiceFragment : Fragment(), AdapterView.OnItemSelectedListener {
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
        viewModel.deltaTimeList = resources.getIntArray(R.array.offset_time_integer_array).toList()
        viewModel.deltaTimePosition.value = sharedPref?.getInt(getString(R.string.alarmOffsetKey), alarmOffsetDefaultValue)


        val adapter = AlarmListAdapter(listOf())
        binding.alarmList.adapter = adapter


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

        viewModel.deltaTimePosition.observe(viewLifecycleOwner, androidx.lifecycle.Observer { position ->
            viewModel.alarmList.value?.let {
                adapter.setList(it.map { time ->
                    val deltaTime = viewModel.deltaTimeList[position]
                    time.plusMinutes(deltaTime.toLong())
                })
            }
            Log.i("observe", "viewModel.alarmOffsetTimeが変更された")
        })


        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.deltaTimePosition.value = position
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putInt(getString(R.string.alarmOffsetKey), position)
            apply()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}