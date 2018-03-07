package com.example.crossroads

/**
 * Created by Owner on 12/22/2017.
 */
/* Return one Tweet */
//        val tweetId = 510908133917487104L
//        TweetUtils.loadTweet(tweetId, object : Callback<Tweet>() {
//            override fun success(result: Result<Tweet>) {
//                layout.addView(TweetView(this@MainActivity, result.data))
//            }
//
//            override fun failure(exception: TwitterException) {
//                // Toast.makeText(...).show();
//            }
//        })

/*Return a user timeline*/
//  Twitter.initialize(this)
//
//
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val userTimeline : UserTimeline = UserTimeline.Builder()
//                .screenName("CRNews_DeKalb")
//                .build();
//
//        val adapter = TweetTimelineRecyclerViewAdapter.Builder(this)
//                .setTimeline(userTimeline)
//                .setViewStyle(R.style.tw__TweetLightStyle)
//                .build()
//
//        recyclerView.adapter = adapter;

/*Old Image Setting Code*/
//			try {
//				if (article.get()?.get(viewHolder.adapterPosition)?.child(0)?.child(0)?.childNodeSize()  == 3) {
//					viewHolder.itemImage.setImageBitmap(images[viewHolder.adapterPosition])
//					/*Tell list position too?*/Log.e("Child Node Size", "adapterPosition ${viewHolder.adapterPosition} has a child node size of ${article.get()?.get(viewHolder.adapterPosition)?.child(0)?.child(0)?.childNodeSize()}")
//				} else {
//					viewHolder.itemImage.setImageBitmap(null)
//				}
//			} catch (e: Exception) {
//			Log.e("The image didn't load.", "${e.toString()}")
//			}