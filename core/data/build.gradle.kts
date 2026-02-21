import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.room)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
}

kotlin {

    androidLibrary {
        namespace = "de.gello.data"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "dataKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)
                implementation(projects.core.util)

                implementation(libs.kotlin.stdlib)
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.material3)
                api(libs.kotlinx.datetime)

                implementation(libs.forge.network.core.session)
                implementation(libs.forge.network.core.client)
                implementation(libs.forge.datastore.api)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

buildkonfig {
    packageName = "de.gello"
    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "http://192.168.2.40:8080/api") //needs to be changed to actual server ip or your network ip for testing
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}


ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}