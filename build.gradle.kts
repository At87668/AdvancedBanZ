plugins {
    `java`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

group = properties["group"] as String
version = properties["version"] as String

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://repo.md-5.net/content/repositories/snapshots/") }
        maven { url = uri("https://repo.codemc.org/repository/maven-public") }
        maven { url = uri("https://repo.cloudnetservice.eu/repository/releases/") }
        maven { url = uri("https://cloudnetservice.eu/repositories") }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
        // Ensure doclet uses UTF-8 charset as well
        (options as? org.gradle.external.javadoc.StandardJavadocDocletOptions)?.addStringOption("charSet", "UTF-8")
    }

    tasks.withType<ProcessResources> {
        // Expand common Maven-style placeholders in plugin descriptors that use ${project.version} / ${project.groupId}
        val projectMap = mapOf("version" to project.version, "groupId" to project.group.toString())
        filesMatching("plugin.yml") {
            expand("project" to projectMap)
        }
        filesMatching("bungee.yml") {
            expand("project" to projectMap)
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }
}
