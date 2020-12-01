package hu.bme.aut.packager.packageChooser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.packager.DatatTransformer.NewPackage
import hu.bme.aut.packager.DatatTransformer.RecyclerPackage
import hu.bme.aut.packager.R
import hu.bme.aut.packager.adapter.PackageListAdapter
import hu.bme.aut.packager.data.DatabaseAccess
import hu.bme.aut.packager.data.Package
import hu.bme.aut.packager.data.State
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import hu.bme.aut.packager.userAccount.UserAccountActivity
import kotlinx.android.synthetic.main.activity_package_list.*
import kotlin.concurrent.thread

class PackageListActivity : AppCompatActivity(), PackageListAdapter.PackageListClickListener, NewPackageDialog.NewPackageDialogListener, PackageDetailsDialog.PackageDetailsListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PackageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener{
            NewPackageDialog().show(
                    supportFragmentManager,
                    NewPackageDialog.TAG
            )
        }

        accountButton.setOnClickListener{
            startActivity(Intent(this, UserAccountActivity::class.java))
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {

        toolbar_data.text = getString(R.string.basic_details)
        toolbar_vehicle.text = getString(R.string.necessary_vehicle)
        toolbar_more.text = getString(R.string.more_details)

        recyclerView = packagesList
        adapter = PackageListAdapter(this.applicationContext, this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {

        var packagesItems = mutableListOf<RecyclerPackage>()

        thread {
            val items =  DatabaseAccess.usersDatabase.PackageDao().getOnState(State.Waiting);

            var fromUser: User
            var toUser: User

            for (i: Package in items){
                fromUser =  DatabaseAccess.usersDatabase.UserDao().getUser(i.fromID)
                toUser =  DatabaseAccess.usersDatabase.UserDao().getUser(i.toID)

                packagesItems.add(RecyclerPackage(i.id, i.weight, i.reward, i.carrier, i.state, fromUser.address, i.height, i.length, i.width, toUser.address, null))
            }

            runOnUiThread {
                adapter.update(packagesItems)
            }
        }
    }

    override fun onItemClicked(item: RecyclerPackage) {
        PackageDetailsDialog(item)
            .show(
            supportFragmentManager,
            PackageDetailsDialog.TAG
        )
    }

    override fun onPackageCreated(newItem: NewPackage) {
        thread {

            val newItemPackage: Package
            val newItemRecyclerPackage: RecyclerPackage

            val toID =  DatabaseAccess.usersDatabase.UserDao().getUserByEmail(newItem.toEmail).id

            newItemPackage = Package(newItem.id, newItem.weight, newItem.reward, newItem.carrier, newItem.state, newItem.fromID, newItem.height, newItem.length, newItem.width, toID, null)

            val newId =  DatabaseAccess.usersDatabase.PackageDao().insert(newItemPackage)

            var fromUser: User =  DatabaseAccess.usersDatabase.UserDao().getUser(newItemPackage.fromID)
            var toUser: User =  DatabaseAccess.usersDatabase.UserDao().getUser(toID)

            newItemRecyclerPackage = RecyclerPackage(newId, newItem.weight, newItem.reward, newItem.carrier, newItem.state, fromUser.address, newItem.height, newItem.length, newItem.width, toUser.address, null)

            runOnUiThread {
                adapter.addItem(newItemRecyclerPackage)
            }
        }
    }

    override fun onPackageTaken(Item: RecyclerPackage) {

        thread {
            runOnUiThread {
                adapter.deleteItem(Item)
            }

            val old: Package =  DatabaseAccess.usersDatabase.PackageDao().getByID(Item.id)
             DatabaseAccess.usersDatabase.PackageDao().update(Package(old.id, old.weight, old.reward, old.carrier, State.Undertaken, old.fromID, old.height, old.length, old.width, old.toID, LoginActivity.loggedInUserID))
        }
    }
}