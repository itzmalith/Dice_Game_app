//https://drive.google.com/drive/folders/1CFnJTPRTQHNTtYeA3Ei91g1oXy5dBEtg?usp=share_link

package com.example.dice_app
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById(R.id.btn2) as Button
        btn.setOnClickListener(){
            val poppy = layoutInflater.inflate(R.layout.popup_mzg,null)

            val mypop = Dialog(this)
            mypop.setContentView(poppy)
            mypop.setCancelable(true)
            mypop.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mypop.show()

        }

        val newgamebtn = findViewById(R.id.btn1) as Button
        newgamebtn.setOnClickListener(){
            val Intent  = Intent(this,GameFace::class.java)
            startActivity(Intent)
        }
        val settingsbtn = findViewById(R.id.btn3) as Button
        settingsbtn.setOnClickListener(){
            val Intent  = Intent(this,Settingspage::class.java)
            startActivity(Intent)

        }

    }


}