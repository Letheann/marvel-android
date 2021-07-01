package dependencies

object TestDependencies {
    const val JUNIT = "junit:junit:${BuildDependenciesVersions.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${BuildDependenciesVersions.MOCKK}"
    const val ASSERTJ = "org.assertj:assertj-core:${BuildDependenciesVersions.ASSERTJ}"
    const val EXT = "androidx.test.ext:junit:${BuildDependenciesVersions.EXT}"
    const val CORE = "androidx.test:core:${BuildDependenciesVersions.TEST}"
    const val RUNNER = "androidx.test:runner:${BuildDependenciesVersions.TEST}"
    const val RULES = "androidx.test:rules:${BuildDependenciesVersions.TEST}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${BuildDependenciesVersions.COROUTINES}"
    const val ARCH_CORE = "androidx.arch.core:core-testing:${BuildDependenciesVersions.ARCH_CORE}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${BuildDependenciesVersions.MOCK_WEB_SERVER}"
}
