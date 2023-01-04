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

        router.route('/getIsFollowing').post((req, res) => {
            let username = req.body.username;
            let followingVar = req.body.following;
            
            following.findOne({'username' : username, 'following' : followingVar}, (err, follow) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(follow) res.json({status: 1, poruka: "success"});
                    else res.json({status: 0, poruka: "error"});
                }
            });
        });

        router.route('/setIsFollowing').post((req, res) => {
            let username = req.body.username;
            let followingVar = req.body.following;
            
            following.findOne({'username' : username, 'following' : followingVar}, (err, follow) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(follow) res.json({status: 0, poruka: "Vec se pratite."});
                    else{
                        following.collection.insertOne({'username' : username, 'following' : followingVar});
                        res.json({status: 1, poruka: "success"});
                    }
                }
            });
        });

        router.route('/unfollow').post((req, res) => {
            let username = req.body.username;
            let followingVar = req.body.following;
            
            following.findOne({'username' : username, 'following' : followingVar}, (err, follow) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(follow){
                        following.collection.deleteOne({'username' : username, 'following' : followingVar});
                        res.json({status: 1, poruka: "success"});
                    }
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