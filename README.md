# HOW TO INSTALL
```
git clone https://github.com/F0-t0/FepCore.git
cd FepCore
mvn install
```

Then in your project add:

## MAVEN:
```xml
<dependency>
        <groupId>pl.fepbox.core</groupId>
        <artifactId>FepCore</artifactId>
        <version>»PUT THE LATEST VERSION HERE«</version>
        <scope>provided</scope> 
</dependency>
```
## GRADLE (KTS)
```kts
repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("pl.fepbox.core:FepCore:»PUT THE LATEST VERSION HERE«")
}
```