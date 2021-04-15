Implementation of IO using Panama Native

# Status / TODO / Notes
There are differences between Linux & BSD Posix implementations,
mainly in constants (defines) used for socket addresses (i.e. AF_INET6)

If there are bigger differences in ohter C structs - rather no.

Constants should be generated in per implementation basis (two subpackages Lnx & BSD)

Rest binding can stay same.

Binding right now are kept in generated folder - this is not target behaviour

Win API is somehow based on Posix too, but uses Handles.