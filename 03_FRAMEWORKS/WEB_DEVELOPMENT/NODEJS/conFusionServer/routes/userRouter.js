var express = require('express');
const bodyParser = require('body-parser');
const cors = require('./cors');
var User = require('../models/userModel');
const { post } = require('./dishRouter');

var router = express.Router();
router.use(bodyParser.json());
// Get and Delete All users
router.route('/')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .get(cors.cors, function(req, res, next) {
        User.find({})
        .then(
            (users) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(users);
            }, 
            (err) => next(err)
        )
        .catch((err) => next(err));
    })
    .delete(cors.cors, function(req, res, next) {
        User.remove({})
        .then(
            (resp) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(resp);
            }, 
            (err) => next(err)
        )
        .catch((err) => next(err));
    });
//User Update
router.route('/profile')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .put(cors.cors, function(req, res, next) {
        User.findOne({username: req.body.username})
        .then(
            (usr) => {
                if (usr != null) {
                    if(usr.password==req.body.password)
                    {
                        if (req.body.newpassword) {
                            usr.password = req.body.newpassword;
                        }
                        if (req.body.firstname) {
                            usr.firstname = req.body.firstname;
                        }
                        if (req.body.lastname) {
                            usr.lastname = req.body.lastname;
                        }
                        if (req.body.email) {
                            usr.email = req.body.email;              
                        }
                        if (req.body.admin!=null) {
                            usr.admin = req.body.admin;              
                        }
                        usr.save()
                        .then((usr) => {
                            res.statusCode = 200;
                            res.setHeader('Content-Type', 'application/json');
                            res.json(usr);                
                            }, 
                            (err) => next(err)
                        );
                    }
                    else{
                        err = new Error('Your password is incorrect!');
                        err.status = 403;
                        return next(err);
                    }
                }
                else {
                    err = new Error('User with username: ' + req.body.username + ' not found');
                    err.status = 404;
                    return next(err);
                }
            }, 
            (err) => next(err)
        )
        .catch((err) => next(err));
    })
    .delete(cors.cors, function(req, res, next) {

        var authHeader = req.headers.authorization;
        if (!authHeader) {
            var err = new Error('You are not authenticated!');
            res.setHeader('WWW-Authenticate', 'Basic');
            err.status = 401;
            return next(err);
        }

        var auth = new Buffer.from(authHeader.split(' ')[1], 'base64').toString().split(':');
        var username = auth[0];
        var password = auth[1];

        User.findOne({username: username})
        .then(
            (admin) => {
                if (admin != null) {
                    if(admin.password==password)
                    {
                        User.findOne({username: req.body.username})
                        .then((usr) =>{
                            if(usr!=null)
                            {
                                User.remove(usr)
                                .then((response) => {
                                    res.statusCode = 200;
                                    res.setHeader('Content-Type', 'application/json');
                                    res.json(response);                
                                    }, 
                                    (err) => next(err)
                                );
                            }
                            else{
                                err = new Error('No user found to delete with username:'+req.body.username);
                                err.status = 403;
                                return next(err);
                            }
                        })
                    }
                    else{
                        err = new Error('Your password is incorrect!');
                        err.status = 403;
                        return next(err);
                    }
                }
                else {
                    err = new Error('User with username: ' + req.param.username + ' not found');
                    err.status = 404;
                    return next(err);
                }
            }, 
            (err) => next(err)
        )
        .catch((err) => next(err));
    });
//User Registration
router.route('/signup')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .post(cors.cors, (req, res, next) => {
        User.findOne({username: req.body.username})
        .then(
            (user) => {
                if(user != null) {
                    var err = new Error('User ' + req.body.username + ' already exists!');
                    err.status = 403;
                    next(err);
                }
                else {
                    return User.create({
                    username: req.body.username,
                    password: req.body.password,
                    firstname: req.body.firstname,
                    lastname: req.body.lastname,
                    email: req.body.email
                    });
                }
            }
        )
        .then(
            (user) => {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(user);
            }, 
            (err) => next(err)
        )
        .catch((err) => next(err)
        );
    });
//User Login
router.route('/login')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .post(cors.cors, (req, res, next) => {
        if(!req.session.user) {
            var authHeader = req.headers.authorization;
            if (!authHeader) {
                var err = new Error('You are not authenticated!');
                res.setHeader('WWW-Authenticate', 'Basic');
                err.status = 401;
                return next(err);
            }

            var auth = new Buffer.from(authHeader.split(' ')[1], 'base64').toString().split(':');
            var username = auth[0];
            var password = auth[1];

            User.findOne({username: username})
            .then((user) => {
                if (user === null) {
                    var err = new Error('User ' + username + ' does not exist!');
                    err.status = 403;
                    return next(err);
                }
                else if (user.password !== password) {
                    var err = new Error('Your password is incorrect!');
                    err.status = 403;
                    return next(err);
                }
                else if (user.username === username && user.password === password) {
                    req.session.user = 'authenticated';
                    res.statusCode = 200;
                    res.setHeader('Content-Type', 'text/plain');
                    res.end('You are authenticated!')
                }
            })
            .catch((err) => next(err));
        }
        else {
            res.statusCode = 200;
            res.setHeader('Content-Type', 'text/plain');
            res.end('You are already authenticated!');
        }
    });

router.route('/logout')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .get(cors.cors, (req, res) => {
        if (req.session) {
            req.session.destroy();
            res.clearCookie('session-id');
            res.redirect('/');
        }
        else {
            var err = new Error('You are not logged in!');
            err.status = 403;
            next(err);
        }
    });

module.exports = router;
