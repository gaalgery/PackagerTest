package hu.bme.aut.packager.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room.databaseBuilder
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.data.UserDatabase
import hu.bme.aut.packager.packageChooser.PackageListActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnSignUp
import kotlinx.android.synthetic.main.activity_login.etEmailAddress
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.properties.Delegates


class LoginActivity : AppCompatActivity() {

    private val obj = MutableLiveData<Boolean>()

    companion object{
        lateinit var usersDatabase: UserDatabase
        var loggedInUserID: Long? = 0
        var email: String = ""
        var password: String = ""
        var registrationSucces: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersDatabase = databaseBuilder(
                applicationContext,
                UserDatabase::class.java,
                "users"
        ).fallbackToDestructiveMigration().build()
        etEmailAddress.text

        Thread{
            Log.d("LETREHOZASKOR", "ennyi van" + usersDatabase.UserDao().getUserCount())
            // usersDatabase.clearAllTables()
            Log.d("LETREHOZASKOR", "ennyi van" + usersDatabase.UserDao().getUserCount())
        }.start()

        btnSignUp.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener{

            if(etEmailAddress.text.toString().isEmpty()){
                etEmailAddress.requestFocus()
                etEmailAddress.error="Please enter your email address"
            }
            else if(etPassword.text.toString().isEmpty()){
                etPassword.requestFocus()
                etPassword.error="Please enter your password"
            }
            else{

                Thread{
                    val authUser = usersDatabase.UserDao().getUserByEmail(etEmailAddress.text.toString())
                    if(authUser.password == etPassword.text.toString())
                    {
                        loggedInUserID = authUser.id
                        Log.d("engeely", "belepett" + loggedInUserID)
                        startActivity(Intent(this, PackageListActivity::class.java))
                    }
                    else {
                        obj.postValue(false)
                    }
                }.start()

                obj.observe(this, Observer {

                    Snackbar.make(btnSignUp,"This email and password does not match with any useraccount", 5000).show()
                    etPassword.requestFocus()
                    etPassword.error="Login failed"

                })
            }

        }
    }

    override fun onResume() {
        super.onResume()

        if(registrationSucces){
            registrationSucces = false
            etEmailAddress.setText(email)
            etPassword.setText(password)
            Snackbar.make(btnSignUp,"Registration successful", 5000).show()
        }

    }

}