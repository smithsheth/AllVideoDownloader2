package com.video.allvideodownloaderbt

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdSize
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import com.video.allvideodownloaderbt.extraFeatures.ExtraFeaturesFragment
import com.video.allvideodownloaderbt.fragments.download
import com.video.allvideodownloaderbt.statussaver.StatusSaverMainFragment
import com.video.allvideodownloaderbt.tasks.downloadVideo
import com.video.allvideodownloaderbt.utils.Constants
import com.video.allvideodownloaderbt.utils.IOUtils
import com.video.allvideodownloaderbt.utils.LocaleHelper
import com.video.allvideodownloaderbt.utils.iUtils
import java.util.*

class MainActivity : AppCompatActivity() {
    var myString: String? = ""
    val REQUEST_PERMISSION_CODE = 1001
    val REQUEST_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    lateinit var mAdView: AdView
    var fbAdView: com.facebook.ads.AdView? = null
    lateinit var progressDralogGenaratinglink: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)



        progressDralogGenaratinglink = ProgressDialog(this)
        progressDralogGenaratinglink.setMessage(resources.getString(R.string.genarating_download_link))
        progressDralogGenaratinglink.setCancelable(false)


        mAdView = findViewById(R.id.adView)
        val adContainer = findViewById<LinearLayout>(R.id.banner_container)


        val random = Random()
        var num = random.nextInt(2)

        Log.e("myhdasbdhf ", num.toString())


        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                handleSendText(intent) // Handle text being sent
            }
        }

        //TODO facebook uncomment the code below
        if (!Constants.show_facebookads) {
            num = 0
        }



        if (num == 0) {
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)



            mAdView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.e("AdLoadeed", "AdLoaded")


                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }


        } else {


            val res = resources
            //adView = new com.facebook.ads.AdView(this, res.getString(R.string.AdmobBanner), AdSize.BANNER_HEIGHT_50);
            //adView = new com.facebook.ads.AdView(this, res.getString(R.string.AdmobBanner), AdSize.BANNER_HEIGHT_50);
            fbAdView = com.facebook.ads.AdView(
                this,
                res.getString(R.string.fbAdmobBanner),
                AdSize.BANNER_HEIGHT_50
            )
            // AdSettings.addTestDevice("328404cebf50ec1fdb05795c0007a8a7")

            adContainer.addView(fbAdView)

            val adListener = object : AdListener(), com.facebook.ads.AdListener {
                override fun onError(ad: Ad, adError: AdError) {
                    Toast.makeText(
                        this@MainActivity,
                        "Facebook App Is not Installed OR " + adError.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAdLoaded(ad: Ad) {

                }

                override fun onAdClicked(ad: Ad) {

                }

                override fun onLoggingImpression(ad: Ad) {

                }
            }

            val loadAdConfig = fbAdView?.buildLoadAdConfig()
                ?.withAdListener(adListener)
                ?.build()

            fbAdView?.loadAd(loadAdConfig)


        }






        if (!isNeedGrantPermission()) {
            setlayout()
        }


    }


    fun getMyData(): String? {
        return myString
    }


    fun setmydata(mysa: String) {
        this.myString = mysa
    }


    fun handleSendText(intent: Intent) {

        var url = intent.getStringExtra(Intent.EXTRA_TEXT)



        if (url.equals("") && iUtils.checkURL(url)) {
            iUtils.ShowToast(
                this@MainActivity,
                this@MainActivity?.resources?.getString(R.string.enter_valid)
            )
            return

        }

//        if (url?.contains("instagram.com")!!) {
//            progressDralogGenaratinglink.show()
//            startInstaDownload(url)
//        }
        if (url?.contains("myjosh.in")!!) {


            Log.e("downloadFileName", (url?.substring(url.indexOf("http")) ?: url) + "")

            url = url.substring(url.indexOf("http")) ?: url
            url = url?.substring(
                url.indexOf("http://share.myjosh.in/"),
                url.indexOf("Download Josh for more videos like this!")
            ) ?: url


            downloadVideo.Start(this, url.trim(), false)
            Log.e("downloadFileName12", url.trim())


        } else if (url.contains("chingari")) {


            url = url?.substring(
                url.indexOf("https://chingari.io/"),
                url.indexOf("For more such entertaining")
            ) ?: url


            downloadVideo.Start(this, url.trim(), false)
            Log.e("downloadFileName12", url.trim())
        } else if (url.contains("instagram.com")) {


            //   downloadVideo.Start(this, url, false)


            val bundle = Bundle()
            bundle.putString("myinstaurl", url)
            val fragobj = download()
            fragobj.setArguments(bundle)

            this.setmydata(url)


            Log.e("downloadFileName12", url)
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

            downloadVideo.Start(this, myurl?.trim(), false)
            Log.e("downloadFileName12", myurl!!?.toString())
        } else {

            downloadVideo.Start(this, url?.substring(url.indexOf("http")).trim() ?: url, false)

            Log.e("downloadFileName", (url?.substring(url.indexOf("http")).trim() ?: url) + "")
        }
//        val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
//            override fun handleMessage(message: Message) {
//                iUtils.ShowToast(this@MainActivity, "Working On Download")
//            }
//        }
    }


    fun setlayout() {
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        setupViewPager(viewPager!!)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout!!.setupWithViewPager(viewPager)
        setupTabIcons()


    }

    fun setupTabIcons() {
        tabLayout?.getTabAt(0)?.setIcon(R.drawable.ic_download_color_24dp)
        //  tabLayout?.getTabAt(1)?.setIcon(R.drawable.ic_gallery_color_24dp)
        tabLayout?.getTabAt(1)?.setIcon(R.drawable.statuspic)
        tabLayout?.getTabAt(2)?.setIcon(R.drawable.ic_puzzle)

    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(download(), getString(R.string.download_fragment))
        // adapter.addFragment(DummyFragment(), getString(R.string.gallery_fragment))
        adapter.addFragment(StatusSaverMainFragment(), getString(R.string.StatusSaver))
        adapter.addFragment(ExtraFeaturesFragment(), getString(R.string.extrafeatures))
        viewPager.adapter = adapter
    }

    private fun isNeedGrantPermission(): Boolean {
        try {
            if (IOUtils.hasMarsallow()) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        REQUEST_PERMISSION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this@MainActivity,
                            REQUEST_PERMISSION
                        )
                    ) {
                        val msg =
                            String.format(
                                getString(R.string.format_request_permision),
                                getString(R.string.app_name)
                            )

                        val localBuilder = AlertDialog.Builder(this@MainActivity)
                        localBuilder.setTitle(getString(R.string.permission_title))
                        localBuilder
                            .setMessage(msg).setNeutralButton(
                                getString(R.string.grant_option)
                            ) { paramAnonymousDialogInterface, paramAnonymousInt ->
                                ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    arrayOf(REQUEST_PERMISSION),
                                    REQUEST_PERMISSION_CODE
                                )
                            }
                            .setNegativeButton(
                                getString(R.string.cancel_option)
                            ) { paramAnonymousDialogInterface, paramAnonymousInt ->
                                paramAnonymousDialogInterface.dismiss()
                                finish()
                            }
                        localBuilder.show()

                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(REQUEST_PERMISSION),
                            REQUEST_PERMISSION_CODE
                        )
                    }
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            if (requestCode == REQUEST_PERMISSION_CODE) {
                if (grantResults != null && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                    setlayout()
                } else {
                    iUtils.ShowToast(this@MainActivity, getString(R.string.info_permission_denied))

                    finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            iUtils.ShowToast(this@MainActivity, getString(R.string.info_permission_denied))
            finish()
        }

    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {

            return mFragmentList[position]

//            viewPager!!.currentItem;
//            return when(position){
//
//                0-> download();
//                1->gallery();
//                else -> gallery();
//            }
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.


        menuInflater.inflate(R.menu.main, menu)

        val prefs: SharedPreferences = getSharedPreferences(
            "whatsapp_pref",
            Context.MODE_PRIVATE
        )
        val name =
            prefs.getString("whatsapp", "main") //"No name defined" is the default value.


        if (name == "main") {
            menu.findItem(R.id.action_shwbusinesswhatsapp).isVisible = true

            menu.findItem(R.id.action_shwmainwhatsapp).isVisible = false

        } else if (name == "bus") {
            menu.findItem(R.id.action_shwbusinesswhatsapp).isVisible = false

            menu.findItem(R.id.action_shwmainwhatsapp).isVisible = true

        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_privacy -> {

//                val intent = Intent(this, MainActivityStatusSaver::class.java)
//                startActivity(intent)
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.privacy))
                    .setMessage(R.string.privacy_message)
                    .setPositiveButton(
                        android.R.string.yes
                    ) { dialog, which -> dialog.dismiss() }
                    .setIcon(R.drawable.ic_info_black_24dp)
                    .show()



                true
            }

            R.id.action_downloadtiktok -> {

                val intent = Intent(this, TikTokDownloadWebview::class.java)
                startActivity(intent)
                //   finish()


                true
            }


            R.id.action_rate -> {

//                val intent = Intent(this, MainActivityStatusSaver::class.java)
//                startActivity(intent)

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.RateAppTitle))
                    .setMessage(getString(R.string.RateApp))
                    .setCancelable(false)
                    .setPositiveButton(
                        getString(R.string.rate_dialog),
                        DialogInterface.OnClickListener { dialog, whichButton ->
                            val appPackageName = packageName
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
                        })
                    .setNegativeButton(getString(R.string.later_btn), null).show()

                true
            }


            R.id.ic_whatapp -> {

//                val intent = Intent(this, MainActivityLivewallpaper::class.java)
//                startActivity(intent)
                val launchIntent = packageManager.getLaunchIntentForPackage("com.whatsapp")
                if (launchIntent != null) {

                    startActivity(launchIntent)
                    finish()
                } else {

                    iUtils.ShowToast(
                        this,
                        this.resources.getString(R.string.appnotinstalled)
                    )
                }
                true
            }


            R.id.action_language -> {

                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog_change_language)

                val l_english = dialog.findViewById(R.id.l_english) as TextView
                l_english.setOnClickListener {

                    LocaleHelper.setLocale(application, "en")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL


                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "en")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }

                val l_arabic = dialog.findViewById(R.id.l_arabic) as TextView
                l_arabic.setOnClickListener {
                    LocaleHelper.setLocale(application, "ar")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "ar")

                    editor.apply()


                    recreate()
                    dialog.dismiss()

                }
                val l_urdu = dialog.findViewById(R.id.l_urdu) as TextView
                l_urdu.setOnClickListener {
                    LocaleHelper.setLocale(application, "ur")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR


                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "ur")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }


                val l_turkey = dialog.findViewById(R.id.l_turkey) as TextView
                l_turkey.setOnClickListener {
                    LocaleHelper.setLocale(application, "tr")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "tr")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }


                val l_portougese = dialog.findViewById(R.id.l_portougese) as TextView
                l_portougese.setOnClickListener {
                    LocaleHelper.setLocale(application, "pt")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "pt")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }


                val l_chinese = dialog.findViewById(R.id.l_chinese) as TextView
                l_chinese.setOnClickListener {
                    LocaleHelper.setLocale(application, "zh")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "zh")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }


                val l_hindi = dialog.findViewById(R.id.l_hindi) as TextView
                l_hindi.setOnClickListener {
                    LocaleHelper.setLocale(application, "hi")
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    val editor: SharedPreferences.Editor = getSharedPreferences(
                        "lang_pref",
                        Context.MODE_PRIVATE
                    ).edit()
                    editor.putString("lang", "hi")

                    editor.apply()


                    recreate()
                    dialog.dismiss()
                }




                dialog.show()

                true
            }

            R.id.action_shwbusinesswhatsapp -> {


                val editor: SharedPreferences.Editor = getSharedPreferences(
                    "whatsapp_pref",
                    Context.MODE_PRIVATE
                ).edit()
                editor.putString("whatsapp", "bus")

                editor.apply()

                if (Build.VERSION.SDK_INT >= 11) {
                    recreate()
                } else {
                    val intent = intent
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }



                true
            }


            R.id.action_shwmainwhatsapp -> {


                val editor: SharedPreferences.Editor = getSharedPreferences(
                    "whatsapp_pref",
                    Context.MODE_PRIVATE
                ).edit()
                editor.putString("whatsapp", "main")

                editor.apply()


                if (Build.VERSION.SDK_INT >= 11) {
                    recreate()
                } else {
                    val intent = intent
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }



                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {

//        val builder =
//            androidx.appcompat.app.AlertDialog.Builder(this)
//        builder.setTitle(getString(R.string.app_name))
//        builder.setIcon(R.drawable.ic_appicon)
//        builder.setMessage(getString(R.string.txt_exit))
//        builder.setPositiveButton(
//            getString(R.string.cancel)
//        ) { dialogInterface, i -> }
//        builder.setNegativeButton(
//            getString(R.string.rate_it)
//        ) { dialogInterface, i -> System.exit(0) }
//        builder.show()


        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_ad_exit)
        val yesBtn = dialog.findViewById(R.id.btn_exitdialog_yes) as Button
        val noBtn = dialog.findViewById(R.id.btn_exitdialog_no) as Button

        val adviewnew = dialog.findViewById(R.id.adView_dia) as AdView
        val adRequest = AdRequest.Builder().build()
        adviewnew.loadAd(adRequest)



        yesBtn.setOnClickListener {
            System.exit(0)
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("proddddd11111222 $resultCode __" + data)

        if (requestCode == 200 && resultCode == RESULT_OK) {

            println("proddddd11111 $resultCode __" + data)

        }
        try {
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    override fun attachBaseContext(newBase: Context?) {
        var newBase = newBase
        newBase = LocaleHelper.onAttach(newBase)
        super.attachBaseContext(newBase)
    }
}