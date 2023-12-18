package co.tiagoaguiar.netflixremake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {
    private var mSplash_Timer = 4000 // Splash screen timer in milliseconds
    private val mTimeCounter = object : CountDownTimer(mSplash_Timer.toLong(), 100) {
        override fun onTick(p0: Long) {
            // Not used in this example
        }

        override fun onFinish() {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mTimeCounter.start()
    }


}