package com.cpbl.infiniteticket.DataSource

import android.util.Log
import com.cpbl.infiniteticket.Model.Member
import com.cpbl.infiniteticket.Model.Member.Companion.MemberList
import com.cpbl.infiniteticket.Model.Member.Companion.toMember
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

object FirebaseService_Member {

    private val db = FirebaseFirestore.getInstance().collection("Member")
    suspend fun getOne(id: String): Member {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("ID") != null) {
            return data.toMember()
        }
        return Member(
            ID = "newOne",
            LastTime = "",
            Team = "",
        )
    }

    suspend fun getDatas(): MutableList<Member> {
        Log.d("showMemberDialog", "getDatas")
        MemberList = db
            .orderBy("LastTime", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toMember() }.toMutableList()

        return MemberList
    }

    fun insertData(id: String, data: Map<String, Any>) {

        db.document(id)
            .set(data).addOnSuccessListener {
            }
    }

    fun updateData(id: String, data: Map<String, Any>) {

        db.document(id)
            .update(data).addOnSuccessListener {
            }
    }

    fun deleteData(ID: String) {
        db.document(ID)
            .delete()
            .addOnSuccessListener {
            }
    }

}