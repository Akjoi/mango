package com.example.mango.confirmcode.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mango.R
import com.example.mango.authorization.AuthorizationViewModel

class CodeVerifierFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_code_verifier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model  = ViewModelProvider(
            requireActivity(),
            AuthorizationViewModel.Factory(
                context = this.requireContext(),
                navController = findNavController()
            )
        )[AuthorizationViewModel::class.java]
        val code = view.findViewById<EditText>(R.id.code)
        val btn = view.findViewById<Button>(R.id.next)
        val error = view.findViewById<TextView>(R.id.error)
        btn?.setOnClickListener {
            if (code.text.toString().length < 6 ) {
                code.error = getString(R.string.code_error)
                return@setOnClickListener
            }
            model.confirmCode(code.text.toString())
        }

        model.error.observe(viewLifecycleOwner) {
            error.isVisible = it
        }
    }

}