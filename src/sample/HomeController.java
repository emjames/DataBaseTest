package sample;

//TODO add the derby server stuff
// https://builds.apache.org/job/Derby-docs/lastSuccessfulBuild/artifact/trunk/out/getstart/index.html
//       http://db.apache.org/derby/papers/DerbyTut/embedded_intro.html
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by A on 2015/1/17.
 */
public class HomeController {
    public Button btnOpenFile;
    private Scene scene;

    //Database stuff
    private static final String dir = System.getProperty("user.home") + "/JavaFXDatabase/";
    private static final String dbURL = "jdbc:derby:";
    private static final String dbDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String user = "app";
    private static final String password = "app";
    private static final List<File> dummyFileList = new ArrayList<File>();


    public HomeController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent parent = fxmlLoader.load();
            scene = new Scene(parent, 1050, 630);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    //When the open file button is pressed
    protected void handleSubmitButtonAction() throws SQLException {
        //Create a file chooser
        FileChooser fileChooser = new FileChooser();
        List<File> filesToOpen = fileChooser.showOpenMultipleDialog(null);

        for (int i = 0; i < filesToOpen.size(); i++) {
            //System.out.println(filesToOpen.get(i));
            System.out.println(filesToOpen.get(i).getAbsolutePath());
            System.out.println(filesToOpen.get(i).getName());
            System.out.println(filesToOpen.get(i).hashCode());
        }
        runServer();
    }

    public void runServer() throws SQLException {
        System.out.println("JavaFXDB - " + dir +")");

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //Load Driver
            Class.forName(dbDriver).newInstance();
        } catch (Exception e){
            System.err.println("ERROR: Could not load driver!");
            e.printStackTrace(System.err);
        }
        //Test the connection
        Connection connection = DriverManager.getConnection(dbURL + "derbyDB;create=true", user, password);
        try {
            //do something here
            createFileTable(connection);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        connection.close();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, user, password);
    }
    private void createFileTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE FILES(" +
                            "ID int," +
                            "NAME varchar(30)," +
                            "TAG varchar(30),");
    }

    public void dropFileTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE FILES");
        connection.close();
    }

    public void deleteAllFiles() throws Exception {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        statement.execute("DELETE FROM FILES");
        con.close();
    }

    public void deleteFile(File file) throws Exception {
        Connection con = getConnection();
        int fileID;
        //get the id from the database
        PreparedStatement preparedStatement = con.prepareStatement("SELECT *" +
                "FROM FILES" +
                "WHERE NAME=?");
        preparedStatement.setString(1, file.getName());
        fileID = preparedStatement.executeUpdate();

        PreparedStatement prepStatement = con.prepareStatement(
                "DELETE FROM FILES WHERE ID=? AND \"NAME\"=?"
        );
        prepStatement.setInt(1, fileID);
        prepStatement.setString(2, file.getName());
        prepStatement.executeUpdate();
        con.close();
    }

    public void populateDummyData() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "INSERT INTO FILES(ID, \"NAME\", TAG) VALUES(?,?,?");

        for (File file : dummyFileList){
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, "to read");
            preparedStatement.executeUpdate();
        }
    }
    public void redirectHome(Stage stage){
        stage.setTitle("TaggR");
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
}
