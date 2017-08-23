# KPermission
使用Kotlin封装的Android6.0权限调用库

> Kotlin！Kotlin！Kotlin！重要的事情说三遍，虽然Java和Kotlin支持互相调用，但官方并不建议Kotlin作为Library，所以只发了关键文件。我没有测试Java上的效果，希望喜欢的你可以帮我测试一下。

直接上使用方法，源码直接看文件即可：
------
	KPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                before = object : IShowRationable {
                    override fun onPrepareRequest(requestBody: PermissionRequest) {
                        //请求前的处理，可以提示用户等...
                        requestBody.proceed()//继续执行请求
                        requestBody.cancel()//停止请求
                    }
                },
                activity = this,
                requestCode = 0x111,
                handle = object : IPermissionResult {
                    override fun onGranted(permission: ArrayList<String>) {
                        //授权的权限会回调该方法
                    }

                    override fun onDenied(permission: ArrayList<String>) {
                        //被拒绝的权限列表（未勾选不再显示）
                    }

                    override fun onNeverShow(permission: ArrayList<String>) {
                        //被勾选不再显示的权限
                    }
                })


同时，在onRequestPermissionsResult，使用该代码移交处理
-----
	KPermission.handleResult(this, requestCode, permissions, grantResults)


Lincense
-----
Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.