package com.example.memoryandbraintraining


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Инициализация объекта FirebaseAuth
        auth = FirebaseAuth.getInstance()


        sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)


        // Находим вьюхи
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        btnLogin = view.findViewById(R.id.btn_login)

        // Обработчик нажатия на кнопку входа
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Аутентификация пользователя через Firebase
            if(email.isNotEmpty() && password.isNotEmpty() ) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Успешный вход
                            Toast.makeText(requireContext(), "Вход успешен", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(requireContext(), MainMenu::class.java))
                            requireActivity().finish()

                        } else {
                            // Ошибка входа
                            Toast.makeText(
                                requireContext(),
                                "Ошибка входа: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        return view
    }


}