package hu.bme.aut.packager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "packages")
public data class Package(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
        @ColumnInfo(name = "weight") val weight: Long,
        @ColumnInfo(name = "reward") val reward: Long,
        @ColumnInfo(name = "carrier") val carrier: Carrier,
        @ColumnInfo(name = "state") val state: State,
        @ColumnInfo(name = "fromID") val fromID: Long?,
        @ColumnInfo(name = "height") val height: Long,
        @ColumnInfo(name = "length") val length: Long,
        @ColumnInfo(name = "width") val width: Long,
        @ColumnInfo(name = "toID") val toID: Long?,
        @ColumnInfo(name = "courierID") val courierID: Long?) {
}