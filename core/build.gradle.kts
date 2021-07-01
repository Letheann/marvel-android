

import dependencies.AnnotationProcessorsDependencies
import dependencies.Dependencies
import extensions.buildConfigStringField
import extensions.getLocalProperty
import extensions.implementation
import extensions.kapt

plugins {
    id("commons.android-library")
}

android {
    buildTypes.forEach {
        try {
            it.buildConfigStringField("BASE_URL", "https://gateway.marvel.com/")
            it.buildConfigStringField("KEY_PUBLIC", getLocalProperty("key.public"))
            it.buildConfigStringField("KEY_PRIVATE", getLocalProperty("key.private"))
        } catch (ignored: Exception) {
            throw InvalidUserDataException("Defina as chaves 'marvel.key.public' e" +
                    "'marvel.key.private' em local.properties.")
        }
    }
}

dependencies {
    implementation(Dependencies.LIFECYCLE_EXTENSIONS)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.MOSHI)
    implementation(Dependencies.MOSHI_KTX)
    implementation(Dependencies.KOIN_CORE)
    implementation(Dependencies.KOIN_ANDROID)
    implementation(Dependencies.KOIN_VIEW_MODEL)
    implementation(Dependencies.COIL)

    kapt(AnnotationProcessorsDependencies.DATABINDING)
}
