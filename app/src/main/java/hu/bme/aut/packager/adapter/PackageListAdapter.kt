package hu.bme.aut.packager.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.Package
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import kotlin.concurrent.thread

class PackageListAdapter(private val context: Context, private val listener: PackageListClickListener) : RecyclerView.Adapter<PackageListAdapter.PackageViewHolder>() {

    private var packagesItems = mutableListOf<Package>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.content_package_list, parent, false)
        return PackageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        val item = packagesItems[position]
        var userFrom: User? = null
        var userTo: User? = null

        thread {
            userFrom = item.fromID?.let { LoginActivity.usersDatabase.UserDao().getUser(it) }
            userTo = item.toID?.let { LoginActivity.usersDatabase.UserDao().getUser(it) }
            context.run{
                holder.iconImageView.setImageResource(getImageResource(item.carrier))
                holder.weightTextView.setText(item.weight.toString() + "kg")
                holder.fromTextView.text = userFrom?.address.toString()
                holder.toTextView.text = userTo?.address.toString()
                holder.item = item;
            }
        }


    }

    override fun getItemCount(): Int {
        return packagesItems.size
    }

    fun addItem(item: Package) {
        packagesItems.add(item)
        notifyItemInserted(packagesItems.size - 1)
    }

    fun update(shoppingItems: List<Package>) {
        packagesItems.clear()
        packagesItems.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    @DrawableRes
    private fun getImageResource(category: Package.Carrier) = when (category) {
        Package.Carrier.BIKE -> R.drawable.bicycle
        Package.Carrier.CAR -> R.drawable.car
        Package.Carrier.TRAILER -> R.drawable.trailer
        Package.Carrier.TRUCK -> R.drawable.truck
    }

    interface PackageListClickListener {
        fun onItemClicked(item: Package)

    }

    class PackageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val iconImageView: ImageView
        val fromTextView: TextView
        val toTextView: TextView
        val weightTextView: TextView
        val detailsButton: ImageButton

        var item: Package? = null

        init {
            iconImageView = itemView.findViewById(R.id.CarrierIconImageView)
            fromTextView = itemView.findViewById(R.id.from)
            toTextView = itemView.findViewById(R.id.to)
            weightTextView = itemView.findViewById(R.id.weight)
            detailsButton = itemView.findViewById(R.id.DetailsButton)
            detailsButton.setOnClickListener{details(layoutPosition)}

        }

        fun details(position: Int){
            Log.d("sdf", "sddddddd")

        }

    }

}