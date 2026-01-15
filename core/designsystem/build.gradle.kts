plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {

    androidLibrary {
        namespace = "de.gello.designsystem"
        compileSdk = 36
        minSdk = 24
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.util)

                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.material3)
            }
        }
    }
}