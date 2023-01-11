const user = require("../models/user")

class UserController{
    routeMethods(router){
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
                    else res.json({status: 0, poruka: "Wrong credentials."});
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
                            res.json({status: 1, poruka: "Registration successful."});
                        }
                        else res.json({status: 0, poruka: "User with that email already exists."});
                    });
                }
                else res.json({status: 0, poruka: "User with that username already exists."});
            });
        });

        router.route('/getAllImagesNames').get((req, res) => {
            const fs = require('fs');
            const files = fs.readdirSync('../../Web app/ECookBook/src/assets/recipes');
            res.json({files: files});
        });
    }

    defaultRecords(){
        user.collection.deleteMany();
        user.collection.insertOne({ username: "anja", email: "anja@gmail.com", password: "123" });
        user.collection.insertOne({ username: "ogi", email: "ogi@gmail.com", password: "123" });
        user.collection.insertOne({ username: "nina", email: "nina@gmail.com", password: "123" });
    }
}

module.exports = UserController;