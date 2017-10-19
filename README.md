- Able to write objects (Maps, Collections, Arrays, SimpleValue) into strings and Streams.
- Annotation support for:
  o Changes to the property name
  o Ignoring a property when serializing
- Support for transient fields (transient int x;):
  o By default, Ignore them
  o If the JsonProperty annotation is attached to it, serialize it.
- Support both fields
  o By default all public fields
  o Private fields marked with the JsonProperty annotation
  o In the case of name conflicts, priority is given to the entity on which there is an annotation
- Ability of outputting JSON both in a compact form (without extra spaces, without entrants), and in a readable form (with indentations, line breaks, spaces around ":")
- Write null values.
