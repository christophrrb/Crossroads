package com.example.crossroads

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL

/**
 * Created by Owner on 12/22/2017.
 */
class ArticleText : AsyncTask<Int, Void, String>() {
	override fun doInBackground(vararg i: Int?): String {
		var doc: Document = Jsoup.connect("http://www.crossroadsnews.com/news").get();
		var doc_string = "http://www.crossroadsnews.com/news"
		var article = doc.getElementsByTag("article");
		var iterate: Int = 0;
		var new_url: String = ""
		var text: String = "\n"

		for (result in i) {
			iterate = result!!;
			Log.e("adapterPosition", "$iterate")
		}

		try {
			new_url = (doc_string + article[iterate].child(0).child(0).child(0).child(0).child(0).child(0).attr("href"))
		} catch (e: Exception) {
			try {
				new_url = (doc_string + article[iterate].child(0).child(0).child(0).child(0).child(0).attr("href"))
			} catch (e: Exception) {
				new_url = (doc_string + article[iterate].child(0).child(0).child(1).child(0).child(0).attr("href"))
			}
		}

		Log.e("URL", new_url)
		Log.e("adapterPosition", "$iterate")

		var doc_two: Document = Jsoup.connect(new_url).get()
		var article_two = doc_two.getElementsByClass("subscriber-preview")
		var article_two_sub = doc_two.getElementsByClass("subscriber-only")

		//Get Subscriber-Preview Content
		for (result in article_two) {
			try {
				text += result.child(0).text().trim() + "\n\n"
			} catch (e: Exception) {
				Log.e("Could't Get Paragraph", e.toString())
			}
		}

		//Get Subscriber-Only Content
		for (result in article_two_sub) {
			try {
				text += result.child(0).text().trim() + "\n\n"
			} catch (e: Exception) {
				Log.e("Couldn't Get Paragraph", e.toString())
			}
		}

		Log.w("Text", "$text")

		if (text.trim().equals("")) {
			text = "This article contains no text."
		}

		return text
	}
}