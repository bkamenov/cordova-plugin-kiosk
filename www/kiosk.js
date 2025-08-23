var exec = require('cordova/exec');

exports.enable = function (success, error) {
  exec(success, error, 'KioskPlugin', 'enable', []);
};

exports.switchLauncher = function (success, error) {
  exec(success, error, 'KioskPlugin', 'switchLauncher', []);
};
