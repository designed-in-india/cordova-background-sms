  var exec = require('cordova/exec');
  
  var SmsPlugin = function() {}; 
 
 
  SmsPlugin.prototype.doSomething = function(successCallback,failureCallback) {
    console.log('inside cordova')
    return exec(successCallback, failureCallback, 'SmsPlugin', 'start', []);
  }
  
  var smsplugin = new SmsPlugin();
  module.exports = smsplugin;