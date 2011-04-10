# HDFS(Hadoop) File Block Finder #

This is a samll utility I wrote to find the locataion of a files blockes in hdfs.  
It does this by quering the namenode for the datanodes that have the various blocks of the file. There are two ways to use it

* Display the location of all blocks for a file and where they are
* Display the location of a single selected block

## Usage ##
-h - The url for the namenode
-u user name and group to use
-f the file to get the block locations for
-b (Optional) the block to list the location for

eg.
java -cp 'dist/*'  hadoop.utils.BlockFinder -h hdfs://localhost:9000 -u superuser,supergroup -f /tmp/temp-1416832744/tmp154868920/part-r-00000 -b 67108864

## Displaying all blocks for a file ##

If you don't include a block you will get all the blocks for a file 
eg.
java -cp 'dist/*'  hadoop.utils.BlockFinder -h hdfs://localhost:9000 -u superuser,supergroup -f /tmp/temp-1416832744/tmp154868920/part-r-00000

will yield

> Trying to connect to hdfs://localhost:9000 as superuser,supergroup
> File: /tmp/temp-1416832744/tmp154868920/part-r-00000
> Replication : 1
> Requested block :
> Start,End,DataNode
> 67108864,24546580,localhost.corp.ad.local


## Displaying a single block from a file ##

## Getting it working ##

## Contribution ##

The source code is available under the Apache 2.0 license. We are actively looking for contributors so if you have ideas, 
code, bug reports, or fixes you would like to contribute please do so.