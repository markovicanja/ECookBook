const express = require("express")
const bodyParser = require("body-parser")
const mongoose = require("mongoose")
const cors = require("cors")
const app = express()
const http = require('http').Server(app)
const defaultRecords = require('./data.js')

//models:
const user = require("./models/user")
const following = require("./models/following")
const comment = require("./models/comment")
const recipe = require("./models/recipe")

app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

mongoose.connect('mongodb://localhost:27017/mserver');

const conn = mongoose.connection;

conn.once('open', () => {
    console.log('mongo open');
});

const router = express.Router();

// User methodes
router.route('/getAllUsers').get((req, res) => {
    user.find({}, (err, users) => {
        if (err) res.json({status: 0, poruka: err});
        else res.json(users);
    });
});

router.route('/findUser').post((req, res) => {
    let username = req.body.username;
    
    user.findOne({'username' : username}, (err, user) => {
        if (err) res.json({status: 0, poruka: err});
        else{
            if(user) res.json({status: 1, username: user.username, email: user.email});
            else res.json({status: 0, poruka: "error"});
        }
    });
});

router.route('/loginUser').post((req, res) => {
    let username = req.body.username;
    let password = req.body.password;
    
    user.findOne({'username' : username, 'password' : password}, (err, user) => {
        if (err) res.json({status: 0, poruka: err});
        else{
            if(user) res.json({status: 1, poruka: user});
            else res.json({status: 0, poruka: "Neispravni kredencijali."});
        }
    });
});

router.route('/registerUser').post((req, res) => {
    let username = req.body.username;
    let password = req.body.password;
    let email = req.body.email;
    
    user.find({ 'username' : username }, (err, users) => {
        if (users.length == 0){
            user.find({ 'email' : email }, (err, users) => {
                if (users.length == 0){
                    user.collection.insertOne({ 'username' : username, 'email': email, 'password' : password });
                    res.json({status: 1, poruka: "Uspesno ste se registrovali."});
                }
                else res.json({status: 0, poruka: "Korisnik sa unetim mejlom vec postoji."});
            });
        }
        else res.json({status: 0, poruka: "Korisnik sa unetim korisnickim imenom vec postoji."});
    });
});

// Recipe methodes
router.route('/getAllRecipes').get((req, res) => {
    recipe.find({}, (err, recipes) => {
        if (err) res.json({status: 0, poruka: err});
        else res.json({status: 1, poruka: recipes});
    });
});

router.route('/getUserRecipes').post((req, res) => {
    let username = req.body.username;
    
    recipe.find({'author' : username}, (err, recipes) => {
        if (err) res.json({status: 0, poruka: err});
        else{
            if(recipes.length != 0) res.json({status: 1, poruka: recipes});
            else res.json({status: 0, poruka: "error"});
        }
    });
});

// Following methodes
router.route('/getFollowings').post((req, res) => {
    let username = req.body.username;
    
    following.find({'username' : username}, (err, followings) => {
        if (err) res.json({status: 0, poruka: err});
        else{
            if(followings.length != 0) res.json({status: 1, poruka: followings.map(f => f.following)});
            else res.json({status: 0, poruka: "error"});
        }
    });
});

defaultRecords(user, following, comment, recipe);

app.use('/', router)
http.listen(1234, () => {
	console.log("Server started on PORT 1234")
});