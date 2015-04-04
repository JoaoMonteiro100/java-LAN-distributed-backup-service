# java-LAN-distributed-backup-service

Distributed backup service for a local area network (LAN). Uses the unused disk space of the computers in a LAN for backing up files in other computers in the same LAN. The service is provided by servers, one per computer, in an environment that is assumed cooperative. Each server retains control over its own disks and, if needed, may reclaim the space it made available for backing up other computers' files.
This project was developed in the scope of the course SDIS (Distributed Systems).