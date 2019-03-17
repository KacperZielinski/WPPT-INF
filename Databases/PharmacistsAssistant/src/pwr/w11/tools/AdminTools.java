package pwr.w11.tools;

import javafx.scene.control.Alert;
import pwr.w11.GUI.PharmacistGUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kacper on 2018-01-11.
 */
public class AdminTools
{
	public static void BackupDB(String mysqldumpPath, String user, String pass, String db)
	{
		try
		{

        /*NOTE: Getting path to the Jar file being executed*/
        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
			CodeSource codeSource = PharmacistGUI.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();


        /*NOTE: Creating Database Constraints*/
			String dbUser = user;
			String dbPass = pass;
			String dbName = db;

        /*NOTE: Creating Path Constraints for folder saving*/
        /*NOTE: Here the backup folder is created for saving inside it*/
			String folderPath = jarDir + "\\backup";

        /*NOTE: Creating Folder if it does not exist*/
			File f1 = new File(folderPath);
			f1.mkdir();

        /*NOTE: Creating Path Constraints for backup saving*/
        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			String savePath = "\"" + jarDir + "\\backup\\" + "backup" + dateFormat.format(date)  + ".sql\"";
			System.out.println(savePath);

        /*NOTE: Used to create a cmd command*/
			String executeCmd;
        if(dbPass.isEmpty())
			executeCmd = mysqldumpPath + " -u " + dbUser + " --database " + dbName + " -r " + savePath;
        else
			executeCmd = mysqldumpPath + " -u " + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

        /*NOTE: Executing the command here*/
			Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
			if (processComplete == 0) {
				System.out.println("Backup Complete");
			} else {
				System.out.println("Backup Failure");
			}

		}
		catch (URISyntaxException | IOException | InterruptedException ex)
		{
			errorMessage("Backup error", "Can't create a backup :(", ex.getMessage());
		}
	}

	public static void RestoreDB(String mysqlPath, String filename, String user, String pass, String db)
	{
		try
		{
            /*NOTE: String s is the mysql file name including the .sql in its name*/
            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
			CodeSource codeSource = PharmacistGUI.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();

            /*NOTE: Creating Database Constraints*/
			String dbName = db;
			String dbUser = user;
			String dbPass = pass;

            /*NOTE: Creating Path Constraints for restoring*/
			String restorePath = filename;
//			String restorePath = jarDir + "\\backup" + "\\" + filename;

            /*NOTE: Used to create a cmd command*/
            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
			String executeCmd;
			if(dbPass.isEmpty())
//				executeCmd = new String[]{mysqldumpPath, dbName, "-u" + dbUser, "-e", " source " + restorePath};
//				executeCmd = mysqlPath + " -u " + dbUser + " < \"" + restorePath + "\"";
				//executeCmd = mysqlPath + " -u " + dbUser + " -e source \"" + restorePath + "\"";
				executeCmd = mysqlPath + " -u " + dbUser + " -e" + " \"source " + restorePath + "\"";
			else
				//executeCmd = mysqlPath + " -u " + dbUser + "-p" + dbPass + " < \"" + restorePath + "\"";
//				executeCmd = new String[]{mysqlPath, dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};
//				executeCmd = new String[]{mysqlPath, "--user=", dbUser, "--password=", dbPass, " -e ", "source " ,restorePath};
				executeCmd = mysqlPath + " -u " + dbUser + " -p " + dbPass+ " -e" + " \"source " + restorePath + "\"";

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
			Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
			if (processComplete == 0)
			{
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Success");
				alert.setContentText("Successfully restored from SQL : " + filename);
				alert.showAndWait();
			}
			else
				errorMessage("RestoreDB error", "Can't restore a backup :(","Process class error");

		}
		catch (URISyntaxException | IOException | InterruptedException | HeadlessException ex) {
			errorMessage("RestoreDB error", "Can't restore a backup :(", ex.getMessage());
		}
	}

	private static void errorMessage(String title, String headerText, String content)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);

		alert.showAndWait();
	}
}
