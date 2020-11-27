package hu.bme.aut.packager.data

import androidx.room.*

@Dao
interface PackageDao {
    @Query("SELECT * FROM packages")
    fun getAll(): List<Package>

    @Insert
    fun insert(new: Package): Long

    @Update
    fun update(new: Package)

    @Delete
    fun deleteItem(old: Package)
}