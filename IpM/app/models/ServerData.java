package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@SuppressWarnings("serial")
@Entity
public class ServerData extends Model{

	@Id
	public String Id;
	
	@Required
	public String hostname;
	
	@Required
	public String domain;
	
	@Required
	@Min(0)
	@Max(65535)
	@Pattern("\\d")
	public Integer port;
	
	@Required
	public String ipAddress;
	
	public static Model.Finder<String, ServerData> find = new Model.Finder<String, ServerData>(String.class, ServerData.class);
	
}