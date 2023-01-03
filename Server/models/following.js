const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Following = new Schema({
    username: {
        type: String,
        required: true
    },
    following: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('followings', Following);