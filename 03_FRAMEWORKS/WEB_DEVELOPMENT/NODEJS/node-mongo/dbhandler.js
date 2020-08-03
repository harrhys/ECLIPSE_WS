const assert = require('assert');

exports.insertDocument = (db, document, collection, callback) => {

    const coll = db.collection(collection);

    const insertCallback  = (err, result) => {

        assert.equal(err, null);
        console.log("\nInserted \'" + result.result.n +
            "\' documents into the collection: " + collection+'\n');
        callback(result);
    };

    coll.insertOne(document, insertCallback );
};

exports.findDocuments = (db, collection, callback) => {

    const coll = db.collection(collection);

    coll.find({}).toArray((err, docs) => {
        
        assert.equal(err, null);
        callback(docs);        
    });
};

exports.updateOneDocument = (db, document, update, collection, callback) => {

    const coll = db.collection(collection);

    const updateCallback = (err, result) => {

        assert.equal(err, null);
        console.log("Updated the document with ", update);
        callback(result);        
    };

    coll.updateMany(document, { $set: update }, null, updateCallback);
};

exports.updateAllDocument = (db, document, update, collection, callback) => {

    const coll = db.collection(collection);

    const updateCallback = (err, result) => {

        assert.equal(err, null);
        console.log("Updated the document with ", update);
        callback(result);        
    };

    coll.updateMany(document, { $set: update }, null, updateCallback);
};

exports.deleteDocument = (db, document, collection, callback) => {

    const coll = db.collection(collection);

    const deleteCallback = (err, result) => {

        assert.equal(err, null);
        console.log("Removed the document ", document);
        callback(result);        
    };

    coll.deleteOne(document, deleteCallback );
};


