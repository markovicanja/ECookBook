const comment = require("../models/comment")

class CommentController{
    routeMethods(router){
        router.route('/getComments').post((req, res) => {
            let recipe = req.body.recipe;
            
            comment.find({'recipe' : recipe}, (err, comments) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(comments.length != 0) res.json({status: 1, poruka: comments});
                    else res.json({status: 0, poruka: "error"});
                }
            });
        });
        
        router.route('/addComment').post((req, res) => {
            let recipe = req.body.recipe;
            let author = req.body.author;
            let date = req.body.date;
            let time = req.body.time;
            let body = req.body.body;
            
            comment.collection.insertOne({
                recipe: recipe, 
                author: author,
                date: date,
                time: time,
                body: body
            });
            res.json({status: 1, poruka: "success"});
        });
    }
    
    defaultRecords(){
        comment.collection.deleteMany();
        comment.collection.insertOne({
            recipe: "Cookies", 
            author: "ogi",
            date: "26/11/2022",
            time: "19:14",
            body: "My go to cookie recipe"
        });
        comment.collection.insertOne({
            recipe: "Cookies", 
            author: "nina",
            date: "2/11/2022",
            time: "11:14",
            body: "Love this recipe"
        });
        comment.collection.insertOne({
            recipe: "Chicken salad", 
            author: "ogi",
            date: "6/10/2022",
            time: "12:21",
            body: "Perfect summer salad!"
        });        
    }
}

module.exports = CommentController;