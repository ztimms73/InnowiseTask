package org.xtimms.innowise.weather.model

import android.os.Parcel
import android.os.Parcelable

class TodayWeather(
    val humidity: Int = 0,
    val speed: Float = 0f,
    val deg: Int = 0,
    val temp: Float = 0f,
    val feelsLike: Float = 0f,
    val weatherName: String = "",
    val icon: String = "",
    val countryName: String = "",
    val pressure: Int = 0,
    val cityName: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readInt(),
        parcel.readString() as String
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(humidity)
        parcel.writeFloat(speed)
        parcel.writeInt(deg)
        parcel.writeFloat(temp)
        parcel.writeFloat(feelsLike)
        parcel.writeString(weatherName)
        parcel.writeString(icon)
        parcel.writeString(countryName)
        parcel.writeInt(pressure)
        parcel.writeString(cityName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForecastRecyclerItemView> {
        override fun createFromParcel(parcel: Parcel): ForecastRecyclerItemView {
            return ForecastRecyclerItemView(parcel)
        }

        override fun newArray(size: Int): Array<ForecastRecyclerItemView?> {
            return arrayOfNulls(size)
        }
    }
}