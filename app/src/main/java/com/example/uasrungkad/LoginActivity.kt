package com.example.uasrungkad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.uasrungkad.HomeActivity.Companion.EMAIL
import com.example.uasrungkad.HomeActivity.Companion.NAMA
import com.example.uasrungkad.HomeActivity.Companion.NIM
import com.example.uasrungkad.HomeActivity.Companion.PRODI
import com.example.uasrungkad.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val load = LoadingDialog(this@LoginActivity)
        load.startLoading()
        val hand = Handler()
        hand.postDelayed({ load.isDismiss() },3000)

        /**
         * Login
         */
        binding.btnLog.setOnClickListener {
            val nim = binding.edtNim.text.toString()
            val password = binding.edtPw.text.toString()

            if (nim.isEmpty() ) {
                binding.edtNim.error = "NIM tidak boleh kosong"
                Toast.makeText(this, "Masukan NIM",Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()){
                binding.edtPw.error = "Password tidak boleh kosong"
                Toast.makeText(this, "Masukan Password",Toast.LENGTH_SHORT).show()
            } else {
                val client = ApiConfig.getApiService().getLogin(nim,password)
                client.enqueue(object : retrofit2.Callback<Response> {
                    override fun onFailure(call: retrofit2.Call<Response>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: retrofit2.Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data
                            if (data != null) {
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                intent.putExtra(NIM, data.nim)
                                intent.putExtra(NAMA, data.nama)
                                intent.putExtra(EMAIL, data.email)
                                intent.putExtra(PRODI, data.prodi)

                                val load = LoadingDialog(this@LoginActivity)
                                load.startLoading()
                                val hand = Handler()
                                hand.postDelayed({ load.isDismiss() },5000)

                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                binding.edtNim.error = "NIM tidak ditemukan"
                            }
                        }
                    }
                })
            }
        }

        /**
         * Register
         */
        binding.btnReg.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
