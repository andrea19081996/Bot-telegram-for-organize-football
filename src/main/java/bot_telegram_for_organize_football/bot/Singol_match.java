package bot_telegram_for_organize_football.bot;

public class Singol_match {
	private String date;
	private String date_time;
	
	public Singol_match(String date, String date_time) {
		this.date=date;
		this.date_time=date_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	
	public void clear() {
		this.date=null;
		this.date_time=null;
	}
	
	
}