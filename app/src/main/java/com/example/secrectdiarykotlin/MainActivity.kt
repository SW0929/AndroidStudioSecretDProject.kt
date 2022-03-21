package com.example.secrectdiarykotlin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstNumberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.firstNumberPicker).apply {
            maxValue = 9
            minValue = 0
        }
    }
    private val secondNumberPicker:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.secondNumberPicker).apply {
            maxValue = 9
            minValue = 0
        }
    }
    private val thirdNumberPicker:NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.thirdNumberPicker).apply {
            maxValue = 9
            minValue = 0
        }
    }
    private val openButton: AppCompatButton by lazy {
        findViewById(R.id.openButton)
    }
    private val changePasswordButton: AppCompatButton by lazy {
        findViewById(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNumberPicker()
        initOpenButton()
        initChangePasswordButton()
    }

    private fun initNumberPicker(){
        firstNumberPicker
        secondNumberPicker
        thirdNumberPicker
    }

    private fun initOpenButton(){
        openButton.setOnClickListener {

            if(changePasswordMode){
                Toast.makeText(this, "비밀번호 변경 모드입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val password = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if(sharedPreferences.getString("password", "000").equals(password)){
                startActivity(Intent(this, SecretD::class.java))
            }else{
                alertError()
            }
        }
    }

    private fun initChangePasswordButton(){
        changePasswordButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${firstNumberPicker.value}${secondNumberPicker.value}${thirdNumberPicker.value}"

            if(changePasswordMode){
                sharedPreferences.edit(true){
                    putString("password", passwordFromUser)
                }
                changePasswordButton.setBackgroundColor(Color.BLACK)
                changePasswordMode = false
            }
            else{
                if(sharedPreferences.getString("password", "000").equals(passwordFromUser)){
                    Toast.makeText(this, "변경할 패스워드를 입력하세요.", Toast.LENGTH_SHORT).show()
                    changePasswordMode = true
                    changePasswordButton.setBackgroundColor(Color.RED)

                }
                else{
                    alertError()
                    return@setOnClickListener
                }
            }

        }
    }

    private fun alertError(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 옳바르지 않습니다.")
            .setPositiveButton("확인"){_, _ -> }
            .create()
            .show()
    }

}