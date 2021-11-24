package org.xtimms.innowise.weather.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView

class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        val list =
            arguments?.getParcelableArrayList<ForecastRecyclerItemView>("ARRAY") as ArrayList<ForecastRecyclerItemView>
        val listID = arrayListOf<Int>()

        list.forEach {
            if (it.type == 1) {
                listID.add(list.indexOf(it))
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = ForecastAdapter(
            list
        )
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

}