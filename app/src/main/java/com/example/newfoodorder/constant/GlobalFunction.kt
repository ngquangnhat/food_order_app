package com.example.newfoodorder.constant

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.text.Normalizer
import java.util.regex.Pattern

object GlobalFunction {
    fun startActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
    fun startActivity(context: Context, clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle!!)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun onClickOpenGmail(context: Context) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", AboutUsConfig.GMAIL, null
            )
        )
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

    fun onClickOpenSkype(context: Context) {
        try {
            val skypeUri = Uri.parse(("skype:" + AboutUsConfig.SKYPE_ID).toString() + "?chat")
            context.packageManager.getPackageInfo("com.skype.raider", 0)
            val skypeIntent = Intent(Intent.ACTION_VIEW, skypeUri)
            skypeIntent.setComponent(ComponentName("com.skype.raider", "com.skype.raider.Main"))
            context.startActivity(skypeIntent)
        } catch (e: java.lang.Exception) {
            openSkypeWebView(context)
        }
    }
    private fun openSkypeWebView(context: Context) {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(("skype:" + AboutUsConfig.SKYPE_ID).toString() + "?chat")
                )
            )
        } catch (exception: java.lang.Exception) {
            val skypePackageName = "com.skype.raider"
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            "market://details?id=$skypePackageName"
                        )
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            "https://play.google.com/store/apps/details?id=$skypePackageName"
                        )
                    )
                )
            }
        }
    }

    fun onClickOpenFacebook(context: Context) {
        var intent: Intent
        try {
            var urlFacebook = AboutUsConfig.PAGE_FACEBOOK
            val packageManager = context.packageManager
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                urlFacebook = "fb://facewebmodal/f?href=" + AboutUsConfig.LINK_FACEBOOK
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFacebook))
        } catch (e: java.lang.Exception) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.LINK_FACEBOOK))
        }
        context.startActivity(intent)
    }
    fun onClickOpenYoutubeChannel(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.LINK_YOUTUBE)))
    }

    fun onClickOpenZalo(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.ZALO_LINK)))
    }
    fun callPhoneNumber(activity: Activity) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
                return
            }

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.setData(Uri.parse("tel:" + AboutUsConfig.PHONE_NUMBER))
            activity.startActivity(callIntent)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getTextSearch(input: String): String {
        val nfdNormalizedString: String =Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern : Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(nfdNormalizedString).replaceAll("")
    }

}