package com.example.uasrungkad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.uasrungkad.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val load = LoadingDialog(this@RegisterActivity)
        load.startLoading()
        val hand = Handler()
        hand.postDelayed({ load.isDismiss() },5000)


        binding.btnReg.setOnClickListener {
            val nim = binding.edtNim.text.toString()
            val nama = binding.edtNama.text.toString()
            val prodi = binding.edtProdi.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPw2.text.toString()

            if (binding.edtNim.text.toString().isEmpty()) {
                binding.edtNim.error = "Isi NIM"
                Toast.makeText(this, " Isi NIM anda", Toast.LENGTH_SHORT).show()
            } else
                if (binding.edtNama.text.toString().isEmpty()) {
                    binding.edtNama.error = "Isi Nama"
                    Toast.makeText(this, " Isi Nama anda", Toast.LENGTH_SHORT).show()
                } else
                    if (binding.edtProdi.text.toString().isEmpty()) {
                        binding.edtProdi.error = "Isi Program Studi"
                        Toast.makeText(this, " Isi Program Studi anda", Toast.LENGTH_SHORT).show()
                    } else
                        if (binding.edtEmail.text.toString().isEmpty()) {
                            binding.edtEmail.error = "Isi Email"
                            Toast.makeText(this, " Isi Email anda", Toast.LENGTH_SHORT).show()
                        } else
                            if (binding.edtPw2.text.toString().isEmpty()) {
                                binding.edtPw2.error = "Isi Password"
                                Toast.makeText(this, " Isi Password anda", Toast.LENGTH_SHORT).show()
                            } else {
                                val client = ApiConfig.getApiService().postRegister(
                                    nim,
                                    nama,
                                    prodi,
                                    email,
                                    password
                                )
                                client.enqueue(object : retrofit2.Callback<Response> {
                                    override fun onFailure(call: retrofit2.Call<Response>, t: Throwable) {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "Register Gagal",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                    override fun onResponse(call: retrofit2.Call<Response>, response: retrofit2.Response<Response>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                "Register Success, Anda akan di arahkan ke halaman Login",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            val load = LoadingDialog(this@RegisterActivity)
                                            load.startLoading()
                                            val hand = Handler()
                                            hand.postDelayed({ load.isDismiss() },8000)

                                            finish()
                                        }
                                    }
                                })
                            }

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}