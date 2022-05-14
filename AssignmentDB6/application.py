
from flask import Flask, request, jsonify, render_template
import redis
import json
from json2html import *
import time

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
    
def getData(n1, n2):
    dataList = []
    tic = time.perf_counter()
    for n in range(int(n1), int(n2)+1):
        #print(n)
        #print(r.hgetall(str(n)))
        d = r.hgetall(str(n))
        dataList.append(d)
    toc = time.perf_counter()
    dataList.append(toc - tic)
    return dataList

def getAttributes():
    tic = time.perf_counter()
    unPrettyList = list(r.hgetall('1'))
    toc = time.perf_counter()
    prettyList = []
    for s in unPrettyList:
        txt = str(s).split("'")
        prettyList.append(txt[1])
    time2 = toc - tic
    prettyList.append(time2)
    return prettyList

def getBySpecificAtt(n, s):
    tic = time.perf_counter()
    string = r.hget(n,s)
    toc = time.perf_counter()
    time2 = toc - tic
    txt = str(string).split("'")
    
    dataList = []
    dataList.append(txt[1])
    dataList.append(time2)
    return dataList


# ---------------- THE ENDPOINTS --------------------
@app.route('/')
def start():
    attributes = getAttributes()
    
    return render_template('start.html', attributes=attributes[1:-1], time=attributes[-1])

@app.route('/data', methods=['GET', 'POST'])
def data():
    if request.method == 'POST':
        n1 = request.form['n1']
        n2 = request.form['n2']
        data = getData(n1, n2)
          
        return render_template("data.html", data=data[int(n1):int(n2)], time=data[-1])
    
@app.route('/specificData', methods=['GET', 'POST'])
def specificData():
    if request.method == 'POST':
        n = request.form['n']
        s = request.form['s']
        data = getBySpecificAtt(n, s)
          
        return render_template("specificData.html", data=data[0], time=data[1])

@app.route('/redis', methods=['GET', 'POST'])
def predict():
    if request.method == 'GET':
        data = request.data
    return render_template('redis.html', url1=data)

if __name__ == '__main__':
    app.run(debug=True)
