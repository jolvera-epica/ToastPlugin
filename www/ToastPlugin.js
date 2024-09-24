/*var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'ToastPlugin', 'coolMethod', [arg0]);
};*/


var cordova = require('cordova');
var exec = require('cordova/exec');

var ToastPlugin = {
    show: function(message, success, error) {
        exec(success, error, 'ToastPlugin', 'show', [message]);
    }
};

module.exports = ToastPlugin;