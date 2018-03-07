package com.example.crossroads

import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.TwitterException;

/**
 * Created by Owner on 12/22/2017.
 */
class Tweets: AsyncTask<Void, Int, Elements?>() {
    var exception: Exception? = null;

    override fun doInBackground(vararg p0: Void?): Elements? {
		var article: Elements? = null;
		try {
			var doc: Document = Jsoup.connect("http://www.crossroadsnews.com/news").get();
			article = doc.getElementsByTag("article");
		} catch (e: Exception) {
			println(e)
		}

		return article;
    }
}