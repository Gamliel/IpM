package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@SuppressWarnings("serial")
@Entity
public class ServerData extends Model{

	@Id
	public String id;
	
	@Required
	public String conventionalName;
	
	@Required
	public String hostName;
	
	@Required
	public String domain;
	
	@Required
	@Min(0)
	@Max(65535)
	@NotNull
	public Integer port;
	
	@Required
	public String ipAddress;

	public static Model.Finder<String, ServerData> find = new Model.Finder<String, ServerData>(String.class, ServerData.class);
	
}