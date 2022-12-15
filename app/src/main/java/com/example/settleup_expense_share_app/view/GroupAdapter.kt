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

class GroupAdapter (val c: Context, val GroupList:ArrayList<GroupData>) :RecyclerView.Adapter<GroupAdapter.GroupViewHolder >()
{
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }
    inner class GroupViewHolder(val v: View, listener: onItemClickListener): RecyclerView.ViewHolder(v){
        var name: TextView
        var reason: TextView
        var mMenus: ImageView

        init {
            itemView.setOnClickListener{
                listener.onItemClick((adapterPosition))
            }
            name = v.findViewById<TextView>(R.id.mTitle)
            reason = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener{ popupMenus(it)
            }
        }
        private fun popupMenus(v:View) {
            val position = GroupList[adapterPosition]
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
                                    position.GroupName = name.text.toString()
                                    position.Reason = reason.text.toString()
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
                                GroupList.removeAt(adapterPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return GroupViewHolder(v,mListener)

    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val newList = GroupList[position]
        holder.name.text = newList.GroupName
        holder.reason.text = newList.Reason
    }

    override fun getItemCount(): Int {
       return GroupList.size
    }
}