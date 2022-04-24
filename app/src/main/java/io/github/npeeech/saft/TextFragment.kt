package io.github.npeeech.saft

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        // 外部からテキストを受け取る
        val receiveText = activity?.intent?.getStringExtra(Intent.EXTRA_TEXT) ?:""

        viewModelFactory = SharedViewModelFactory(receiveText)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(SharedViewModel::class.java)
        binding.sharedViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        binding.parseButton.setOnClickListener { _ ->
            Toast.makeText(activity, viewModel.plainText.value, Toast.LENGTH_SHORT).show()
        }

        viewModel.eventParse.observe(viewLifecycleOwner, Observer { parse ->
            if (parse){
                if(binding.inputPlainText.text.isNullOrBlank()){
                    Toast.makeText(activity, R.string.empty_input_text_message, Toast.LENGTH_SHORT).show()
                }else {
                    // TODO viewModel.parse()のようなものを呼ぶ
                    findNavController().navigate(TextFragmentDirections.actionTextFragmentToChoiceFragment())
                    viewModel.onParseComplete()
                }
            }
        })

        return binding.root
    }


}