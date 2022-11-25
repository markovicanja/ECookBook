export class Recipe {
    name: string;
    difficulty: number;
    category: string;
    cuisine: string;
    img: string;
    description: string;
    author: string;
    rating: number;
    comments: [{
        author: string;
        date: string;
        time: string;
        body: string;
    }] | [];
}