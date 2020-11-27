package hu.bme.aut.packager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "balance") val balance: Long){

}