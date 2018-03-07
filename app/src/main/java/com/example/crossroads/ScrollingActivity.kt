package com.example.crossroads

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import org.jetbrains.anko.*

class ScrollingActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_scrolling)
		setSupportActionBar(toolbar)

		val extras = intent.extras
		val value: Int?
		lateinit var title: String

		if (extras.getString("title") != null) {
			title = extras.getString("title")
			value = extras.getInt("index")
			textForArticle.text = ArticleText().execute(value).get()
			setSupportActionBar(toolbar)
			supportActionBar?.title = title
		}

		fab.setOnClickListener { view ->
			var tweetActivity = Intent(view.context, TwitterLogin::class.java)
			tweetActivity.putExtra("articleTitle", title)
			startActivity(tweetActivity)
		}

		if (extras.get("picture") != null) {
			try {
				var f: File = File(extras.getString("picture"), "scrim.jpg")
				if (BitmapFactory.decodeStream(FileInputStream(f)) != null) {
					var b: Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
					backdrop.setImageBitmap(b)
				}
			} catch (e: FileNotFoundException) {
				e.printStackTrace()
			}
		}

//		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}
