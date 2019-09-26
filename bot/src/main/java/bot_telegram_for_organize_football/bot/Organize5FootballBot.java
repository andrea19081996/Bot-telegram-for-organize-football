package bot_telegram_for_organize_football.bot;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Organize5FootballBot extends TelegramLongPollingBot{

	private Map<Long,Set<String>> match= new HashMap<Long, Set<String>>();
	private Map<Long,Singol_match> setting_match = new HashMap<Long, Singol_match>() ; 
	
	public String getBotUsername() {
		return "Organize5FootballBot";
	}
	
	public void onUpdateReceived(Update update) {
		System.out.println("prova");
			
		if(update.hasMessage() && update.getMessage().hasText()) {
			
			String message_text= update.getMessage().getText();
			long chat_id= update.getMessage().getChatId();
			
			clean_data(chat_id);

			
			String user = update.getMessage().getFrom().getUserName();

			SendMessage message= new SendMessage();
			message.setChatId(chat_id);
			
			Set<String> current_match=match.get(chat_id);
			if (current_match==null) {
				current_match= new HashSet<String>();
			}
			
			if(is_a_date(message_text)) {
				Singol_match singol= setting_match.get(chat_id);
				Calendar calendar= Calendar.getInstance();
				System.out.println(calendar.toString());
				Date date = new Date();
				if(singol==null)
					singol= new Singol_match(date, message_text);
				else {
					singol.setDate(date);
					singol.setDate_time(message_text);
				}
				this.setting_match.put(chat_id, singol);
				message.setText("Impostata partita per " + message_text);
				execution(message);
			
//			} else if(this.setting_match.get(chat_id)==null) {
//				
//				message.setText("Prima di inserire persone nella partita bisogna scegliere un giorno per la partita\nEsempio Lunedì 19:00");
//				execution(message);
				
			/*case booking of a person*/
			} else if(message_text.contains("ci sono") || message_text.contains("presente")) {
				if(this.setting_match.get(chat_id)==null) {
					message.setText("Prima di inserire persone nella partita bisogna scegliere un giorno per la partita\nEsempio Lunedì 19:00");
					execution(message);
				}else {
					booking(current_match, message, user, this.match, this.setting_match.get(chat_id).getDate_time());
					execution(message);
				}
				
			} else if (message_text.contains("non ci sono")) {
				if(this.setting_match.get(chat_id)==null) {
					message.setText("Prima di inserire persone nella partita bisogna scegliere un giorno per la partita\nEsempio Lunedì 19:00");
					execution(message);
				} else {
					delete(current_match, message, user, this.match, this.setting_match.get(chat_id).getDate_time());
					execution(message);
				}
			} else if(message_text.equals("/list")) {
				
				message.setText(list_person_match(current_match));
				execution(message);
				
			} else if(message_text.equals("/day_time")) { 
				
				message.setText("Scegli un giorno e un orario in cui far svolgere la partita\n Scrivere GIORNO e ORA per impostare correttamente la partita\n Esempio: Lunedì 19:00");
				execution(message);
				
			} 
			
//			message.setChatId(chat_id);
//			message.setText("prova");
			
			
//			try {
//				execute(message);
//			} catch (TelegramApiException e) {
//				e.printStackTrace();
//			}
			
		} 
		
	}

	public static void booking(Set<String> current_match, SendMessage message, String user, Map<Long,Set<String>> match, String date) {
		if(!current_match.contains(user) && current_match.size()<10) {
			current_match.add(user);
			String[] date_array= date.split(" ");
			String match_person = "Partita per "+date_array[0]+" alle ore"+date_array[1]+": \n";
			String list_person_match= list_person_match(current_match);
			match_person= match_person.concat(list_person_match);
			if(current_match.size()==10) {
				match_person= match_person.concat("\nFormazione al completo");

			}
			message.setText(match_person);
		} else {
			if(current_match.contains(user))
				message.setText(user + " già sei nella lista");
			else
				message.setText(user + " per la partita già sono 10 sarà per la prossima");
		}
	}
	
	
	public void delete(Set<String> current_match, SendMessage message, String user, Map<Long,Set<String>> match, String date) {
		
		if(current_match.contains(user)) {
			current_match.remove(user);
				String[] date_array= date.split(" ");
				String match_person = "Partita per "+date_array[0]+" alle ore"+date_array[1]+": \n";
				String list_person_match= list_person_match(current_match);
				match_person= match_person.concat(list_person_match);
				message.setText(match_person);
				
		} else {
			if(!current_match.contains(user))
				message.setText(user + " non sei nella lista");
		}
	}
	
	
	/*returns the list of person in the current_match*/
	public static String list_person_match(Set<String> current_match) {
		String list_person_match;
		Iterator<String> i= current_match.iterator();
		if(i.hasNext()) {
			list_person_match=i.next().concat("\n");
			while(i.hasNext()) {
				list_person_match= list_person_match.concat(i.next()+ "\n");
			}
			return list_person_match;
		}
		return "Non c'è ancora nessuno per la partita";
	}
	
	/*used for clean the old data*/
	public void clean_data(long chat_id) {
		boolean i;
		Date now= new Date();
		Calendar calendar1= Calendar.getInstance();
		calendar1.setTime(now);
		if(this.setting_match.get(chat_id)==null)
			i=false;
		else {
			Date old= this.setting_match.get(chat_id).getDate();
			Calendar calendar2= Calendar.getInstance();
			calendar2.setTime(old);
			calendar2.add(Calendar.WEEK_OF_YEAR, +1);
			
			i= ((calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) && (calendar1.get(Calendar.WEEK_OF_YEAR) > calendar2.get(Calendar.WEEK_OF_YEAR)) || (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) && (calendar1.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR)) && (calendar1.get(Calendar.DAY_OF_YEAR) > calendar2.get(Calendar.DAY_OF_YEAR)) || (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)));
			
			if (i==true) {
				if(this.match.get(chat_id)!=null)
					this.match.get(chat_id).clear();
				this.setting_match.get(chat_id).clear();
			}
		}
		
	}
	
	
	public void execution(SendMessage message) {
		
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean is_a_date(String message) {
		String[] array_message= message.split(" ");
		if (array_message[0]== null)
			return false;
		
		if (is_a_day(array_message[0]) && is_a_time(array_message[1])) {
			return true;
		}
		return false;
		
	}
	
	
	public boolean is_a_day(String day) {
		day=day.toLowerCase();
		if (day.equals("lunedì") || day.equals("martedì") || day.equals("mercoledì") || day.equals("giovedì") || day.equals("venerdì") || day.equals("sabato") || day.equals("domenica"))
			return true;
		else 
			return false;
	}
	
	public boolean is_a_time(String time) {
		time=time.replace(" ", "");
		String[] array_time= time.split(":");
		int hours= Integer.parseInt(array_time[0]);
		int minute= Integer.parseInt(array_time[1]);
		
		if (hours<24 && hours>=00 && minute>=00 && minute<60)
			return true;
		else
			return false;
		
	}
	
	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
