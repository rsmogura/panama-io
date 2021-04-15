JEXTRACT=/mnt/home/home/radek/Dev/panama-foreign/build/linux-x86_64-server-release/images/jdk/bin/jextract

DST=src/main/java
mkdir -p $DST

$JEXTRACT -d $DST -t eu.smogura.panama.io.posix.internal \
  --source \
  --include-function open --include-function close \
  --include-function connect --include-function socket \
  --include-function read --include-function write \
  --include-struct sockaddr --include-struct sockaddr_storage \
  --include-struct sockaddr_in --include-struct in_addr \
  --include-struct sockaddr_in6 --include-struct in6_addr \
  --include-macro AF_INET --include-macro AF_INET6 \
  --include-macro SOCK_STREAM \
  --include-var errno \
  $(dirname $0)/posix_io_lnx.h