# KPermission
使用Kotlin封装的Android6.0权限调用库

> Kotlin！Kotlin！Kotlin！重要的事情说三遍，虽然Java和Kotlin支持互相调用，但官方并不建议Kotlin作为Library，所以只发了关键文件。我没有测试Java上的效果，希望喜欢的你可以帮我测试一下。

新添加部分Kotlin封装的库，不限于权限

- KCache，简单缓存
- KSharedPreference，使用Kotlin特性封装的SP库
- ViewExt，针对View进行的扩展
- GsonParse，Kotlin简易Gson封装
- ContextExtend，我是充数的

最新修改

- 因部分第三方机型，重写权限请求逻辑进行适配
- 新加检查是否授权的方法
- 新加Lambda方式的支持
- 修改参数类型

BUG
> 对于没有注释的行为请见谅，正在培养自己写注释的习惯，后期会加上去的


此处仅展示部分用法，更多API请参阅文件（没有注释哟，我承认我是直接复制过来发布的，太懒了）

直接上使用方法，源码直接看文件即可：

Lambda版 请求
------
	KPermission.requestOfLambda(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                activity = this) { g, d, n ->
            /*针对权限做不同处理*/
        }


处理移交
-----
	KPermission.handleResultOfLambda(this, requestCode, permissions.asList(), grantResults)


普通版请求
----
	KPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                activity = this,handle = object : IPermissionResult{
            override fun onGranted(permission: List<String>) {

            }

            override fun onDenied(permission: List<String>) {
            }

            override fun onNeverShow(permission: List<String>) {
            }

        })

处理移交
-----
	KPermission.handleResult(this, requestCode, permissions.asList(), grantResults)


Perference和Cache，参照《Kotlin For Android Developer》一书中的思路实现，有兴趣的朋友可以查一下
-----
	private var bannerJson: String by SPExt.SpDelegate(context, "banners", "")

	KCache<String>(context, goodsId.toString()).putValue(json)

View中的内容直接使用即可，均为扩展方法
---

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