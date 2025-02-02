import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "C:\\Users\\abbyc\\Documents\\Development Projects\\Backend Projects\\CLI Task Tracker\\resources\\Tasks.json";

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Task>>() {}.getType();

        // read tasks from file if file already exists
        JsonParser parser = new JsonParser();
        Object obj=parser.parse(new FileReader(fileName));
        List<Task> listOfTask= new ArrayList<>(); ;
        if(!obj.getClass().equals(JsonNull.class)) {
            JsonArray jsonArray = (JsonArray) obj;
            listOfTask = gson.fromJson(jsonArray, listType);
        }



        // check for arguments in CLI
        if(args.length>0){
            String command = args[1];
            switch(command){
                case "add":
                    Task task = new Task();
                    if(listOfTask.isEmpty()){
                        task.setId(1);
                    }else{
                        task.setId(listOfTask.size()+2);
                    }
                    task.setDesc(args[2]);
                    Date date = new Date();
                    task.setCreatedAt(date);
                    task.setUpdatedAt(date);
                    task.setStatus("Todo");
                    listOfTask.add(task);
                    System.out.println("Created new Task!");
                    break;
                case "update":
                    if(listOfTask.isEmpty()){
                        System.out.println("Nothing to update!");
                    }else{
                        boolean hasTask = false;
                        for(Task task1:listOfTask){
                            if(task1.getId()==Integer.parseInt(args[2])){
                                task1.setDesc(args[3]);
                                System.out.println("Updated Task!");
                                hasTask=true;
                            }
                        }
                        if(!hasTask){
                            System.out.println("Invalid Task ID!");
                        }
                    }
                    break;
                case "delete":
                    if(listOfTask.isEmpty()){
                        System.out.println("Nothing to delete!");
                    }else{
                        boolean hasTask = false;
                        for(Task task1:listOfTask){
                            if(task1.getId()==Integer.parseInt(args[2])){
                                listOfTask.remove(task1);
                                System.out.println("Deleted Task!");
                                hasTask=true;
                            }
                        }
                        if(!hasTask){
                            System.out.println("Invalid Task ID!");
                        }
                    }
                    break;
                case "mark-in-progress":
                    if(listOfTask.isEmpty()){
                        System.out.println("Nothing to mark!");
                    }else{
                        boolean hasTask = false;
                        for(Task task1:listOfTask){
                            if(task1.getId()==Integer.parseInt(args[2])){
                                task1.setStatus("In-Progress");
                                System.out.println("Marked Task as In-Progress!");
                                hasTask=true;
                            }
                        }
                        if(!hasTask){
                            System.out.println("Invalid Task ID!");
                        }
                    }
                    break;
                case "mark-done":
                    if(listOfTask.isEmpty()){
                        System.out.println("Nothing to mark!");
                    }else {
                        boolean hasTask = false;
                        for (Task task1 : listOfTask) {
                            if (task1.getId() == Integer.parseInt(args[2])) {
                                task1.setStatus("Done");
                                System.out.println("Marked Task as Done!");
                                hasTask = true;
                            }
                        }
                        if (!hasTask) {
                            System.out.println("Invalid Task ID!");
                        }
                    }
                    break;
                case "list":
                    if(args.length==3){
                       if("in-progress".equalsIgnoreCase(args[2])){
                            printAllTasks(listOfTask,"in-progress");
                        }else if("done".equalsIgnoreCase(args[2])){
                            printAllTasks(listOfTask,"done");
                        }else if("to-do".equalsIgnoreCase(args[2])){
                            printAllTasks(listOfTask,"to-do");
                        }
                    }
                    else{
                        printAllTasks(listOfTask,"all");
                    }

                    break;
                default:
                    System.out.println("Invalid Command");
                    break;
            }
        }else{
            System.out.println("No input given!!!");
        }

        //write tasks to json file
        String json = new Gson().toJson(listOfTask);

        try{
            PrintStream out = new PrintStream(new FileOutputStream(fileName));
            out.print(json);
        }catch (FileNotFoundException ex){
            System.out.println("No such file");
        }

    }

    private static void printAllTasks(List<Task> listOfTask, String status) {
        if(listOfTask.isEmpty()){
            System.out.println("Empty List");
        }else{
            for(Task task: listOfTask){
                if("all".equalsIgnoreCase(status))
                    System.out.println("Task:" + task.getDesc() + "\nStatus: " + task.getStatus());
                else if("in-progress".equalsIgnoreCase(status)){
                    if(task.getStatus().equalsIgnoreCase("in-progress")){
                        System.out.println("Task:" + task.getDesc() + "\nStatus: " + task.getStatus());
                    }
                }else if("to-do".equalsIgnoreCase(status)){
                    if(task.getStatus().equalsIgnoreCase("to-do")){
                        System.out.println("Task:" + task.getDesc() + "\nStatus: " + task.getStatus());
                    }
                }else if("done".equalsIgnoreCase(status)){
                    if(task.getStatus().equalsIgnoreCase("done")){
                        System.out.println("Task:" + task.getDesc() + "\nStatus: " + task.getStatus());
                    }
                }
            }
        }
    }
}