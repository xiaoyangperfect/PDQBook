# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xiaoyang/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontskipnonpubliclibraryclasses # 不忽略非公共的库类
-optimizationpasses 5            # 指定代码的压缩级别
-dontusemixedcaseclassnames      # 是否使用大小写混合
-dontpreverify                   # 混淆时是否做预校验
-verbose                         # 混淆时是否记录日志
-keepattributes *Annotation*     # 保持注解
-ignorewarning                   # 忽略警告
-dontoptimize                    # 优化不优化输入的类文件
-keep class org.greenrobot.**{*;}
-keep class com.pdq.htextview.**{*;}
-keep class com.squareup.**{*;}
-keep public class * extends android.support.** #如果有引用v4或者v7包，需添加
# -libraryjars libs/xxx.jar        #混淆第三方jar包，其中xxx为jar包名
-keepattributes Signature        #不混淆泛型

