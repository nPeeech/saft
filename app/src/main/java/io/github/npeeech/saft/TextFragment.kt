package io.github.npeeech.saft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import io.github.npeeech.saft.databinding.FragmentTextBinding

class TextFragment : Fragment() {
    private lateinit var binding: FragmentTextBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var viewModelFactory: SharedViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_text,
            container,
            false
        )

        viewModelFactory = SharedViewModelFactory("すとりんぐだよ")
        viewModel = ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)
        binding.sharedViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.parseButton.setOnClickListener { _ ->
            Toast.makeText(activity, viewModel.plainText.value, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


}