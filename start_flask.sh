#! /bin/bash
cd ./src/flask
. ./venv/Scripts/activate
python -m pip install -r requirements.txt
python -m pip install -e ./../py
python app.py
read -p "Press any key to continue..."