* Able to write objects (Maps, Collections, Arrays, SimpleValue) into strings and Streams.
* Annotation support for:
  - Changes to the property name
  - Ignoring a property when serializing
* Support for transient fields (transient int x;):
  - By default, Ignore them
  - If the JsonProperty annotation is attached to it, serialize it.
* Support both fields
  - By default all public fields
  - Private fields marked with the JsonProperty annotation
  - In the case of name conflicts, priority is given to the entity on which there is an annotation
* Ability of outputting JSON both in a compact form (without extra spaces, without entrants), and in a readable form (with indentations, line breaks, spaces around ":")
* Write null values.
