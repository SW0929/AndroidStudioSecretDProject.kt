package com.example.secrectdiarykotlin

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class SecretD: AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val userEditText: EditText by lazy {
        findViewById(R.id.UserEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secret_diary)

        initDetailEditText()

    }


    private fun initDetailEditText(){
        val detail = getSharedPreferences("diary", Context.MODE_PRIVATE)
        userEditText.setText(detail.getString("detail", ""))
        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit(true) {
                   putString("detail", userEditText.text.toString())
            }
        }
        userEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}