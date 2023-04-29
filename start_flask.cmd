cd ./src/flask
/venv/Scripts/activate.bat
python -m pip install -r requirements.txt
python -m pip install -e ./../py
python -m app.py