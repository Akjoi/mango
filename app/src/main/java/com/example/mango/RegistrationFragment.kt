package com.example.mango

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.databinding.RegistrationFragmentBinding


class RegistrationFragment : Fragment() {

    private var _binding: RegistrationFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val model = ViewModelProvider(
            requireActivity(),
            AuthorizationViewModel.Factory(
                context = this.requireContext(),
                navController = findNavController()
            )
        )[AuthorizationViewModel::class.java]
        _binding = RegistrationFragmentBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            model.registerUser(binding.name.text.toString(), binding.username.text.toString())
        }

        binding.phone.text = model.unmaskedPhone
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}