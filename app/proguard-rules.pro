# Add project specific ProGuard rules here.
# By default_banner, the flags in this file are appended to flags specified
# in D:\develop\AndroidStudioSDK/tools/proguard/proguard-android.txt
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

#-------------------------------------------配置混淆方法：定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.zhumei.commercialscreen.bean.** { *; }
-keep class com.zhumei.commercialscreen.entity.** { *; }

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
# Okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}

# Okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# Okio
-dontwarn okio.**
-keep class okio.**{*;}

# Filedownloader

# EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# SwipeRecyclerView
-keepclasseswithmembers class android.support.v7.widget.RecyclerView$ViewHolder {
   public final View *;
}

# Percent-support-extends

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# MarqueeViewLibrary

# Banner
-keep class com.youth.banner.** {
    *;
}

# VerticalViewPager

# Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-dontwarn butterknife.internal.ButterKnifeProcessor.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# FastJson
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }
# 这儿的"com.android.beandao"是自己的bean包
#-keep class com.android.beandao.**{ *; }

# 百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

# Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# AndPermission
-dontwarn com.yanzhenjie.permission.**

# 去掉Log
-assumenosideeffects class android.util.Log{
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
    public static *** w(...);
}

# greendao
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep public interface org.greenrobot.greendao.**
-keep class net.sqlcipher.database.**{*;}
-keep public interface net.sqlcipher.database.**
-dontwarn net.sqlcipher.database.**
-dontwarn org.greenrobot.greendao.**

# GreenDaoUpgradeHelper
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}

# jar包或so库  忽略警告
-ignorewarnings

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
# 指定代码的压缩级别,0-7之间
-optimizationpasses 5
# 混淆后类名均为小写
-dontusemixedcaseclassnames
# 不去忽略非公共的库和类
-dontskipnonpubliclibraryclasses
# 不优化输入的类文件  去掉Log 注释
#-dontoptimize
# 不去预校验
-dontpreverify
# 生成原类名和混淆后的类名的映射文件
-verbose
-printmapping proguardMapping.txt
# 混淆时所采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
# 不混淆注解和内部类
-keepattributes *Annotation*,InnerClasses
# 不混淆泛型 如果混淆报错建议关掉
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------
