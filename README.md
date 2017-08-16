# Split
Simple File Splitter based using java

# Usage
` java Split [ -c | -j | -s[size] | -? ] filename [outputpath]`

```
ls -l /location/ | awk '{print $9,$5}'
2141x64.jpg.zip 37094831

java Split -c /location/2141x64.jpg.zip
The checksum for /location/2141x64.jpg.zip is f45179c6.

java Split -s14400 /location/2141x64.jpg.zip /location/output/
ls -l /location/output/ | awk '{print $9,$5}'
#2141x64.jpg.zip.001 14745600
#2141x64.jpg.zip.002 14745600
#2141x64.jpg.zip.003 7603631

java Split -j /location/output/2141x64.jpg.zip /location/output/
ls -l /location/output/ | awk '{print $9,$5}'
#2141x64.jpg.zip 37094831
#2141x64.jpg.zip.001 14745600
#2141x64.jpg.zip.002 14745600
#2141x64.jpg.zip.003 7603631

java Split -c /location/output/2141x64.jpg.zip
The checksum for /location/output/2141x64.jpg.zip is f45179c6.
```
