package com.example.mango.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mango.R
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.databinding.AuthorizationFragmentBinding

class AuthorizationFragment : Fragment() {

    private var _binding: AuthorizationFragmentBinding? = null

    private val binding get() = _binding!!

    private var viewModel: AuthorizationViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[AuthorizationViewModel::class.java]
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)


        val dropdown: Spinner = binding.spinner

        val adapter =
            CountryAdapter(
                requireContext(), R.layout.country_item, R.id.country_name,
                viewModel?.countries?.value!!
            )

        dropdown.adapter = adapter

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel?.changePhone(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        viewModel?.countries?.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }

        dropdown.setSelection(viewModel?.localeIndex ?: 0)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.phone?.observe(viewLifecycleOwner) {
            binding.phone.setText(it)
        }

//        binding.phone.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                Log.i("AAAAA", p0.toString())
//            }
//
//        })



        binding.next.setOnClickListener {
            if (!viewModel?.authorize(binding.phone.unMasked)!!) {
                binding.phone.error = getString(R.string.phone_error)
                return@setOnClickListener
            }

            findNavController().navigate(R.id.auth_to_code)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}