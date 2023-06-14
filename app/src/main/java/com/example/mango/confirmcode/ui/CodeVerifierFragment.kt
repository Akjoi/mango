package com.example.mango.confirmcode.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.mango.R
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.confirmcode.ConfirmCodeViewModel

class CodeVerifierFragment : Fragment() {
    private var _binding: CodeVerifierFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_code_verifier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity())[AuthorizationViewModel::class.java]
        val code = view.findViewById<EditText>(R.id.code)
        val btn = view.findViewById<Button>(R.id.next)
        btn?.setOnClickListener {
            model.confirmCode(code.text.toString())
        }
    }

}