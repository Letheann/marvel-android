import extensions.implementation
import dependencies.Dependencies
import dependencies.AnnotationProcessorsDependencies

plugins {
    id("commons.android-dynamic-feature")
}

dependencies {
    implementation(Dependencies.RECYCLE_VIEW)
    implementation(Dependencies.PAGING)

    implementation(Dependencies.KOIN_CORE)
    implementation(Dependencies.KOIN_ANDROID)
    implementation(Dependencies.KOIN_VIEW_MODEL)
    implementation(Dependencies.COROUTINES)
    kapt(AnnotationProcessorsDependencies.DATABINDING)
}
