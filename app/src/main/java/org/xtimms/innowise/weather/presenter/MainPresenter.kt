package org.xtimms.innowise.weather.presenter

import android.annotation.SuppressLint
import com.example.innowiseweatherapplication.view.IMainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import org.xtimms.innowise.weather.model.MainModel
import org.xtimms.innowise.weather.model.TodayWeather
import org.xtimms.innowise.weather.model.Weather
import org.xtimms.innowise.weather.utils.Utils

class MainPresenter(private var view: IMainView?, private val utils: Utils) :
    IMainPresenter {

    private val model = MainModel()
    lateinit var weather: Weather

    @SuppressLint("CheckResult")
    override fun getData(cityName: String) {
        val dataObservable: Observable<Weather> = model.getWeather(cityName)
        dataObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                weather = it
                val arrayList = ArrayList<ForecastRecyclerItemView>()
                it.list?.forEach {
                    val array = utils.parseFunction(it.dtTxt!!)
                    val parceledDay = utils.anotherParcelFunction(array[1])
                    if (array[0] == 0 && arrayList.size != 0) arrayList.add(
                        ForecastRecyclerItemView(
                            ForecastRecyclerItemView.HEADER_TYPE,
                            day = parceledDay
                        )
                    )
                    arrayList.add(
                        ForecastRecyclerItemView(
                            ForecastRecyclerItemView.WEATHER_TYPE, it.weather[0].icon,
                            it.main!!.temp - 273, it.weather[0].main, parceledDay, array[0]
                        )
                    )
                }

                val todayWeather = TodayWeather(
                    it.list!![0].main!!.humidity,
                    it.list!![0].wind!!.speed,
                    it.list!![0].wind!!.deg,
                    it.list!![0].main!!.temp - 273,
                    it.list!![0].main!!.seaLevel,
                    it.list!![0].weather[0].main,
                    it.list!![0].weather[0].icon,
                    it.city!!.country,
                    it.list!![0].main!!.pressure,
                    it.city!!.name
                )
                view!!.hideProgress()
                view!!.openTodayWeather(todayWeather, arrayList)
            },
                { t ->
                    println(t.message)
                    view!!.hideProgress()
                    view!!.showError("Some trouble with loading info")
                }
            )
    }

    @SuppressLint("CheckResult")
    override fun getLastLocation() {
        view!!.showProgress()
        if (utils.isConnection()) {
            if (utils.checkPermission()) {
                if (utils.isLocationEnabled()) {
                    utils.mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                        val isLocation: Boolean = utils.isLocationAvailable(task.result)
                        if (isLocation) {
                            utils.requestNewLocationData()
                            getLastLocation()
                        } else {
                            if (utils.getCity() != "Error")
                                getData(utils.getCity())
                            else {
                                view!!.hideProgress()
                                view!!.showError("Trouble with your location, try to check your emulator is fine")
                            }
                        }
                    }
                } else {
                    utils.createNewIntent()
                }
            } else {
                view!!.hideProgress()
                view!!.requestPermission()
            }
        } else {
            view!!.hideProgress()
            view!!.showError("You don't have internet connection")
        }
    }

    override fun detachView() {
        view = null
    }

}