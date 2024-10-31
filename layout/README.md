# Layout
각종 레이아웃 샘플 구현

## 프로젝트 초기화

### 1. 의존성 주입을 위한 hilt import

프로젝트 단위 build.gradle

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    /*Hilt*/
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
```

app 단위 build.gradle

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    /*Hilt*/
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

//생략

kapt {
    correctErrorTypes = true
}

dependencies {
    
    //생략
    
    /*Hilt*/
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

}
```

### 2. 데이터바인딩 설정

app 단위 build.gradle
```kotlin

//생략
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
    }
```

### 3. App 추가

프로젝트 root에 App.kt 추가 (com/example/layout/App.kt)

- 이때 ClassName은 App 뿐만아니라 다양한 클래스명으로 구현해도됩니다. (ex. ExampleApplication)
- Hilt 구성요소는 Application 객체의 수명 주기에 연결되며 이와 관련한 종속 항목을 제공합니다. 
- 또한 이는 앱의 상위 구성요소이므로 다른 구성요소는 이 상위 구성요소에서 제공하는 종속 항목에 액세스할 수 있습니다.

```kotlin
@HiltAndroidApp
class App : Application(){
}
```

### 4. MainActivity 추가

## Splash
