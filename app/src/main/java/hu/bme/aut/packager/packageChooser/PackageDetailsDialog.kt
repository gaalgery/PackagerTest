package hu.bme.aut.packager.packageChooser

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.packager.DatatTransformer.RecyclerPackage
import hu.bme.aut.packager.R

class PackageDetailsDialog constructor(val chosenPackage: RecyclerPackage): DialogFragment() {

    interface PackageDetailsListener {
        fun onPackageTaken(Item: RecyclerPackage)
    }

    private lateinit var listener: PackageDetailsDialog.PackageDetailsListener

    private lateinit var weight: TextView
    private lateinit var height: TextView
    private lateinit var length: TextView
    private lateinit var width: TextView
    private lateinit var reward: TextView
    private lateinit var to: TextView
    private lateinit var from: TextView
    private lateinit var id: TextView
    private lateinit var weight_l: TextView
    private lateinit var height_l: TextView
    private lateinit var length_l: TextView
    private lateinit var width_l: TextView
    private lateinit var reward_l: TextView
    private lateinit var to_l: TextView
    private lateinit var from_l: TextView
    private lateinit var id_l: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? PackageDetailsDialog.PackageDetailsListener
            ?: throw RuntimeException("Activity must implement the NewPackageDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle("Package details")
            .setView(getContentView())
            .setNegativeButton("Dismiss", null)
            .setPositiveButton("Undertake") { dialogInterface, i ->
                    listener.onPackageTaken(chosenPackage);
            }
            .create()
    }

    companion object {
        const val TAG = "PackageDetailsDialog"
    }

    private fun getContentView(): View {

        val contentView = LayoutInflater.from(context).inflate(R.layout.package_details_dialog, null)

        weight = contentView.findViewById(R.id.weight_detail)
        weight.setText(chosenPackage.weight.toString() + " kg")

        height = contentView.findViewById(R.id.height_detail)
        height.setText(chosenPackage.height.toString() + " cm")

        length = contentView.findViewById(R.id.length_detail)
        length.setText(chosenPackage.length.toString() + " cm")

        width = contentView.findViewById(R.id.width_detail)
        width.setText(chosenPackage.width.toString() + " cm")

        reward = contentView.findViewById(R.id.reward_detail)
        reward.setText(chosenPackage.reward.toString() + " Ft")

        to = contentView.findViewById(R.id.pickup_detail)
        to.setText(chosenPackage.to.toString())

        from = contentView.findViewById(R.id.destination_detail)
        from.setText(chosenPackage.from.toString())

        id = contentView.findViewById(R.id.id_detail)
        id.setText(chosenPackage.id.toString())

        weight_l = contentView.findViewById(R.id.weight_label_detail)
        weight_l.setText("Weight:")

        height_l = contentView.findViewById(R.id.height_label_detail)
        height_l.setText("Height:")

        length_l = contentView.findViewById(R.id.length_label_detail)
        length_l.setText("Length:")

        width_l = contentView.findViewById(R.id.width_label_detail)
        width_l.setText("Width:")

        reward_l = contentView.findViewById(R.id.reward_label_detail)
        reward_l.setText("Reward:")

        to_l = contentView.findViewById(R.id.pickup_label_detail)
        to_l.setText("Destination address:")

        from_l = contentView.findViewById(R.id.destination_label_detail)
        from_l.setText("Pickup address:")

        id_l = contentView.findViewById(R.id.id_label_detail)
        id_l.setText("Package identifier:")

        return contentView
    }
}