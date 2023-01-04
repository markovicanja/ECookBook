const express = require("express")
const bodyParser = require("body-parser")
const mongoose = require("mongoose")
const cors = require("cors")
const app = express()
const http = require('http').Server(app)

//controllers:
const UserController = require("./controllers/userController");
const RecipeController = require("./controllers/recipeController");
const FollowingController = require("./controllers/followingController");
const CommentController = require("./controllers/commentController");
let controllers = [];
controllers.push(new UserController);
controllers.push(new RecipeController);
controllers.push(new FollowingController);
controllers.push(new CommentController);

app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

mongoose.connect('mongodb://localhost:27017/mserver');

const conn = mongoose.connection;

conn.once('open', () => {
    console.log('mongo open');
});

const router = express.Router();

// Route methods
controllers.forEach(element => {
    element.defaultRecords();
    element.routeMethods(router);
});

app.use('/', router)
http.listen(1234, () => {
	console.log("Server started on PORT 1234")
});