package bot_telegram_for_organize_football.bot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Organize5FootballBot extends TelegramLongPollingBot{

	private Map<Long,Set<String>> match;
	
	public String getBotUsername() {
		return "Organize5FootballBot";
	}

	public void onUpdateReceived(Update update) {
		System.out.println("prova");
			
		if(update.hasMessage() && update.getMessage().hasText()) {
			String message_text= update.getMessage().getText();
			long chat_id= update.getMessage().getChatId();
			
			
			User user = update.getMessage().getFrom();
//			System.out.println(aux.getFirstName());
//			System.out.println(aux.getLastName());
//			System.out.println(aux.toString());
//			System.out.println(aux.getUserName());
			
			SendMessage message= new SendMessage();
			
			/*case booking of a person*/
			if(message_text.contains("ci sono") || message_text.contains("presente")) {
				Set<String> current_match= new HashSet<String>();
				current_match=this.match.get(chat_id);
				if(!current_match.contains(user.getUserName())) {
					current_match.add(user.getUserName());
					if(current_match.size()==10) {
						message.setChatId(chat_id);
						message.setText("Formazione al completo");
					}
						
				}
				
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

	@Override
	public String getBotToken() {
		/*token of the bot is in the private class*/
		return AuxiliarClass.token;
	}

}
