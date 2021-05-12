/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;

class ServerFileSystem {

	public static void main(String args[]){ 
		try {
                        ArrayList<String> clientIDs = new ArrayList<String>();
			
			ServerSocket mySocket = new ServerSocket(5555);

			
			System.out.println("Startup the server side over port 5555 ....");

			
			Socket connectedClient = mySocket.accept();
			
			System.out.println("Connection established");

			// BufferReader object to read data coming from the client
			BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

			// PrintStream object to send data to the connected client
			PrintStream ps = new PrintStream(connectedClient.getOutputStream());

			
			String inputData;
                        
                        //Create a folder on the server where all the client's directories are 
                        String home = System.getProperty("user.home");
                        File serverFolder = new File(home + File.separator + "Documents" + File.separator + "FileSystem");
                        serverFolder.mkdir();
                        
                        /*
			while (!(inputData = br.readLine()).equals("exit")) {    
				
				System.out.println("received a message from client: " + inputData);   //print the incoming data from the client

				ps.println("Here is an acknowledgement from the server");              //respond back to the client
				
			}
                        */
                        String CID; 
                        String folderName;
                        
                        while (!(inputData = br.readLine()).equals("exit")) {    
				
                                
				System.out.println("received a message from client: " + inputData);   //print the incoming data from the client
                                
                                if(inputData.matches("NewClient .*"))
                                {
                                    if(clientIDs.contains(inputData.substring(10)))
                                    {
                                        ps.println("CID already exists");
                                    }
                                        
                                    else
                                    {
                                        clientIDs.add(inputData.substring(10));
                                        home = System.getProperty("user.home");
                                        File f = new File(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + inputData.substring(10));
                                        f.mkdir();
                                        ps.println("Welcome to our file system, you are added as a new client.");
                                    }
                                }
                                else if(inputData.matches("CreateDirectory .*"))
                                {
                                    CID = inputData.substring(inputData.indexOf(" ") +1, inputData.indexOf(";"));
                                    folderName = inputData.substring(inputData.indexOf(";")+2);
                                   
                                    
                                    if(!clientIDs.contains(CID))
                                    {
                                        ps.println("This CID does not exist yet.");
                                    }
                                    
                                    else
                                    {
                                        home = System.getProperty("user.home");
                                        File f = new File(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + CID + File.separator + folderName);
                                        f.mkdir();
                                        ps.println("The directory was successfully created.");
                                    }
                                    
                                }
                                else if(inputData.matches("MoveFile .*"))
                                {
                                    CID = inputData.substring(inputData.indexOf(" ") +1, inputData.indexOf(";"));
                                    String [] items = inputData.split(";");
                                    home = System.getProperty("user.home");
                                    File f = new File(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + CID + File.separator + items[1].substring(1));
                                    String [] path = items[items.length-1].split("/");
                                    String fileName = path[(path.length)-1];
                                    
                                    File userFile = new File(items[2].substring(1));
                                    
                                    if(!clientIDs.contains(CID))
                                    {
                                        ps.println("This CID does not exist yet.");
                                    }
                                    else if(!f.isDirectory())
                                    {
                                        ps.println("The directory does not exist for this client.");
                                    }
                                    else
                                    {
                                        Files.move(Paths.get(userFile + ""), Paths.get(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + CID + File.separator + items[1].substring(1) + File.separator + fileName), REPLACE_EXISTING);
                                        
                                        ps.println("The file is successfully moved under your directory");
                                    }
                                }
                                
                                else if(inputData.matches("DeleteFile .*"))
                                {
                                    CID = inputData.substring(inputData.indexOf(" ") + 1, inputData.indexOf(";"));
                                    String [] items = inputData.split(";");
                                    home = System.getProperty("user.home");
                                    File f = new File(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + CID + File.separator + items[1].substring(1));
                                    File deletedFile = new File(home + File.separator + "Documents" + File.separator + "FileSystem" + File.separator + CID + File.separator + items[1].substring(1) + File.separator + items[2].substring(1));
                                    
                                            if(!clientIDs.contains(CID))
                                    {
                                        ps.println("This CID does not exist yet.");
                                        
                                    }
                                    
                                    else if(!f.isDirectory())
                                    {
                                        ps.println("The directory does not exist for this client.");

                                    }
                                    
                                    else
                                    {
                                        deletedFile.delete();
                                        ps.println("The file was successfully removed from this directory.");
                                    }
                                }
                                else if(inputData.matches("Exit"))
                                {
                                    ps.println("Thanks for using our remote file system. Goodbye.");
                                    ps.close();
			            br.close();
			            mySocket.close();
			            connectedClient.close();
                                    
                                }
                                else
                                {
                                    ps.println("Not a valid Command. Try again.");
                                }
                                
                                				
			}
			
			System.out.println("Closing the connection and the sockets");

			// close the input/output streams and the created client/server sockets
			ps.close();
			br.close();
			mySocket.close();
			connectedClient.close();

		} catch (Exception exc) {
			System.out.println("Error :" + exc.toString());
		}

	}
}