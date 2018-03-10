package com.example.crossroads

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.os.Process
import android.support.v4.content.ContextCompat.*
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.net.URL

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Owner on 12/22/2017.
 */

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    val article = Tweets().execute()
	var titles: MutableList<String> = mutableListOf()
//	var doc: Document = Jsoup.connect("http://www.crossroadsnews.com/news").get();
//	var jsoup_article = doc.getElementsByTag("article");

	val itemView = R.layout.card_layout
	val mainView = R.layout.activity_main

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

			itemView.setOnClickListener {v: View ->
				val myActivity = Intent(v.getContext(), ScrollingActivity::class.java)
				myActivity.putExtra("index", adapterPosition)
				myActivity.putExtra("title", itemTitle.text)

				//Save Picture
				fun saveToInternalStorage(bitmapImage: Bitmap?): String {
					var cw: ContextWrapper = ContextWrapper(v.context)
					var directory: File = cw.getDir("imgDir", Context.MODE_PRIVATE)
					var myPath: File = File(directory, "scrim.jpg")

					lateinit var fos: FileOutputStream
					try {
						fos = FileOutputStream(myPath)
						bitmapImage?.compress(Bitmap.CompressFormat.PNG, 100, fos)
					} catch (e: Exception) {
						e.printStackTrace()
					} finally {
						try {
							fos.close()
						} catch (e: IOException) {
							e.printStackTrace()
						}
					}
					Log.w("Abs. Path", directory.absolutePath)
					Log.w("Abs. Path", myPath.absolutePath)
					return directory.absolutePath
				}

				myActivity.putExtra("picture", saveToInternalStorage(images[adapterPosition - 1]))

				v.getContext().startActivity(myActivity);
			}
        }

    }

	var images = Images().execute().get()

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

		fun validTitle(title: String?): Boolean {
			if (
			/*Contains "hrs"*/ (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.contains("hrs")) ||
			/*Contains "hrs"*/ (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.equals("DeKalb County lifts boil water advisory")) ||
			/*Equals the string*/ (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.equals("To view our latest e-Edition click the image on the left.")) ||
			/*Already a listed title*/ (titles.contains(article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!)) ||
			/*Empty Title*/ (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.equals("")) ||
			/*Empty Title*/ (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.equals(null))
			) {
				return false;
			} else {
				return true;
			}
		}

		//Titles
		var createViewHolder = true
		try {
			if ((article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 11) || (article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 9)) {
				var title: String? = article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()
				if (validTitle(title) == false) {
					createViewHolder = false
				} else {
					var title: String? = article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()
					viewHolder.itemTitle.text = article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()
					titles.add(title!!)
				}
			} else if ((article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 7) || (article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 5)) {
				if ((article.get()?.get(i)?.child(0)?.child(1)?.child(0)?.child(0)?.child(0)?.text()?.trim()!!.contains("hrs")) || (article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.equals("To view our latest e-Edition click the image on the left.")) || (titles.contains(article.get()?.get(i)?.child(0)?.child(1)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!))) {
					createViewHolder = false
				} else {
					viewHolder.itemTitle.text = article.get()?.get(i)?.child(0)?.child(1)?.child(0)?.child(0)?.child(0)?.text()?.trim()
					var title: String? = article.get()?.get(i)?.child(0)?.child(1)?.child(0)?.child(0)?.child(0)?.text()?.trim()
					titles.add(title!!)
				}
			}
		} catch (e: IndexOutOfBoundsException) {
//			try {
////				if (article.get()?.get(i)?.child(0)?.child(0)?.child(1)?.child(0)?.child(0)?.text()?.trim()!!.contains("hrs") || (titles.contains(article.get()?.get(i)?.child(0)?.child(0)?.child(0)?.child(0)?.child(0)?.text()?.trim()!!))) {
////					createViewHolder = false
////				} else {
//					viewHolder.itemTitle.text = article.get()?.get(i)?.child(0)?.child(0)?.child(1)?.child(0)?.child(0)?.text()?.trim()
//					var title: String? = article.get()?.get(i)?.child(0)?.child(0)?.child(1)?.child(0)?.child(0)?.text()?.trim()
//					titles.add(title!!)
////				}
//			} catch (e: Exception) {
//				Log.w("No title for article $i", e)
//				createViewHolder = false;
//			}

			try {
				if (article.get()?.get(i)?.child(0)?.child(0)?.child(0)?.child(0)?.child(0)?.text()?.trim()!!.contains("hrs") || (titles.contains(article.get()?.get(i)?.child(0)?.child(0)?.child(0)?.child(0)?.child(0)?.text()?.trim()!!))) {
					createViewHolder = false
				} else {
					viewHolder.itemTitle.text = article.get()?.get(i)?.child(0)?.child(0)?.child(0)?.child(0)?.child(0)?.text()?.trim()
					var title: String? = article.get()?.get(i)?.child(0)?.child(0)?.child(0)?.child(0)?.child(0)?.text()?.trim()
					titles.add(title!!)
				}
			} catch (e: Exception) {
				try {
					viewHolder.itemTitle.text = article.get()?.get(i)?.child(0)?.child(0)?.child(1)?.child(0)?.child(0)?.text()?.trim()
					var title: String? = article.get()?.get(i)?.child(0)?.child(0)?.child(1)?.child(0)?.child(0)?.text()?.trim()
					titles.add(title!!)
				} catch (e: Exception) {
					Log.w("No title for article $i", e)
					Log.w("0 Child Node Size of Article $i", "${article.get()?.get(i)?.child(0)?.child(0)?.childNodeSize()}")
					createViewHolder = false;
				}
			}
		}

		//Leads
		if (createViewHolder) {
			try {
				if (article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 11) {
					viewHolder.itemDetail.text = article.get()?.get(i)?.child(0)?.child(1)?.child(4)?.child(0)?.text()?.trim();
				} else if (article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 9) {
					viewHolder.itemDetail.text = article.get()?.get(i)?.child(0)?.child(1)?.child(3)?.child(0)?.text()?.trim();
				} else if (article.get()?.get(i)?.child(0)?.child(1)?.childNodeSize() == 7) {
					viewHolder.itemDetail.text = article.get()?.get(i)?.child(0)?.child(1)?.child(2)?.child(0)?.text()?.trim();
				}
			} catch (e: Exception) {
				if (article.get()?.get(i)?.child(0)?.childNodeSize() == 3) {
					viewHolder.itemDetail.text = article.get()?.get(i)?.child(0)?.child(0)?.child(2)?.child(0)?.text()?.trim();
				} else if (article.get()?.get(i)?.child(0)?.childNodeSize() == 2) {
					viewHolder.itemDetail.text = article.get()?.get(i)?.child(0)?.child(0)?.child(4)?.child(0)?.text()?.trim();
				}
			} catch (e: Exception) {
				Log.e("Lead", "Supposedly this article has no lead...")
			}

			//Images
			try {
				viewHolder.itemImage.setImageBitmap(images[viewHolder.adapterPosition - 1])
			} catch (e: Exception) {
				Log.e("RecyclerView had a problem with the image.", "${e.stackTrace}")
			}
		}
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup?.context).inflate(R.layout.card_layout, viewGroup, false)

        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
		return 51
    }


}