package hu.bme.aut.packager.userAccount

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.packager.R
import hu.bme.aut.packager.adapter.AccountPageAdapter
import hu.bme.aut.packager.login.LoginActivity
import hu.bme.aut.packager.userAccount.fragments.AccountDetailsFragment
import hu.bme.aut.packager.userAccount.fragments.AddMoneyDialog
import kotlinx.android.synthetic.main.activity_user_account.*


class UserAccountActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)
        setSupportActionBar(findViewById(R.id.toolbarLogout))

        // LogoutButton.setImageResource(R.drawable.logout)

        LogoutButton.setOnClickListener{
            finishAffinity()
            startActivity(Intent(this, LoginActivity::class.java))
        }



        userAccount.adapter = AccountPageAdapter(supportFragmentManager, this.applicationContext)
    }

}