package com.chs.lib_core.constant

import android.Manifest

/**
 *  @author chs
 *  date: 2020-07-13 12:07
 *  des:
 */
object Constant {

    //二维码requestCode
    val REQUEST_CODE_SCAN_ONE = 0X01

    val QR_CODE_PERMISSION = listOf<String>(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)

}