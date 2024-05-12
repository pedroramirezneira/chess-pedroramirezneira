val ktorVersion = "2.3.10"

plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "2.3.10"
}

group = "org.example"
version = "unspecified"

val githubUser: Any = project.findProperty("github.user") ?: System.getenv("GITHUB_USER")
val githubToken: Any = project.findProperty("github.token") ?: System.getenv("GITHUB_TOKEN")

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/chess-ui")
        credentials {
            username = githubUser as String
            password = githubToken as String
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":engine"))
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.24")
    implementation("io.ktor:ktor-network-tls-certificates")
}

application {
    mainClass.set("com.mediaversetv.chess.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
