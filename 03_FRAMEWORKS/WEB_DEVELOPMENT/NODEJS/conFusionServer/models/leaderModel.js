const mongoose = require('mongoose');
const Schema = mongoose.Schema;
require('mongoose-currency').loadType(mongoose);
const Currency = mongoose.Types.Currency;

const leaderSchema = new Schema(
    {
        name: {
            type: String,
            required: true,
            unique: true
        },
        description: {
            type: String,
            required: true
        },
        image: {
            type: String,
            required: true
        },
        designation: {
            type: String,
            required: true
        },
        featured: {
            type: Boolean,
            default:false      
        }
    },
    {
        timestamps: true
    }
);

var Leader = mongoose.model('Leader', dishSchema);

module.exports = Leader;