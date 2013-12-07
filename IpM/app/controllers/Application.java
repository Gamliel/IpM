package controllers;

import static play.data.Form.form;

import java.util.List;

import models.ServerData;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.showAllServerData;
import views.html.addServerDataForm;

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
}