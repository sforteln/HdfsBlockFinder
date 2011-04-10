package hadoop.utils;


import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class BlockFinder {
    private final static String name = "Hadoop File Block Finder";
    
    public static void main(String [ ] args){
        PrintWriter writer=null;
        try{
            writer = new PrintWriter(System.out);
            FileBlockLocations requestData = parseCommandLine(args,writer);
            requestData.writeResult();
        }catch (NumberFormatException e) {
            System.out.println("Unable to parse the supplied block offset into a Long");
            printUsage(writer);
            System.exit(0);
        }catch(IllegalArgumentException e){
            System.out.println("Unable to parse the supplied block offset into a Long");
            printUsage(writer);
            System.exit(0);
        }catch (ParseException e) {
            System.out.println("There was an exception processing the supplied options");
            printUsage(writer);
            e.printStackTrace();
            System.exit(0);
        }finally{
            if(writer != null){
                writer.close();
            }          
        }
               
    }

    
    private static Options createCommandLineOptions(){
        Options options = new Options();
        Option host   = OptionBuilder.withArgName( "fs.default.name" )
                            .hasArg()
                            .withDescription(  "fs.default.name of hadoop namenode i.e. hdfs://localhost:9000" )
                            .create( "h" );
        options.addOption(host);
        Option uag   = OptionBuilder.withArgName( "userAndGroup" )
                            .hasArg()
                            .withDescription(  "User and group to connect as" )
                            .create( "u" );
        options.addOption(uag);
        Option filename   = OptionBuilder.withArgName( "filename" )
                            .hasArg()
                            .withDescription(  "The file to show block locations for" )
                            .create( "f" );
        options.addOption(filename);
        Option block   = OptionBuilder.withArgName( "blockOffset" )
                            .hasArg()
                            .withDescription(  "Offset of specific block to show locations for, must be a valid long" )
                            .create( "b" );
        options.addOption(block);
        return options;
    }
    
    private static void printUsage(PrintWriter writer){
        final HelpFormatter usageFormatter = new HelpFormatter();  
        usageFormatter.printUsage(writer, 80, name, createCommandLineOptions());  
    }
    
    private static FileBlockLocations parseCommandLine(String [ ] args,PrintWriter writer) throws ParseException{
      //parse options
        Options options = createCommandLineOptions();
        CommandLineParser parser = new PosixParser();
        CommandLine cmd=null;
        cmd = parser.parse( options, args);
        
        //parse cmd line args
        String fsName=null;
        String userAndGroup=null;
        String fileName=null;
        Long blockOffset=null;
        if(cmd.hasOption("h")){
            fsName = cmd.getOptionValue("h");
        }
        if(cmd.hasOption("u")){
            userAndGroup =cmd.getOptionValue("u");
        }
        if(cmd.hasOption("f")){
            fileName = cmd.getOptionValue("f");
        }
        if(cmd.hasOption("b")){
            blockOffset= Long.valueOf(cmd.getOptionValue("b"));
        }
        
        return  new FileBlockLocations(fsName, userAndGroup, fileName, blockOffset, writer);
        
    }
}
