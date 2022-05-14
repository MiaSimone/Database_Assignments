
from flask import Flask, request, jsonify, render_template
import redis
import json
from json2html import *

app = Flask(__name__, template_folder='hmtl_documents')

# ---------------- THE CODE -------------------------
r = redis.Redis(db=1)

with open('url1.json') as json_file:
    data = json.load(json_file)

with r.pipeline() as pipe:
    for dic in data['data']:
        pipe.hmset((data['data'].index(dic)+1), dic)
        pipe.execute()
r.bgsave()
    
def getData(n1, n2) -> dict:
    dataList = []
    for n in range(n1, n2+1):
        dataList.append(r.hgetall(str(n)))
    return dataList


# ---------------- THE ENDPOINTS --------------------
@app.route('/')
def start():
    return render_template('start.html')

@app.route('/data', methods=['GET', 'POST'])
def data():
    if request.method == 'POST':
        n1 = request.form['n1']
        n2 = request.form['n2']
        data = getData(int(n1), int(n2))
          
        return render_template("data.html", content=data)
    

@app.route('/redis', methods=['GET', 'POST'])
def predict():
    if request.method == 'GET':
        data = request.data
    return render_template('redis.html', url1=data)

if __name__ == '__main__':
    app.run(debug=True)
