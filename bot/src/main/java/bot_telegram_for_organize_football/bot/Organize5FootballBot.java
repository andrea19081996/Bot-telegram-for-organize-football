package bot_telegram_for_organize_football.bot;

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
	
	public String getBotUsername() {
		return "Organize5FootballBot";
	}
	
	public void onUpdateReceived(Update update) {
		System.out.println("prova");
			
		if(update.hasMessage() && update.getMessage().hasText()) {
			String message_text= update.getMessage().getText();
			long chat_id= update.getMessage().getChatId();
			
			
			String user = update.getMessage().getFrom().getUserName();

			SendMessage message= new SendMessage();
			message.setChatId(chat_id);
			
			Set<String> current_match= new HashSet<String>();
			current_match=match.get(chat_id);
			
			/*case booking of a person*/
			if(message_text.contains("ci sono") || message_text.contains("presente")) {
				booking(current_match, message, user, this.match);
				
				try {
					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if(message_text.equals("/list")) {
				message.setText(list_person_match(current_match));
				
				try {
					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if(message_text.equals("/day_time")) { 
				
				message.setText("Scegli un giorno e un orario in cui far svolgere la partita");
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

	public static void booking(Set<String> current_match, SendMessage message, String user, Map<Long,Set<String>> match) {
		if(!current_match.contains(user) && current_match.size()<10) {
			current_match.add(user);
			if(current_match.size()==10) {
				String match_person = "Partità per GIORNO alle ore XX: \n";
				String list_person_match= list_person_match(current_match);
				match_person= match_person.concat(list_person_match);
				match_person= match_person.concat("\nFormazione al completo");
				message.setText(match_person);
			}
				
		} else {
			if(current_match.contains(user))
				message.setText(user + " già sei nella lista");
			else
				message.setText(user + " per la partita già sono 10 sarà per la prossima");
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
	
	
	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
