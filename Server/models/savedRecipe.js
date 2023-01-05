const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const SavedRecipe = new Schema({
    username: {
        type: String,
        required: true
    },
    recipeNames: {
        type: [String],
        required: true
    }
});

module.exports = mongoose.model('savedRecipe', SavedRecipe);