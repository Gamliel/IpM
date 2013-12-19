package controllers;

import static play.data.Form.form;
import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.List;

import models.ServerData;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addServerDataForm;
import views.html.index;
import views.html.showAllServerData;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result addServerData() {
        Form<ServerData> formData = form(ServerData.class).bindFromRequest();
        ServerData serverData = formData.get();
        serverData.save();
        return redirect(routes.Application.addServerDataForm());
    }
    
    public static Result addServerDataForm() {
        return ok(addServerDataForm.render(""));
    }
    
    public static Result showAllServerData(){
    	List<ServerData> allServerData = ServerData.find.all();
		return ok(showAllServerData.render(allServerData));
    }
    
    public static Result deleteServerData(String id){
    	ServerData serverData= ServerData.find.where().idEq(id).findUnique();
    	serverData.delete();    	
    	return redirect(routes.Application.showAllServerData());
    }
    
    public static Result searchServerData(String conventionalName){
    	List<ServerData> serverData= null;
    	if (conventionalName != null){
    		serverData = new ArrayList<ServerData>();
    		serverData.add(ServerData.find.where().eq("conventionalName", conventionalName).findUnique());
    	} else {
    		serverData = ServerData.find.all();
    	}
    	if (serverData == null) {
    		return ok(toJson(""));
    	}
    	return ok(toJson(serverData));
    }
    
}