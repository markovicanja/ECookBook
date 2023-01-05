const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const RecommendedRecipe = new Schema({
    username: {
        type: String,
        required: true
    },
    usernameOfFollowing: {
        type: String,
        required: true
    },
    recipeNames: {
        type: [String],
        required: true
    }
});

module.exports = mongoose.model('recommendedRecipe', RecommendedRecipe);