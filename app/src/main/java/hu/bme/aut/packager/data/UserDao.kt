package hu.bme.aut.packager.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id = :id_searched")
    fun getUser(id_searched: Long?): User

    @Query("SELECT password FROM users WHERE email = :email")
    fun getPassword(email: String): String

    @Query("SELECT COUNT(*) FROM users")
    fun getUserCount(): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): User

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Delete
    fun deleteItem(user: User)

}