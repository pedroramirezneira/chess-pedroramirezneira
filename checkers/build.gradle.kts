plugins {
    kotlin("jvm")
    id("application-conventions")
    id("org.openjfx.javafxplugin").version("0.0.13")
}

group = "edu.austral.dissis.chess"
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
    implementation("org.apache.commons:commons-text")
    implementation("com.google.code.gson:gson:2.10.1")
}

javafx {
    // Define the JavaFX version to use.
    version = "21"
    modules = listOf("javafx.graphics")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
