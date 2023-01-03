const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Recipe = new Schema({
    name: {
        type: String,
        required: true
    },
    difficulty: {
        type: String,
        required: true
    },
    category: {
        type: String,
        required: true
    },
    cuisine: {
        type: String,
        required: true
    },
    img: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    author: {
        type: String,
        required: true
    },
    rating: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('recipes', Recipe);