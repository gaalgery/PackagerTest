package hu.bme.aut.packager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [User::class, Package::class], version = 5)
@TypeConverters(value = [Package.State::class, Package.Carrier::class])
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun PackageDao(): PackageDao
}