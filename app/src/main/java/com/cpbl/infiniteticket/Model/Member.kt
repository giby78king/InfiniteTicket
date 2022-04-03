package com.cpbl.infiniteticket.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Member(
    val ID: String,
    val LastTime: String,
    val Team: String,
) {
    companion object {
        var MemberList = mutableListOf<Member>()

        fun DocumentSnapshot.toMember(): Member {

            val ID = getString("ID")!!
            val LastTime = getString("LastTime")!!
            val Team = getString("Team")!!


            return Member(
                ID,
                LastTime,
                Team,
            )
        }
    }
}

