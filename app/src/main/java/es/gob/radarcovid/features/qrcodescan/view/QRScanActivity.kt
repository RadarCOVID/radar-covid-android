/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.features.qrcodescan.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import es.gob.radarcovid.R
import es.gob.radarcovid.common.base.BaseBackNavigationActivity
import es.gob.radarcovid.common.extensions.setSafeOnClickListener
import es.gob.radarcovid.features.qrcodescan.protocols.ScanQRView
import kotlinx.android.synthetic.main.activity_qr.*
import java.io.IOException


@Suppress("DEPRECATION")
class QRScanActivity : BaseBackNavigationActivity(), ScanQRView {

    companion object {
        private const val PERMISSIONS_REQUEST = 100
        const val EXTRA_QR_RESULT = "EXTRA_QR_RESULT"

        fun open(context: Context) {
            context.startActivity(Intent(context, QRScanActivity::class.java))
        }
    }

    private lateinit var barcodeDetector: BarcodeDetector

    private lateinit var cameraSource: CameraSource

    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setAutoFocusEnabled(true)
            .setRequestedPreviewSize(1600, 1024)
            .build()

        cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermission()
                    } else {
                        cameraSource.start(cameraSurfaceView.holder)
                        setupFlashButton(cameraSource)
                    }
                } catch (ie: IOException) {
                    println("CAMERA SOURCE ${ie.message}")
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}
            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    val data = barcodes.valueAt(0).displayValue
                    returnData(data)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.release()
        barcodeDetector.release()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSIONS_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate()
            } else {
                finish()
            }
        }
    }

    private fun returnData(data: String?) {
        if (data != null) {
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_QR_RESULT, data)
            setResult(RESULT_OK, resultIntent)
        } else {
            setResult(RESULT_CANCELED)
        }
        finish()
    }

    private fun setupFlashButton(cameraSource: CameraSource) {

        val camera = getCamera(cameraSource)
        if (camera != null) {

            flashButton.setSafeOnClickListener {
                try {
                    if (isFlashOn) {
                        flashButton.setBackgroundResource(R.drawable.shape_circle_border_white)
                        flashButton.setImageResource(R.drawable.ic_flash_off)
                    } else {
                        flashButton.setBackgroundResource(R.drawable.shape_circle_border_purple)
                        flashButton.setImageResource(R.drawable.ic_flash_on)
                    }
                    val param = camera.parameters
                    param.flashMode =
                        if (!isFlashOn) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF
                    camera.parameters = param
                    isFlashOn = !isFlashOn
                } catch (e: Exception) {
                }
            }
        }
    }

    private fun getCamera(cameraSource: CameraSource): Camera? {
        val declaredFields = CameraSource::class.java.declaredFields

        for (field in declaredFields) {
            if (field.type == Camera::class.java) {
                field.isAccessible = true
                try {
                    return field.get(cameraSource) as Camera
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        return null
    }

}