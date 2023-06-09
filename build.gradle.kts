plugins {
    kotlin("jvm") version "1.8.0"
}

group = "kr.hqservice.book"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc", "spigot", "1.19.3-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveFileName.set("${rootProject.name}-${project.version}.jar")
    destinationDirectory.set(File("D:\\서버\\1.19.3 - 개발\\plugins"))
}