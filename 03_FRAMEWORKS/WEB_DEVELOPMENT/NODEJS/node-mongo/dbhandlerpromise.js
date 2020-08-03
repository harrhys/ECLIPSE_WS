const assert = require('assert');

exports.insertDocument = (db, document, collection) => {

    const coll = db.collection(collection);
    return coll.insertOne(document);
};

exports.findDocuments = (db, collection) => {

    const coll = db.collection(collection);
    return coll.find({}).toArray();
};

exports.updateOneDocument = (db, document, update, collection) => {

    const coll = db.collection(collection);
    return coll.updateMany(document, { $set: update }, null);
};

exports.updateAllDocument = (db, document, update, collection) => {

    const coll = db.collection(collection);
    return coll.updateMany(document, { $set: update }, null);
};

exports.deleteDocument = (db, document, collection) => {

    const coll = db.collection(collection);
    return coll.deleteOne(document);
};


