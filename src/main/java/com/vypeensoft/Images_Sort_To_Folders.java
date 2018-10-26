//http://www.programcreek.com/2011/08/java-parse-xml-by-using-stax/
package com.vypeensoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.GZIPInputStream;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
//----------------------------------------------------------------------------------------------------------------------------- 
public class Images_Sort_To_Folders {
	private static List<String> sourceFileList = new ArrayList<String>(1000);
//----------------------------------------------------------------------------------------------------------------------------- 
	public static void main(String[] args) {
		try	{
			doIt(args);
		}catch (Throwable t){
			t.printStackTrace();
		}
	}
//----------------------------------------------------------------------------------------------------------------------------- 
	public static void doIt(String[] args) throws FileNotFoundException, IOException, XMLStreamException, FactoryConfigurationError {
		String userCurrentDir = System.getProperty("user.dir");
		System.out.println("REM - userCurrentDir = "+userCurrentDir);
		sourceFileList = getFileListFromFolder(userCurrentDir);
		for(int i=0;i<sourceFileList.size();i++) {
		  try {
			  String sourceFileFullPath = sourceFileList.get(i);
			  File sourceFile = new File(sourceFileFullPath);
			  File parentFolder = sourceFile.getParentFile();
			  String parentFolderName = parentFolder.getName();
			  String name = sourceFile.getName();
			  String dateStr ="";
			  if(name.matches("IMG[0-9]*\\.jpg")) {
				  dateStr = name.substring(3, 11);
			  }
			  if(name.matches("IMG[0-9]*\\_01.jpg")) {
				  dateStr = name.substring(3, 11);
			  }
			  if(name.matches("IMG_[0-9]{8}_[0-9]{6}\\.jpg")) {
				  dateStr = name.substring(4, 12);
			  }
			  if(name.matches("IMG_[0-9]{8}_[0-9]{6}_.*\\.jpg")) {
				  dateStr = name.substring(4, 12);
			  }
			  if(name.matches("VID[0-9]*\\.mp4")) {
				  dateStr = name.substring(3, 11);
			  }
			  if(name.matches("VID_[0-9]*\\.mp4")) {
				  dateStr = name.substring(4, 12);
			  }
			  if(dateStr.equals("")) {
				System.out.println("Unable to determine date : "+name);
				  continue;
			  }
			  if(dateStr.length() == 8) {
				  StringBuffer dateStrSB = new StringBuffer(dateStr);
				  dateStrSB.insert(6,"-");
				  dateStrSB.insert(4,"-");
				  //System.out.println("dateStrSB="+dateStrSB);
				  dateStr = dateStrSB.toString();
			  }
			  //System.out.println("parentFolderName="+parentFolderName);
			  
			  if(parentFolderName.contains(dateStr)) {
				  //file is already in date named folder
			  } else {
				  System.out.println("name = "+name );
				  System.out.println("dateStr="+dateStr);
				  File newFolder = new File(parentFolder.getAbsolutePath() + "\\" + dateStr);
				  //System.out.println("newFolder="+newFolder);
				  newFolder.mkdirs();
				  File destFile = new File(newFolder.getAbsolutePath() + "\\" + name);
				  System.out.println("destFile="+destFile);
				  sourceFile.renameTo(destFile);
			  }
			  //-------------------------------------
			  System.out.println("--------------------------------------------------");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
        }
	}
//----------------------------------------------------------------------------------------------------------------------------- 
  /******************************************************************************************/
    public static List<String> getFileListFromFolder(String sourcePath) {
        //System.out.println(sourcePath);
        File dir = new File(sourcePath);
        if(!dir.exists()) {
            return new ArrayList<String>();
        }
        List<String> fileTree = new ArrayList<String>(100);
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                //System.out.println(entry);
                fileTree.add(entry.getAbsolutePath());
            } else {
                try
                {
                    fileTree.addAll(getFileListFromFolder(entry.getAbsolutePath()));
                }
                catch (Exception e)
                {
                }
            }
        }
        return fileTree;
    }
  /******************************************************************************************/
}
