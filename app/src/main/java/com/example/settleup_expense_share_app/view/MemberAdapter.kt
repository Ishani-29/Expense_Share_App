package com.example.settleup_expense_share_app.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.settleup_expense_share_app.R
import com.example.settleup_expense_share_app.model.GroupData
import com.example.settleup_expense_share_app.model.MemberData
import java.lang.reflect.Member

class MemberAdapter (val c: Context, val MemberList:ArrayList<MemberData>) :RecyclerView.Adapter<MemberAdapter.MemberViewHolder >()
{

    inner class MemberViewHolder(val v: View): RecyclerView.ViewHolder(v){
        var MemberName: TextView
        var EmailId: TextView
        var mMenus: ImageView

        init {
            MemberName = v.findViewById<TextView>(R.id.mTitle)
            EmailId = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener{ popupMenus(it)
            }
        }
        private fun popupMenus(v:View) {
            val position = MemberList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.GroupName)
                        val reason = v.findViewById<EditText>(R.id.Reason)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){dialogInterface, which ->
                                position.MemberName = name.text.toString()
                                position.EmailId = reason.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"Group Information is Edited", Toast.LENGTH_SHORT).show()
//                                 dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){dialogInterface, which ->

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setMessage("Are you sure you want to delete this Information")
                            .setPositiveButton("Yes"){dialodgInterface, which->
                                MemberList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("No"){dialogInterface, which->

                            }
                            .create()
                            .show()


                        true
                    }
                    else->true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return MemberViewHolder(v)

    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val newList = MemberList[position]
        holder.MemberName.text = newList.MemberName
        holder.EmailId.text = newList.EmailId
    }

    override fun getItemCount(): Int {
        return MemberList.size
    }
}