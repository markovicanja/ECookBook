const recipe = require("../models/recipe")
const savedRecipe = require("../models/savedRecipe")
const recommendedRecipe = require("../models/recommendedRecipe");
const following = require("../models/following")
const e = require("express");

class RecipeController{
    routeMethods(router){
        router.route('/getAllRecipes').get((req, res) => {
            recipe.find({}, (err, recipes) => {
                if (err) res.json({status: 0, poruka: err});
                else res.json({status: 1, poruka: recipes});
            });
        });
        
        router.route('/getAllRecipesByVisibility').post((req, res) => {
            let username = req.body.username;

            recipe.find({}, (err, recipes) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    following.find({'username' : username}, (error, followers) => {
                        if (error) res.json({status: 0, poruka: error});
                        else{
                            let array = [];
                            recipes.forEach(element => {
                                if(element.author === username){
                                    array.push(element);
                                }else{
                                    if(element.visibility == 0){
                                        array.push(element);
                                    }
                                    else if(element.visibility == 1){
                                        if(followers.findIndex((obj) => element.author === obj.following) !== -1){
                                            array.push(element);
                                        }
                                    }
                                }
                            });
                            res.json({status: 1, poruka: array});
                        }
                    });
                }
            });
        });

        router.route('/getUserRecipes').post((req, res) => {
            let username = req.body.username;
            
            recipe.find({'author' : username}, (err, recipes) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(recipes.length != 0) res.json({status: 1, poruka: recipes});
                    else res.json({status: 0, poruka: "error"});
                }
            });
        });

        router.route('/addRecipe').post((req, res) => {
            let name = req.body.name;
            let difficulty = req.body.difficulty;
            let category = req.body.category;
            let cuisine = req.body.cuisine;
            let img = req.body.img;
            let description = req.body.description;
            let author = req.body.author;
            let visibility = req.body.visibility;
            let rating = req.body.rating;
            
            recipe.find({ 'name' : name }, (err, recipes) => {
                if (recipes.length == 0){
                    recipe.collection.insertOne({
                        'name' : name,
                        'difficulty': difficulty,
                        'category' : category,
                        'cuisine' : cuisine,
                        'img': img,
                        'description' : description,
                        'author' : author,
                        'visibility' : visibility,
                        'rating' : rating
                    });
                    res.json({status: 1, poruka: "Recipe successfully added."});
                }
                else res.json({status: 0, poruka: "Recipe already exists."});
            });
        });

        router.route('/changeVisiblity').post((req, res) => {
            let name = req.body.name;
            let visibility = req.body.visibility;
            
            recipe.findOne({ 'name' : name }, (err, rec) => {
                if (rec){
                    recipe.collection.updateOne({'name' : name}, {$set: {'visibility': visibility }});
                    res.json({status: 1, poruka: "Successfully changed!"});
                }
                else res.json({status: 0, poruka: "Recipe doesn't exist."});
            });
        });

        router.route('/rateRecipe').post((req, res) => {
            let name = req.body.name;
            let rating = req.body.rating;
            
            recipe.findOne({ 'name' : name }, (err, rec) => {
                if (rec){
                    let newVal = rec.rating;
                    if(newVal > rating) newVal = parseFloat(newVal) - 0.1;
                    else if(newVal < rating) newVal = parseFloat(newVal) + 0.1;
                    newVal = parseFloat(newVal);

                    recipe.collection.updateOne({'name' : name}, {$set: {'rating': newVal }});
                    res.json({status: 1, poruka: newVal});
                }
                else res.json({status: 0, poruka: "Recipe doesn't exist."});
            });
        });

        router.route('/getAllRecommendedRecipes').post((req, res) => {
            let username = req.body.username;
            
            recommendedRecipe.find({ 'usernameOfFollowing' : username }, (err, rRecipes) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    let array = [];
                    if(rRecipes && rRecipes.length != 0){
                        rRecipes.forEach(x => {
                            if(x.recipeNames.length != 0){
                                x.recipeNames.forEach(y => {
                                    if(array.indexOf(y) === -1) {
                                        array.push(y);
                                    }
                                });
                            } 
                        }); 
                        
                        if(array.length !=0){
                            recipe.find({}, (err, allRecipes) => {
                                if (err) res.json({status: 0, poruka: err});
                                else{
                                    let arrayRet = [];
                                    allRecipes.forEach(x => {
                                        array.forEach(y => {
                                            if(x.name === y) arrayRet.push(x);
                                        });
                                    });
    
                                    res.json({status: 1, poruka: arrayRet});
                                }
                            }); 
                        }
                        else res.json({status: 0, poruka: "no recipes"});
                    }
                    else res.json({status: 0, poruka: "no recipes"});
                }
            });
        });

        router.route('/recommendRecipe').post((req, res) => {
            let username = req.body.username;
            let usernameOfFollowing = req.body.usernameOfFollowing;
            let recipeName = req.body.recipeName;
            
            recommendedRecipe.findOne({ 'username' : username, 'usernameOfFollowing' : usernameOfFollowing }, (err, rRecipe) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(rRecipe){
                        if(!rRecipe.recipeNames.includes(recipeName)){
                            recommendedRecipe.collection.updateOne({ 'username' : username,  'usernameOfFollowing' : usernameOfFollowing }, {$push: {'recipeNames' : recipeName}});
                            res.json({status: 1, poruka: "success"});
                        }else{
                            res.json({status: 0, poruka: "no recipe"});
                        }
                    }
                    else{
                        recommendedRecipe.collection.insertOne({ 'username' : username, 'usernameOfFollowing' : usernameOfFollowing, 'recipeNames' : [recipeName]});
                    }
                }
            });
        });

        router.route('/getAllSavedRecipes').post((req, res) => {
            let username = req.body.username;
            
            savedRecipe.findOne({ 'username' : username }, (err, sRecipe) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(sRecipe){
                        recipe.find({}, (err, allRecipes) => {
                            if (err) res.json({status: 0, poruka: err});
                            else{
                                let array = [];
                                allRecipes.forEach(x => {
                                    sRecipe.recipeNames.forEach(y => {
                                        if(x.name === y) array.push(x);
                                    });
                                });

                                res.json({status: 1, poruka: array});
                            }
                        });                        
                    }
                    else res.json({status: 0, poruka: "no recipes"});
                }
            });
        });

        router.route('/findIfUserSavedRecipe').post((req, res) => {
            let username = req.body.username;
            let recipeName = req.body.recipeName;
            
            savedRecipe.findOne({ 'username' : username }, (err, recipe) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(recipe){
                        if(recipe.recipeNames.includes(recipeName)){
                            res.json({status: 1, poruka: "success"});
                        }else{
                            res.json({status: 0, poruka: "no recipe"});
                        }
                    }
                    else res.json({status: 0, poruka: "no recipes"});
                }
            });
        });
        
        router.route('/saveRecipe').post((req, res) => {
            let username = req.body.username;
            let recipeName = req.body.recipeName;
            
            savedRecipe.findOne({ 'username' : username }, (err, recipe) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(recipe){
                        if(!recipe.recipeNames.includes(recipeName)){
                            savedRecipe.collection.updateOne({ 'username' : username }, {$push: {'recipeNames' : recipeName}});
                            res.json({status: 1, poruka: "success"});
                        }else{
                            res.json({status: 0, poruka: "no recipe"});
                        }
                    }
                    else{
                        savedRecipe.collection.insertOne({ 'username' : username , 'recipeNames' : [recipeName]});
                        res.json({status: 1, poruka: "success"});
                    }
                }
            });
        });
        
        router.route('/removeSavedRecipe').post((req, res) => {
            let username = req.body.username;
            let recipeName = req.body.recipeName;
            
            savedRecipe.findOne({ 'username' : username }, (err, recipe) => {
                if (err) res.json({status: 0, poruka: err});
                else{
                    if(recipe){
                        if(recipe.recipeNames.includes(recipeName)){
                            savedRecipe.collection.updateOne({ 'username' : username }, {$pull: {'recipeNames' : recipeName}});
                            res.json({status: 1, poruka: "success"});
                        }else{
                            res.json({status: 0, poruka: "no recipe"});
                        }
                    }
                    else{
                        res.json({status: 0, poruka: "no recipes"});
                    }
                }
            });
        });
    }

    defaultRecords(){
        recommendedRecipe.collection.deleteMany();
        savedRecipe.collection.deleteMany();

        recipe.collection.deleteMany();
        recipe.collection.insertOne({
            name: "Chicken salad",
            difficulty: 2,
            category: "Salad",
            cuisine: "Chinese",
            img: "chicken_salad",
            description: "This recipe for Basic Chicken Salad uses six simple \
            ingredients and takes only 10 minutes to make from start to finish. \
            After that, you can serve it up on salad greens, scoop it into pita \
            bread or half an avocado, roll it up in a tortilla for a wrap, or serve \
            it hot in a quesadilla or chicken melt. \
            Place almonds in a frying pan. Toast over medium-high heat, \
            shaking frequently. Watch carefully, as they burn easily. \
            Mix together mayonnaise, lemon juice, and pepper in a medium bowl. \
            Toss with chicken, toasted almonds, and celery.",
            author: "anja",
            visibility: 0,
            rating: 4
        });
        recipe.collection.insertOne({
            name: "Cookies",
            difficulty: 3,
            category: "Dessert",
            cuisine: "American",
            img: "cookies",
            description: "Preheat the oven to 350 degrees F (175 degrees C). \
            Beat butter, white sugar, and brown sugar with an electric mixer in \
            a large bowl until smooth. Beat in eggs, one at a time, then stir in \
            vanilla. Dissolve baking soda in hot water. Add to batter along with salt. \
            Stir in flour, chocolate chips, and walnuts. \
            Drop spoonfuls of dough 2 inches apart onto ungreased baking sheets. \
            Bake in the preheated oven until edges are nicely browned, about 10 minutes. \
            Cool on the baking sheets briefly before removing to a wire rack to cool completely.",
            author: "nina",
            visibility: 0,
            rating: 4
        });
        recipe.collection.insertOne({
            name: "Breakfast muffins",
            difficulty: 1,
            category: "Breakfast",
            cuisine: "American",
            img: "breakfast_muffins",
            description: "Preheat the oven to 350 degrees F (175 degrees C). \
            Grease a 12-cup muffin tin. \
            Place green onions and bell peppers into a large mixing bowl. \
            Add eggs, bacon, milk, garlic powder, onion powder, salt, and pepper. \
            Sprinkle Cheddar cheese into the bowl and whisk ingredients together until \
            incorporated. \
            Pour mixture equally into the prepared muffin cups. \
            Bake in the preheated oven until a toothpick inserted \
            into the center of a muffin comes out clean, about 30 minutes. \
            Let cool slightly before serving.",
            author: "ogi",
            visibility: 0,
            rating: 5
        });
        recipe.collection.insertOne({
            name: "Healthy carbonara",
            difficulty: 5,
            category: "Dinner",
            cuisine: "Italian",
            img: "healthy_carbonara",
            description: "Place chicken, garlic, lemon zest, chives, thyme, oregano, \
            1/2 teaspoon salt, 1/4 teaspoon pepper, and 1/4 cup olive oil in a resealable \
            plastic bag; toss to coat chicken and refrigerate for 3 hours or up to overnight.\
            Heat a large skillet over medium heat; cook and stir chicken with marinade until \
            chicken is no longer pink at the center and juices run clear, about 8 minutes. \
            An instant-read thermometer inserted into the center should read at least 165 \
            degrees F (74 degrees C). Remove chicken from pan; set aside to keep warm. \
            Drizzle about 1 teaspoon oil into the same skillet over medium-high heat; \
            stir in zucchini and red pepper flakes and cook until zucchini is warm, \
            about 3 minutes; season with salt and pepper. Stir ricotta cheese and basil \
            into zucchini; cook until heated through, about 2 minutes. \
            Return chicken to pan with zucchini mixture; stir to combine. \
            Remove pan from heat, squeeze lemon juice over entire dish, \
            and garnish with diced tomatoes.",
            author: "anja",
            visibility: 0,
            rating: 5
        });
        recipe.collection.insertOne({
            name: "Lava cake",
            difficulty: 5,
            category: "Dessert",
            cuisine: "French",
            img: "lava_cake",
            description: "Generously butter the inside of 4 (5 1/2 ounce) ramekins. \
            Place them in a casserole dish. \
            Whisk together egg yolks, eggs, and sugar in a bowl until light, foamy, and \
            lemon colored. \
            Melt chocolate and butter in a microwave-safe bowl in 30-second intervals, \
            stirring after each melting, 1 to 3 minutes.\
            Stir melted chocolate mixture into egg and sugar mixture until combined. \
            Sift cocoa powder into the mixture; stir to combine. \
            Sift flour and salt into the mixture; stir to combine into a batter. \
            Stir vanilla extract into the batter. \
            Transfer batter to a resealable plastic bag. Snip one corner of the bag with \
            scissors to create a tip. \
            Divide batter evenly between the prepared ramekins; tap gently on the counter \
            to remove any air bubbles. \
            Refrigerate 30 minutes. \
            Preheat an oven to 425 degrees F (220 degrees C). \
            Arrange the ramekins in a casserole dish. Pour enough hot tap water into the \
            casserole dish to reach halfway up the sides of the ramekins. \
            Bake in the preheated over for 15-18 minutes. Set aside to cool for 15 minutes. \
            Loosen the edges from the ramekin with a knife. Invert each cake onto a plate and \
            dust with powdered sugar.",
            author: "anja",
            visibility: 0,
            rating: 5
        });
        recipe.collection.insertOne({
            name: "Nutty squares",
            difficulty: 3,
            category: "Dessert",
            cuisine: "Hungarian",
            img: "nutty_squares",
            description: "Grease a 9x13-inch dish. To make the crust, place 1/2 cup of butter \
            in a large saucepan, and melt over low heat. Stir in the sugar, cocoa powder, and \
            vanilla extract until the sugar has dissolved. Remove from the heat, mix in the egg \
            until the mixture is well-combined, then stir in graham cracker crumbs and walnuts. \
            Pack the mixture into the bottom of the prepared baking dish, smooth it into an even \
            layer, and refrigerate until cooled, at least 30 minutes. \
            Make the filling: beat together confectioners' sugar, 1/2 cup of softened butter, \
            pudding mix, and milk until the filling is smooth and fluffy. Spread the filling \
            over the cooled crust, and return to refrigerator for 30 more minutes. \
            For topping, melt chocolate with 2 tablespoons of butter over very low heat, and \
            stir until the mixture is warm (not hot) and pourable. Pour the topping over the \
            cooled filling, spreading the topping out if necessary with an offset spatula or \
            knife to cover the topping completely. Return the dish to the refrigerator to cool \
            the topping for about 15 minutes. When the topping is cool but not yet hard, cut \
            into squares; return to refrigerator to finish chilling.",
            author: "nina",
            visibility: 0,
            rating: 5
        });
        recipe.collection.insertOne({
            name: "Oatmeal bars",
            difficulty: 2,
            category: "Dessert",
            cuisine: "American",
            img: "oatmeal_bars",
            description: "Grease a 9-inch square pan. \
            Melt butter in a large saucepan over medium heat. Stir in brown sugar and vanilla; \
            mix in oats. Cook over low heat until ingredients are well blended, about 2 to 3 \
            minutes. Press 1/2 of the mixture into the bottom of the prepared pan. Reserve \
            remaining oat mixture for topping. \
            Meanwhile, melt chocolate chips and peanut butter in a small heavy saucepan over \
            low heat, stirring frequently until smooth. Pour chocolate mixture over crust in \
            the pan and spread evenly with a knife or the back of a spoon. \
            Crumble remaining oat mixture over chocolate layer, pressing down gently. \
            Cover and refrigerate for 2 to 3 hours or overnight. Bring to room temperature \
            before cutting into bars.",
            author: "anja",
            visibility: 0,
            rating: 3
        });
        recipe.collection.insertOne({
            name: "Pork chops",
            difficulty: 5,
            category: "Dinner",
            cuisine: "Serbian",
            img: "pork_chops",
            description: "Preheat grill for medium heat and lightly oil the grate. \
            Whisk ketchup, honey, soy sauce, and garlic together in a bowl to make a glaze. \
            Sear the pork chops on both sides on the preheated grill. Lightly brush glaze onto \
            each side of the chops as they cook; grill until no longer pink in the center, \
            about 7 to 9 minutes per side. An instant-read thermometer inserted into the center \
            should read 145 degrees F (63 degrees C).",
            author: "ogi",
            visibility: 0,
            rating: 3
        });
        recipe.collection.insertOne({
            name: "Spinach pie",
            difficulty: 3,
            category: "Breakfast",
            cuisine: "Greek",
            img: "spinach_pie",
            description: "Preheat the oven to 350 degrees F (175 degrees C). \
            Lightly oil a 9-inch square baking pan. \
            Heat 3 tablespoons olive oil in a large skillet over medium heat. Saute \
            chopped onion, green onions, and garlic in the hot oil until soft and lightly \
            browned, about 5 minutes. Stir in spinach and parsley, and continue to saute until \
            spinach is limp, about 2 minutes. Remove from the heat and set aside to cool.\
            Mix feta cheese, ricotta cheese, and eggs in a medium bowl until well combined. \
            Stir in spinach mixture. \
            Lay one sheet of phyllo dough in the prepared baking pan, and brush lightly with \
            olive oil. Lay another sheet of phyllo dough on top and brush with olive oil. \
            Repeat the process with two more sheets of phyllo dough; the sheets will overlap the pan. \
            Spread spinach and cheese mixture into the pan. Fold any overhanging dough over the filling. \
            Brush with oil. \
            Layer the remaining 4 sheets of phyllo dough, brushing each with oil. \
            Tuck overhanging dough into the pan to seal the filling. \
            Bake in the preheated oven until golden brown, 30 to 40 minutes. \
            Cut into squares and serve while hot.",
            author: "nina",
            visibility: 0,
            rating: 4
        });
        recipe.collection.insertOne({
            name: "Stew",
            difficulty: 4,
            category: "Dinner",
            cuisine: "Serbian",
            img: "stew",
            description: "Place meat in slow cooker. \
            Mix flour, salt, and pepper together in a small bowl. Pour over meat, and stir \
            until meat is coated. \
            Add beef broth, carrots, potatoes, onion, celery, Worcestershire sauce, paprika, \
            garlic, and bay leave; stir to combine. \
            Cover, and cook until beef is tender enough to cut with a spoon, on Low for 8 \
            to 12 hours, or on High for 4 to 6 hours.",
            author: "ogi",
            visibility: 0,
            rating: 4
        });
        recipe.collection.insertOne({
            name: "Strawberry and banana smoothie",
            difficulty: 1,
            category: "Dessert",
            cuisine: "American",
            img: "strawberry_smoothie",
            description: "Place strawberries, banana, almonds, and water into a blender; \
            blend to combine. Add ice cubes and puree until smooth. \
            Add protein powder and mix until evenly incorporated, about 30 seconds.",
            author: "anja",
            visibility: 0,
            rating: 5
        });
        recipe.collection.insertOne({
            name: "Watermelon ice pops",
            difficulty: 2,
            category: "Dessert",
            cuisine: "American",
            img: "watermelon_ice_pops",
            description: "Blend watermelon, water, honey, lemon juice, and sugar together \
            in a blender until smooth. Pour mixture into ice pop molds and freeze until solid, \
            about 2 hours.\
            Run hot water over ice pop molds for a few seconds to unmold.",
            author: "nina",
            visibility: 0,
            rating: 3
        });        
    }
}

module.exports = RecipeController;