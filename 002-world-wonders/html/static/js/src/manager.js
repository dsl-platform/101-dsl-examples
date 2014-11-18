// wonderData (wonderListData) - JS Object representation of a single Wonder (list of Wonders)
// wonderJSON (wonderListJSON) - JSON representation of a single Wonder (list of Wonders)
// wonderHTML (wonderListHTML) - HTML table entry for a single Wonder (list of Wonders)

var Manager = function manager(rest, templater) {
  this.rest = rest;
  this.templater = templater;
}

Manager.prototype.init = function() {
  this.templater.init();
}

Manager.prototype.findAll = function() {
  var self = this;
  this.templater.deleteAll();
  this.showSpinner();
  this.rest.get('wonders', function(wonderDataList) {
    self.hideSpinner();
    self.templater.insertWonderList(wonderDataList)
  });
}

Manager.prototype.resetAll = function() {
  var self = this;
  this.templater.deleteAll();
  this.showSpinner();
  this.rest.get('reset', function(wonderDataList) {
    self.hideSpinner();
    self.templater.insertWonderList(wonderDataList);
  });
}

Manager.prototype.deleteWonder = function(id) {
  var self = this;
  this.templater.disableWonder(id);
  var uri = this.templater.idToURI(id);
  this.rest.delete('wonders/' + uri, function(wonderData) {
    self.templater.deleteWonder(id);
  })
}

Manager.prototype.updateWonder = function(id, wonderData) {
  var self = this;
  this.templater.disableWonder(id);
  this.rest.put('wonders/' + wonderData.URI, wonderData, function(wonderData) {
    self.templater.replaceWonder(id, wonderData);
    this.templater.enableWonder(id);
  });
}

Manager.prototype.insertNew = function() {
  return this.rest.get('randomimg', function(imageData) {
    var wonderData = new Object();
    wonderData.URI = imageData.title;
    wonderData.isAncient = false;
    wonderData.englishName = imageData.title;
    wonderData.nativeNames = [];
    wonderData.imageURL = imageData.url;
    this.rest.post('wonders', wonderData, function(insertedWonderData) {
      templater.insertWonder(insertedWonderData);
    });
  });
}

Manager.prototype.showSpinner = function() {
  $('#div-spinner').removeClass('hidden');
}

Manager.prototype.hideSpinner = function() {
  $('#div-spinner').addClass('hidden');
}