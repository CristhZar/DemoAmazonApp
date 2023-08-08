package com.cristh.amazondemostefanini.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer

fun Context.readRawResource( fileResourceID: Int): String {
    val rawJsonIS = resources.openRawResource(fileResourceID)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    rawJsonIS.use { rawJsonIS ->
        val reader: Reader = BufferedReader(InputStreamReader(rawJsonIS, "UTF-8"))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    }

    return writer.toString()
}