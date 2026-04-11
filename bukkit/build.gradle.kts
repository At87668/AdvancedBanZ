plugins {
    `java`
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    implementation(project(":core"))
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
}
