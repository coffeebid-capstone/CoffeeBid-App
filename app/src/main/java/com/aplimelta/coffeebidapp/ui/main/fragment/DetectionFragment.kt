package com.aplimelta.coffeebidapp.ui.main.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aplimelta.coffeebidapp.databinding.FragmentDetectionBinding
import com.aplimelta.coffeebidapp.ui.main.activity.CameraActivity
import com.aplimelta.coffeebidapp.utils.rotateFile
import java.io.File

class DetectionFragment : Fragment() {

    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding

    private var getFile: File? = null

    private val launcherRequestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        val isGranted = permission.entries.all { it.value }

        if (!isGranted) {
            Toast.makeText(requireActivity(), "Permission denied", Toast.LENGTH_LONG).show()
            requireActivity().finishAffinity()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                (it.data?.getSerializableExtra("picture"))
            } as File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding?.sivPhotoPreview?.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            if (!allPermissionsGranted()) {
                makePermission()
            }

            binding?.apply {
                btnCamera.setOnClickListener { cameraX() }
            }
        }
    }

    private fun cameraX() {
        val intent = Intent(requireActivity(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun makePermission() {
        launcherRequestPermission.launch(MAKE_REQUIRED_PERMISSION)
    }

    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                activity as AppCompatActivity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSION = mutableListOf(
            Manifest.permission.CAMERA
        )
        private const val REQUEST_CODE_PERMISSION = 10
        private val MAKE_REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }
}