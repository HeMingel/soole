package com.songpo.searched.wxpay.util

import java.security.MessageDigest

object MD5Util {

    private val hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (aB in b) {
            resultSb.append(byteToHexString(aB))
        }

        return resultSb.toString()
    }

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0) {
            n += 256
        }
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

    fun MD5Encode(origin: String, charsetname: String?): String? {
        var resultString: String? = null
        try {
            resultString = origin
            val md = MessageDigest.getInstance("MD5")
            if (charsetname == null || "" == charsetname) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .toByteArray()))
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .toByteArray(charset(charsetname))))
            }
        } catch (exception: Exception) {
        }

        return resultString
    }

}

