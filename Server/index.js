const express = require("express")
const bodyParser = require("body-parser")
const mongoose = require("mongoose");
const app = express()
const http = require('http').Server(app)

//models:
const user = require("./models/user");

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

mongoose.connect('mongodb://localhost:27017/mserver');

const conn = mongoose.connection;

conn.once('open', () => {
    console.log('mongo open');
});

http.listen(1234, () => {
	console.log("Server started on PORT 1234")
})