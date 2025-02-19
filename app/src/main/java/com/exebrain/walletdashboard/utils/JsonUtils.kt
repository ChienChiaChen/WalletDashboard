package com.exebrain.walletdashboard.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

@SuppressLint("StaticFieldLeak")
object JsonUtils {
    var context: Context? = null
    val gson = Gson()

    // 初始化方法，傳遞 context
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    // 解析 JSON 文件並返回結果
     inline fun <reified T> parseJsonFromFile(fileName: String): T? {
        return try {
            // 取得 InputStream 並解析
            val inputStream = context?.assets?.open(fileName)
            inputStream?.bufferedReader()?.use { reader -> // 使用 use 來自動關閉 InputStream 和 Reader
                gson.fromJson(reader, T::class.java)
            }
        } catch (e: IOException) {
            // 讀取文件錯誤
            e.printStackTrace()
            null
        } catch (e: Exception) {
            // 解析錯誤
            e.printStackTrace()
            null
        }
    }
}