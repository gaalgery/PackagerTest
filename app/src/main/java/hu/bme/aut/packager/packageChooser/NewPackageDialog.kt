package hu.bme.aut.packager.packageChooser

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.internal.ViewUtils.getContentView
import hu.bme.aut.packager.R
import hu.bme.aut.packager.data.Package

import android.opengl.ETC1.isValid
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.packager.data.User
import hu.bme.aut.packager.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*


class NewPackageDialog :  DialogFragment(){

    interface NewPackageDialogListener {
        fun onPackageCreated(newItem: Package)
    }

    private val obj = MutableLiveData<Boolean>()

    private lateinit var weight: EditText
    private lateinit var height: EditText
    private lateinit var length: EditText
    private lateinit var width: EditText
    private lateinit var reward: EditText
    private lateinit var toEmail: EditText
    private lateinit var carrier: Spinner

    private var destinationUser: User? = null
    private lateinit var listener: NewPackageDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewPackageDialogListener
                ?: throw RuntimeException("Activity must implement the NewPackageDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
                .setTitle("New Package")
                .setView(getContentView())
                .setNegativeButton("Dismiss", null)
                .setPositiveButton("Save") { dialogInterface, i ->
                    if (isValid()) {
                        listener.onPackageCreated(getPackage());
                    }
                }
                .create()
    }

    companion object {
        const val TAG = "NewPackageDialogFragment"
    }

    private fun isValid() = toEmail.text.isNotEmpty()

    private fun getContentView(): View {

        val contentView =
                LayoutInflater.from(context).inflate(R.layout.new_package_dialog, null)
        weight = contentView.findViewById(R.id.WeightEditText)
        height = contentView.findViewById(R.id.HeightEditText)
        length = contentView.findViewById(R.id.LengthEditText)
        width = contentView.findViewById(R.id.WidthEditText)
        reward = contentView.findViewById(R.id.RewardEditText)
        toEmail = contentView.findViewById(R.id.DestinationEmailEditText)
        width = contentView.findViewById(R.id.WidthEditText)
        carrier = contentView.findViewById(R.id.CarrierSpinner)
        carrier.setAdapter(
                ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        resources.getStringArray(R.array.carrier_items)
                )
        )
        Thread{
            destinationUser = LoginActivity.usersDatabase.UserDao().getUserByEmail(toEmail.text.toString())
        }.start()
        return contentView
    }

    private fun getPackage() = hu.bme.aut.packager.data.Package(

            id = null,
            weight = try {
                weight.text.toString().toLong()
            } catch (e: java.lang.NumberFormatException) {
                0
            },
            reward = try {
                reward.text.toString().toLong()
            } catch (e: java.lang.NumberFormatException) {
                0
            },
            carrier = hu.bme.aut.packager.data.Package.Carrier.getByOrdinal(carrier.selectedItemPosition) ?: hu.bme.aut.packager.data.Package.Carrier.CAR,
            state = hu.bme.aut.packager.data.Package.State.Waiting,
            fromID = LoginActivity.loggedInUserID,

            height = try {
                height.text.toString().toLong()
            } catch (e: java.lang.NumberFormatException) {
                0
            },
            length = try {
                length.text.toString().toLong()
            } catch (e: java.lang.NumberFormatException) {
                0
            },
            width = try {
                width.text.toString().toLong()
            } catch (e: java.lang.NumberFormatException) {
                0
            },
            toID = destinationUser?.id
    )

}