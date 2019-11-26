### 快速集成：
- **Step 1.** Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- **Step 2.** Add the dependency
```groovy
dependencies {
    def  version = "0.0.1"
    implementation "com.github.andnux:pay:${version}"
    or
    implementation "com.github.andnux:pay:alipay:${version}" 
    implementation "com.github.andnux:pay:wxpay:${version}" 
}
```