package mchehab.com.networkhandling

import android.app.ProgressDialog
import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var alertDialogNoInternet: android.app.AlertDialog? = null

    private val broadcastReceiverConnectionChanged = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (!NetworkUtil.isNetworkAvailable(this@MainActivity)) {
                displayNoInternetAlertDialog()
            } else {
                if (alertDialogNoInternet?.isShowing!!) {
                    alertDialogNoInternet!!.dismiss()
                }
                if (!NetworkUtil.isWifiNetwork(this@MainActivity)) {
                    initMobileDataAlertDialog()
                }
            }
        }
    }

    override fun onPause() {
//        remove broadcast receiver when activity stops
        unregisterReceiver(broadcastReceiverConnectionChanged)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
//        register broadcast receiver after starting activity
        registerBroadcastReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiverConnectionChanged, intentFilter)
    }

    private fun initMobileDataAlertDialog() {
        alertDialogNoInternet = android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertMobileDataTitle))
                .setNegativeButton(getString(R.string.alertMobileDataNegativeButton)) { dialog, which ->
                    dialog.dismiss()
                    //perform any tasks if necessary
                }.setPositiveButton(getString(R.string.alertMobileDataPositiveButton)) { dialog, which ->
            NetworkUtil.enableWifi(this@MainActivity)
            showProgressDialog()
        }
                .setCancelable(false)
                .create()
        alertDialogNoInternet!!.show()
    }

    protected fun checkNetwork() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            if (NetworkUtil.isWifiNetwork(this)) {
                Log.d("TAG", "device is using wifi network")
            } else {
                //                notify the user that s/he is using mobile data (optional)
                displayMobileDataAlertDialog()
            }
        } else {
            //            device has no internet connection, inform user
            displayNoInternetAlertDialog()
        }
    }

    protected fun displayMobileDataAlertDialog() {
        initMobileDataAlertDialog()
    }

    protected fun displayNoInternetAlertDialog() {
        alertDialogNoInternet = android.app.AlertDialog.Builder(this).setTitle(getString(R.string
                .alertNoInternetTitle)).setMessage(getString(R.string.alertNoInternetMessage))
                .setNegativeButton(getString(R.string.alertNoInternetNegativeButton)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(getString(R.string.alertNoInternetPositiveButton)) { dialog, which ->
                    checkNetwork()
                    dialog.dismiss()
                }
                .setNeutralButton(getString(R.string.alertNoInternetNeutralButton)) { dialog, which ->
                    NetworkUtil.enableWifi(this@MainActivity)
                    showProgressDialog()
                }
                .setCancelable(false).create()
        alertDialogNoInternet!!.show()
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Connecting")
        progressDialog!!.setCancelable(false)
    }

    private fun showProgressDialog() {
        initProgressDialog()
        progressDialog!!.show()
        Handler().postDelayed({
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                if (!NetworkUtil.isNetworkAvailable(this)) {
                    displayFailedWifiConnectionAlertDialog()
                }
            }
        }, 3000)
    }

    private fun displayFailedWifiConnectionAlertDialog() {
        alertDialogNoInternet = android.app.AlertDialog.Builder(this)
                .setCancelable(false).setTitle(getString(R.string.alertFailedWifiTitle))
                .setNegativeButton(getString(R.string.alertFailedWifiNegativeButton)) { dialog, which ->
                    dialog.dismiss()
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                .setPositiveButton(getString(R.string.alertFailedWifiPositiveButton)) { dialog, which -> showProgressDialog() }
                .create()
        alertDialogNoInternet!!.show()
    }
}