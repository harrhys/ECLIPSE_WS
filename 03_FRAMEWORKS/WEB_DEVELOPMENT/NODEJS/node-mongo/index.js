const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const dbhandler = require('./dbhandler');
const dbhandlerpromise = require('./dbhandlerpromise');

const url = 'mongodb://127.0.0.1:27017/';
const dbname = 'conFusion';

// Callback Hell 
MongoClient.connect(url, (err, client) => {

    assert.equal(err,null);

    console.log('Connected correctly to server');

    const db = client.db(dbname);

    var dish = { name: "Vadonut", description: "Initial Description"};
   
    dbhandler.insertDocument(db, dish , "dishes", (result) => { 

        console.log("Inserted Document:\n", result.ops);

        dbhandler.findDocuments(db, "dishes", (docs) => {

            console.log("Found Documents:\n", docs);

            var insertedDish = { name: "Vadonut" };

            var updatedDish =  { description: "Updated Description" };

            dbhandler.updateAllDocument(db, insertedDish , updatedDish , "dishes", (result) => {
                    
                console.log("Updated Document(s):\n", result.result);

                dbhandler.findDocuments(db, "dishes", (docs) => {

                    console.log("Found Updated Documents:\n", docs);

                    db.dropCollection("dishes", (result) => {

                        console.log("Dropped Collection: ", result);

                        client.close();
                    }); 
                });
            });
        });
    });
}); 

// Promises
MongoClient.connect(url).then((client) => {

    console.log('Connected correctly to server');
    const db = client.db(dbname);
    var dish = { name: "Vadonut", description: "Initial Promise Description"};
    dbhandlerpromise.insertDocument(db, dish , "dishes")
    .then((result) => { 

            console.log("Inserted Document:\n", result.ops);
            return dbhandlerpromise.findDocuments(db, "dishes");
        }
    )
    .then((docs) => {

            console.log("Found Documents:\n", docs);
            var insertedDish = { name: "Vadonut" };
            var updatedDish =  { description: "Updated Promise Description" };
            return dbhandlerpromise.updateAllDocument(db, insertedDish , updatedDish , "dishes");
        }
    )
    .then( (result) => {
                    
            console.log("Updated Document(s):\n", result.result);
            return dbhandlerpromise.findDocuments(db, "dishes");
        }
    )
    .then( (docs) => {

            console.log("Found Updated Documents:\n", docs);
            return db.dropCollection("dishes");
        }   
    )
    .then( (result) => {

            console.log("Dropped Collection: ", result);
            client.close();
        }
    )
    .catch((err) => {console.log(err)});
})
.catch((err) => {console.log(err)});