# CnNews -- 菜鸟新闻
一个新闻阅读APP
#### 一、整体描述：整个应用整体上分为四大模块：首页（新闻模块）、专题、图片以及设置。
#### 二、系统开发环境：jdk1.8、SDK28
#### 三、运行流程描述：
       1、应用开始运行时，首先进入广告动画页面，默认5秒后进入下一步，当点击跳过时直接停止定时器直接进入下一步；
       2、广告页面之后，判断之前是否进入过主页，即是不是第一次开启本应用，若从未进入过，则进入引导页面。若进入过，跳过引导页面进入主页。
       3、进入MianActivity后，初始化两个Fragment：leftFragment和ContentFragment
       ......
       
#### 四、用到的网络请求框架和图片加载框架
##### 1、网络请求框架：xutils3 和 Volley
##### 2、图片加载框架：xUtils3、Volley、Glide、Picasso（该框架主要用于图片模块）

#### 五、各模块实现细节描述

##### 1、欢迎页面
整体为一个相对布局，里面一个TextView，用于显示定时。进入页面后全屏显示getWindow().setFlags(flag, flag);。然后设置一个定时器，每隔一秒刷新显示定时的TextView文本内容。若点击跳过，移除定时任务，直接进入下一步。如果不点击，则5秒后结束进入下一步。

##### 2、引导页面
(1)在整个布局中添加ViewPager、进入主页的按钮、灰色的点、小红点。
注：ViewPager，视图翻页工具，提供了多页面切换的效果。ViewPager使用起来就是我们通过创建适配器给它填充多个view，左右滑动时，切换不同的view。
(2)设置ViewPager的适配器，添加三个有引导效果的ImageView，在适配器中添加滑动效果。
(3)小红点的移动原理
小红点的移动距离：灰色点的间距 = 屏幕的滑动距离：屏幕的宽度
即：小红点的移动距离=灰色点的间距*ViewPager的移动比例。而ViewPager自带的OnPageChangeListener提供了ViewPager的移动比例
(4)判断是否是ViewPager的最后一个页面，如果是则显示进入主页的按钮，点击按钮，将进入过主页加入缓存，并进入主页。

##### 3、内容碎片（ContentFragment）     
由NoscrollPager和RadioGroup组成，其中NoscrollPager是自定义的ViewPager，目的是实现各ViewPager不能滑动。RadioGroup里放置了四个RadioButton。每点击一个RadioButton实现切换一个NoscrollPager。

##### 4、首页的设计与实现
###### (1)布局设计
整体是一个线性布局。新闻标题部分也是一个线性布局，新闻内容部分整体是一个ViewPager。
新闻标题又分为TabPageIndicator和一个ImageButton。新闻内容部分是PullToRefreshListView。PullToRefreshListView是一个下拉刷新控件，继承自ListView，最后刷新接收内容同样用的还是ListView。
顶部轮播图部分以添加头的方式添加到ListView中的：listview.addHeaderView(topNewsView);图片的轮播通过Handler来实现，每隔4秒切换一次。
具体每一条新闻部分整体上是一个相对布局，包含四部分：新闻图片ImageView、线性布局、评论ImageView、分割线。线性布局里包含着新闻标题和发布时间。

###### (2)新闻设计
每一条新闻设计点击监听，当点击具体某条新闻后，会判断该条新闻是否被查看过。如果未被查看过，存入缓存(SharedPreferences)中，即将新闻ID作为Key存入进去，然后刷新适配器，将已经读取过的新闻标题设置为灰色。然后进入DetailNewsActivity，并将该新闻链接地址作为参数传入。
新闻阅读界面采用的是WebView，将请求到的希望到的HTML内容显示出来。

###### (3)字体设置
字体大小设置有利于提高用户与软件的友好交互性。字体设置主要通过AlertDialog.Builder和webSettings.setTextZoom来实现的。
###### (4)新闻分享
新闻分享功能采用的是ShareSDK
文档链接：http://wiki.mob.com/android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97

##### 5、组图的设计与实现

组图模块主要视图为ListView和GrideView，这两个视图可共用同一个适配器，点击切换按钮，将ListView隐藏，并将GrideView以两列的格式显示出来，并切换按钮的背景图片。



       
       
