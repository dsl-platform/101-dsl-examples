// wonderData (wonderListData) - JS Object representation of a single Wonder (list of Wonders)
// wonderJSON (wonderListJSON) - JSON representation of a single Wonder (list of Wonders)
// wonderHTML (wonderListHTML) - HTML table entry for a single Wonder (list of Wonders)
// *Ref - Reference to an element in the DOM.

// Set of methods for creating, removing and editing HTML wonder entries.
var Templater = function templater() {
  // HTML model for native names list creation.
  this.nativeModel = null;
  // HTML model for Wonder creation.
  this.wonderModel = null;
  // Reference to main table.
  this.tableRef = $('table#mainTable');
}

// ----------------------------------------------------------------------------

// Wonder URIs can contain spaces, which aren't allowed in HTML ID attribute.
// Reversibly converts specified URI into a valid ID.
Templater.prototype.uriToID = function(uri) {
  return uri
    .replace(/\\/g, '\\\\')
    .replace(/-/g, '\\-')
    .replace(/ /g, '-');
}

// Wonder URIs can contain spaces, which aren't allowed in HTML ID attribute.
// Converts back from specified ID to URI.
Templater.prototype.idToURI = function(id) {
  return id
    .replace(/\\\\/g, '\\')
    .replace(/\\-/g, '-')
    .replace(/-/g, ' ');
}

// Converts specified wonder JS Object into its HTML representation.
Templater.prototype.dataToHTML = function(wonderData) {
  var self = this;
  var nativeClones = [];
  wonderData.nativeNames.forEach(function(nativeName) {
    var nativeClone = self.nativeModel.clone();
    nativeClone.text(nativeName);
    nativeClones.push(nativeClone);
  });
  var wonderHTML = this.wonderModel.clone();
  wonderHTML.attr('id', this.uriToID(wonderData.URI));
  if (wonderData.isAncient) {
    wonderHTML.find('.td-ancient input').attr('checked', 'checked');
  }
  wonderHTML.find('.td-english').html(wonderData.englishName);
  wonderHTML.find('.td-native').html(nativeClones);
  wonderHTML.find('.td-image > img').attr('src', wonderData.imageURL);
  wonderHTML.find('.td-image > img').attr('alt', wonderData.englishName);
  return wonderHTML;
}

// Converts specified HTML representation of a Wonder into a JS Object.
Templater.prototype.htmlToData = function(wonderHTML) {
  var id = $(wonderHTML).attr('id')
  var uri = this.idToURI(id);
  var nativeNames = [];
  wonderHTML.find('.td-native > span').each(function() {
    nativeNames.push($(this).text());
  });
  var wonderData = {
    URI:         uri,
    isAncient:   wonderHTML.find('.td-ancient > div.bootstrap-switch').hasClass('bootstrap-switch-on'),
    englishName: wonderHTML.find('.td-english').text(),
    nativeNames: nativeNames,
    imageURL:    wonderHTML.find('.td-image > img').attr('src')
  }
  return wonderData;
}

// Initializes HTML templates for wonder creation.
Templater.prototype.init = function() {
  var nativeRef = this.tableRef.find('tbody > tr > .td-native');
  this.nativeModel = nativeRef.find('span').clone();
  nativeRef.empty();
  
  var tbodyRef = this.tableRef.find('tbody');
  this.wonderModel = tbodyRef.find('tr').clone();
  tbodyRef.empty();
}

// Returns a reference to all main table entries.
Templater.prototype.refAll = function() {
  return this.tableRef.find('tbody > tr');
}

// Returns a reference to main table reference specified by id.
Templater.prototype.refByID = function(id) {
  return this.tableRef.find('tbody > tr#' + id);
}

// ----------------------------------------------------------------------------


// Disables all Wonder HTML entries.
Templater.prototype.disableAll = function() {
  this.refAll().children().addClass('disabled');
}

// Disables specified Wonder HTML entry.
Templater.prototype.disableWonder = function(id) {
  this.refByID(id).children().addClass('disabled');
}

// Enables specified Wonder HTML entry.
Templater.prototype.enableAll = function() {
  this.refAll().children().removeClass('disabled');
}

// Enables specified Wonder HTML entry.
Templater.prototype.enableWonder = function(id) {
  this.refByID(id).removeClass('disabled');
}

// Inserts a list of Wonder JS Objects into the page.
Templater.prototype.insertWonderList = function(wonderDataList) {
  var self = this;
  wonderDataList.forEach(function(wonderData) {
    self.insertWonder(wonderData)
  });
}

// Inserts a Wonder JS Object into the page.
Templater.prototype.insertWonder = function(wonderData) {
  var wonderHTML = this.dataToHTML(wonderData);
  this.tableRef.find('tbody').append(wonderHTML);
}

// Replaces a Wonder entry specified by ID with a new Wonder JS Object.
Templater.prototype.replaceWonder = function(id, wonderData) {
  var oldWonderHTML = this.refByID(id);
  var newWonderHTML = this.dataToHTML(wonderData);
  $(oldWonderHTML).html(newWonderHTML.html());
}

// Removes a Wonder entry specified by ID.
Templater.prototype.deleteWonder = function(id) {
  this.refByID(id).remove();
}

// Removes all Wonder entries.
Templater.prototype.deleteAll = function() {
  this.refAll().remove();
}
