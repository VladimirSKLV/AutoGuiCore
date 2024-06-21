plugins {
    id("java")
}

group = "ru.augui.core"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.github.romankh3:image-comparison:4.4.0")
    implementation ("net.sf.py4j:py4j:0.10.9.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}