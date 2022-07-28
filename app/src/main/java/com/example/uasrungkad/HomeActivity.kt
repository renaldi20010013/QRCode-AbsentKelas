package com.example.uasrungkad

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uasrungkad.databinding.ActivityHomeBinding
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var nim = ""


    private val cr = "https://classroom.google.com/"
    private val meet = "https://meet.google.com/"

    companion object {
        const val NIM = "nim"
        const val NAMA = "nama"
        const val PRODI = "prodi"
        const val EMAIL = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nim = intent.getStringExtra(NIM).toString()

        val nama = intent.getStringExtra(NAMA)
        val prodi = intent.getStringExtra(PRODI)
        val email = intent.getStringExtra(EMAIL)

        Toast.makeText(
            this,
            "NIM : $nim\nNama : $nama\nProdi : $prodi\nEmail : $email",
            Toast.LENGTH_LONG
        ).show()

        binding.btnMeet.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(meet))
            startActivity(intent)
        }

        binding.btnJd.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(cr))
            startActivity(intent)
        }

        binding.btnAbs.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.captureActivity = ScanViewActivity::class.java
            integrator.setOrientationLocked(false)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt("Scanning...")
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                    val client = ApiConfig.getApiService().postScan(nim = nim, token = result.contents)
                    client.enqueue(object : retrofit2.Callback<Response> {
                        override fun onResponse(
                            call: Call<Response>, response: retrofit2.Response<Response>
                        ) {
                            Toast.makeText(
                                this@HomeActivity,
                                "Absen Berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: Call<Response>, t: Throwable) {
                            Toast.makeText(
                                this@HomeActivity,
                                "Absen Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}