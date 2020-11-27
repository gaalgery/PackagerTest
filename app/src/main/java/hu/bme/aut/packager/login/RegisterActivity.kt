package hu.bme.aut.packager.login

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.User
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private val obj = MutableLiveData<User>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // MUKODIK, DE A LIVEOBJECTTEL VAN PROBLEMA, SOKSZOR HIVODIK MEG, DE EBBEN ARONDA FORMABAN MUKODIK
        btnSignUp.setOnClickListener{

            if(etName.text.toString().isEmpty()){
                etName.requestFocus()
                etName.error="Please enter your name"
            }
            else if(etAddress.text.toString().isEmpty()){
                etAddress.requestFocus()
                etAddress.error="Please enter your address"
            }
            else if(etEmailAddress.text.toString().isEmpty()){
                etEmailAddress.requestFocus()
                etEmailAddress.error="Please enter your email address"
            }
            else if(etPassword.text.toString().isEmpty()){
                etPassword.requestFocus()
                etPassword.error="Please enter your password"
            }
            else {

                var user: User? = null

                Thread{
                    user = LoginActivity.usersDatabase.UserDao().getUserByEmail(etEmailAddress.text.toString())

                    if(user  == null) {

                        val newUser = User(null, etName.text.toString(), etEmailAddress.text.toString(), etAddress.text.toString(), etPassword.text.toString(), etPhone.text.toString(), 0)
                        LoginActivity.usersDatabase.UserDao().insert(newUser)

                        LoginActivity.email = etEmailAddress.text.toString()
                        LoginActivity.password = etPassword.text.toString()
                        LoginActivity.registrationSucces = true

                        finish()
                    }
                    else {
                        obj.postValue(user)
                    }

                }.start()

                obj.observe(this, Observer {

                    Snackbar.make(btnSignUp,"Fill all input with valid information", 5000).show()
                    etEmailAddress.requestFocus()
                    etEmailAddress.error="This email is already in use"

                })

            }
        }
    }
}