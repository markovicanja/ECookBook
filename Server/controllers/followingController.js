const following = require("../models/following")

class FollowingController{
    routeMethods(router){
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
    }
    
    defaultRecords(){
        following.collection.deleteMany();
        following.collection.insertOne({ username: "anja", following: "ogi" });
        following.collection.insertOne({ username: "anja", following: "nina" });        
    }
}

module.exports = FollowingController;