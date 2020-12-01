package hu.bme.aut.packager.userAccount.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_account_details.*
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.packager.data.DatabaseAccess
import kotlin.concurrent.thread

class AccountDetailsFragment(val fm: FragmentManager): Fragment(R.layout.fragment_account_details), AddMoneyDialog.MoneyAdded{

    private val obj = MutableLiveData<User>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user: User? = null

        thread {
           user =  DatabaseAccess.usersDatabase.UserDao().getUser(LoginActivity.loggedInUserID)
            obj.postValue(user)
        }

        addMoney.setOnClickListener{

            AddMoneyDialog().show(
                fm,
                AddMoneyDialog.TAG
            )
        }


        title_user.text = getString(R.string.title_u)
        id_label_user.text = getString(R.string.id_u)
        name_label_user.text = getString(R.string.name_u)
        address_label_user.text = getString(R.string.address_u)
        phone_label_user.text = getString(R.string.phone_u)
        balance_label_user.text = getString(R.string.balance_u)
        email_label_user.text = getString(R.string.email_u)
        password_label_user.text = getString(R.string.password_u)


        obj.observe(viewLifecycleOwner, {
            name_user.text = it.name.toString()
            address_user.text = it.address.toString()
            phone_user.text = it.phone.toString()
            balance_user.text = it.balance.toString() + " Ft"
            id_user.text = it.id.toString()
            email_user.text = it.email.toString()
            password_user.text = it.password.toString()
        })

    }

    override fun onMoneyAdded(money: Long) {
        Log.d("sdf", "+"+money)
    }


}