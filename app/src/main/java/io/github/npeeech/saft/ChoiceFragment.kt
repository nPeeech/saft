package io.github.npeeech.saft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val adapter = AlarmListAdapter(viewModel.alarmList)
        binding.alarmList.adapter = adapter

        return binding.root
    }

}