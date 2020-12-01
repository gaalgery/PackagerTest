package hu.bme.aut.packager.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.packager.DatatTransformer.RecyclerPackage
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.Carrier
import hu.bme.aut.packager.data.Package
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import kotlin.concurrent.thread

class PackageListAdapter(private val context: Context, val listener: PackageListClickListener) : RecyclerView.Adapter<PackageListAdapter.PackageViewHolder>() {

    private var packagesItems = mutableListOf<RecyclerPackage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.content_package_list, parent, false)
        return PackageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        val item = packagesItems[position]

        holder.iconImageView.setImageResource(getImageResource(item.carrier))
        holder.weightTextView.text = item.weight.toString() + " kg"
        holder.fromTextView.text = item.from.toString()
        holder.toTextView.text = item.to.toString()
        holder.label_weight.text = "Weight:"
        holder.label_from.text = "Pickup:"
        holder.label_to.text = "Destination:"

        holder.item = item;
    }

    override fun getItemCount(): Int {
        return packagesItems.size
    }

    fun addItem(item: RecyclerPackage) {
        packagesItems.add(item)
        notifyItemInserted(packagesItems.size - 1)
    }

    fun update(shoppingItems: List<RecyclerPackage>) {
        packagesItems.clear()
        packagesItems.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    @DrawableRes
    private fun getImageResource(category: Carrier) = when (category) {
        Carrier.BIKE -> R.drawable.bicycle
        Carrier.CAR -> R.drawable.car
        Carrier.TRAILER -> R.drawable.trailer
        Carrier.TRUCK -> R.drawable.truck
    }

    interface PackageListClickListener {
        fun onItemClicked(item: RecyclerPackage)
    }

    fun deleteItem(item: RecyclerPackage){
        packagesItems.remove(item)
        notifyDataSetChanged()
    }

    inner class PackageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val iconImageView: ImageView
        val fromTextView: TextView
        val toTextView: TextView
        val weightTextView: TextView
        val detailsButton: ImageButton
        val label_from: TextView
        val label_to: TextView
        val label_weight: TextView

        var item: RecyclerPackage? = null

        init {
            iconImageView = itemView.findViewById(R.id.CarrierIconImageView)
            fromTextView = itemView.findViewById(R.id.from)
            toTextView = itemView.findViewById(R.id.to)
            weightTextView = itemView.findViewById(R.id.weight)
            detailsButton = itemView.findViewById(R.id.DetailsButton)
            detailsButton.setOnClickListener{
                item?.let {
                    listener.onItemClicked(it)
                }
            }
            label_weight = itemView.findViewById(R.id.weight_label)
            label_to = itemView.findViewById(R.id.to_label)
            label_from = itemView.findViewById(R.id.from_label)
        }

    }

}