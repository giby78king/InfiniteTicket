package com.cpbl.infiniteticket

import MemberAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cpbl.infiniteticket.Function.crypt
import com.cpbl.infiniteticket.Model.Member.Companion.MemberList
import com.cpbl.infiniteticket.Module.Member.Domain.MemberEn
import com.cpbl.infiniteticket.ViewModel.VmMemberViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var vmMemberViewModel: VmMemberViewModel

    companion object {
        var showMemberDialog = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webFami.settings.javaScriptEnabled = true
        webFami.loadUrl("https://www.famiticket.com.tw/Home")
        webFami.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        vmMemberViewModel =
            ViewModelProvider(this).get(VmMemberViewModel::class.java)

        editMemberId.setOnLongClickListener {

            vmMemberViewModel.getDatas("editMemberId")
            vmMemberViewModel.memberDatas.observe(this) {
                Log.d("showMemberDialog", "open")
                if (showMemberDialog) {
                    showMemberDialog = false
                    val inflater =
                        LayoutInflater.from(this)
                            .inflate(R.layout.fragment_dialog_location, null)
                    val dialog = AlertDialog.Builder(this)
                        .setView(inflater)
                        .show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.setOnDismissListener {
                        showMemberDialog = true
                    }
                    val lp = WindowManager.LayoutParams()
                    val window: Window = dialog.window!!
                    lp.copyFrom(window.attributes)
                    lp.width = 825
                    window.attributes = lp
                    val rvListItem = inflater.findViewById<RecyclerView>(R.id.rvListItem)
                    rvListItem.layoutManager = FlexboxLayoutManager(this)
                    rvListItem.adapter = MemberAdapter(MemberList, this)

                    val btnMostUse = inflater.findViewById<Button>(R.id.btnFubon)
                    btnMostUse.setOnClickListener {

                        val conditionList =
                            MemberList.filter { it.Team == "Fubon" }.toMutableList()

                        val rvListItem = inflater.findViewById<RecyclerView>(R.id.rvListItem)
                        rvListItem.layoutManager = FlexboxLayoutManager(this)
                        rvListItem.adapter = MemberAdapter(conditionList, this)
                    }
                    val btnRecent = inflater.findViewById<Button>(R.id.btnRakuten)
                    btnRecent.setOnClickListener {

                        val conditionList =
                            MemberList.filter { it.Team == "Rakuten" }.toMutableList()

                        val rvListItem = inflater.findViewById<RecyclerView>(R.id.rvListItem)
                        rvListItem.layoutManager = FlexboxLayoutManager(this)
                        rvListItem.adapter = MemberAdapter(conditionList, this)
                    }
                }
            }

            true
        }

        btnInject.setOnClickListener {
            var memberId = editMemberId.text.toString().padStart(11, '0')
            editMemberId.setText(memberId)
            injectFamiTicket(memberId)
        }

        btnTicket.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.famiticket.com.tw/cms/login.aspx")))
        }

        btnVerify.setOnClickListener {
            injectETicketVerify()
        }

        btnVerify.setOnLongClickListener {
            when (btnVerify.text.toString()) {
                "VerifyRakuten" -> {
                    btnVerify.text = "VerifyFubon"
                }
                "VerifyFubon" -> {
                    btnVerify.text = "VerifyRakuten"
                }
                else -> {
                    btnVerify.text = "VerifyFubon"
                }
            }
            true
        }

        btnUpsertMember.setOnLongClickListener {
            val upsertObject = btnUpsertMember.text.toString()

            when (upsertObject) {
                "Upsert Fubon Member" -> {
                    btnUpsertMember.text = "Upsert Rakuten Member"
                }
                "Upsert Rakuten Member" -> {
                    btnUpsertMember.text = "Upsert Fubon Member"
                }
                else -> {
                    btnUpsertMember.text = "Upsert Fubon Member"
                }
            }

            true
        }

        btnUpsertMember.setOnClickListener {
            vmMemberViewModel.getDatas("UpsertMember")
            vmMemberViewModel.upsertMemberDatas.observe(this) {

                val upsertObject = btnUpsertMember.text.toString()
                var team = ""

                when (upsertObject) {
                    "Upsert Fubon Member" -> {
                        team = "Fubon"
                    }
                    "Upsert Rakuten Member" -> {
                        team = "Rakuten"
                    }
                    else -> {
                        team = "Fubon"
                    }
                }


                val conditionList =
                    MemberList.sortedByDescending { it.LastTime }.toMutableList()

                if (conditionList.size > 0) {
                    if (editMemberId.text.toString().isNullOrEmpty()) {
                        val memberId = conditionList[0].ID
                        editMemberId.setText(memberId)
                        injectFamiTicket(memberId)

                        Toast.makeText(
                            this,
                            "Already Inject Member：${memberId}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        upsertMember(editMemberId.text.toString().padStart(11, '0'), team)
                    }
                } else {
                    upsertMember(editMemberId.text.toString().padStart(11, '0'), team)
                }
            }
        }

        btnSeat.setOnClickListener {
            webFami.loadUrl("file:///android_asset/InfiniteTicket/FamiTicket.html")
        }

        btnHide.setOnClickListener {
            linearControl.visibility = View.GONE
        }
    }

    private fun injectFamiTicket(memberId: String) {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")
        timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
        val time = timeFormat.format(calendar.time)

        val sessionToken = crypt.encrypt("|$memberId||$time|Bill")

        CookieManager.setAcceptFileSchemeCookies(true)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.acceptCookie()
        Log.d("token", ":" + sessionToken)
        cookieManager.setCookie("https://www.famiticket.com.tw/", "SessionToken=$sessionToken")
        webFami.loadUrl("https://www.famiticket.com.tw/Home")
    }

    private fun injectETicketVerify() {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")
        timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
        val time = timeFormat.format(calendar.time)


        val sessionToken = when (btnVerify.text.toString()) {
            "VerifyFubon" -> {
                crypt.encrypt("|fbgt111|$time|$time|富邦育樂2||||121.136.106.121|")
            }
            "VerifyRakuten" -> {
                crypt.encrypt("|rakuten21|$time|$time|樂天桃猿||||121.77.65.121|")
            }
            else -> {
                crypt.encrypt("|fbgt111|$time|$time|富邦育樂2||||121.136.106.121|")
            }
        }


        CookieManager.setAcceptFileSchemeCookies(true)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.acceptCookie()
        Log.d("token", ":" + sessionToken)
        cookieManager.setCookie(
            "https://www.famiticket.com.tw/eticketverify/",
            "VerifyToken=$sessionToken"
        )
        webFami.loadUrl("https://www.famiticket.com.tw/eticketverify/")
    }

    private fun upsertMember(memberId: String, team: String) {

        val data = MemberEn(
            ID = memberId,
            LastTime = "",
            Team = team,
        )
        vmMemberViewModel.upsertOne(data)

    }
}