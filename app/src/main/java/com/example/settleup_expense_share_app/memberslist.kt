package com.example.settleup_expense_share_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.settleup_expense_share_app.model.GroupData
import com.example.settleup_expense_share_app.model.MemberData
import com.example.settleup_expense_share_app.view.MemberAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.reflect.Member

class memberslist : AppCompatActivity() {
    lateinit var addsBtn: FloatingActionButton
    lateinit var recv: RecyclerView
    lateinit var MemberList:ArrayList<MemberData>
    lateinit var MemberAdapter: MemberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memberslist)

        val GroupName : TextView = findViewById(R.id.GroupName)
        val Reason : TextView = findViewById(R.id.Reason)

        val bundle : Bundle?= intent.extras

        val heading = bundle!!.getString("heading")
        val CReason = bundle.getString("reason")

        GroupName.text = heading
        Reason.text = CReason

        MemberList = ArrayList()
        addsBtn =findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        MemberAdapter = MemberAdapter(this, MemberList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = MemberAdapter
        addsBtn.setOnClickListener{ addInfo() }
    }


    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_mem, null)
        val MemberName = v.findViewById<EditText>(R.id.MemberName)
        val EmailId = v.findViewById<EditText>(R.id.EmailId)

        val addDialog = AlertDialog.Builder(this)
        with(addDialog) {
            addDialog.create()
            addDialog.setPositiveButton("Ok") { dialogInterface, which ->
                val MemberName = MemberName.text.toString()
                val EmailId = EmailId.text.toString()
                MemberList.add(MemberData("Member Name: $MemberName", "Email ID: $EmailId"))
                MemberAdapter.notifyDataSetChanged()
                Toast.makeText(
                    applicationContext,
                    "Adding group information success",
                    Toast.LENGTH_SHORT
                ).show()

            }
            addDialog.setNegativeButton("Cancel")
            { dialogInterface, which ->
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()

            }
            setView(v)
            addDialog.show()
        }
    }
    }