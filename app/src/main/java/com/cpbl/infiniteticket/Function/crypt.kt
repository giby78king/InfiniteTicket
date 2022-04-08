package com.cpbl.infiniteticket.Function

import android.util.Log
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object crypt {

    private val KEY = byteArrayOf(
        56.toByte(),
        57.toByte(),
        99.toByte(),
        55.toByte(),
        99.toByte(),
        101.toByte(),
        49.toByte(),
        99.toByte(),
        55.toByte(),
        57.toByte(),
        98.toByte(),
        49.toByte(),
        52.toByte(),
        48.toByte(),
        54.toByte(),
        53.toByte(),
        97.toByte(),
        49.toByte(),
        57.toByte(),
        52.toByte(),
        49.toByte(),
        48.toByte(),
        97.toByte(),
        55.toByte(),
        99.toByte(),
        57.toByte(),
        55.toByte(),
        102.toByte(),
        50.toByte(),
        51.toByte(),
        57.toByte(),
        100.toByte()
    )
    private val IV_BYTES = byteArrayOf(
        144.toByte(),
        254.toByte(),
        149.toByte(),
        41.toByte(),
        139.toByte(),
        255.toByte(),
        69.toByte(),
        240.toByte(),
        150.toByte(),
        203.toByte(),
        89.toByte(),
        52.toByte(),
        228.toByte(),
        85.toByte(),
        236.toByte(),
        240.toByte()
    )

    fun encrypt(sSrc: String): String {
        val skeySpec = SecretKeySpec(KEY, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(IV_BYTES)
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec)
        val encrypted = cipher.doFinal(sSrc.toByteArray(charset("UTF_16LE")))
        return Base64.getEncoder().encodeToString(encrypted)
    }
    fun decrypt(sSrc: String): String {
        val data = Base64.getDecoder().decode(sSrc)
        val skeySpec = SecretKeySpec(KEY, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivSpec = IvParameterSpec(IV_BYTES)
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec)
        val original = cipher.doFinal(data)
        return String(original, Charsets.UTF_16LE)
    }
}