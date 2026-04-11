plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `java`
}

dependencies {
    implementation(project(":bukkit"))
    implementation(project(":bungee"))
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("${project.rootProject.name}-${project.version}")
    relocate("org.apache.commons", "net.hnt8.advancedban.shaded.org.apache.commons")
    relocate("org.slf4j", "net.hnt8.advancedban.shaded.org.slf4j")
    relocate("com.zaxxer.hikari", "net.hnt8.advancedban.shaded.com.zaxxer.hikari")
    // Do not relocate org.sqlite so the driver class remains `org.sqlite.JDBC` at runtime
    relocate("org.bstats", "net.hnt8.advancedban.shaded.org.bstats")
    mergeServiceFiles()
}

tasks.register("bundleJar") {
    dependsOn(tasks.named("shadowJar"))
}
