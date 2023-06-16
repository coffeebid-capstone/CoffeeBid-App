package com.aplimelta.coffeebidapp.ui.main.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.databinding.FragmentDetectionBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.main.activity.CameraActivity
import com.aplimelta.coffeebidapp.utils.load

class DetectionFragment : Fragment() {

    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding

    private var getFile: String? = null

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

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
            val myFile = it.data?.getStringExtra("picture") as String
            Log.d("Detection", "dalam myfile $myFile")

            myFile.let { file ->
                getFile = file
                binding?.sivPhotoPreview?.load(file)
                Log.d("Detection", getFile as String)

                binding?.btnUpload?.setOnClickListener { predictQuality(getFile as String) }
                binding?.btnRosting?.setOnClickListener { predictRoast(getFile as String) }
            }

//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

//            myFile.let { file ->
//                rotateFile(file, isBackCamera)
//                getFile = reduceFileImage(file)
//                binding?.sivPhotoPreview?.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
        }
    }

//    private val launcherIntentGallery = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == AppCompatActivity.RESULT_OK) {
//            val selectedImg: Uri = it.data?.data as Uri
//            val myFile = uriToFile(selectedImg, requireActivity())
//
//            getFile = myFile
//            binding?.sivPhotoPreview?.setImageURI(selectedImg)
//        }
//    }

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
//            if (!allPermissionsGranted()) {
//                makePermission()
//            }

            binding?.apply {
                btnCamera.setOnClickListener { cameraX() }
                Log.d("Detection", "onViewCreated: $getFile")

                if (getFile != null) {

                    btnUpload.setOnClickListener { predictQuality(getFile as String) }
                    btnRosting.setOnClickListener { predictRoast(getFile as String) }
                } else {
                    Toast.makeText(requireActivity(), "Upload Image Failed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun predictRoast(file: String) {
        viewModel.predictRoast(file).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG)
                        .show()
                }

                is Result.Success -> {
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil Upload Image ${result.data}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding?.tvInfoDetection?.text = result.data
                    binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                }

                else -> {
                    binding?.progressBar?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun predictQuality(file: String) {
        viewModel.predictQuality(file).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG)
                        .show()
                }

                is Result.Success -> {
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil Upload Image ${result.data}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding?.tvInfoDetection?.text = result.data
                    binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                }

                else -> {
                    binding?.progressBar?.progressBar?.visibility = View.VISIBLE
                }
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

        val REQUEST_CODE_PERMISSION = 10
        val REQUIRED_PERMISSION = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
        val MAKE_REQUIRED_PERMISSION =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}