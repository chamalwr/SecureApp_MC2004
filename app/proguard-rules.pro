# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-obfuscationdictionary "../and_obs_dir.txt"
-classobfuscationdictionary "../and_obs_dir.txt"
-packageobfuscationdictionary "../and_obs_dir.txt"

-mergeinterfacesaggressively
-overloadaggressively
-repackageclasses "com.chamalwr.secureapp"

