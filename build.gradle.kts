plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "me.hunter"
version = "1.0.0"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.named<JavaExec>("run") {
    jvmArgs("--add-modules", "java.desktop")
}

application {
    mainClass.set("me.noteapp.App")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.xerial:sqlite-jdbc:3.45.1.0")
    implementation("org.controlsfx:controlsfx:11.2.0")
}

tasks.test {
    useJUnitPlatform()
}


tasks.register<Exec>("jpackageApp") {
    dependsOn("build")

    val mainJar = tasks.named<Jar>("jar").get().archiveFile.get().asFile
    val outputDir = layout.buildDirectory.dir("installer").get().asFile

    doFirst {
        commandLine(
            "jpackage",
            "--input", mainJar.parent,
            "--main-jar", mainJar.name,
            "--main-class", "me.noteapp.App",
            "--name", "NoteApp",
            "--app-version", project.version.toString(),
            "--vendor", "Group 8",
            "--description", "DBMS Note app.",
            "--type", "exe",
            "--dest", outputDir.absolutePath
        )
    }
}
