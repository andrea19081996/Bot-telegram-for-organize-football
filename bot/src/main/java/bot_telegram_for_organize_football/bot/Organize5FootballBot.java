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
//			System.out.println(aux.getFirstName());
//			System.out.println(aux.getLastName());
//			System.out.println(aux.toString());
//			System.out.println(aux.getUserName());
			
			SendMessage message= new SendMessage();
			message.setChatId(chat_id);
			
			/*case booking of a person*/
			if(message_text.contains("ci sono") || message_text.contains("presente")) {
				booking(chat_id, message, user, this.match);
			}
			
//			message.setChatId(chat_id);
//			message.setText("prova");
			
			
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void booking(long chat_id, SendMessage message, String user, Map<Long,Set<String>> match) {
		Set<String> current_match= new HashSet<String>();
		current_match=match.get(chat_id);
		if(!current_match.contains(user) && current_match.size()<10) {
			current_match.add(user);
			if(current_match.size()==10) {
				String match_person = "Partità per GIORNO alle ore XX: \n";
				Iterator<String> i= current_match.iterator();
				while(i.hasNext()) {
					match_person= match_person.concat(i.next()+ "\n");
				}
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
	
	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
