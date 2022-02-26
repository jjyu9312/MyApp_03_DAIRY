package com.kkuber.myapp_03_dairy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.System.putString
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePwdButton : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePwdButton)
    }

    private var changePwdMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if (changePwdMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다,", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pwdFromPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val pwdFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (pwdFromPreferences.getString("password", "000").equals(pwdFromUser)) {
                // 성공
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                // 실패
                showErrorAlertDialog()
            }
        }

        changePwdButton.setOnClickListener {

            val pwdFromPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            if (changePwdMode) {
                // 번호를 저장하는 기능
                pwdFromPreferences.edit(true) {
                    val pwdFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                    putString("password", pwdFromUser)
                }

                changePwdMode = false
                changePwdButton.setBackgroundColor(Color.BLACK)

            } else {
                // changePwdMode 활성화 :: 비밀번호가 맞는 지 체크
                val pwdFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

                if (pwdFromPreferences.getString("password", "000").equals(pwdFromUser)) {
                    // 성공
                    changePwdMode = true
                    Toast.makeText(this, "변경할 패스워드로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    changePwdButton.setBackgroundColor(Color.RED)
                    // startActivity()
                } else {
                    // 실패
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인") { _, _ -> } // 굳이 사용하지 않을 때 _ 로 대체
            .create()
            .show()
    }
}
