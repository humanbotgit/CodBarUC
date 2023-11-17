package com.example.codbaruc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.codbaruc.databinding.ActivityCamaraBinding

class Camara : AppCompatActivity() {
    private var requestCamara:ActivityResultLauncher<String>?=null;
    private lateinit var binding : ActivityCamaraBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestCamara = registerForActivityResult(ActivityResultContracts.
        RequestPermission(),){
            if(it){

            }else{
                Toast.makeText(this, "Permiso no otorgado",
                    Toast.LENGTH_SHORT).show();
            }
        }
        binding.btnBc.setOnClickListener(){
            requestCamara?.launch(android.Manifest.permission.CAMERA)
        }
    }
}