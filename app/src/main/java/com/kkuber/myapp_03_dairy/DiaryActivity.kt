package com.kkuber.myapp_03_dairy

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    /*
    private val diaryEditText : EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }
    */

    // 메인(UI) 스레드와 연결하는 Handler
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryEditText.text.toString())
            }

            Log.d("DiaryActivity", "onCreate : SAVE!!!");
        }

        diaryEditText.addTextChangedListener {
            Log.d("DiaryActivity", "onCreate : TextChanged :: $it");
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500) // 0.5초에 한번씩 runnable 수행
        }
    }
}