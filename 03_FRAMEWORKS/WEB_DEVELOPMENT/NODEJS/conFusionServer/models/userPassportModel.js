var mongoose = require('mongoose');
var passportLocalMongoose = require('passport-local-mongoose');
var Schema = mongoose.Schema;

var User = new Schema(
    {
        firstname:  {
            type: String,
           
        },
        lastname:  {
            type: String,
            
        },
        email:  {
            type: String,
            
        },
        admin:   {
            type: Boolean,
            default: false
        }
    }, 
    {
        timestamps: true
    }
);

User.plugin(passportLocalMongoose);

module.exports = mongoose.model('PassportUser', User);