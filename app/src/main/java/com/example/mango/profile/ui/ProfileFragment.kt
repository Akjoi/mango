package com.example.mango.profile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mango.R
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.databinding.FragmentProfileBinding
import com.example.mango.profile.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var model: ProfileViewModel? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        model = ViewModelProvider(
            this,
        )[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        model?.user?.observe(viewLifecycleOwner) {
            it.apply {
                binding.name.text = name
                binding.phone.text = phone
                binding.username.text = username
                binding.birthday.text = birthday
                binding.city.text = city
                binding.instagram.text = instagram
                binding.vk.text = vk
                binding.status.text = status
                binding.created.text = created
                binding.completedTask.text = completedTask.toString()
            }
        }

        binding.changeProfile.setOnClickListener {
            findNavController().navigate(R.id.profile_to_change)
        }
    }

}