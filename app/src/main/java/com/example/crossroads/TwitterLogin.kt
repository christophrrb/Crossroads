package com.example.crossroads

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.activity_twitter_login.*
import kotlinx.android.synthetic.main.content_twitter_login.*
import com.twitter.sdk.android.core.TwitterSession
import android.content.Intent
import android.os.Vibrator
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import org.jetbrains.anko.toast


class TwitterLogin : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_twitter_login)
		setSupportActionBar(toolbar)

		Twitter.initialize(this)

		login_button.setCallback(object : Callback<TwitterSession>() {
			override fun success(result: Result<TwitterSession>) {
				var session: TwitterSession = TwitterCore.getInstance().sessionManager.activeSession
				var authToken = session.authToken
				var token = authToken.token
				var secret = authToken.secret

				compose_tweet()

			}

			override fun failure(exception: TwitterException) {
				toast("The login failed. Please try again.")
			}
		})

//		val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//		vibrator.vibrate(2000)

//		fab.setOnClickListener { view ->
//			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//					.setAction("Action", null).show()
//		}
	}

	fun compose_tweet() {
		var session: TwitterSession = TwitterCore.getInstance().sessionManager.activeSession
		var intent = ComposerActivity.Builder(this@TwitterLogin)
				.session(session)
				.text("@CRNews_DeKalb")
				.createIntent()
		startActivity(intent)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		super.onActivityResult(requestCode, resultCode, data)

		// Pass the activity result to the login button.
		login_button.onActivityResult(requestCode, resultCode, data)
	}

}
