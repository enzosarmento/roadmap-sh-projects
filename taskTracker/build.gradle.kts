plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.0.0"
    application
}

group = "org.example"
version = "1.0"

application {
    mainClass.set("MainKt")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-class"] = "MainKt"
    }
    from(sourceSets.main.get().output)  // Incluir todas as classes compiladas no JAR
    configurations.runtimeClasspath.get().forEach { file ->
        from(zipTree(file))  // Inclui as dependências (como a biblioteca Kotlin)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}