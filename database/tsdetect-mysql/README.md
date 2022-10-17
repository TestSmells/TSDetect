# Setup the TSDetect Centralized Relational Database

The TSDetect centralized relational database is hosted in a Docker container and is accessible with phpMyAdmin.

The docker-compose file builds the MySQL image and populates the tsdetect database with the initial tables via an SQL query located in the init.sql file. The phpMyAdmin image is built as well and linked to the database instance. Both images are then run in separate containers.

The init.sql file creates the tables as defined in the database schema TDD. The test_smells table is populated with all currently defined smell types and the test_runs and test_smell_runs table are populated with random data for testing. 

Remove or comment out the last line of the SQL file to prevent random data insertion.

</br>

## Install Docker

### Docker Desktop (Recommended) 

> [Windows](https://docs.docker.com/desktop/install/windows-install/) | [Mac](https://docs.docker.com/desktop/install/mac-install/) | [Linux](https://docs.docker.com/desktop/install/linux-install/) </br> Open Docker Desktop after installation.

</br>

### Docker from binaries

> Follow [these instructions](https://docs.docker.com/engine/install/binaries/) to install Docker using binaries. This is not recommended by Docker for production. This method should only be used for a Linux distribution not supported by Docker Desktop.

</br>

## Create and run containers

### Windows

- Double click the name-appropriate bat file to start and kill the database.

### MacOS and Linux

Launch a terminal window and navigate to this directory on your machine: tsdetect-mysql

1.  Ensure Docker is running!

    `docker ps`

![docker ps](docs/docker_ps.png)

---

2. Run docker compose to create and run the containers 

    `docker compose up -d`

![docker compose](docs/docker_compose.png)

---

3. Navigate to http://localhost:8081 in a browser and login with username=root and password=password

![phpmyadmin](docs/phpmyadmin.png)

---

4. Verify the tsdetect database has been created and populated.

![phpmyadmin_tsdetect](docs/phpmyadmin_tsdetect.png)