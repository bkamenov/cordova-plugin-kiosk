var exec = require('cordova/exec');

exports.enableImmersiveMode = function (success, error) {
  exec(success, error, 'KioskPlugin', 'enableImmersiveMode', []);
};

exports.disableImmersiveMode = function (success, error) {
  exec(success, error, 'KioskPlugin', 'disableImmersiveMode', []);
};

exports.isLauncher = function (success, error) {
  exec(success, error, 'KioskPlugin', 'isLauncher', []);
};

exports.switchLauncher = function (success, error) {
  exec(success, error, 'KioskPlugin', 'switchLauncher', []);
};

