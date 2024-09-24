import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        val jserialcommVersion: String by project
        val exposedVersion: String by project
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            // Serial Communication - jSerialComm
            implementation(libs.jSerialComm)
            // Database - Exposed
            implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
            implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.openswim.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "OpenSwim"
            packageVersion = "1.0.0"
            licenseFile.set(project.file("../LICENSE"))
        }
    }
}
