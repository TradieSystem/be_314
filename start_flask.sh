#! /bin/bash
cd ./src/flask
. ./venv/Scripts/activate
python3 -m pip install -r requirements.txt
python3 -m pip install -e ./../py
python3 app.py
read -p "Press any key to continue..."