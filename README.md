# HDFS(Hadoop) File Block Finder #

This is a small utility I wrote to find the locataion of a files blocks in hdfs.  
It does this by quering the namenode for the datanodes that have the various blocks of the file. There are two ways to use it

* Display the location of all blocks for a file and where they are
* Display the location of a single selected block

## Usage ##
    -h The url for the namenode
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
    Trying to connect to hdfs://localhost:9000 as superuser,supergroup
    File: /tmp/temp-1416832744/tmp154868920/part-r-00000
    Replication : 1
	Block Locations:
    Start,End,DataNode
    0,67108864,localhost
    67108864,24546580,localhost

## Displaying a single block from a file ##

To only see the location(DataNode) for a single block include the block's position
eg.
    java -cp 'dist/*'  hadoop.utils.BlockFinder -h hdfs://localhost:9000 -u superuser,supergroup -f /tmp/temp-1416832744/tmp154868920/part-r-00000 -b 67108864

will yield
    Trying to connect to hdfs://localhost:9000 as pdadmin,supergroup
    File: /tmp/temp-1416832744/tmp154868920/part-r-00000
    Replication : 1
    Requested block :
    Start,End,DataNode
    67108864,24546580,localhost

## Getting it working ##
To get it working you can build the project using the maven pom or just download the dependencies, into dist/, using

    mkdir dist
    cd dist
    wget http://mirrors.ibiblio.org/pub/mirrors/maven/commons-cli/jars/commons-cli-1.1.jar
    wget http://mirrors.ibiblio.org/pub/mirrors/maven2/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar
    wget http://apache.mirrors.hoobly.com//hadoop/core/hadoop-0.20.2/hadoop-0.20.2.tar.gz
    tar -zxf hadoop-0.20.2.tar.gz hadoop-0.20.2/hadoop-0.20.2-core.jar
	mv hadoop-0.20.2/hadoop-0.20.2-core.jar .
	wget --no-check-certificate https://github.com/downloads/sforteln/HdfsBlockFinder/blockFinder-1.0.jar
    cd ../
	java -cp 'dist/*'  hadoop.utils.BlockFinder -h hdfs://localhost:9000 -u superuser,supergroup -f /tmp/temp-1416832744/tmp154868920/part-r-00000 -b 67108864
	
Then all the examples above should work.
	
