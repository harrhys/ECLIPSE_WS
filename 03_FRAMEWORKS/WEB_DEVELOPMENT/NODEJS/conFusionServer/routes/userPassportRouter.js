var express = require('express');
const bodyParser = require('body-parser');
const cors = require('./cors');
var passport = require('passport');
var User = require('../models/userPassportModel');
var authenticate = require('../authenticate');

var router = express.Router();
router.use(bodyParser.json());

router.route('/signup')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .post(cors.cors, (req, res, next) => {
        var usr =   new User({ 
                        username: req.body.username, 
                        firstname:req.body.firstname,
                        lastname:req.body.lastname,    
                        email:req.body.email
                    });
        var registerCallback =  (err, user) => {    
            if(err) {
                res.statusCode = 500;
                res.setHeader('Content-Type', 'application/json');
                res.json({err: err});
            }
            else {
                passport.authenticate('local')(req, res, () => {
                    res.statusCode = 200;
                    res.setHeader('Content-Type', 'application/json');
                    res.json({status: 'success', msg: 'Registration Successful!', user:user});
                });
            }
        };
        User.register(usr, req.body.password, registerCallback );
    });

router.route('/login')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .post(cors.cors, passport.authenticate('local'), (req, res) => {
        var token = authenticate.getToken({_id: req.user._id});
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json({status: 'success', msg: 'You are successfully logged in!', token:token});
    });

router.route('/logout')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .post(cors.cors, (req, res) => {
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

router.route('/:username')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .put(cors.cors, function(req, res, next) {
        User.findOne({username: req.params.username})
        .then(
            (usr) => {
                if (usr != null) {
                    if (req.body.password) {
                        usr.password = req.body.password;
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
                    usr.save()
                    .then((usr) => {
                        res.statusCode = 200;
                        res.setHeader('Content-Type', 'application/json');
                        res.json(usr);                
                        }, 
                        (err) => next(err)
                    );
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
    });

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

router.route('/:username')
    .options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })
    .put(cors.cors, function(req, res, next) {
        User.findOne({username: req.params.username})
        .then(
            (usr) => {
                if (usr != null) {
                    if (req.body.password) {
                        usr.password = req.body.password;
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
                    usr.save()
                    .then((usr) => {
                        res.statusCode = 200;
                        res.setHeader('Content-Type', 'application/json');
                        res.json(usr);                
                        }, 
                        (err) => next(err)
                    );
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
    });

module.exports = router;
