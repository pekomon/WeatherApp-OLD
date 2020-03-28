package com.example.pekomon.weatherapp.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pekomon.weatherapp.R
import com.example.pekomon.weatherapp.data.db.entry.list.SimpleFutureWeatherEntry
import com.example.pekomon.weatherapp.data.db.typeconverters.LocalDateConverter
import com.example.pekomon.weatherapp.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)

        bindUi()
    }

    // coroutine
    private fun bindUi() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()

        futureWeatherEntries.observe(viewLifecycleOwner, Observer { weatherEntries ->
            if (weatherEntries == null) {
                return@Observer
            }
            group_loading.visibility = View.GONE
            updateDateToNextWeek()
            initRecyclerView(weatherEntries.toFutureWeatherItems())

        })
    }

    private fun updateLocation(location: String) {
        (activity as AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToNextWeek() {
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Next 7 days"
    }

    private fun List<SimpleFutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as FutureWeatherItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }

    private fun showWeatherDetail(date: LocalDate, view: View) {
        Log.d("zzz", "Date in: ${date.toString()}" )
        val dateString = LocalDateConverter.dateToString(date)
        Log.d("zzz", "Date converted: $dateString")

        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(dateString)
        Navigation.findNavController(view).navigate(actionDetail)
    }
}
