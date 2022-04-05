package com.cpbl.infiniteticket.ViewHolder

import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cpbl.infiniteticket.Model.Member
import com.cpbl.infiniteticket.Module.Member.Domain.MemberEn
import com.cpbl.infiniteticket.R
import com.cpbl.infiniteticket.ViewModel.VmMemberViewModel


class MemberViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val holderTagText: TextView = v.findViewById(R.id.tag_text)

    fun bind(item: Member, parent: AppCompatActivity) {

        //參考主檔
        val memberId = item.ID

        holderTagText.text = memberId

        itemView.setOnClickListener {
            if (parent.findViewById<EditText>(R.id.editMemberId) != null) {
                val editOrder: EditText = parent.findViewById(R.id.editMemberId)
                editOrder.setText(memberId)
            }
        }
        itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(parent)
            builder.setTitle("刪除").setMessage("確認刪除該會員？").setNeutralButton("取消", null)

            builder.setPositiveButton("確認",
                DialogInterface.OnClickListener { dialogInterface, i ->

                    try {
                        val vmMemberViewModel =
                            ViewModelProvider(parent).get(VmMemberViewModel::class.java)
                        val data = MemberEn(
                            ID = memberId,
                            LastTime = "",
                            Team = "",
                        )
                        vmMemberViewModel.deleteOne(data)

                    } catch (ex: Exception) {
                        Toast.makeText(parent, ex.message, Toast.LENGTH_LONG).show()
                    }
                })
            builder.show()
            true
        }
    }

}
