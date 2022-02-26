package com.kkuber.myapp_03_dairy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.System.putString
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val secondNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val thirdNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePwdButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNumberPicker()
        initOpenButton()
        initChangePasswordButton()
    }

    private fun initNumberPicker() {
        firstNumberPicker
        secondNumberPicker
        thirdNumberPicker
    }

    private fun initOpenButton() {
        openButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 모드입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val password = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"
            Log.d("MainActivity", "initOpenButton : ${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}");
            Log.d("MainActivity", "initOpenButton : ${sharedPreferences.all}");

            if (password == sharedPreferences.getString("password", "000")) {
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorPopup()
            }
        }
    }

    private fun initChangePasswordButton() {
        val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

        changePasswordButton.setOnClickListener {
            if (changePasswordMode) {

                sharedPreferences.edit {
                    this.putString("password", "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}")
                    Log.d("MainActivity", "initChangePasswordButton : ${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}");
                    commit()
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)
            } else {
                val password = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

                if (password != sharedPreferences.getString("password", "000")) {
                    showErrorPopup()
                    return@setOnClickListener
                }

                changePasswordButton.setBackgroundColor(Color.RED)
                Toast.makeText(this, "변경할 비밀번호를 입력하고 다시 눌러주세요.", Toast.LENGTH_SHORT).show()
                changePasswordMode = true
            }
        }
    }


    private fun showErrorPopup() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}
