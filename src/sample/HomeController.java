package sample;

//TODO add the derby server stuff
// https://builds.apache.org/job/Derby-docs/lastSuccessfulBuild/artifact/trunk/out/getstart/index.html
//       http://db.apache.org/derby/papers/DerbyTut/embedded_intro.html
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.table.TableColumn;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2015/1/17.
 */
public class HomeController {
    public Button btnOpenFile;
    public TableView tableView;
    private Scene scene;

    //Database stuff
    //private static final String dir = System.getProperty("user.home" + "/JavaFXDatabase/");
    private static final String dbDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String dbURL = "jdbc:derby:TaggR";
//    private static final Connection connection= DriverManager.getConnection(dbURL);
    private static Connection connection = null;
    private static Statement statement = null;
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
        try {
            createTagFileTable();
        } catch (SQLException e) {
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
            //add file one by one in loop
            addFile(filesToOpen.get(i));
            System.out.println(filesToOpen.get(i).getAbsolutePath());
            System.out.println(filesToOpen.get(i).getName());
            System.out.println(filesToOpen.get(i).hashCode());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, user, password);
    }
    private void createTagFileTable() throws SQLException {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbURL + ";create=true");
            System.out.println("connection created");
            statement = connection.createStatement();
            statement.execute("CREATE TABLE FILES (Fhash INT, FName VARCHAR(20), FLocation VARCHAR (50))");
            statement.execute("CREATE TABLE TAGS(Fhash INT, TName VARCHAR(20))");
            System.out.println("Table created.");
        } catch (SQLException e){
            if(DerbyUtils.tableAlreadyExists(e)){
                System.out.println("Table already exists.");
            }else{
                e.printStackTrace();
            }
        }
        statement.close();
        connection.close();
        System.out.println("connection closed");
    }

    public void dropFileTable() throws SQLException {
        connection = getConnection();
        statement = connection.createStatement();
        statement.execute("DROP TABLE FILES");
        connection.close();
    }

    public void deleteAllFiles() throws Exception {
        connection = getConnection();
        statement = connection.createStatement();
        statement.execute("DELETE * FROM FILES");
        connection.close();
    }

    public void deleteFile(File file) throws Exception {
        connection = getConnection();
        statement = connection.createStatement();
        int fileID;
        //get the id from the database
    }

    public void populateDummyData() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "INSERT INTO FILES(ID, \"NAME\", TAG) VALUES(?,?,?");

        for (File file : dummyFileList){
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, "to read");
            preparedStatement.executeUpdate();
        }
    }

    public void addFile(File fileToInsert) throws SQLException {
    //Add new files received from the open dialog
        //System.out.println(filesToOpen.get(i).getAbsolutePath());
        //System.out.println(filesToOpen.get(i).getName());
        //System.out.println(filesToOpen.get(i).hashCode());
        ResultSet resultSet;
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbURL);
            System.out.println("connection created");
            statement = connection.createStatement();
            statement.execute("INSERT INTO files VALUES " +
                                "(" + fileToInsert.hashCode() + ", '" +
                                fileToInsert.getName() + "', '" +
                                fileToInsert.getAbsolutePath() + "')");
            System.out.println("Inserted new file.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        statement.close();
        connection.close();
        System.out.println("connection closed");
    }

    public void buildData() throws SQLException {
        connection = DriverManager.getConnection(dbURL);
        System.out.println("connection created");
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM files");

        /*
        table column added dynamically
         */

    }

    public void redirectHome(Stage stage){
        stage.setTitle("TaggR");
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }
}
