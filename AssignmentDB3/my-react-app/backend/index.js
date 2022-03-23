// To connect with your mongoDB database
const mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/', {
    dbName: 'twitter',
    useNewUrlParser: true,
    useUnifiedTopology: true
}, err => err ? console.log(err) :
    console.log('Connected to twitter database'));

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

const TwitterPost = mongoose.model('tweets', {
    _id: { type: String },
    count: { type: Number },
});
TwitterPost.createIndexes();

app.get("/twitterPosts", async (req, resp) => {

    try {

        TwitterPost.aggregate([{ $unwind: '$entities.hashtags' },
        { $group: { _id: '$entities.hashtags.text', count: { $sum: 1 } } },
        { $sort: { count: -1 } },
        { $limit: 10 }], function (err, docs) {
            if (err) {
                console.log(err);
            }
            else {
                var arr = docs;
                resp.send(arr);
                console.log("It worked : ", docs);
            }
        });

    } catch (e) {
        resp.send("Something Went Wrong");
    }

})
app.listen(5000);