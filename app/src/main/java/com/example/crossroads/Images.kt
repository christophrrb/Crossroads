package com.example.crossroads

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Process
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.content_main.view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL

/**
 * Created by Owner on 12/22/2017.
 */

	class Images : AsyncTask<Int, Void, MutableList<Bitmap?>>() {

		override fun onPreExecute() {
			super.onPreExecute()
			fun load(context: Context) {
				var intent = Intent(context, Loading::class.java)
				context.startActivity(intent)
			}
		}

		override fun doInBackground(vararg p0: Int?): MutableList<Bitmap?> {
			Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE)
			var bmp: Bitmap? = null;
			var doc: Document = Jsoup.connect("http://www.crossroadsnews.com/news").get();
			var doc_string = "http://www.crossroadsnews.com/news"
			var article = doc.getElementsByTag("article");
			var list: MutableList<Bitmap?> = mutableListOf()
			var failed_image = 0;

			try {
				var x = 0
				Log.e("Article Size", "${article.size}")
				while (x < article.size) {
					failed_image = x;
					try {
						if ((article[x].child(0).childNodeSize() == 4) && (article[x].child(0).child(0).child(0).child(0).child(0).child(0).childNodeSize() == 5)) {
							var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(0).child(0).child(1).attr("data-srcset")
							var url: URL = URL(data_srcset);
							Log.e("Bitmap URL", "${url.toString()}")
							bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
							list.add(bmp)
						} else if ((article[x].child(0).childNodeSize() == 4) && (article[x].child(0).child(0).child(0).child(0).child(0).child(0).childNodeSize() == 3)) {
							var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(0).child(0).child(0).attr("data-srcset")
							var url: URL = URL(data_srcset);
							Log.e("Bitmap URL", "${url.toString()}")
							bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
							list.add(bmp)
						} else if (article[x].child(0).child(0).childNodeSize() == 3) {
							var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(0).child(0).child(0).attr("data-srcset")
							var url: URL = URL(data_srcset);
							Log.e("Bitmap URL", "${url.toString()}")
							bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
							list.add(bmp)
						} else if (article[x].child(0).child(0).childNodeSize() == 7) {
							list.add(null)
						}
					} catch (e: Exception) {
						try {
							if (article[x].child(0).childNodeSize() == 4) {
								try {
									var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(0).child(0).child(0).attr("data-src")
									var url: URL = URL(data_srcset);
									Log.e("Bitmap URL", "${url.toString()}")
									bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
									list.add(bmp)
								} catch (e: Exception) {
									var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(0).child(0).child(1).attr("data-src")
									var url: URL = URL(data_srcset);
									Log.e("Bitmap URL", "${url.toString()}")
									bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
									list.add(bmp)
								}
							} else if (article[x].child(0).child(0).childNodeSize() == 3) {
								var data_srcset: String = article[x].child(0).child(0).child(0).child(0).child(1).attr("data-srcset")
								var url: URL = URL(data_srcset);
								Log.e("Bitmap URL", "${url.toString()}")
								bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
								list.add(bmp)
							} else {
								list.add(null)
							}
						} catch (e: Exception) {
							list.add(null)
						}
					}
					Log.e("Value of x", "$x")
					var progg: Int = x / article.size
					x++
				}
				Log.e("Bitmap Position", "${p0[0]} - p0")
			} catch (e: Exception) {
				Log.e("The image couldn't load because...", e.toString())
			}


			Log.e("Bitmap Index", "This bitmap list has indices of ${list.indices} with its last index being ${list.lastIndex}")
			return list;
		}
	}