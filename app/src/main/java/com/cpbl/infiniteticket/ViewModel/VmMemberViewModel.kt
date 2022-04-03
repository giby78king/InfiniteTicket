package com.cpbl.infiniteticket.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpbl.infiniteticket.DataSource.FirebaseService_Member
import com.cpbl.infiniteticket.Model.Member
import com.cpbl.infiniteticket.Model.Member.Companion.MemberList
import com.cpbl.infiniteticket.Module.Member.Domain.MemberEn
import kotlinx.coroutines.launch

class VmMemberViewModel : ViewModel() {

    private val _memberDatas = MutableLiveData<MutableList<Member>>()
    private val _upsertMemberDatas = MutableLiveData<MutableList<Member>>()
    val memberDatas: LiveData<MutableList<Member>> = _memberDatas
    val upsertMemberDatas: LiveData<MutableList<Member>> = _upsertMemberDatas

    fun getDatas(selection:String) {
        viewModelScope.launch {
            FirebaseService_Member.getDatas()
            when(selection){
                "editMemberId"-> {
                    _memberDatas.value = MemberList
                }
                "UpsertMember"->{
                    _upsertMemberDatas.value = MemberList
                }
            }
        }
    }

    fun upsertOne(data: MemberEn) {
        viewModelScope.launch {
            data.save()
        }
    }
}