# java-LAN-distributed-backup-service

Distributed backup service for a local area network (LAN). Uses the unused disk space of the computers in a LAN for backing up files in other computers in the same LAN. The service is provided by servers, one per computer, in an environment that is assumed cooperative. Each server retains control over its own disks and, if needed, may reclaim the space it made available for backing up other computers' files.
This project was developed in the scope of the course SDIS (Distributed Systems).

It makes use of an external library. To access it you have to do the following (in Eclipse):

A1) Right-click the project and go to `Build Path` -> `Add External Archives`

A2) Select the file `commons-codec-1.10.jar` (should be in the `bin` folder)


If the file isn't there:

B1) Go to `http://commons.apache.org/proper/commons-codec/download_codec.cgi`

B2) Download either of the binaries available

B3) Place it somewhere safe and copy the directory

B4) Do the steps A1 and A2 (with the directory you just copied)