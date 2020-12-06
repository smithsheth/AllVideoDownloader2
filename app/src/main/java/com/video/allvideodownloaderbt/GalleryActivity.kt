package com.video.allvideodownloaderbt

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.video.allvideodownloaderbt.fragments.GalleryFragmentMainGallery
import com.video.allvideodownloaderbt.fragments.InstagalleryImagesGallery
import com.video.allvideodownloaderbt.fragments.StatusSaverGallery
import com.video.allvideodownloaderbt.utils.LocaleHelper
import com.video.allvideodownloaderbt.utils.iUtils
import java.util.*

class GalleryActivity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_gallery)




        setlayout()

    }


    fun setlayout() {
        viewPager = findViewById<ViewPager>(R.id.viewpagergallery)
        setupViewPager(viewPager!!)

        tabLayout = findViewById<TabLayout>(R.id.tabsgallery)
        tabLayout!!.setupWithViewPager(viewPager)
        setupTabIcons()


    }

    fun setupTabIcons() {

        tabLayout?.getTabAt(0)?.setIcon(R.drawable.ic_gallery_color_24dp)
        tabLayout?.getTabAt(1)?.setIcon(R.drawable.statuspic)
        tabLayout?.getTabAt(2)?.setIcon(R.drawable.ic_image)


    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(
            GalleryFragmentMainGallery(),
            getString(R.string.gallery_fragment_statussaver)
        )
        adapter.addFragment(StatusSaverGallery(), getString(R.string.StatusSaver_gallery))
        adapter.addFragment(InstagalleryImagesGallery(), getString(R.string.instaimage))

        viewPager.adapter = adapter
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


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

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

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun attachBaseContext(newBase: Context?) {
        var newBase = newBase
        newBase = LocaleHelper.onAttach(newBase)
        super.attachBaseContext(newBase)
    }


}