package com.example.adminpanel.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.R
import com.example.adminpanel.data.presenter_impl.AddVotingFragmentPresenterImpl
import com.example.adminpanel.domain.presenter.AddVotingFragmentContract
import com.example.adminpanel.presentation.adapter.VariantsAdapter
import kotlinx.android.synthetic.main.dialog_custom.view.*
import kotlinx.android.synthetic.main.fragment_add_voting.view.*


class AddVotingFragment : Fragment(), AddVotingFragmentContract.View {

    private lateinit var addVotingPresenter: AddVotingFragmentPresenterImpl

    private lateinit var buttonBack: ImageButton
    private lateinit var buttonAddVariant: ImageButton
    private lateinit var buttonAdd: Button

    private lateinit var variantsRecyclerView: RecyclerView

    private lateinit var editQuestion: EditText

    private val variantArrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_add_voting, container, false)

        addVotingPresenter = AddVotingFragmentPresenterImpl(this)

        buttonBack = view!!.btnBack
        buttonAddVariant = view!!.btnAddVariant
        buttonAdd = view!!.btnQuestion

        editQuestion = view!!.editTextQuestion

        variantsRecyclerView = view!!.variantRecyclerView

        init()


        buttonAddVariant.setOnClickListener {
            clickToCreateVariant()
        }

        buttonBack.setOnClickListener {
           enterToVotingsFragment()
        }

        buttonAdd.setOnClickListener {
            addVotingToSystem()
        }

        return view
    }

    private fun enterToVotingsFragment(){
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.replace(R.id.fl_layout, VotingsFragment())
        transactionFragment.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addVotingToSystem() {
        if(!editQuestion.text.toString().isNullOrEmpty() && variantArrayList.size > 0){
            addVotingPresenter.addToFireBaseVoting(editQuestion.text.toString(), variantArrayList)
            editQuestion.text.clear()

            variantArrayList.clear()
            variantsRecyclerView.adapter!!.notifyDataSetChanged()

            enterToVotingsFragment()
        }else{
            Toast.makeText(requireContext(), R.string.fill_form, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(){
        variantsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        variantsRecyclerView.adapter = VariantsAdapter(variantArrayList)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clickToCreateVariant(){
        val  inflater = this.layoutInflater
        val custom_dialog: View = inflater.inflate(R.layout.dialog_custom, null)

        val editText = custom_dialog.edit_text_receiptent

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.app_name)
            .setIcon(R.drawable.ic_launcher_foreground)
            .setView(custom_dialog)
            .setPositiveButton("Ok"
            ) { p0, p1 ->
                variantArrayList.add(editText.text.toString())
                variantsRecyclerView.adapter!!.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
        dialog.show()
    }

    companion object {

    }
}