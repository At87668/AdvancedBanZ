plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("commons-io:commons-io:2.18.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit5.version")}")
    runtimeOnly("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-nop:2.0.13")
    implementation("com.zaxxer:HikariCP:6.2.1") {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    compileOnly("com.google.code.gson:gson:2.11.0")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-text-serializer-ansi:4.17.0")
}
