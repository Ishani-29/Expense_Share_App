package com.example.settleup_expense_share_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")


        findViewById<Button>(R.id.CNewGrpBtn).setOnClickListener() {
            auth.signOut()
            startActivity(Intent(this , MainActivity::class.java))

        }
        val buttonClick = findViewById<Button>(R.id.ViewGroups)
        buttonClick.setOnClickListener {
            val intent = Intent(this, grouplist::class.java)
            startActivity(intent)
        }

    }
}


