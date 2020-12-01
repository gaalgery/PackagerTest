package hu.bme.aut.packager.userAccount.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.packager.DatatTransformer.RecyclerPackage
import hu.bme.aut.packager.R
import hu.bme.aut.packager.adapter.PackageListAdapter
import hu.bme.aut.packager.data.DatabaseAccess
import hu.bme.aut.packager.data.Package
import hu.bme.aut.packager.data.State
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import kotlinx.android.synthetic.main.activity_package_list.*
import kotlin.concurrent.thread


class UndertakenPackagesFragment(context: Context): Fragment(R.layout.fragment_undertaken_packages), PackageListAdapter.PackageListClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PackageListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {

        toolbar_data.text = getString(R.string.basic_details)
        toolbar_vehicle.text = getString(R.string.necessary_vehicle)
        toolbar_more.text = getString(R.string.more_details)

        recyclerView = packagesList
        adapter = context?.let { PackageListAdapter(it, this) }!!
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {

        var packagesItems = mutableListOf<RecyclerPackage>()

        thread {
            val items = context?.let { DatabaseAccess.usersDatabase.PackageDao().getOnStateByID(State.Undertaken, LoginActivity.loggedInUserID) };

            var fromUser: User
            var toUser: User

            if (items != null) {
                for (i: Package in items){
                    fromUser = context?.let { DatabaseAccess.usersDatabase.UserDao().getUser(i.fromID) }!!
                    toUser = context?.let { DatabaseAccess.usersDatabase.UserDao().getUser(i.toID) }!!

                    packagesItems.add(RecyclerPackage(i.id, i.weight, i.reward, i.carrier, i.state, fromUser.address, i.height, i.length, i.width, toUser.address, null))
                }
            }

            adapter.update(packagesItems)

        }
    }

    override fun onItemClicked(item: RecyclerPackage) {
        Log.d("asdssss", "dsssssssssssss" + item.weight)
    }
}