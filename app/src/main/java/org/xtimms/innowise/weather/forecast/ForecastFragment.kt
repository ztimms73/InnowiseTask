package org.xtimms.innowise.weather.forecast

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseFragment
import org.xtimms.innowise.weather.databinding.FragmentForecastBinding
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView

class ForecastFragment : BaseFragment<FragmentForecastBinding>() {

    override fun getTitle(): CharSequence? {
        return context?.getString(R.string.forecast)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentForecastBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list =
            arguments?.getParcelableArrayList<ForecastRecyclerItemView>("ARRAY") as ArrayList<ForecastRecyclerItemView>
        val listID = arrayListOf<Int>()

        list.forEach {
            if (it.type == 1) {
                listID.add(list.indexOf(it))
            }
        }

        val recyclerView = binding.recycler
        recyclerView.adapter = ForecastAdapter(
            this.requireContext(),
            list
        )
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val layMan = recyclerView.layoutManager as LinearLayoutManager
        var firstItemPosition: Int
        var prevItemPos: Int = -1
        val header = binding.header

        if ((list.size - 40) == 4) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("SetTextI18n")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    firstItemPosition = layMan.findFirstVisibleItemPosition()
                    if (prevItemPos != firstItemPosition) {
                        when (firstItemPosition) {
                            0 -> {
                                header.text = "Today"
                            }
                            (listID[0] - 1) -> {
                                header.text = "Today"
                            }
                            (listID[0] + 1) -> {
                                header.text = list[listID[0]].day
                            }
                            (listID[1] - 1) -> {
                                header.text = list[listID[0]].day
                            }
                            (listID[1] + 1) -> {
                                header.text = list[listID[1]].day
                            }
                            (listID[2] - 1) -> {
                                header.text = list[listID[1]].day
                            }
                            (listID[2] + 1) -> {
                                header.text = list[listID[2]].day
                            }
                            (listID[3] - 1) -> {
                                header.text = list[listID[2]].day
                            }
                            (listID[3] + 1) -> {
                                header.text = list[listID[3]].day
                            }
                        }
                    }
                    prevItemPos = firstItemPosition
                }
            })
        } else {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("SetTextI18n")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    firstItemPosition = layMan.findFirstVisibleItemPosition()
                    if (prevItemPos != firstItemPosition) {
                        when (firstItemPosition) {
                            0 -> {
                                header.text = "Today"
                            }
                            (listID[0] - 1) -> {
                                header.text = "Today"
                            }
                            (listID[0] + 1) -> {
                                header.text = list[listID[0]].day
                            }
                            (listID[1] - 1) -> {
                                header.text = list[listID[0]].day
                            }
                            (listID[1] + 1) -> {
                                header.text = list[listID[1]].day
                            }
                            (listID[2] - 1) -> {
                                header.text = list[listID[1]].day
                            }
                            (listID[2] + 1) -> {
                                header.text = list[listID[2]].day
                            }
                            (listID[3] - 1) -> {
                                header.text = list[listID[2]].day
                            }
                            (listID[3] + 1) -> {
                                header.text = list[listID[3]].day
                            }
                            (listID[4] - 1) -> {
                                header.text = list[listID[3]].day
                            }
                            (listID[4] + 1) -> {
                                header.text = list[listID[4]].day
                            }
                        }
                    }
                    prevItemPos = firstItemPosition
                }
            })
        }
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }

}