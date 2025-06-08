package com.example.proyectorecu

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectorecu.models.PexelsResponse
import com.example.proyectorecu.providers.db.PhotoAdapter
import com.example.proyectorecu.providers.db.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app)

        recyclerView = findViewById(R.id.popularView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitClient.api.getCuratedPhotos().enqueue(object : Callback<PexelsResponse> {
            override fun onResponse(call: Call<PexelsResponse>, response: Response<PexelsResponse>) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos ?: emptyList()
                    recyclerView.adapter = PhotoAdapter(photos)
                } else {
                    Log.e("API", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PexelsResponse>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
            }
        })
    }
}