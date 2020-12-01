package hu.bme.aut.packager.data

import androidx.room.*

@Dao
interface PackageDao {
    @Query("SELECT * FROM packages")
    fun getAll(): List<Package>

    @Query("SELECT * FROM packages WHERE state = :state")
    fun getOnState(state: State): List<Package>

    @Query("SELECT * FROM packages WHERE state = :state AND courierID = :id")
    fun getOnStateByID(state: State, id: Long?): List<Package>

    @Query("SELECT * FROM packages WHERE id = :id LIMIT 1")
    fun getByID(id: Long?): Package

    @Query("SELECT COUNT(*) FROM packages")
    fun getCount(): Long

    @Insert
    fun insert(new: Package): Long

    @Update
    fun update(new: Package)

    @Delete
    fun deleteItem(old: Package)
}