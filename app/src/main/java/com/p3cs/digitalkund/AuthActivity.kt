package com.p3cs.digitalkund

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.p3cs.digitalkund.ui.auth.AuthFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment.newInstance())
                .commitNow()
        }
    }

}
