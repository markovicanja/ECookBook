import { Recipe } from "../model/recipe.model";

export var allRecipes: Recipe[] = [
    {
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
        rating: 4, 
        comments: [
            {
                author: "nina",
                date: "25/11/2022",
                time: "15:04",
                body: "My go to chicken salad recipe..."
            }
        ]
    },
    {
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
        rating: 4, 
        comments: []
    },
    {
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
        rating: 5, 
        comments: []
    },
    {
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
        rating: 5, 
        comments: []
    },
    {
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
        rating: 5, 
        comments: []
    },
    {
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
        rating: 5, 
        comments: []
    },
    {
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
        rating: 3, 
        comments: []
    },
    {
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
        rating: 3, 
        comments: []
    },
    {
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
        rating: 4, 
        comments: []
    },
    {
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
        rating: 4, 
        comments: []
    },
    {
        name: "Strawberry and banana smoothie",
        difficulty: 1,
        category: "Dessert",
        cuisine: "American",
        img: "strawberry_smoothie",
        description: "Place strawberries, banana, almonds, and water into a blender; \
        blend to combine. Add ice cubes and puree until smooth. \
        Add protein powder and mix until evenly incorporated, about 30 seconds.",
        author: "anja",
        rating: 5, 
        comments: []
    },
    {
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
        rating: 3, 
        comments: []
    },
]