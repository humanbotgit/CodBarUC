package com.example.codbaruc.View.ui.theme

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import com.example.codbaruc.R
import com.example.codbaruc.databinding.ActivityScanBarCodeBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

class ScanBarCode : AppCompatActivity() {
    private lateinit var binding: ActivityScanBarCodeBinding;
    private lateinit var barcodeDetector: BarcodeDetector;
    private lateinit var cameraSource: CameraSource;
    var intentData = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarCodeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_scan_bar_code)
    }
    private fun iniBc(){
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this,barcodeDetector)
            .setRequestedPreviewSize(1920,1080)
            .setAutoFocusEnabled(true)
            //.setFacing(CameraSource.CAMERA_FACING_FRONT)
            .build()
        binding.surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback{
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    cameraSource.start(binding.surfaceView!!.holder)
                }catch (e:IOException){
                    e.printStackTrace()
                }
            }
            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }

        })
        barcodeDetector.setProcessor(object :Detector.Processor<Barcode>{
            override fun release() {
                Toast.makeText(applicationContext,"lector de código de barras se ha detenido",
                    Toast.LENGTH_SHORT).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                var barcodes = detections.detectedItems
                if(barcodes.size()!=0){
                    binding.txtBarCodeValue!!.post{
                        binding.btnAction!!.text = "Buscar ITEM"
                        intentData = barcodes.valueAt(0).displayValue
                        binding.txtBarCodeValue.setText(intentData)
                        finish()
                    }
                }else{

                }
            }

        })
    }
    override fun onPause(){
        super.onPause()
        cameraSource!!.release()
    }
    override fun onResume(){
        super.onResume()
        iniBc()
    }

}