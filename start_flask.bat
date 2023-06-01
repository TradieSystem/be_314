cd ./src/flask
cd
call ./flask/Scripts/activate.bat && python -m pip install -r requirements.txt && python -m pip install -e ./../py && python app.py
cd
PAUSE
