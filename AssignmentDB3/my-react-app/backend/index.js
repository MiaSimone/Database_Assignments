// To connect with your mongoDB database
const mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/', {
    dbName: 'cats',
    useNewUrlParser: true,
    useUnifiedTopology: true
}, err => err ? console.log(err) : 
    console.log('Connected to cats database'));

// Schema for users of app
const UserSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    date: {
        type: Date,
        default: Date.now,
    },
});
const User = mongoose.model('users', UserSchema);
User.createIndexes();

// For backend and express
const express = require('express');
const app = express();
const cors = require("cors");
console.log("App listen at port 5000");
app.use(express.json());
app.use(cors());
app.get("/", (req, resp) => {

    resp.send("App is Working");
    // You can check backend is working or not by 
    // entering http://loacalhost:5000
    
    // If you see App is working means
    // backend working properly
});

app.post("/register", async (req, resp) => {
    try {
        const user = new User(req.body);
        let result = await user.save();
        result = result.toObject();
        if (result) {
            delete result.password;
            resp.send(req.body);
            console.log(result);
        } else {
            console.log("User already register");
        }

    } catch (e) {
        resp.send("Something Went Wrong");
    }
});


const Contries = mongoose.model('Contries', {
    name: { type: String },
    short: { type: String }
});

const Customers = mongoose.model('Customers', {
    dust: { type: String },
    mist: { type: String }
});

app.get("/twitterPosts", async (req, resp) => {

    try {

        Customers.find(null, null, {limit:10}, function (err, docs) {
            if (err){
                console.log(err);
            }
            else{
                var arr = docs;
                resp.send(arr);
                console.log("First function call : ", docs);
            }
        });

    } catch (e) {
        resp.send("Something Went Wrong");
    }

})
app.listen(5000);