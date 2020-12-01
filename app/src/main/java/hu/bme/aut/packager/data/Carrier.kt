package hu.bme.aut.packager.data

import androidx.room.TypeConverter

enum class Carrier {
    BIKE, CAR, TRAILER, TRUCK;
    companion object {
        @JvmStatic
        @TypeConverter
        fun getByOrdinal(ordinal: Int): Carrier? {
            return values().find{it.ordinal == ordinal}
        }

        @JvmStatic
        @TypeConverter
        fun toInt(carrier: Carrier): Int {
            return carrier.ordinal
        }
    }
}