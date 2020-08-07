const express = require('express');
const cors = require('cors');
const app = express();

const whitelist = ['http://127.0.0.1:7000', 'http://127.0.0.1:19006', 'http://192.168.43.85:7000'];
var corsOptionsDelegate = (req, callback) => {
    var corsOptions;
    console.log('Origin--'+req.header('Origin'));
    if(whitelist.indexOf(req.header('Origin')) !== -1) {
        corsOptions = { origin: true };
    }
    else {
        corsOptions = { origin: false };
    }
    corsOptions = { origin: true };
    callback(null, corsOptions);
};

exports.cors = cors();
exports.corsWithOptions = cors(corsOptionsDelegate);