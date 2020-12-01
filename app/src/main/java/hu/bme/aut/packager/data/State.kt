package hu.bme.aut.packager.data

import androidx.room.TypeConverter

enum class State {
    Waiting, PickedUp, Undertaken, Delivered;
    companion object {
        @JvmStatic
        @TypeConverter
        fun getByOrdinal(ordinal: Int): State? {
            return values().find{it.ordinal == ordinal}
        }

        @JvmStatic
        @TypeConverter
        fun toInt(state: State): Int {
            return state.ordinal
        }
    }
}