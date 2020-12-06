package com.video.allvideodownloaderbt.fragments

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.AdListener
import com.video.allvideodownloaderbt.R
import com.video.allvideodownloaderbt.adapters.Adapter_VideoFolder
import com.video.allvideodownloaderbt.models.Model_Video
import com.video.allvideodownloaderbt.utils.Constants.DOWNLOAD_DIRECTORY
import com.video.allvideodownloaderbt.utils.Constants.MY_ANDROID_10_IDENTIFIER_OF_FILE
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.io.File
import java.util.concurrent.TimeUnit


class galleryMain : Fragment() {
    var obj_adapter: Adapter_VideoFolder? = null
    var al_video = ArrayList<Model_Video>()
    var recyclerView1: RecyclerView? = null
    var recyclerViewLayoutManager: RecyclerView.LayoutManager? = null


    private val KEY_RECYCLER_STATE = "recycler_state"
    var listState: Parcelable? = null


    var fbdView: AdView? = null

    private var fbInterstitialAd: com.facebook.ads.InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_gallery, container, false)

        recyclerView1 = view.recyclerView



        recyclerViewLayoutManager = GridLayoutManager(activity, 3)
        recyclerView1!!.layoutManager = recyclerViewLayoutManager


        // addFbAd();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.let { fn_video_android_10(it, requireActivity(), true) }

        } else {
            // activity?.let { fn_video(it, requireActivity(), true) }
            activity?.let { fn_video_android_10(it, requireActivity(), true) }

        }


        return view
    }


    //TODO Call this function to show Facebook ads
    private fun addFbAd() {


        //ads
        fbInterstitialAd = com.facebook.ads.InterstitialAd(
            activity,
            resources.getString(R.string.fbAdmobInterstitial)
        )
        // Set listeners for the Interstitial Ad
        // Set listeners for the Interstitial Ad
        val adListener = object : AdListener(), com.facebook.ads.AdListener,
            InterstitialAdListener {
            override fun onError(ad: Ad, adError: AdError) {
                Toast.makeText(
                    activity,
                    "Facebook App Is not Installed OR " + adError.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(ContentValues.TAG, "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad
                if (fbInterstitialAd!!.isAdLoaded) {
                    fbInterstitialAd!!.show()
                } else {
                    fbInterstitialAd!!.loadAd()
                }
            }

            override fun onAdClicked(ad: Ad) {

            }

            override fun onLoggingImpression(ad: Ad) {

            }

            override fun onInterstitialDisplayed(p0: Ad?) {
                TODO("Not yet implemented")
            }

            override fun onInterstitialDismissed(p0: Ad?) {
                TODO("Not yet implemented")
            }
        }

        val loadAdConfig = fbInterstitialAd?.buildLoadAdConfig()
            ?.withAdListener(adListener)
            ?.build()

        fbInterstitialAd?.loadAd(loadAdConfig)

    }


    public fun fn_video(cn: Context, activity: FragmentActivity, f: Boolean) {
        al_video = ArrayList<Model_Video>()
        val int_position = 0
        val uri: Uri
        val cursor: Cursor
        val column_index_data: Int
        val column_index_folder_name: Int
        val column_id: Int
        val thum: Int
        val duration: Int

        var absolutePathOfImage: String? = null
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val condition = MediaStore.Video.Media.DATA + " like?"
        val selectionArguments = arrayOf("%$DOWNLOAD_DIRECTORY%")

//        val condition = MediaStore.Video.Media.DATA + " like? "+ Constants.MY_ANDROID_10_IDENTIFIER_OF_FILE
//        val selectionArguments = arrayOf("%${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}%")
//
        val sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC"
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Video.Media.DURATION
        )
        cursor = cn.contentResolver
            .query(uri, projection, condition, selectionArguments, "$sortOrder")!!

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)
        duration = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        var i: Int = 0
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            Log.e("Column", absolutePathOfImage)
            Log.e("Folder", cursor.getString(column_index_folder_name))
            Log.e("column_id", cursor.getString(column_id))
            Log.e("thum", cursor.getString(thum))
            Log.e("duration", cursor.getString(duration))

            try {


                val mp: MediaPlayer = MediaPlayer.create(
                    activity, FileProvider.getUriForFile(
                        activity,
                        activity.applicationContext.packageName + ".provider",
                        File(absolutePathOfImage)
                    )
                )

                val durationnew: Int = mp.duration


                if (absolutePathOfImage.contains(MY_ANDROID_10_IDENTIFIER_OF_FILE)) {
                    val obj_model = Model_Video()
                    obj_model.isBoolean_selected = false
                    obj_model.str_path = absolutePathOfImage
                    obj_model.str_thumb = cursor.getString(thum)
                    obj_model.duration = durationnew
                    obj_model.id = i

                    al_video.add(obj_model)

                    i = i + 1
                } else {
                    val obj_model = Model_Video()
                    obj_model.isBoolean_selected = false
                    obj_model.str_path = absolutePathOfImage
                    obj_model.str_thumb = cursor.getString(thum)
                    obj_model.duration = durationnew
                    obj_model.id = i

                    al_video.add(obj_model)
                    i = i + 1
                }


            } catch (e: Exception) {
                //iUtils.ShowToast(context, "Gallery Load Error")
            }
        }


        obj_adapter = Adapter_VideoFolder(cn, al_video, activity)

        recyclerView1!!.adapter = null
        recyclerView1!!.adapter = obj_adapter
        obj_adapter!!.notifyDataSetChanged()

//
//        //recyclerView1!!.setLayoutManager(null);
//        recyclerView1!!.getRecycledViewPool().clear();
//        recyclerView1!!.swapAdapter(obj_adapter, false);
//       // recyclerView1!!.setLayoutManager(layoutManager);
//        obj_adapter!!.notifyDataSetChanged();


    }


    public fun fn_video_android_10(cn: Context, activity: FragmentActivity, f: Boolean) {
        al_video = ArrayList<Model_Video>()
        val int_position = 0
        val uri: Uri
        val cursor: Cursor
        val column_index_data: Int
        val column_index_folder_name: Int
        val column_id: Int
        val thum: Int
        val duration: Int

        var absolutePathOfImage: String? = null
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val condition = MediaStore.Video.Media.DATA + " like?"
        val selectionArguments =
            arrayOf("%${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}%")

//        val condition = MediaStore.Video.Media.DATA + " like? "+ Constants.MY_ANDROID_10_IDENTIFIER_OF_FILE
//        val selectionArguments = arrayOf("%${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}%")
//
        val sortOrder = MediaStore.Video.Media.DATE_TAKEN + " DESC"
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Video.Media.DURATION
        )
        cursor = cn.contentResolver
            .query(uri, projection, condition, selectionArguments, "$sortOrder")!!

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        column_index_folder_name =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)
        duration = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)
        var i: Int = 0
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            Log.e("Column", absolutePathOfImage)
            Log.e("Folder", cursor.getString(column_index_folder_name))
            Log.e("column_id", cursor.getString(column_id))
            Log.e("thum", cursor.getString(thum))


            try {

                val mp: MediaPlayer = MediaPlayer.create(
                    activity, FileProvider.getUriForFile(
                        activity,
                        activity.applicationContext.packageName + ".provider",
                        File(absolutePathOfImage)
                    )
                )

                val durationnew: Int = mp.duration
                mp.release()


                Log.e("duration", cursor.getString(duration) + "")
                Log.e("durationnew", durationnew.toString())


                val minutes = TimeUnit.MILLISECONDS.toMinutes(durationnew.toLong())

                // long seconds = (milliseconds / 1000);
                val seconds = TimeUnit.MILLISECONDS.toSeconds(durationnew.toLong())

                if (absolutePathOfImage.contains(MY_ANDROID_10_IDENTIFIER_OF_FILE)) {
                    val obj_model = Model_Video()
                    obj_model.isBoolean_selected = false
                    obj_model.str_path = absolutePathOfImage
                    obj_model.str_thumb = cursor.getString(thum)
                    obj_model.duration = durationnew
                    obj_model.id = i

                    al_video.add(obj_model)

                    i = i + 1
                }
            } catch (e: Exception) {

            }

        }


        obj_adapter = Adapter_VideoFolder(cn, al_video, activity)

        recyclerView1!!.adapter = null
        recyclerView1!!.adapter = obj_adapter
        obj_adapter!!.notifyDataSetChanged()

//
//        //recyclerView1!!.setLayoutManager(null);
//        recyclerView1!!.getRecycledViewPool().clear();
//        recyclerView1!!.swapAdapter(obj_adapter, false);
//       // recyclerView1!!.setLayoutManager(layoutManager);
//        obj_adapter!!.notifyDataSetChanged();


    }


//    override fun setMenuVisibility(visible: Boolean) {
//        super.setMenuVisibility(visible)
//        if (visible) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                fn_video_android_10(activity, requireActivity(), true)
//
//            } else {
//             //   fn_video(activity, requireActivity(), true)
//            }
//        }
//    }


    override fun onResume() {
        super.onResume()
        Log.e("resume", "12412535")

    }

}

