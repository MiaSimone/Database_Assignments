
import joblib
import pickle
from flask import Flask, request, jsonify, render_template

app = Flask(__name__, template_folder='hmtl_documents')


#@app.route('/', methods=['GET', 'POST'])

@app.route('/')
def start():
    return render_template('start.html')

@app.route('/data', methods=['GET', 'POST'])
def data():
    if request.method == 'POST':
        n1 = request.form['n1']
        n2 = request.form['n2']
        data = getData(n1, n2)
          
        return render_template("data.html", content=data)
    

@app.route('/redis', methods=['GET', 'POST'])
def predict():
    if request.method == 'GET':
        data = request.data
    return render_template('redis.html', url1=data)

if __name__ == '__main__':
    app.run(debug=True)
