# 简介

AndroidLib 总结了项目中常用的库，实现Code更便捷的重用，优化项目结构，加速项目开发。

- 丰富的功能库
- 大量的测试用例
- 完整说明文档
- 实时更新


主要包含两部分：

1. appbase，主要是app常用的一些基类。使用的时候视情况copy过去即可（BaseActiviy, BaseView等）
2. utilslib，主要是常用的一些方法库

# utilslib使用方法 (Android Studio)

使用方法说明，点击[这里](https://github.com/vivianking6855/android-library/tree/master/AndroidLib/AndroidLib)

- AppUtils: App常用方法，比如app version等
- ConvertUtils: bitmap, byte[], stream，时间格式 等转换
- SizeUtils： 尺寸转换，比如px, sp等
- DeviceUtils：设备尺寸，设备截屏等
- DiskLruCacheUtils： DiskLruCache 方法封装，支持bitmap, drawable, json等
- TimeUtils，TimeConstants：时间，日期，时间差等
- ToastUtils： 不会重复的toast
- EncryptUtils： 加解密
- EncodeUtils： 编码
- PathUtils： 获取cache路径
- FileUtils： 文件操作
- CleanUtils： 文件夹clean等
- NetworkUtils： 网络状态，打开网络设定等
- ZipUtils： 压缩，解压文件
- SDCardUtils： SD卡路径，空间，信息等
- MemoryConstants： KB,MB等常量
- CloseUtils： safe close 等



# Reference

- [Lazy](https://github.com/l123456789jy/Lazy)
- [base-diskcache](https://github.com/hongyangAndroid/base-diskcache)

