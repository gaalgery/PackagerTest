package hu.bme.aut.packager.userAccount.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.packager.R


class AddMoneyDialog: DialogFragment() {

    interface MoneyAdded {
        fun onMoneyAdded(money: Long)
    }

    private lateinit var money: EditText

    private lateinit var listener: AddMoneyDialog.MoneyAdded

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? MoneyAdded
                ?: throw RuntimeException("Activity must implement the MoneyAdded interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
                .setTitle("AA")
                .setView(getContentView())
                .setNegativeButton("Dismiss", null)
                .setPositiveButton("Save") { dialogInterface, i ->
                        listener.onMoneyAdded(money.text.toString().toLong());
                }
                .create()
    }

    companion object {
        const val TAG = "AddMoneyDialog"
    }

    private fun getContentView(): View {

        val contentView = LayoutInflater.from(context).inflate(R.layout.add_money_dialog, null)
        money = contentView.findViewById(R.id.AddedMoney)

        return contentView
    }
}