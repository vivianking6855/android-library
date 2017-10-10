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

1. load AndroidLib code
2. add following to settings.gradle (cacheLib, appLib 可选). 

        include ':AndroidLib'
        project(':AndroidLib').projectDir = new File('../AndroidLib/')
        include ':AndroidLib:utilslib'

路径'../AndroidLib/' 需要据实际路径调整

3. add following to build.gradle (app)

        dependencies {
            compile project(':AndroidLib:utilslib')
        }
        
## Proguard

- applib

        -keep class com.open.utilslib.** { *; }
        -keepclassmembers class com.open.utilslib.** { *; }
        -dontwarn com.open.utilslib.**

# Java Doc 

# Contributor

vivian sun