package hu.bme.aut.packager.packageChooser

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.packager.R
import hu.bme.aut.packager.adapter.PackageListAdapter
import hu.bme.aut.packager.data.Package
import hu.bme.aut.packager.login.LoginActivity
import kotlinx.android.synthetic.main.activity_package_list.*
import kotlin.concurrent.thread

class PackageListActivity : AppCompatActivity(), PackageListAdapter.PackageListClickListener, NewPackageDialog.NewPackageDialogListener {

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

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = packagesList
        adapter = PackageListAdapter(this.applicationContext, this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            val items = LoginActivity.usersDatabase.PackageDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemClicked(item: Package) {
        Log.d("kattintas tortent","" + item.toString())
    }

    override fun onPackageCreated(newItem: Package) {
        thread {
            val newId = LoginActivity.usersDatabase.PackageDao().insert(newItem)
            val newPackage = newItem.copy(
                    id = newId
            )
            runOnUiThread {
                adapter.addItem(newPackage)
            }
        }
    }
}