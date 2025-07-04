## 1.20.1 Forge
```groovy
repositories {
    maven {
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    jarJar(implementation(fg.deobf("maven.modrinth:simplebedrockmodel:1.3.0-forge+mc1.20.1"))) {
        jarJar.ranged(it, "[1.3.0,)")
    }
}
```

## 1.21.1 NeoForge
```groovy
repositories {
    maven {
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    implementation jarJar("maven.modrinth:simplebedrockmodel:1.3.0-neoforge+mc1.21.1") {
        version {
            strictly '[1.3.0-neoforge+mc1.21.1,)'
            prefer '1.3.0-neoforge+mc1.21.1'
        }
    }
}
```