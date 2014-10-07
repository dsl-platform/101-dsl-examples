module WorldWonders
{
  aggregate Wonder(englishName) {
    String        englishName;
    List<String>  nativeNames;
    Boolean       isAncient;
  }
}
