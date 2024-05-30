plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
val v: (String) -> Any? = { rootProject.ext.get(it) }
android {
    namespace = "com.tianjuan.hightlights"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tianjuan.hightlights"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.activity:activity:${v("activity_version")}")
    implementation("androidx.activity:activity-ktx:${v("activity_version")}")
    implementation("androidx.appcompat:appcompat:${v("appcompat_version")}")
    implementation("androidx.appcompat:appcompat-resources:${v("appcompat_version")}")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}