"""
py that builds all required dependencies for backend local and starts services
"""

from subprocess import Popen, run, CREATE_NEW_CONSOLE
import os
import datetime

# static variables
docker_image_name = "csit314-mysql-image"
docker_image_ver = ":0.1"
docker_container_name = "csit314-mysql-container-{}".format(((datetime.datetime.now() - datetime.datetime.utcfromtimestamp(0)).total_seconds() * 1000.0))
database_dir = "./src/database"
flask_dir = "./src/flask"
join_script = "join.cmd"

# move to join directory and ensure most up to date version of sql queries generated in all
os.chdir(database_dir)
run([join_script])

# move back to root and run docker build
os.chdir("../..")
run("docker build -t {}{} .".format(docker_image_name, docker_image_ver))

# move back to root and run docker start container
database_process = Popen('docker run -p 3306:3306 --name {} {}{} mysqld --sql-mode=""'.format(docker_container_name, docker_image_name, docker_image_ver), creationflags=CREATE_NEW_CONSOLE)

# start flask
flask_process = Popen("start_flask.bat", creationflags=CREATE_NEW_CONSOLE)
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
