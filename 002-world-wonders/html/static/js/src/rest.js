// req*  - Fields related to the request
// res*  - Fields related to the response
// *Data - Data in JS Object representation
// *JSON - Data in JSON representation

// Set of methods for communication with a REST service.
var RestClient = function restClient(host, port) {
  this.url = 'http://' + host + ':' + port + '/';
}

// Sends a GET request.
RestClient.prototype.get = function(path, callback) {
  this.request(path, 'GET', null, callback);
}

// Sends a PUT request.
RestClient.prototype.put = function(path, reqData, callback) {
  this.request(path, 'PUT', reqData, callback);
}

// Sends a DELETE request.
RestClient.prototype.delete = function(path, callback) {
  this.request(path, 'DELETE', null, callback);
}

// Sends a PUT request.
RestClient.prototype.post = function(path, reqData, callback) {
  this.request(path, 'POST', reqData, callback);
}

// Sends a specified request.
RestClient.prototype.request = function(path, method, reqData, callback) {
  var url = this.url + path;
  var reqJSON = null;
  if (reqData !== null) {
    reqJSON = JSON.stringify(reqData);
  }
  $.ajax({
    method: method,
    url: url,
    data: reqJSON,
    success: function(resJSON) {
      var resData = $.parseJSON(resJSON);
      callback(resData)
    }
  });
}
