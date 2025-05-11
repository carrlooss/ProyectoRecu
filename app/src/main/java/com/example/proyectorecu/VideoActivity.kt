package com.example.proyectorecu

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectorecu.databinding.VideoLayoutBinding


class VideoActivity : AppCompatActivity() {
    private var binding: VideoLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Inicializa el ViewBinding
        binding = VideoLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())

        // Configura el VideoView
        val videoPath = "android.resource://" + packageName + "/" + R.raw.lamine
        val uri = Uri.parse(videoPath)
        binding!!.videoView.setVideoURI(uri)

        // Agrega los controles de reproducción
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding!!.videoView)
        binding!!.videoView.setMediaController(mediaController)

        // Iniciar la reproducción automática
        binding!!.videoView.start()
    }
}