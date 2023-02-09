# Setup the TSDetect Server

## Install Docker

### Docker Desktop (Recommended) 

> [Windows](https://docs.docker.com/desktop/install/windows-install/) | 
> [Mac](https://docs.docker.com/desktop/install/mac-install/) | 
> [Linux](https://docs.docker.com/desktop/install/linux-install/) </br> Open Docker Desktop after installation.

### Docker from binaries

> Follow [these instructions](https://docs.docker.com/engine/install/binaries/) to install Docker using binaries. This is not recommended by Docker for production. This method should only be used for a Linux distribution not supported by Docker Desktop.

---

## Build Application

### Intellij Build

#### Option 1

From the Gradle submenu located in the top right traverse to the lifecycle folder:
<br>
`Tasks -> build`
<br>
Select the `build` task, right click and select `Run "server [build]"`

#### Option 2

Create a new `Gradle` run configuration in the top right dropdown menu with the following task:
```
build
```
Run the configuration by selecting the play button

### CLI Build

Within the `server` directory, run the following command:
```bash
./gradlew build
```

### Docker Build

#### Option 1
From the Gradle submenu located in the top right traverse to the lifecycle folder:
<br>
`Tasks -> build`
<br>
Select the `bootBuildImage` task, right click and select `Run "server [bootBuildImage]"`

#### Option 2

Within the `server` directory, run the following command:
```bash
./gradlew bootBuildImage
```

---

## Running Application

### Intellij Run

#### Option 1

From the Gradle submenu located in the top right traverse to the lifecycle folder:
<br>
`Tasks -> application`
<br>
Select the `bootRun` task, right click and select `Run "Run Server"`

#### Option 2

Create a new `Gradle` run configuration in the top right dropdown menu with the following task:
```
bootRun
```
Run the configuration by selecting the play button

#### Option 3

Right click the `ServerApplication` class and select `Run`

### CLI Run

Within the `server` directory, run the following command:
```
./gradlew bootRun
```

### Docker Run

Assuming you have built the image correctly, you may enter the following docker command
to run the application in a container:
```
docker run --name tsdetect-server --net tsdetect-network -d -p 8080:8080 -t tsdetect-server:1.0.0
```
