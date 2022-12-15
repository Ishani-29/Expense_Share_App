package com.example.settleup_expense_share_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.settleup_expense_share_app.model.GroupData
import com.example.settleup_expense_share_app.view.GroupAdapter
import com.example.settleup_expense_share_app.view.MemberAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.security.acl.Group

class grouplist : AppCompatActivity()
{
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var GroupList:ArrayList<GroupData>
    private lateinit var GroupAdapter: GroupAdapter
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grouplist)

        database = Firebase.database.reference
        GroupList = ArrayList()
        addsBtn =findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        GroupAdapter = GroupAdapter(this, GroupList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = GroupAdapter
        addsBtn.setOnClickListener{ addInfo() }


    }
    private fun addInfo()
    {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        val GroupName = v.findViewById<EditText>(R.id.GroupName)
        val Reason = v.findViewById<EditText>(R.id.Reason)

        val addDialog = AlertDialog.Builder(this)
        with(addDialog)
        {
            addDialog.create()
            addDialog.setPositiveButton("Ok") {dialogInterface, which->
                val names = GroupName.text.toString()
                val reason = Reason.text.toString()
                GroupList.add(GroupData("Name: $names", "Reason: $reason"))
                saveData(names,reason)
                GroupAdapter.notifyDataSetChanged()
                Toast.makeText(applicationContext,"Adding group information success",Toast.LENGTH_SHORT).show()

            }
            addDialog.setNegativeButton("Cancel")
            {dialogInterface, which->
                Toast.makeText(applicationContext,"Cancel",Toast.LENGTH_SHORT).show()

         }
            setView(v)
            addDialog.show()

        }

        GroupAdapter.setOnItemClickListener(object : GroupAdapter.onItemClickListener
        {
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@grouplist, "You clicked on item no. $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@grouplist, memberslist::class.java)
                intent.putExtra("Group Name",GroupList[position].GroupName)
                intent.putExtra("Reason",GroupList[position].Reason)
                startActivity(intent)
            }

        })


    }

    private fun saveData(name: String,Reason : String) {

        val user = UserModel(name,Reason)
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("User").child(userID).setValue(user)

    }

}