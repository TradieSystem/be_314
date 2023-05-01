cd ./src/flask
call /venv/Scripts/activate.bat && python -m pip install -r requirements.txt && python -m pip install -e ./../py && python app.py
PAUSE