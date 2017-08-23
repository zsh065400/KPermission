package cn.zhaoshuhao.kotlinfirst.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Scout
 * Created on 2017/8/23 15:41.
 */
object KPermission {
    private var handle: IPermissionResult? = null
    private var requestCode: Int? = null

    /*避免同一时间多次请求，可不指定请求代码*/
    fun request(vararg permissions: String, activity: Activity, before: IShowRationable? = null, handle: IPermissionResult, requestCode: Int = 0x111) {
        this.handle = handle
        this.requestCode = requestCode
        val instance = PermissionRequest(activity, requestCode, permissions)
        before?.onPrepareRequest(instance) ?: instance.proceed()
    }

    fun handleResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != requestCode) return
        val granted = ArrayList<String>()
        val denied = ArrayList<String>()
        val neverShow = ArrayList<String>()
        for (i in 0 until permissions.size) {
            val permission = permissions[i]
            val result = grantResults[i]
            if (result == PackageManager.PERMISSION_GRANTED) granted.add(permission)
            else if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) neverShow.add(permission)
            else denied.add(permission)
        }
        handle?.onGranted(granted)
        handle?.onDenied(granted)
        handle?.onNeverShow(granted)
    }
}

data class PermissionRequest(private val activity: Activity,
                             private val requestCode: Int,
                             private val permissions: Array<out String>) {
    fun proceed() {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
        log(LogType.INFO, "KPermission : PermissionRequest, proceed -----> ", permissions.toString())
    }

    fun cancel() {
        log(LogType.INFO, "KPermission : PermissionRequest, cancel -----> ", permissions.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PermissionRequest

        if (requestCode != other.requestCode) return false
        if (!Arrays.equals(permissions, other.permissions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = requestCode
        result = 31 * result + Arrays.hashCode(permissions)
        return result
    }
}

interface IShowRationable {
    fun onPrepareRequest(requestBody: PermissionRequest)
}

interface IPermissionResult {
    fun onGranted(permission: ArrayList<String>)

    fun onDenied(permission: ArrayList<String>)

    fun onNeverShow(permission: ArrayList<String>)
}
