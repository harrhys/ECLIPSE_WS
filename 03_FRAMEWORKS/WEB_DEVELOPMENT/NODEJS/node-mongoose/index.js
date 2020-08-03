const mongoose = require('mongoose');

const Dishes = require('./models/dishes');

const url = 'mongodb://127.0.0.1:27017/conFusion';
const connection = mongoose.connect(url);

// Usual way to create a Dish
/* connection.then((db) => {

    console.log('Connected correctly to server');

    var newDish = Dishes({
        name: 'Uthappizza',
        description: 'test'
    });

    newDish.save()
        .then((dish) => {

            console.log(dish);
            return Dishes.find({});
        })
        .then((dishes) => {

            console.log(dishes);
            return Dishes.remove({});
        })
        .then(() => {

            return mongoose.connection.close();
        })
        .catch((err) => {
            console.log(err);
        });
}); */

// Simplified way using promises
connection.then((db) => {

    console.log('Connected correctly to server');

    Dishes.create({
        name: 'Uthappizza',
        description: 'test'
    })
    .then((dish) => {

        return Dishes.findByIdAndUpdate(
            dish._id, 
            {$set: { description: 'Updated test'}},
            { new: true }
        )
        .exec();
    })
    .then((dish) => {

        dish.comments.push({
            rating: 5,
            comment: 'I\'m getting a sinking feeling!',
            author: 'Leonardo di Carpaccio'
        });
        return dish.save();
    })
    .then((dish) => {

        console.log(dish);
        return Dishes.remove({});
    })
    .then(() => {

        return mongoose.connection.close();
    })
    .catch((err) => {
        console.log(err);
    });
});