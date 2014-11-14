module WorldWonders
{
  aggregate Wonder(englishName) {
    Boolean       isAncient;
    String        englishName;
    List<String>  nativeNames;
    String        imageURL;
  }
}
