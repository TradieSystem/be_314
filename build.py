"""
py that builds all required dependencies for backend local and starts services
"""

import subprocess
import os
import datetime

# static variables
docker_image_name = "csit314-mysql-image"
docker_image_ver = ":0.1"
docker_container_name = "csit314-mysql-container"
database_dir = "./src/database"
join_script = "join.cmd"

# move to join directory and ensure most up to date version of sql queries generated in all
os.chdir(database_dir)
subprocess.run([join_script])

# move back to root and run docker build
os.chdir("../..")
subprocess.run("docker build -t {}{} .".format(docker_image_name, docker_image_ver), shell=True)

# start flask

# start container
subprocess.run("docker run -p 3306:3306 --name {}-{} {}{}".format(docker_container_name, ((datetime.datetime.now() - datetime.datetime.utcfromtimestamp(0)).total_seconds() * 1000.0), docker_image_name, docker_image_ver), shell=True)

