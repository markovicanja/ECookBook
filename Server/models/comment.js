const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Comment = new Schema({
    recipe: {
        type: String,
        required: true
    },
    author: {
        type: String,
        required: true
    },
    date: {
        type: String,
        required: true
    },
    time: {
        type: String,
        required: true
    },
    body: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('comments', Comment);