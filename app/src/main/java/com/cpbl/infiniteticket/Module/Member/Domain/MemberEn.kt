package com.cpbl.infiniteticket.Module.Member.Domain

import com.cpbl.infiniteticket.DataSource.FirebaseService_Member
import com.cpbl.infiniteticket.TimeFormat

data class MemberEn(
    val ID: String,
    val LastTime: String,
    val Team: String,
) {
    suspend fun <T> updateOne(data: T) {
        data as MemberEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["ID"] = data.ID
        dbData["LastTime"] = data.LastTime
        dbData["Team"] = data.Team
        FirebaseService_Member.updateData(ID, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_Member.getOne(ID)
        val lastTime = TimeFormat().TodayDate().yyyyMMddHHmmss()
        if (data.ID == "newOne") {
            val dbData = hashMapOf(
                "ID" to ID,
                "LastTime" to lastTime,
                "Team" to Team,
            )
            FirebaseService_Member.insertData(ID, dbData)
        } else {
            val dbData = hashMapOf(
                "ID" to ID,
                "LastTime" to lastTime,
                "Team" to Team,
            )
            FirebaseService_Member.updateData(ID, dbData)
        }
    }
    fun delete() {
        FirebaseService_Member.deleteData(ID)
    }
}