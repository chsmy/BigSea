# BigSea

使用了洋神的[玩安卓](https://www.wanandroid.com/)API
视频接口使用了开眼的api(仅学习使用)

#### 封装Retrofit和协程完成网络请求
请求网络数据如此简单
```kotlin
 val mSystemEntity:MutableLiveData<List<SystemEntity>> by lazy {
        MutableLiveData<List<SystemEntity>>().also {
            launch {
                val systemRes = WanRetrofitClient.service.getSystemData()
                mSystemEntity.value = systemRes.data
            }
        }
    }
```

#### 组件化开发
- lib_core  核心库 引入各种第三方库，utils工具，extension，navigation的封装，room
- lib_commom_ui 主要是UI相关的 CameraX视频录制的封装，全局loading，webview，自定义view，exoplayer的封装使用，各种Base类等
- module_wan  业务模块 玩安卓的各种列表模块和ToDo模块
- module_eye  业务模块 使用开眼的部分开源API，目前主要是视频列表播放和视频详情
- module_video 业务模块 包括视频列表播放  直播推流  直播拉流播放

#### 使用的JetPack组件

- 自定义navigation，使用起来更灵活，自定义FragmentNavigator解决底部导航栏切换导致的fragment重建，支持底部导航栏数量配置，支持组件之间跳转和传值。
- 使用paging3.0组件实现列表的滑动加载。
- 自定义LiveDataBus实现页面间，组件间通信
- 使用Room数据库缓存对象
- 使用cameraX完成拍照和视频录制的功能

使用google的exoplayer来播放视频，并且自定义实现列表滑动播放，列表与详情视频无缝切换

使用SRS直播服务器开源库（服务器8月11号到期~~），[WSLiveDemo](https://github.com/WangShuo1143368701/WSLiveDemo)直播推流，exoplayer拉流播放

![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/home_page.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_1.gif)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_2.png)

![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_3.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_4.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_5.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_6.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_7.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_8.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_9.png)
![home](https://raw.githubusercontent.com/chsmy/BigSea/master/screenshot/bigsea_take.png)
