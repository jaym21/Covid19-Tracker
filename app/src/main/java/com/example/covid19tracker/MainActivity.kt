package com.example.covid19tracker

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

    private const val BASE_URL = "https://api.covid19india.org"
    private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var stateListAdapter: StateListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting ListView
        lvStatesCases.addHeaderView(LayoutInflater.from(this).inflate(R.layout.list_header, lvStatesCases, false))

        getResults()

        btnRefresh.setOnClickListener {
            getResults()
        }
    }

    //making instance of retrofit can connecting with api to get data
    private fun getResults() {
        //create a instance of gson which we will need to create instance of retrofit
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val covidService = retrofit.create(CovidService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = covidService.getStatesData().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()
                launch(Dispatchers.Main) {
                    setTotalStatesData(data?.statewise?.get(0))
                    setStateWiseData(data?.statewise?.subList(1,data.statewise.size))
                }

            }
        }
    }

    private fun setStateWiseData(subList: List<StatewiseItem>?) {
        stateListAdapter = StateListAdapter(subList)
        lvStatesCases.adapter = stateListAdapter
    }

    //setting the data from api to views
    private fun setTotalStatesData(data: StatewiseItem?) {

        val lastUpdatedTime = data?.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        tvLastUpdated.text = "Last Updated\n ${getTimeAgo(
                simpleDateFormat.parse(lastUpdatedTime)
        )}"

        tvConfirmed.text = data?.confirmed
        tvActive.text = data?.active
        tvRecovered.text = data?.recovered
        tvDeath.text = data?.deaths
    }


    //to calculate the time data was last updated we subtract it with currentTime and find it
    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }
}