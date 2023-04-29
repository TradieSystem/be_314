"""
py that builds all required dependencies for backend local and starts services for mac (why would you use mac?)
"""

from subprocess import Popen, run, STDOUT, PIPE
import os
import datetime

# static variables
docker_image_name = "csit314"
docker_image_ver = ":0.1"
docker_container_name = "csit314-mysql-container-{}".format(((datetime.datetime.now() - datetime.datetime.utcfromtimestamp(0)).total_seconds() * 1000.0))
database_dir = "./src/database"
flask_dir = "./src/flask"
join_script = "join.sh"

# run docker build
build_command = "docker build -t {}{} .".format(docker_image_name, docker_image_ver)
Popen(build_command, shell=True)

# move back to root and run docker start container
run_command = 'docker run -p 3306:3306 --name {} {}{} mysqld --sql-mode=""'.format(docker_container_name, docker_image_name, docker_image_ver)
database_process = Popen(run_command, shell=True)

# start flask
chmod_command = "sudo chmod +x start_flask.sh"
Popen(chmod_command, shell=True)

flask_command = "start_flask.sh"
flask_process = Popen(flask_command, shell=True)
print

# wait for shutdown command
print("Please Type Shutdown code 'kill':")

# simple wait for kill command
while input("$ ") != "kill":
    continue

# terminate processes
Popen("TASKKILL /F /PID {} /T".format(flask_process.pid))

# stop and delete docker container
run("docker stop {}".format(docker_container_name))
run("docker rm --volumes {}".format(docker_container_name))
