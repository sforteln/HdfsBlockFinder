package hadoop.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileBlockLocations {
    final static String NEWLINE="\n";
    
    private String fsDefaultName="hdfs://localhost:9000" ;
    private String userAndGroup="superuser,supergroup";
    private String fileName;
    private Long blockOffset;
    private PrintWriter writer;
    private FileStatus status;
    private BlockLocation[] bLocations;
    


    
    public FileBlockLocations(String fsDefaultNameIn, String userAndGroupIn,
            String fileNameIn, Long blockOffsetIn, PrintWriter writerIn) {
        super();
        if(fsDefaultNameIn!=null){//keep default if null
            this.fsDefaultName = fsDefaultNameIn;
        }
        if(userAndGroupIn!=null){//keep the deafault
            this.userAndGroup = userAndGroupIn;
        }
        if(fileNameIn==null){
            throw new IllegalArgumentException("A filename must be supplied");
        }
        this.fileName = fileNameIn;
        this.blockOffset = blockOffsetIn;
        if(writerIn==null){
            throw new IllegalArgumentException("A print writer must be supplied");
        }
        this.writer = writerIn;
        getBlockLocationsFromHdfs();
    }



    public void writeResult(){
        StringBuilder sb = new StringBuilder();
        sb.append("File: ").append(fileName).append(NEWLINE);
        sb.append("Replication : ").append(status.getReplication()).append(NEWLINE);
        if(blockOffset!=null){
            boolean foundBlock=false;
            for(BlockLocation aLocation : bLocations){
                if(aLocation.getOffset()==blockOffset){
                    foundBlock=true;
                    sb.append("Requested block : \n").append("Start,End,DataNode\n").append(aLocation).append(NEWLINE);
                }
            }
            if(!foundBlock){
                sb.append("Unable to find a record for the requested block ").append(blockOffset);
            }
        }else{
            //print out all block locations
            sb.append("Block Locations: \n").append("Start,End,DataNode\n");
            for(BlockLocation aLocation : bLocations){
                sb.append(aLocation).append(NEWLINE);
            }
        }
        
        writer.print(sb.toString());
        writer.flush();
    }
    
    private void getBlockLocationsFromHdfs(){
        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", userAndGroup);
        URI rootUri = URI.create(fsDefaultName);
        // make connection to hdfs
        try {
            writer.println("Trying to connect to "+ fsDefaultName + " as "+ userAndGroup);
            FileSystem fs = FileSystem.get(rootUri, conf);
            Path file = new Path(fileName);
            FileStatus fStatus = fs.getFileStatus(file);
            this.status=fStatus;
            this.bLocations= fs.getFileBlockLocations(status, 0, status.getLen());
        } catch (IOException e) {
            writer.println("Error getting block location data from namenode");
            e.printStackTrace();
        }
        writer.flush();
    }
    
    public FileBlockLocations(String fsDefaultName, String userAndGroup,
            String fileName, Long blockOffset) {
        super();
        this.fsDefaultName = fsDefaultName;
        this.userAndGroup = userAndGroup;
        this.fileName = fileName;
        this.blockOffset = blockOffset;
    }

}
