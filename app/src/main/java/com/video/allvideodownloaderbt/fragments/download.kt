package com.video.allvideodownloaderbt.fragments

import android.app.Activity.RESULT_OK
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.*
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.video.allvideodownloaderbt.*
import com.video.allvideodownloaderbt.Interfaces.UserListInStoryListner
import com.video.allvideodownloaderbt.adapters.ListAllStoriesOfUserAdapter
import com.video.allvideodownloaderbt.adapters.StoryUsersListAdapter
import com.video.allvideodownloaderbt.models.storymodels.*
import com.video.allvideodownloaderbt.receiver.Receiver
import com.video.allvideodownloaderbt.services.ClipboardMonitor
import com.video.allvideodownloaderbt.tasks.downloadFile
import com.video.allvideodownloaderbt.tasks.downloadVideo
import com.video.allvideodownloaderbt.utils.Constants
import com.video.allvideodownloaderbt.utils.Constants.PREF_CLIP
import com.video.allvideodownloaderbt.utils.Constants.STARTFOREGROUND_ACTION
import com.video.allvideodownloaderbt.utils.Constants.STOPFOREGROUND_ACTION
import com.video.allvideodownloaderbt.utils.SharedPrefsForInstagram
import com.video.allvideodownloaderbt.utils.iUtils
import com.video.allvideodownloaderbt.utils.iUtils.isPackageInstalled
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_download.view.*
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URI

@Keep
class download : Fragment(), RewardedVideoAdListener, UserListInStoryListner {
    private lateinit var listAllStoriesOfUserAdapter: ListAllStoriesOfUserAdapter
    private var storyUsersListAdapter: StoryUsersListAdapter? = null
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private lateinit var mInterstitialAd: InterstitialAd
    private var NotifyID = 1001

    private var csRunning = false
    lateinit var progressDralogGenaratinglink: ProgressDialog

    lateinit var prefEditor: SharedPreferences.Editor
    lateinit var pref: SharedPreferences

    var fbdView: AdView? = null


    var myVideoUrlIs: String? = null

    var myPhotoUrlIs: String? = null


    private var fbInterstitialAd: com.facebook.ads.InterstitialAd? = null


    private var clMain: LinearLayout? = null
    private var llTips: LinearLayout? = null
    private var llSearch: LinearLayout? = null
    private var cardView: CardView? = null
    private var purl: LinearLayout? = null
    private var ivLink: ImageView? = null
    private var vurl: LinearLayout? = null
    private var etURL: EditText? = null
    private var pbFetchingVideo: ProgressBar? = null
    private var btnDownload: Button? = null
    private var icInfoAutoDownload: ImageView? = null
    private var chkAutoDownload: CheckBox? = null
    private var checkboxlayoutinsta: RelativeLayout? = null
    private var icInfoDownloadPrivateMedia: ImageView? = null
    private var chkdownloadPrivateMedia: CheckBox? = null
    private var linlayoutInstaStories: LinearLayout? = null
    private var recUserList: RecyclerView? = null
    private var progressLoadingBar: ProgressBar? = null
    private var recStoriesList: RecyclerView? = null
    private var rvGallery: RelativeLayout? = null
    private var llFacebook: LinearLayout? = null
    private var fb1: ImageView? = null
    private var fb2: TextView? = null
    private var llTikTok: LinearLayout? = null
    private var llInstagram: LinearLayout? = null
    private var llTwitter: LinearLayout? = null
    private var likee: LinearLayout? = null
    private var llytdbtn: LinearLayout? = null
    private var llroposo: LinearLayout? = null
    private var llsharechat: LinearLayout? = null
    private var videomoreBtn: TextView? = null
    private var llMessage: LinearLayout? = null
    private var tvMessageTitle: TextView? = null
    private var tvMessageContent: TextView? = null
    private var cvDownload: CardView? = null
    private var llDownload: LinearLayout? = null
    private var imgAvatar: CircleImageView? = null
    private var tvAuthor: TextView? = null
    private var imgClose: ImageView? = null
    private var ivMusicNote: ImageView? = null
    private var tvMusic: TextView? = null
    private var tvDesc: TextView? = null
    private var llDownloadPercentage: LinearLayout? = null
    private var progressBar: ProgressBar? = null
    private var tvDownloadPercentage: TextView? = null
    private var llDownloadCompleted: LinearLayout? = null
    private var btnWatch: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_download, container, false)
        mInterstitialAd = InterstitialAd(context!!)
        mInterstitialAd.adUnitId = getString(R.string.AdmobInterstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())


        //fb add


        progressDralogGenaratinglink = ProgressDialog(activity)
        progressDralogGenaratinglink.setMessage(resources.getString(R.string.genarating_download_link))
        progressDralogGenaratinglink.setCancelable(false)

        //  addFbAd()

        pref = context!!.getSharedPreferences(PREF_CLIP, 0) // 0 - for private mode
        prefEditor = pref.edit()
        csRunning = pref.getBoolean("csRunning", false)

        createNotificationChannel(
            requireActivity(),
            NotificationManagerCompat.IMPORTANCE_LOW,
            true,
            getString(R.string.app_name),
            getString(R.string.aio_auto)
        )
//TODO
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context!!.packageName)
                )
                startActivityForResult(intent, 1234)
            }
        }
//TODO


        linlayoutInstaStories = view.findViewById(R.id.linlayout_insta_stories) as LinearLayout

        progressBar = view.findViewById(R.id.progressBar) as ProgressBar

        view.btnDownload.setOnClickListener { _ ->
            //Log.d("clicked","true")
            // view.btnDownload.visibility=View.GONE
            //  pbFetchingVideo.visibility=View.VISIBLE
//TODO Facebook comment the finction call code below

            if (Constants.show_facebookads) {
                addFbAd()
            }

            val url = view?.etURL.text.toString()
            DownloadVideo(url)
//            val url = etURL.text.toString()
//            if (!url.equals("")) {
//                //  if (url.contains("https://") || url.contains("http://"))
//                DownloadVideo(url)
//
//            } else {
//                iUtils.ShowToast(
//                    context!!,
//                    activity?.resources?.getString(R.string.url_not_suppotr)
//                )
//            }


        }

        if (activity != null) {

            val activity: MainActivity? = activity as MainActivity?
            val strtext: String? = activity?.getMyData()

            println("mydatvgg222 " + strtext)
            if (strtext != null && !strtext.equals("")) {


                view?.etURL.setText(strtext)
                val url = view?.etURL.text.toString()
                DownloadVideo(url)

            }
        }




        view.llFacebook.setOnClickListener { _ ->
            openAppFromPackedge(
                "com.facebook.katana",
                "facebook:/newsfeed",
                activity!!.resources.getString(R.string.install_fb)
            )


        }

        view.llTikTok.setOnClickListener { _ ->

            openAppFromPackedge(
                "com.zhiliaoapp.musically",
                "https://www.tiktok.com/",
                activity!!.resources.getString(R.string.install_tik)
            )


        }
        view.llInstagram.setOnClickListener { _ ->


            openAppFromPackedge(
                "com.instagram.android",
                "https://www.instagram.com/",
                activity!!.resources.getString(R.string.install_ins)
            )


        }
        view.llTwitter.setOnClickListener { _ ->

            openAppFromPackedge(
                "com.twitter.android",
                "https://www.twitter.com/",
                activity!!.resources.getString(R.string.install_twi)
            )


        }

        if (!Constants.showyoutube) {
            view.llytdbtn.visibility = View.GONE
        }

        view.llytdbtn.setOnClickListener { _ ->


            openAppFromPackedge(
                "com.google.android.youtube",
                "https://www.youtube.com/",
                activity!!.resources.getString(R.string.install_ytd)
            )


        }

        view.rvGallery.setOnClickListener { _ ->


            startActivity(Intent(context, GalleryActivity::class.java))


        }


        view.llroposo.setOnClickListener { _ ->


            openAppFromPackedge(
                "com.roposo.android",
                "https://www.roposo.com/",
                activity!!.resources.getString(R.string.install_roposo)
            )


        }

        view.llsharechat.setOnClickListener { _ ->

            openAppFromPackedge(
                "in.mohalla.sharechat",
                "https://www.sharechat.com/",
                activity!!.resources.getString(R.string.install_sharechat)
            )
        }

        view.likee.setOnClickListener { _ ->

            openAppFromPackedge(
                "video.like",
                "https://likee.com/",
                activity!!.resources.getString(R.string.install_likee)
            )


        }

        view.videomore_btn.setOnClickListener { _ ->


            val intent = Intent(context, AllSupportedApps::class.java)

            startActivity(intent)


        }




        view.ivLink.setOnClickListener(fun(_: View) {
            val clipBoardManager =
                context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val primaryClipData = clipBoardManager.primaryClip
            val clip = primaryClipData?.getItemAt(0)?.text.toString()

            view?.etURL.text = Editable.Factory.getInstance().newEditable(clip)
            DownloadVideo(clip)
        })


        if (csRunning) {
            view.chkAutoDownload.isChecked = true
            startClipboardMonitor()
        } else {
            view.chkAutoDownload.isChecked = false
            stopClipboardMonitor()
        }





        view.chkAutoDownload.setOnClickListener { view ->

            val checked = view?.chkAutoDownload?.isChecked
            if (!checked!!) {
                view.chkAutoDownload?.isChecked = false
                stopClipboardMonitor()
            } else {
                showAdDialog()

            }

        }
        val sharedPrefsFor = SharedPrefsForInstagram(activity)
        if (sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE) == null) {
            sharedPrefsFor.clearSharePrefs()
        }


        val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
        if (map != null) {

            if (map.get(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN).equals("true")) {
                view.chkdownload_private_media.setChecked(true)
                linlayoutInstaStories?.visibility = View.VISIBLE
                getallstoriesapicall()
            } else {
                view.chkdownload_private_media.setChecked(false)
                linlayoutInstaStories?.visibility = View.GONE

            }
        }


        view.chkdownload_private_media.setOnClickListener { view ->


            val sharedPrefsForInstagram = SharedPrefsForInstagram(activity)

            val map = sharedPrefsForInstagram.getPreference(SharedPrefsForInstagram.PREFERENCE)


            if (map != null && !map.get(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN)
                    .equals("true")
            ) {
                val intent = Intent(
                    activity,
                    InstagramLoginActivity::class.java
                )
                startActivityForResult(intent, 200)
            } else {
                val ab = AlertDialog.Builder(
                    activity!!
                )
                ab.setPositiveButton(resources.getString(R.string.yes), object :
                    DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {


//                            val asfd:Boolean =  view.chkdownload_private_media.isChecked
//                            if (asfd)
//                            {
//                                view.chkdownload_private_media.setChecked(false)
//                            }else{
//                                view.chkdownload_private_media.setChecked(true)
//                            }


                        val sharedPrefsForInstagram2 = SharedPrefsForInstagram(activity)
                        val map2 =
                            sharedPrefsForInstagram2.getPreference(SharedPrefsForInstagram.PREFERENCE)

                        if (sharedPrefsForInstagram2.getPreference(SharedPrefsForInstagram.PREFERENCE) != null) {
                            sharedPrefsForInstagram2.clearSharePrefs()

                            linlayoutInstaStories?.visibility = View.GONE

                            if (map2 != null && map2.get(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN)
                                    .equals("true")
                            ) {
                                view.chkdownload_private_media.setChecked(true)
                            } else {
                                view.chkdownload_private_media.setChecked(false)
                                recUserList?.setVisibility(View.GONE)
                                recStoriesList?.setVisibility(View.GONE)

                            }
                            p0?.dismiss()

                            view.chkdownload_private_media.setChecked(false)

                        } else {
                            sharedPrefsForInstagram2.clearSharePrefs()

                        }

                    }
                })

                ab.setNegativeButton(
                    resources.getString(R.string.cancel),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id: Int) {
                            dialog.cancel()
                            val asfd: Boolean = view.chkdownload_private_media.isChecked
                            if (asfd) {
                                view.chkdownload_private_media.setChecked(false)
                            } else {
                                view.chkdownload_private_media.setChecked(true)
                            }
                        }
                    })
                val alert = ab.create()
                alert.setTitle("No Private Download ")
                alert.setMessage("Don't want to download media from private Account ")
                alert.show()


            }


//            val checked = view?.chkdownload_private_media?.isChecked
//
//
//
//            if (checked!!) {
//
//                view?.chkdownload_private_media?.isChecked = false
//
//
//                val ab = AlertDialog.Builder(
//                    activity!!
//                )
//                ab.setPositiveButton(resources.getString(R.string.yes), object :
//                    DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface, id: Int) {
//                        val sharedPrefsForInstagram2 = SharedPrefsForInstagram(activity)
//
//
//
//                        if (sharedPrefsForInstagram2 != null) {
//                            sharedPrefsForInstagram2.clearSharePrefs()
//
//                            view.chkdownload_private_media.setChecked(false)
//                            view.linlayout_insta_stories.visibility = View.GONE
//                        }
//
//
//                        dialog.cancel()
//                    }
//                })
//                ab.setNegativeButton(resources.getString(R.string.cancel), object :
//                    DialogInterface.OnClickListener {
//                    override fun onClick(dialog: DialogInterface, id: Int) {
//                        dialog.cancel()
//                        val dd = view?.chkdownload_private_media?.isChecked
//                        if (dd!!) {
//
//                            view.chkdownload_private_media.setChecked(true)
//
//                            view.linlayout_insta_stories.visibility = View.VISIBLE
//
//
//                        } else {
//
//
//                            view.chkdownload_private_media.setChecked(false)
//
//                           // view.linlayout_insta_stories.visibility = View.GONE
//
//                        }
//
//                    }
//                })
//                val alert = ab.create()
//                alert.setTitle("Remove")
//                alert.setMessage("Don't want to download media from private Account ?")
//                alert.show()
//
//
//            } else {
//                val intent = Intent(
//                    activity,
//                    InstagramLoginActivity::class.java
//                )
//                startActivityForResult(intent, 200)
//
//            }

        }



        view.bulb_icon.setOnClickListener {

            // getAllDataFormLink("", true)
            //  callGetShareChatDataURL().execute("")

            //   murl("http://sck.io/p/n1AcOs7M",activity)
        }


        view.search_story.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null && storyUsersListAdapter != null) {
                    storyUsersListAdapter!!.getFilter().filter(text)
                }
                return true
            }
        })






        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context!!)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()





        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }



        return view
    }


    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(
            getString(R.string.AdmobRewardID),
            AdRequest.Builder().build()
        )
    }

    fun openAppFromPackedge(packedgename: String, urlofwebsite: String, installappmessage: String) {


        if (isPackageInstalled(context!!, packedgename)) {

            try {
                val pm: PackageManager = activity!!.packageManager
                val launchIntent: Intent = pm.getLaunchIntentForPackage(packedgename)!!

                activity!!.startActivity(launchIntent);
            } catch (e: ActivityNotFoundException) {
                iUtils.ShowToast(
                    context!!,
                    activity?.resources?.getString(R.string.error_occord_while)
                )


                val uri = urlofwebsite
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                activity!!.startActivity(intent);

            }


        } else {
            iUtils.ShowToast(context!!, installappmessage)
            val appPackageName =
                packedgename // getPackageName() from Context or Activity object
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }


        }

    }


    fun addFbAd() {

        //ads
        fbInterstitialAd = com.facebook.ads.InterstitialAd(
            activity,
            resources.getString(R.string.fbAdmobInterstitial)
        )

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
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
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

    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // 3
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.e("loged112211", "Notificaion Channel Created!")
        }
    }

    private fun setNofication(b: Boolean) {

        if (b) {
            val channelId = "${context!!.packageName}-${context!!.getString(R.string.app_name)}"
            val notificationBuilder = NotificationCompat.Builder(context!!, channelId).apply {
                setSmallIcon(R.drawable.notification_template_icon_bg) // 3
                // setStyle(NotificationCompat.)
                setLargeIcon(
                    BitmapFactory.decodeResource(
                        context!!.resources,
                        R.drawable.notification_template_icon_bg
                    )
                )
                setContentTitle(activity?.resources?.getString(R.string.auto_download_title_notification)) // 4

                setContentText(activity?.resources?.getString(R.string.auto_download_title_notification_start)) // 5
                setOngoing(true)
                priority = NotificationCompat.PRIORITY_LOW // 7
                setSound(null)
                setOnlyAlertOnce(true)
                setAutoCancel(false)
                addAction(
                    R.drawable.navigation_empty_icon,
                    "Stop",
                    makePendingIntent("quit_action")
                )

                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                val pendingIntent = PendingIntent.getActivity(requireActivity(), 0, intent, 0)

                setContentIntent(pendingIntent)
            }
            with(NotificationManagerCompat.from(requireActivity())) {
                // notificationId is a unique int for each notification that you must define
                notify(NotifyID, notificationBuilder.build())

                Log.e("loged", "testing notification notify!")


            }


        } else {
            NotificationManagerCompat.from(requireActivity()).cancel(NotifyID)
        }
    }

    fun startClipboardMonitor() {
        prefEditor.putBoolean("csRunning", true)
        prefEditor.commit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(
                Intent(
                    requireContext(),
                    ClipboardMonitor::class.java
                ).setAction(STARTFOREGROUND_ACTION)
            )
        } else {
            requireActivity().startService(
                Intent(
                    requireContext(),
                    ClipboardMonitor::class.java
                )
            )
        }

    }

    fun stopClipboardMonitor() {
        prefEditor.putBoolean("csRunning", false)
        prefEditor.commit()

        requireActivity().stopService(
            Intent(
                requireContext(),
                ClipboardMonitor::class.java
            ).setAction(STOPFOREGROUND_ACTION)
        )


    }

    fun makePendingIntent(name: String): PendingIntent {
        val intent = Intent(requireActivity(), Receiver::class.java)
        intent.action = name
        return PendingIntent.getBroadcast(requireActivity(), 0, intent, 0)
    }

    fun DownloadVideo(url: String) {


        //if (iUtils.checkURL(url)) {
        if (url.equals("") && iUtils.checkURL(url)) {
            iUtils.ShowToast(context!!, activity?.resources?.getString(R.string.enter_valid))


        } else {


            Log.d("mylogissssss", "The interstitial wasn't loaded yet.")



            if (url.contains("instagram.com")) {
                progressDralogGenaratinglink.show()
                startInstaDownload(url)
            } else if (url.contains("myjosh.in")) {
                var myurl = url
                myurl = myurl.substring(myurl.indexOf("http")) ?: myurl
                myurl = myurl?.substring(
                    myurl.indexOf("http://share.myjosh.in/"),
                    myurl.indexOf("Download Josh for more videos like this!")
                ) ?: myurl


                downloadVideo.Start(context!!, myurl.trim(), false)
                Log.e("downloadFileName12", url.trim())
            } else if (url.contains("chingari")) {
                var myurl = url

                myurl = myurl?.substring(
                    myurl.indexOf("https://chingari.io/"),
                    myurl.indexOf("For more such entertaining")
                ) ?: myurl


                downloadVideo.Start(context!!, myurl.trim(), false)
                Log.e("downloadFileName12", myurl.trim())
            } else if (url.contains("sck.io") || url.contains("snackvideo")) {
                var myurl = url
                try {
                    if (myurl.length > 30) {
                        myurl = myurl?.substring(
                            myurl.indexOf("http"),
                            myurl.indexOf("Click this")
                        ) ?: myurl
                    }
                } catch (e: Exception) {

                }

                downloadVideo.Start(context!!, myurl.trim(), false)
                Log.e("downloadFileName12", myurl.trim())
            } else {
                Log.d("mylogissssss33", "Thebbbbbbbloaded yet.")

                var myurl = url
                try {
                    myurl = myurl.substring(myurl.indexOf("http")).trim() ?: myurl
                } catch (e: Exception) {

                }
                //  Log.e("downloadFileName12", myurl.trim())

                downloadVideo.Start(context!!, myurl, false)
            }


//            when (url) {
//                "instagram.com" -> {
//
//                }
//                "myjosh.in" -> {
//
//
//                }
//                else -> { // Note the block
//
//                }
//            }

//            if (url.contains("instagram.com")) {
//
//            } else {
//
//                downloadVideo.Start(context!!, url, false)
//            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("proddddd11111222 $resultCode __" + data)

        if (requestCode == 200 && resultCode == RESULT_OK) {

            println("proddddd11111 $resultCode __" + data)

            val sharedPrefsForInstagram = SharedPrefsForInstagram(activity)

            val map =
                sharedPrefsForInstagram.getPreference(SharedPrefsForInstagram.PREFERENCE)

            if (map != null) {
                println("proddddd11111  " + map.get(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN))

                if (!map.get(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN)
                        .equals("true")
                ) {
                    view?.chkdownload_private_media?.setChecked(false)
                    linlayoutInstaStories?.visibility = View.GONE


                } else {
                    view?.chkdownload_private_media?.setChecked(true)
                    linlayoutInstaStories?.visibility = View.VISIBLE
                    getallstoriesapicall()


                }
            }
        }
        try {
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun showAdDialog() {


        val dialogBuilder = AlertDialog.Builder(context!!)


        dialogBuilder.setMessage(getString(R.string.doyouseead))

            .setCancelable(false)

            .setPositiveButton(
                getString(R.string.watchad)
            ) { _, _ ->


                if (mRewardedVideoAd.isLoaded) {
                    mRewardedVideoAd.show()
                } else {

                    iUtils.ShowToast(
                        context!!,
                        activity?.resources?.getString(R.string.videonotavaliabl)
                    )

                    view?.chkAutoDownload?.isChecked = true
                    val checked = view?.chkAutoDownload?.isChecked

                    if (checked!!) {
                        Log.e("loged", "testing checked!")
                        startClipboardMonitor()
                    } else {
                        Log.e("loged", "testing unchecked!")


                        stopClipboardMonitor()
                        // setNofication(false);
                    }
                }


            }

            .setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.cancel()

                val checked = view?.chkAutoDownload?.isChecked
                if (checked!!) {
                    view?.chkAutoDownload?.isChecked = false
                }

            }


        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.enabAuto))
        alert.show()

    }


    //insta finctions


    fun startInstaDownload(Url: String) {

        var Urlwi: String? = ""
        try {

            val uri = URI(Url)
            Urlwi = URI(
                uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null,  // Ignore the query part of the input url
                uri.getFragment()
            ).toString()


        } catch (ex: java.lang.Exception) {
            iUtils.ShowToast(activity, "Please Enter A Valid Url")
            return
        }


        var urlwithoutlettersqp: String? = Urlwi

        urlwithoutlettersqp = "$urlwithoutlettersqp?__a=1"
        try {
            System.err.println("workkkkkkkkk 4")


            val sharedPrefsFor = SharedPrefsForInstagram(activity)
            val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
            if (map != null) {

                downloadInstagramImageOrVideodata(
                    urlwithoutlettersqp, "ds_user_id=" + map.get(
                        SharedPrefsForInstagram.PREFERENCE_USERID
                    )
                            + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID)
                )

            } else {


                downloadInstagramImageOrVideodata(urlwithoutlettersqp, "")

            }


        } catch (e: java.lang.Exception) {
            System.err.println("workkkkkkkkk 5")
            e.printStackTrace()
        }
    }


    private fun callStoriesDetailApi(UserId: String) {
        try {
            view?.progress_loading_bar?.visibility = View.VISIBLE

            val sharedPrefsFor = SharedPrefsForInstagram(activity)
            val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
            if (map != null) {

                getFullDetailsOfClickedFeed(
                    UserId,
                    "ds_user_id=" + map.get(SharedPrefsForInstagram.PREFERENCE_USERID)
                        .toString() + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID)
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    fun downloadInstagramImageOrVideodata(URL: String?, Cookie: String?) {


        AndroidNetworking.get(URL)
            .setPriority(Priority.LOW)
            .addHeaders("Cookie", Cookie)
            .addHeaders(
                "User-Agent",
                "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\""
            )
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    println("response1122334455_jsomobj:   ${response}")
                    try {
                        val listType: Type =
                            object : TypeToken<ModelInstagramResponse?>() {}.getType()
                        val modelInstagramResponse: ModelInstagramResponse = Gson().fromJson(
                            response.toString(),
                            listType
                        )


                        if (modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children != null) {
                            val modelGetEdgetoNode: ModelGetEdgetoNode =
                                modelInstagramResponse.modelGraphshortcode.shortcode_media.edge_sidecar_to_children

                            val modelEdNodeArrayList: List<ModelEdNode> =
                                modelGetEdgetoNode.modelEdNodes
                            for (i in 0 until modelEdNodeArrayList.size) {
                                if (modelEdNodeArrayList[i].modelNode.isIs_video) {
                                    myVideoUrlIs = modelEdNodeArrayList[i].modelNode.video_url
                                    downloadFile.DownloadingInsta(
                                        activity,
                                        myVideoUrlIs,
                                        iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                        ".mp4"
                                    )
                                    // etText.setText("");
                                    if (progressDralogGenaratinglink != null) {
                                        progressDralogGenaratinglink.dismiss()
                                    }



                                    myVideoUrlIs = ""
                                } else {
                                    myPhotoUrlIs =
                                        modelEdNodeArrayList[i].modelNode.display_resources[modelEdNodeArrayList[i].modelNode.display_resources.size - 1].src
                                    downloadFile.DownloadingInsta(
                                        activity,
                                        myPhotoUrlIs,
                                        iUtils.getImageFilenameFromURL(myPhotoUrlIs),
                                        ".png"
                                    )
                                    myPhotoUrlIs = ""
                                    if (progressDralogGenaratinglink != null) {
                                        progressDralogGenaratinglink.dismiss()
                                    }
                                    // etText.setText("");
                                }
                            }
                        } else {
                            val isVideo =
                                modelInstagramResponse.modelGraphshortcode.shortcode_media.isIs_video
                            if (isVideo) {
                                myVideoUrlIs =
                                    modelInstagramResponse.modelGraphshortcode.shortcode_media.video_url
                                downloadFile.DownloadingInsta(
                                    activity,
                                    myVideoUrlIs,
                                    iUtils.getVideoFilenameFromURL(myVideoUrlIs),
                                    ".mp4"
                                )
                                if (progressDralogGenaratinglink != null) {
                                    progressDralogGenaratinglink.dismiss()
                                }
                                myVideoUrlIs = ""
                            } else {
                                myPhotoUrlIs =
                                    modelInstagramResponse.modelGraphshortcode.shortcode_media.display_resources[modelInstagramResponse.modelGraphshortcode.shortcode_media.display_resources.size - 1].src
                                downloadFile.DownloadingInsta(
                                    activity,
                                    myPhotoUrlIs,
                                    iUtils.getImageFilenameFromURL(myPhotoUrlIs),
                                    ".png"
                                )
                                if (progressDralogGenaratinglink != null) {
                                    progressDralogGenaratinglink.dismiss()
                                }
                                myPhotoUrlIs = ""
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        view?.progress_loading_bar?.visibility = View.GONE
                        e.printStackTrace()
                        if (progressDralogGenaratinglink != null) {
                            progressDralogGenaratinglink.dismiss()
                        }
                    }


                }

                override fun onError(error: ANError) {
                    println("response1122334455:   " + "Failed1")
                    view?.progress_loading_bar?.visibility = View.GONE
                }
            })


    }


    private fun getallstoriesapicall() {
        try {
            view?.progress_loading_bar?.visibility = View.VISIBLE

            val sharedPrefsFor = SharedPrefsForInstagram(activity)
            val map = sharedPrefsFor.getPreference(SharedPrefsForInstagram.PREFERENCE)
            if (map != null) {

                getallStories(
                    "ds_user_id=" + map.get(SharedPrefsForInstagram.PREFERENCE_USERID)
                        .toString() + "; sessionid=" + map.get(SharedPrefsForInstagram.PREFERENCE_SESSIONID)
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    fun getallStories(Cookie: String?) {
        var Cookie = Cookie
        if (TextUtils.isEmpty(Cookie)) {
            Cookie = ""
        }

        AndroidNetworking.get("https://i.instagram.com/api/v1/feed/reels_tray/")
            .setPriority(Priority.LOW)
            .addHeaders("Cookie", Cookie)
            .addHeaders(
                "User-Agent",
                "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\""
            )
            .build()
            .getAsObject(
                InstaStoryModelClass::class.java,
                object : ParsedRequestListener<InstaStoryModelClass> {
                    override fun onResponse(response: InstaStoryModelClass) {
                        // do anything with response


                        try {

                            println(
                                "response1122334455_story:  " + response.tray
                            )

                            view?.rec_user_list?.visibility = View.VISIBLE
                            view?.progress_loading_bar?.visibility = View.GONE
                            storyUsersListAdapter = StoryUsersListAdapter(
                                activity,
                                response.tray, this@download
                            )
                            val linearLayoutManager =
                                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                            view?.rec_user_list?.layoutManager = linearLayoutManager
                            view?.rec_user_list?.setAdapter(storyUsersListAdapter)
                            storyUsersListAdapter!!.notifyDataSetChanged()
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            println("response1122334455_storyERROR:  " + e.message)

                            view?.progress_loading_bar?.visibility = View.GONE
                        }


                    }

                    override fun onError(anError: ANError) {
                        // handle error
                    }
                })


    }


    fun getFullDetailsOfClickedFeed(UserId: String, Cookie: String?) {


        AndroidNetworking.get("https://i.instagram.com/api/v1/users/$UserId/full_detail_info?max_id=")
            .setPriority(Priority.LOW)
            .addHeaders("Cookie", Cookie)
            .addHeaders(
                "User-Agent",
                "\"Instagram 9.5.2 (iPhone7,2; iPhone OS 9_3_3; en_US; en-US; scale=2.00; 750x1334) AppleWebKit/420+\""
            )
            .build()
            .getAsObject(
                ModelFullDetailsInstagram::class.java,
                object : ParsedRequestListener<ModelFullDetailsInstagram> {
                    override fun onResponse(response: ModelFullDetailsInstagram) {
                        // do anything with response

                        try {

                            view?.rec_user_list?.visibility = View.VISIBLE
                            view?.progress_loading_bar?.visibility = View.GONE
                            println("response1122334455_fulldetails:   ${response.reel_feed}")



                            if (response.reel_feed.items.size == 0) {
                                iUtils.ShowToast(activity, "No Story Found")
                            }

                            listAllStoriesOfUserAdapter = ListAllStoriesOfUserAdapter(
                                activity,
                                response.reel_feed.items
                            )
                            view?.rec_stories_list?.visibility = View.VISIBLE

                            val gridLayoutManager = GridLayoutManager(context, 3)


                            view?.rec_stories_list?.layoutManager = gridLayoutManager
                            view?.rec_stories_list?.isNestedScrollingEnabled = true
                            view?.rec_stories_list?.setAdapter(listAllStoriesOfUserAdapter)
                            listAllStoriesOfUserAdapter.notifyDataSetChanged()
                        } catch (e: java.lang.Exception) {
                            view?.rec_stories_list?.visibility = View.GONE
                            e.printStackTrace()
                            view?.progress_loading_bar?.visibility = View.GONE
                            iUtils.ShowToast(activity, "No Story Found")

                        }

                    }

                    override fun onError(anError: ANError) {
                        println("response1122334455:   " + "Failed2")
                        view?.progress_loading_bar?.visibility = View.GONE
                    }
                })


    }


    override fun onclickUserStoryListeItem(position: Int, modelUsrTray: ModelUsrTray?) {

        println("response1122ff334455:   " + modelUsrTray + position)

        callStoriesDetailApi(modelUsrTray?.getUser()?.getPk().toString());

    }


    //reward video methods

    override fun onRewarded(reward: RewardItem) {

        //  iUtils.ShowToast(context, context!!.resources.getString(R.string.don_start))
        view?.chkAutoDownload?.isChecked = true

        startClipboardMonitor()


    }

    override fun onRewardedVideoAdLeftApplication() {
        //  Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
        //  Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
        iUtils.ShowToast(activity, getString(R.string.completad))
//
        val checked = view?.chkAutoDownload?.isChecked
        if (checked!!) {
            view?.chkAutoDownload?.isChecked = false
        }
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

//        iUtils.ShowToast(context, getString(R.string.videofailedload))
//
//        val checked = view?.chkAutoDownload?.isChecked
//
//        if (checked!!) {
//            Log.e("loged", "testing checked!")
//            startClipboardMonitor()
//        } else {
//            Log.e("loged", "testing unchecked!")
//
//
//            stopClipboardMonitor()
//            // setNofication(false);
//        }

    }

    override fun onRewardedVideoAdLoaded() {
        //   Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdOpened() {
        //   Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
        //  Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
        view?.chkAutoDownload?.isChecked = true

        startClipboardMonitor()
    }


}