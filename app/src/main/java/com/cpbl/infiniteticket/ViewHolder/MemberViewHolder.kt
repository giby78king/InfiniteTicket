package com.cpbl.infiniteticket.ViewHolder

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cpbl.infiniteticket.Model.Member
import com.cpbl.infiniteticket.R


class MemberViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val holderTagText: TextView = v.findViewById(R.id.tag_text)

    fun bind(item: Member, parent: AppCompatActivity) {

        //參考主檔
        val TagText = item.ID

        holderTagText.text = TagText

        itemView.setOnClickListener {
            if (parent.findViewById<EditText>(R.id.editMemberId) != null) {
                val editOrder: EditText = parent.findViewById(R.id.editMemberId)
                editOrder.setText(TagText)
            }
        }
    }

}
