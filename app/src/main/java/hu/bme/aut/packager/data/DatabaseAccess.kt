package hu.bme.aut.packager.data

import android.content.Context
import androidx.room.Room


object DatabaseAccess {

        lateinit var usersDatabase: UserDatabase

        fun createInstance(context: Context){

                usersDatabase = Room.databaseBuilder(
                        context,
                        UserDatabase::class.java,
                        "users"
                ).fallbackToDestructiveMigration().build()



        }
}